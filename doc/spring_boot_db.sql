SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `department_id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `department_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `department_alias_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门别名',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，BI团队使用',
  PRIMARY KEY (`department_id`),
  KEY `idx_sys_department_1` (`department_name`) USING BTREE,
  KEY `idx_sys_department_2` (`department_alias_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名称',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户手机',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，BI团队使用',
  PRIMARY KEY (`user_id`),
  KEY `idx_sys_user_1` (`user_name`) USING BTREE,
  KEY `idx_sys_user_2` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
