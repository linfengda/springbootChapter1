-- my-job分布式任务调度框架表设计

-- my-job执行器表
CREATE TABLE `my_job_executor` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`executor_name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
`executor_desc` varchar(64) NOT NULL DEFAULT '' COMMENT '描述',
`version` tinyint(1) NOT NULL DEFAULT '0' COMMENT '版本',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='my-job执行器表';

-- my-job实例表
CREATE TABLE `my_job_instance` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`executor_id` int(11) NOT NULL DEFAULT '0' COMMENT '执行器id',
`ip` varchar(64) NOT NULL DEFAULT '' COMMENT 'ip地址',
`version` tinyint(1) NOT NULL DEFAULT '0' COMMENT '版本',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='my-job实例表';

-- my-job任务表
CREATE TABLE `my_job_task` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`executor_id` int(11) NOT NULL DEFAULT '0' COMMENT '执行器id',
`job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
`job_desc` varchar(64) NOT NULL DEFAULT '' COMMENT '描述',
`cron` varchar(64) NOT NULL DEFAULT '' COMMENT '表达式',
`state` varchar(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态，ACTIVE：启动，DEAD：未启动',
`version` tinyint(1) NOT NULL DEFAULT '0' COMMENT '版本',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='my-job任务表';