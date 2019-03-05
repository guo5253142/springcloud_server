基于springcloud的微服务框架

admin_services文件结构：
1、admin_client供客户端使用基于FeignClient，最终打成jar包。
2、admin_common存放一些公共常量、方法及接口等代码，最终会打成jar包。
3、admin_server提供基于http协议的服务，负责处理数据库相关的操作（使用mysql），最终打成war包。
4、sso_server提供单点登录服务，最终打成war包。

admin_web：后台管理系统-用户角色权限模板。用来创建用户、菜单，管理用户权限的项目（调用admin_server服务）。 
前端页面框架使用的是layui后台管理系统的模板（因为使用的是收费版，有版权问题，所以我把layui的核心js都删掉了，想要的可以给我留言271797003@qq.com）
