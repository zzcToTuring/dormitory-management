<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card  style = "color: #3F5EFB">
          <div>学生总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{xxx}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style = "color: #3F5EFB">访客数量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{xxx}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style = "color: #3F5EFB">标记学生数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{xxx}}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style = "color: #3F5EFB">寝室数量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            <span>{{xxx}}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width:500px; height: 350px"></div>
      </el-col>
      <el-col  :span="12">
        <div id="pie" style="width: 500px; height: 300px"></div>
      </el-col>
      <el-col  :span="24">
        <div id="category" style="width: 100%; height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: "Home",
  data() {
    return{
      visitorNumber:"",
      addressNumber:"",
      hightNumber:"",
      residentNumber:"",

    }
  },
  created() {
    this.request.get("/echarts/?").then(res => {
      this.residentNumber =res.data[0]
      this.visitorNumber =res.data[1]
      this.hightNumber =res.data[2]
      this.addressNumber =res.data[3]
    })
  },
  mounted() {
    var option;
    var pieOption;
    var dataOption;


    option = {
      title: {
        text: '统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name:"入库学生数量",
          data: [],
          type: 'line'
        },
        {
          name:"入库学生数量",
          data: [],
          type: 'bar'
        },
        {
          name:"访客数量",
          data: [],
          type: 'line'
        },
        {
          name:"访客数量",
          data: [],
          type: 'bar'
        }
      ]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    this.request.get("/echarts/members").then(res => {
      // 填空
      option.series[0].data = res.data
      option.series[1].data = res.data
      // 数据准备完毕之后再set
      myChart.setOption(option);

    })
    this.request.get("/echarts/vis").then(res => {
      // 填空
      option.series[2].data = res.data
      option.series[3].data = res.data
      // 数据准备完毕之后再set
      myChart.setOption(option);

    })

    //饼图
    pieOption = {
      title: {
        text: '学生管理情况概要',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '60%',
          label:{            //饼图图形上的文本标签
            normal:{
              show:true,
              position:'inner', //标签的位置
              textStyle : {
                fontWeight : 300 ,
                fontSize : 14,    //文字的字体大小
                color: "#fff"
              },
              formatter:'{d}%'
            }
          },
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var PieDom = document.getElementById('pie');
    var PieChart = echarts.init(PieDom);
    this.request.get("/echarts/members").then(res => {
      pieOption.series[0].data = [
        {name: "正常学生", value: res.data[0]},
        {name: "标记学生", value: res.data[1]},
      ]
      PieChart.setOption(pieOption)
    })


    //学生电量使用图
    dataOption={
      title: {
        text: '寝室用电监控图',
        subtext: 'Fake'
      },
      tooltip: {
        trigger: 'axis',
            axisPointer: {
          type: 'cross'
        }
      },
      toolbox: {
        show: true,
            feature: {
          saveAsImage: {}
        }
      },
      xAxis: {
        type: 'category',
            boundaryGap: false,
            // prettier-ignore
            data: ['00:00', '01:15', '02:30', '03:45', '05:00', '06:15', '07:30', '08:45', '10:00', '11:15', '12:30', '13:45', '15:00', '16:15', '17:30', '18:45', '20:00', '21:15', '22:30', '23:45']
      },
      yAxis: {
        type: 'value',
            axisLabel: {
          formatter: '{value} W'
        },
        axisPointer: {
          snap: true
        }
      },
      visualMap: {
        show: false,
            dimension: 0,
            pieces: [
          {
            lte: 6,
            color: 'green'
          },
          {
            gt: 6,
            lte: 8,
            color: 'red'
          },
          {
            gt: 8,
            lte: 14,
            color: 'green'
          },
          {
            gt: 14,
            lte: 17,
            color: 'red'
          },
          {
            gt: 17,
            color: 'green'
          }
        ]
      },
      series: [
        {
          name: 'Electricity',
          type: 'line',
          smooth: true,
          data: [300, 280, 250, 260, 270, 300, 550, 500, 400, 390, 380, 390, 400, 500, 600, 750, 800, 700, 600, 400],
          markArea: {
            itemStyle: {
              color: 'rgba(255, 173, 177, 0.4)'
            },
            data: [
              [
                {
                  name: 'Morning Peak',
                  xAxis: '07:30'
                },
                {
                  xAxis: '10:00'
                }
              ],
              [
                {
                  name: 'Evening Peak',
                  xAxis: '17:30'
                },
                {
                  xAxis: '21:15'
                }
              ]
            ]
          }
        }
      ]
    };
    var categoryDom =document.getElementById("category");
    var cateChart=echarts.init(categoryDom);
    cateChart.setOption(dataOption)

  }
}
</script>

<style scoped>

</style>
