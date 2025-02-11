#### 后端技术栈
   
- 基于 SpringBoot + Mybatis Plus+ Shiro + mysql + redis + sharding-jdbc + canal构建的智慧云智能教育平台 
- 基于数据驱动视图的理念封装 element-ui，即使没有 vue 的使用经验也能快速上手  
- 提供 lambda 、stream api 、webflux 的生产实践  

#### 前端技术

- Vue
- Vuex
- Vxe-Table (文档地址：https://gitee.com/xuliangzhan_admin/vxe-table)
- Element-UI
- vue-router
- axios 

### 学生系统功能

|  模块   | 介绍  |
|  ----  | ----  |
| 登录  | 用户名、密码  |  
| 试题  | 题干支持文本、图片、数学公式、表格等|  
| 考试  | 主观题支持答题板作答之后保存文件上传到服务器  |  
| 考试记录  | 查看答卷记录和试卷信息  |  
| 错题本  | 答错题目会自动进入错题本，显示题目基本信息  |  
| 视频学习  | 支持在线学习录播视频 |  
| 个人信息  | 显示学生个人资料  |  
| 更新信息  | 修改个人资料、头像  |  
| 个人动态  | 显示用户最近的个人动态  |  
| 消息中心  | 用于接收管理员发送的消息  |  

### 管理系统功能

|  模块   | 介绍  |
|  ----  | ----  |
| 登录  | 用户名、密码  |  
| 主页  | 试卷总数、题目总数、用户活跃度、题目月数量  |  
| 学生列表  | 显示系统所有的学生，新增、修改、删除、禁用  |  
| 管理员列表  | 显示系统所有的管理员，新增、修改、删除、禁用  |  
| 科目列表  | 学科查询、修改、删除  |   
| 试卷列表  | 试卷查询、修改、删除、设置  |  
| 课程管理| 支持课程视频上传  | 
| 考试管理| 考试列表、考试分析  |  
| 题目列表  | 题目查询、修改、删除  |  
| 题目创建  | 题目支持单选题、多选题、判断题、填空题、简答题，题干支持文本、图片、表格、数学公式  |  
| 用户日志  | 显示所有用户日志  |  
| 个人资料  | 显示管理员用户名、真实姓名  |   
| 修改资料  | 修改姓名、手机号  |  

### 小程序功能

|  模块   | 介绍  |
|  ----  | ----  |
| 登录  | 用户登录登出功能  |  
| 考试  | 题干支持文本、图片、数学公式、表格等 |   
| 视频课堂| 支持在线学习录播课程 |   
| 考试记录  | 查看答卷记录和试卷信息  |  
| 错题本  | 答错题目会自动进入错题本，显示题目基本信息  |  
| 个人信息  | 显示学生个人资料  |  
| 更新信息  | 修改个人资料、头像  |  
| 个人动态  | 显示用户最近的个人动态  |  
| 消息中心  | 用于接收管理员发送的消息  |  


#### 核心依赖 


依赖 | 版本
---|---
Spring Boot |  2.5.0.RELEASE  
Mybatis Plus | 3.4.0  
Mysql | 5.7
Element-UI | 2.13.0
Shiro | 1.4.0
Jfinal Weixin | 2.3
sharding-jdbc | 3.1.0.M1
canal | 1.1.4
node | 建议安装V8.16.0或者V12.22.5
`
### 系统特色
- 集成开源框架sharding-jdbc，目前系统已支持mysql的读写分离
- 集成阿里开源框架canal,支持mysql与其它主菲关系型数据库数据的异步同步
- 集成数据库管理框架flywaydb，项目启动自动创建数据库表结构
- 支持服务集群(系统已集成分布式session与Jwt token 机制)
- 支持填空题、综合题、选择题等多种试题类型的录入
- 支持数学公式的插入同时也支持通过excel 导入试题
- 支持系统自动评分、教师后台批阅学员试卷
- 可以将试卷试题导出word或者html,并且支持试题图片导出word
- 集成了百度地图和百度富文本编辑器

### 视频教程地址

- 学生端管理后台  https://haokan.baidu.com/v?pd=bjh&vid=16055556239765271034&fr=bjhauthor&type=video
- 学生端项目教程  https://haokan.baidu.com/v?pd=bjh&vid=13279860213867889185&fr=bjhauthor&type=video
- 完整项目实战教程 （https://www.51zxw.net/list.aspx?cid=830）
### 项目演示地址

- 管理后台  http://121.40.53.251:8002 （admin 123456）
- 学生端   http://121.40.53.251（student 123456）
- h5、小程序、app预览 http://121.40.53.251:8003

###  模块说明


```
- education
- ├── education-api -- 系统api模块
- └── education-common -- 系统公共模块 
- └── education-business -- 系统业务模块
- └── education-canal -- mysql数据监听同步模块
- └── education-common-api -- 系统公共api模块
- ├── education-model -- 实体类模块	
```
### 安装部署

- 
-  开发环境支持：JDK1.8、mysql5.7、Rabbitmq、Redis
-  配置两台mysql服务器用来做数据库读写分离(注意：该配置非系统必须，如不使用读写分离，在系统配置文件中将读库配置成写库地址即可)
-  启动education-api模块下面的EducationApiApplication即可启动服务(本项目属于创业初期,所以将学生端接口与后台管理接口集成到一个服务器中，节省服务器成本)
-  建好数据库名称，运行服务成功自动创建表结构
-  建议node版本使用8或者12
- 


### 智慧云智能教育系统管理平台
- 项目源码地址：  https://gitee.com/zhuimengshaonian/wisdom-education-admin-front
- 功能模块：系统首页、教育教学模块、考试管理模块、统计分析模块、系统设置模块
- 试题管理：支持excel模板导入试题、支持使用富文本编辑试题及插入数学公式，同时还支持上传试题教学视频
- 试卷管理：支持将试卷导出成word文档、html页面进行打印、支持富文本图片导出到word
- 试卷批改功能：支持教师后台批改试卷，主观题系统自动评分、非主观题由教师评分、错题可设置添加到学员错题本
- RBCA权限管理：主要包括用户、角色、权限

### 智慧云智能教育平台学生端
- 项目源码地址：  https://gitee.com/zhuimengshaonian/wisdom-education-front
- 功能模块：学员在线做课程试题、在线考试、错题本功能记录、考试记录、个人中心

### 小程序及app
 - 项目源码地址：  https://gitee.com/zhuimengshaonian/education-app (功能开发中，敬请期待。。。)
![输入图片说明](https://images.gitee.com/uploads/images/2020/1221/154809_080680e2_1526010.jpeg "QQ图片20201221154646.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1221/154818_7c275efc_1526010.jpeg "QQ图片20201221154703.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1221/154831_32f292e7_1526010.jpeg "QQ图片20201221154716.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1221/154841_0159e6bd_1526010.jpeg "QQ图片20201221154724.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1221/154850_46395ade_1526010.jpeg "QQ图片20201221154729.jpg")
### 智慧云智能教育系统微信交流群（加群获取项目正式部署教程和免费视频教程）
![输入图片说明](%25XUASABBY8AJMIIJBZ@TDLS_tmb.jpg)
### 智慧云智能教育系统交流群（加群获取项目正式部署教程和免费视频教程）

 -  **QQ交流群1 1072042422（已满勿加）** (加群请备注码云，否则拒绝入群）
 -  **QQ交流群2 930422806** (加群请备注码云，否则拒绝入群）
 -  **QQ交流群3 536414635** (加群请备注码云，否则拒绝入群）
 -  **作者QQ 1913188966** 

### 参与贡献

- Fork 本仓库
- 感谢大家关注，点赞，Fork，项目持续更新中，欢迎各位提出意见和建议
- 各位伙伴的star一下就是对我最大的鼓励


#### 项目运行截图 
![输入图片说明](https://images.gitee.com/uploads/images/2020/0425/115112_1eb8a6e7_1526010.png "}CI9A4HDZZAC3%M`N}JL`77.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0311/194825_34662cb0_1526010.png "]06ZXGQ[2~7S3_28H5XZD}9.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0311/194921_bd7bc565_1526010.png "_QG(`5{@F690KF[O$ECXBVU.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0311/194934_9d82c452_1526010.png "0UX7[[}L@8`9QVG~1JZRJ73.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0311/194945_3b2ed607_1526010.png "P67Y6%0X}FR~2$KSG7RZXBO.png")![输入图片说明](https://images.gitee.com/uploads/images/2020/0602/171042_d498a547_1526010.jpeg "1591088933(1).jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1210/200612_cbd7f027_1526010.png "I2O]CD)]]5ATJ170~PODL]N.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1210/200737_335b320c_1526010.png "6$O6RPZ5U(OWZ~MFE42GAKF.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/1024/111204_474e759b_1526010.png "62Z`)))(BT(Y~}0K3_C)(LD.png")
```

