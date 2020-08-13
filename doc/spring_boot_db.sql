/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : spring_boot_db

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 13/08/2020 17:25:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `department_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `department_alias_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门别名',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，BI团队使用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_department_1` (`department_name`) USING BTREE,
  KEY `idx_sys_department_2` (`department_alias_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_team
-- ----------------------------
DROP TABLE IF EXISTS `sys_team`;
CREATE TABLE `sys_team` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '项目名称',
  `team_alias_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '项目别名',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
  `department_id` int NOT NULL COMMENT '所属部门id',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，BI团队使用',
  PRIMARY KEY (`id`),
  KEY `idx_sys_team_1` (`team_name`) USING BTREE,
  KEY `idx_sys_team_2` (`team_alias_name`) USING BTREE,
  KEY `idx_sys_team_3` (`department_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名称',
  `phone` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户手机',
  `password` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
  `department_id` int NOT NULL DEFAULT '0' COMMENT '所属部门id',
  `team_id` int NOT NULL DEFAULT '0' COMMENT '所属团队id',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，BI团队使用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_user_1` (`user_name`) USING BTREE,
  KEY `idx_sys_user_2` (`phone`) USING BTREE,
  KEY `idx_sys_user_3` (`department_id`) USING BTREE,
  KEY `idx_sys_user_4` (`team_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
