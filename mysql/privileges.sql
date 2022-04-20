-- 创建数据库
CREATE DATABASE  IF NOT EXISTS `auto_trade_system`;

use mysql;
select host, user from user;


-- Mysql8.0 将 数据库的权限授权给创建的所有用户，密码为123456：
CREATE USER 'root'@'%' IDENTIFIED BY '123456';
grant all privileges on *.* to 'root'@'%' ;

-- 刷新：
flush privileges;

-- 设置最大连接数
set GLOBAL max_connections = 1000;