/*
 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : spring-boot-demo
 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `t_au_user`;
CREATE TABLE `t_au_user`
(
  `id`          bigint(64)  NOT NULL auto_increment COMMENT '主键',
  `username`    varchar(50) NOT NULL COMMENT '用户名',
  `password`    varchar(60) NOT NULL COMMENT '密码',
  `user_desc_name`    varchar(64) NOT NULL COMMENT '用户名称',
  `phone`       varchar(11)          DEFAULT NULL COMMENT '手机',
  `email`       varchar(50)          DEFAULT NULL COMMENT '邮箱',
  `status`      tinyint      NOT NULL DEFAULT 1 COMMENT '状态，启用-1，禁用-0',
  `create_time` TIMESTAMP default CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_time` timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

-- ----------------------------
-- Records of sec_user
-- ----------------------------
BEGIN;
INSERT INTO `t_au_user`
VALUES (null , 'admin', '$2a$10$piJBt2XGLCEhEWkkA2D11eF6ZKRY51ThYcldjC.W2cSQ9HwlGQvJK','超级管理员',
        '17300000000', '405877095@qq.com', 1,null ,null);
COMMIT;

-- ----------------------------
-- Table structure for sec_role
-- ----------------------------
DROP TABLE IF EXISTS `t_au_role`;
CREATE TABLE `t_au_role`
(
  `id`          bigint(64) auto_increment  NOT NULL COMMENT '主键',
  `name`        varchar(50) NOT NULL COMMENT '角色名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` TIMESTAMP default CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- ----------------------------
-- Records of sec_role
-- ----------------------------
BEGIN;
INSERT INTO `t_au_role`
VALUES (null , 'super', '超级管理员', null , null);
INSERT INTO `t_au_role`
VALUES (null, 'normal', '普通用户', null , null);
COMMIT;


-- ----------------------------
-- Table structure for sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_au_user_role`;
CREATE TABLE `t_au_user_role`
(
  `user_id` bigint(64) NOT NULL COMMENT '用户主键',
  `role_id` bigint(64) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户角色关系表';

-- ----------------------------
-- Records of sec_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_au_user_role`
VALUES (1, 1);
INSERT INTO `t_au_user_role`
VALUES (1, 2);
COMMIT;

-- ----------------------------
-- Table structure for sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_au_permission`;
CREATE TABLE `t_au_permission`
(
  `id`         bigint(64) auto_increment NOT NULL COMMENT '主键',
  `key`        varchar(32) NOT NULL  comment '权限KEY',
  `name`       varchar(50) NOT NULL COMMENT '权限名',
  `url`        varchar(1000) DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `type`       int(2)      NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50)   DEFAULT NULL COMMENT '权限表达式',
  `method`     varchar(50)   DEFAULT NULL COMMENT '后端接口访问方式',
  `sort`       int(11)     NOT NULL COMMENT '排序',
  `parent_id`  bigint(64)  NOT NULL COMMENT '父级id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限表';

-- ----------------------------
-- Records of sec_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_au_permission`
VALUES (null , 'P_TEST','测试页面', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO `t_au_permission`
VALUES (1072806379313565696, '测试页面-查询', '/**/test', 2, 'btn:test:query', 'GET', 1, 1072806379288399872);
INSERT INTO `t_au_permission`
VALUES (1072806379330342912, '测试页面-添加', '/**/test', 2, 'btn:test:insert', 'POST', 2, 1072806379288399872);
INSERT INTO `t_au_permission`
VALUES (1072806379342925824, '监控在线用户页面', '/monitor', 1, 'page:monitor:online', NULL, 2, 0);
INSERT INTO `t_au_permission`
VALUES (1072806379363897344, '在线用户页面-查询', '/**/api/monitor/online/user', 2, 'btn:monitor:online:query', 'GET', 1,
        1072806379342925824);
INSERT INTO `t_au_permission`
VALUES (1072806379384868864, '在线用户页面-踢出', '/**/api/monitor/online/user/kickout', 2, 'btn:monitor:online:kickout',
        'DELETE', 2, 1072806379342925824);
COMMIT;



-- ----------------------------
-- Table structure for sec_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_au_role_permission`;
CREATE TABLE `t_au_role_permission`
(
  `role_id`       bigint(64) NOT NULL COMMENT '角色主键',
  `permission_id` bigint(64) NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_id`, `permission_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色权限关系表';

-- ----------------------------
-- Records of sec_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_au_role_permission`
VALUES (3, 1);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379313565696);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379330342912);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379342925824);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379363897344);
INSERT INTO `sec_role_permission`
VALUES (1072806379208708096, 1072806379384868864);
INSERT INTO `sec_role_permission`
VALUES (1072806379238068224, 1072806379288399872);
INSERT INTO `sec_role_permission`
VALUES (1072806379238068224, 1072806379313565696);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
