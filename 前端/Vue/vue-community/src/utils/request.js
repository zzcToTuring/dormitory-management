import axios from 'axios'
import ElementUI from 'element-ui';
import router from "@/router";

const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let token = localStorage.getItem("token") ? JSON.parse(localStorage.getItem('token')) : null
    if (token){
        config.headers['token'] = token;  // 设置请求头
    }

    return config.headers
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        let status = response.data.status;
        let msg = response.data.msg;
        if (status === 401){
            console.log(msg)
            router.push("/login")
        }
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return null;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request