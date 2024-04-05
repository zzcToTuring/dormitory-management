## 项目简介
**关键字**：寝室管理系统 前后端分离<br />该项目为本人的毕业设计，论文题目为：基于**web**的学生管理系统，可用于毕业设计、大作业、课程设计等.


## 语言&框架

- [x] 采用B/S前后端分离模式，开发平台采用java(jdk8),数据库采用mysql(8.0)，开发平台使用idea
- [x] 前端除了基础的HTML+css+js，还使用vue2+elementUI，jQuery，bootstrap等其他开源库
- [x] 后端使用springboot+springMVC，设计到的技术有mybatisplus，redis缓存，单点登录，jwt加密，网关，第三方登录等等功能
- [x] 详细的技术工具如下图所示：

![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/b4517704-35a3-48af-b05e-3cd8b0144bcf#id=M2hwf&originHeight=691&originWidth=1338&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)



## 功能介绍

### 模块概述：

- 作为管理系统的话，存在管理系统的基本功能，**增加、修改、查找与删除（学生信息、寝室信息）**，均在不同的模块中有展示，其中查找可根据姓名，宿舍号等信息进行进一步的筛选
- **标记学生**功能。可对学生进行标记，在不同的栏位进行管理，标记项可自定义选择
- 角色管理，分为**超级管理员(系统级管理员)，普通管理员(校级管理员)，普通用户(宿管)**，不同的用户拥有的权限不同，最大的区别在于管理员拥有创建角色和删除角色功能
- 注册管理。严格采用**一个注册码一个用户**的策略，确保系统安全，系统操作有据可查
- 辅助功能，其中包括报修，来访人员管理，通知管理等其他**辅助功能**
- 系统首页**概览**，可通过首页的统计图查看系统大概的信息
- 垃圾回收站。所有被**删除均为软删除**(除了超级管理员数据库中直接删除)，删除信息均能还原，确保信息安全，且根据权限不同还能在数据index页面展示数据
- ……
### 功能图标：

![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/ee0d7ccc-4887-49ee-a7b0-838bd6b18609#id=XkG1k&originHeight=828&originWidth=1453&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/578194c6-d240-447d-a0b3-6c58ea33d116#id=huX93&originHeight=457&originWidth=720&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/5c2f047e-f51a-4f31-a133-9411ed6ad9ee#id=RUQFC&originHeight=687&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)


## 项目效果展示

> (应该能看出各个图片说明了啥，就不进行额外说明了)


---

<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/056aa4df-5aea-406c-8547-84135b9d3e7e#id=W9Y2t&originHeight=342&originWidth=783&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/bbf1067b-6ff6-4af4-b684-6454915de7f5#id=CoW2z&originHeight=464&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/13804f17-3c8f-4398-bb2a-e8bd4dee93ff#id=c7yOU&originHeight=481&originWidth=773&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/ab5e283d-6b03-410a-8a29-f3c67ff4bac2#id=KJqcY&originHeight=453&originWidth=844&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/08050828-21a4-4020-8abd-0a002b0ee4a1#id=wXqoA&originHeight=433&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/9e83fc26-8454-4696-b2a7-efc470435f38#id=Xh29w&originHeight=411&originWidth=822&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/899cb775-0bac-499d-8cc3-f99714439ae0#id=LDwDK&originHeight=450&originWidth=838&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/e8b9adef-2d08-480c-b909-5421fd86bee6#id=MmKxt&originHeight=464&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/440a0a06-50af-464e-a792-4ff9b53452fe#id=V01vn&originHeight=462&originWidth=865&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)<br />![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/0a24cba0-d60e-4a9d-95fb-4e2fb8e94c69#id=IEiXw&originHeight=417&originWidth=776&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)


## 其他说明


### 关于成绩

盲审成绩为良(本科....运气不好被抽到盲审了)，论文成绩为优，答辩成绩为良，最后综合成绩为良好

### 关于查重率

在github上面找应该不太关注这个东西吧(主要还是看项目)..查重率20%，没办法学校有自建库，往年学长的论文都会被收入，这又是一个烂大街的题目

![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/4584166d-3c61-454e-8190-573f1821b0fb#id=G7qXg&originHeight=938&originWidth=1910&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)


### 关于项目其他说明

- 🙄计算机软件项目，说完全原创是不可能的，**通过他人开源的模板进行修改成现有项目，在工具自己的任务书要求，添加了一些新的功能上去**
- 😁寝室管理项目大多数都是.jsp的老项目，现在还用这种项目的懂的都懂，而且维普查重能红一片，所以就自己改了一个这样的项目
- 😅项目中存在着一些bug，一些报错直接用try catch包围，不让他展示出来（**非**完美项目），或者有些直接删(毕业设计还想怎么样咯，不然写一个可以上线的？)



## 关于开源

> 上传的文件将在.md文件的最后方进行说明(现在也不清楚会上传多少文件)

**1、**里面有一些bug(**启动运行类**，并非项目原本的bug，故意的还是不小心的？**故意**的)，如果你是一位程序的爱好者，我相信你一定能找到并且修复<br />**2、**如果你只是想拿本项目完成任务，那这个bug肯定会让你头痛，会让你情愿去找过一个其他的项目，当然如果你想我把我毕设项目直接给你的话，我会让你请我喝一杯奶茶的，如果你需要**毕业设计论文，任务书，开题报告，答辩PPT这些东西**，就在请我喝两杯奶茶吧
> 有时间的话，程序员日常搬砖ing
> 前提是你得有对应环境，别node、jdk、MySQL还要我来安装就太离谱了吧

**3、**为什么不直接分享，CSDN直接搬？然后收费个小100？，真正做开发的人在时间充裕的情况下，找到bug会满足自己成就感，而且花更少的时间能完成，何乐而不为呢？**我可以进行开源，但不想我的劳动成果被别人直接搬走，其中的取舍，看各位程序猿的了**
> 此文档上传于23年7月，截止到24年3月，已有多位用户直接fork，文档下载的数不胜数，这是我不想看到的，因此代码你可以拿，但其中的错你得自己找。又不想找错又想白嫖，没这么好的事



## 联系我

1. **邮件**([zzc_wx@qq.com](mailto:zzc_wx@qq.com))与v绑定，基本上可直接回复
2. **telegram**联系我(随缘上线)，可直接发起聊天，你都用github了，telegram应该对你来说不难

> ps1:我也是程序猿，日常板砖ing，看到就回，有时候忘了在发一次就好
> ps2:和其他专门做这种的工作室比不了，只是一个23届毕业生把自己的毕业设计上传上来而已


![](https://github.com/zzcToTuring/dormitory-management/assets/94695853/9424dc4b-8015-4a23-b40f-2a80dcc859ca#height=343&id=wrfOp&originHeight=655&originWidth=517&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=&width=271)



## 文件说明

1、前(vue)/后端(java)--->项目的源代码

2、.mind文件---->画图软件，请使用xmind打开(项目介绍图)

3、任务书、开题报告、答辩PPT、论文最终稿等。使用wps打开

4、.sql文件--->数据库相关配置（**mysql**）

5、readme.md文件--->本项目介绍文件
