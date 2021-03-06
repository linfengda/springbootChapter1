-- my-mq消息队列自动化框架表设计

-- mq消息发送表
CREATE TABLE `mq_send_message` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
`message_param` mediumtext COMMENT '请求报文',
`template_bean_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'bean名称',
`access_system` varchar(64) NOT NULL DEFAULT '' COMMENT '接收系统',
`state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '消息状态(1:等待发送，2：发送成功，3：死亡)',
`send_count` int NOT NULL DEFAULT '0' COMMENT '重发次数',
`send_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '最后一次推送时间',
`die_count` int NOT NULL DEFAULT '5' COMMENT '死亡（最大发送）次数',
`error_msg` varchar(255) NOT NULL DEFAULT '' COMMENT '执行错误信息',
`confirm_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '发送成功回调时间',
`die_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '变更为死亡状态时间',
`send_user` bigint NOT NULL DEFAULT '0' COMMENT '重发人',
`create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人id',
`create_user_name` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
`create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
`update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改人id',
`update_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
`update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mq消息发送表';