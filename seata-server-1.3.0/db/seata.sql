CREATE TABLE `branch_table`
(
    `branch_id`         bigint       NOT NULL,
    `xid`               varchar(128) NOT NULL,
    `transaction_id`    bigint        DEFAULT NULL,
    `resource_group_id` varchar(32)   DEFAULT NULL,
    `resource_id`       varchar(256)  DEFAULT NULL,
    `branch_type`       varchar(8)    DEFAULT NULL,
    `status`            tinyint       DEFAULT NULL,
    `client_id`         varchar(64)   DEFAULT NULL,
    `application_data`  varchar(2000) DEFAULT NULL,
    `gmt_create`        datetime(6) DEFAULT NULL,
    `gmt_modified`      datetime(6) DEFAULT NULL,
    PRIMARY KEY (`branch_id`),
    KEY                 `idx_xid` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `distributed_lock`
(
    `lock_key`   char(20)    NOT NULL,
    `lock_value` varchar(20) NOT NULL,
    `expire`     bigint DEFAULT NULL,
    PRIMARY KEY (`lock_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `global_table`
(
    `xid`                       varchar(128) NOT NULL,
    `transaction_id`            bigint        DEFAULT NULL,
    `status`                    tinyint      NOT NULL,
    `application_id`            varchar(32)   DEFAULT NULL,
    `transaction_service_group` varchar(32)   DEFAULT NULL,
    `transaction_name`          varchar(128)  DEFAULT NULL,
    `timeout`                   int           DEFAULT NULL,
    `begin_time`                bigint        DEFAULT NULL,
    `application_data`          varchar(2000) DEFAULT NULL,
    `gmt_create`                datetime      DEFAULT NULL,
    `gmt_modified`              datetime      DEFAULT NULL,
    PRIMARY KEY (`xid`),
    KEY                         `idx_gmt_modified_status` (`gmt_modified`,`status`),
    KEY                         `idx_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `lock_table`
(
    `row_key`        varchar(128) NOT NULL,
    `xid`            varchar(128) DEFAULT NULL,
    `transaction_id` bigint       DEFAULT NULL,
    `branch_id`      bigint       NOT NULL,
    `resource_id`    varchar(256) DEFAULT NULL,
    `table_name`     varchar(32)  DEFAULT NULL,
    `pk`             varchar(36)  DEFAULT NULL,
    `gmt_create`     datetime     DEFAULT NULL,
    `gmt_modified`   datetime     DEFAULT NULL,
    PRIMARY KEY (`row_key`),
    KEY              `idx_branch_id` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------------------------------

CREATE TABLE `undo_log`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint       NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int          NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;