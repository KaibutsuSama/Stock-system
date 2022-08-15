CREATE TABLE `t_oauth_client_details` (
  `client_id` varchar(250) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `t_trade_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `userId` bigint(20) NOT NULL,
  `accountNo` varchar(16) NOT NULL COMMENT '交易账号',
  `balance` bigint(21) DEFAULT NULL COMMENT '余额',
  `tradeGroupId` bigint(20) NOT NULL COMMENT '账户组ID',
  `activeTime` datetime DEFAULT NULL COMMENT '开户时间',
  `status` tinyint(3) NOT NULL COMMENT '状态(0:有效， 1：锁定， 2：禁用）',
  `institutionTypeId` varchar(32) DEFAULT NULL COMMENT '机构类型id',
  `institutionId` bigint(19) DEFAULT NULL COMMENT '对应机构类型下的机构id',
  `companyId` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `tradeGroupName` varchar(32) DEFAULT NULL COMMENT '账户组名称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_accountNo` (`accountNo`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户账号表';

CREATE TABLE `t_trade_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `userNo` varchar(16) NOT NULL COMMENT '用户编号',
  `name` varchar(16) DEFAULT NULL COMMENT '用户名称',
  `userPwd` varchar(32) NOT NULL COMMENT '用户密码',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话号码',
  `companyId` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `lastLoginIp` varchar(32) DEFAULT NULL COMMENT '最近一次用户登陆IP',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最近一次登陆时间',
  `status` tinyint(3) NOT NULL COMMENT '状态(0:有效， 1：锁定， 2：禁用）',
  `craeteTime` datetime DEFAULT NULL COMMENT '创建时间',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证',
  `institutionTypeId` varchar(32) DEFAULT NULL COMMENT '机构类型id',
  `institutionId` bigint(19) DEFAULT NULL COMMENT '对应机构类型下的机构id',
  `companyName` varchar(64) DEFAULT NULL COMMENT '公司名称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_userNo` (`userNo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

drop table if exists t_trade_user_file;

/*==============================================================*/
/* Table: t_trade_user_file                                     */
/*==============================================================*/
create table t_trade_user_file
(
   id                   bigint not null auto_increment  comment '主键标识',
   userId               bigint(16) not null  comment '用户ID',
   bizType              tinyint(3) not null  comment '业务类型（0：身份证， 1：银行卡， 2：信用卡）',
   fileId               varchar(32) not null  comment '文件ID',
   filename             varchar(64)  comment '文件名称',
   fileType             varchar(32)  comment '文件类型',
   filePath             varchar(255)  comment '文件路径',
   status               tinyint(3) not null  comment '状态(0:有效， 1：无效）',
   createTime           datetime  comment '创建时间',
   updateTime           datetime not null default CURRENT_TIMESTAMP  comment '更新时间',
   primary key (id)
);

alter table t_trade_user_file comment '用户文件表';

/*==============================================================*/
/* Index: idx_userId                                            */
/*==============================================================*/
create index idx_userId on t_trade_user_file
(
   userId
);

drop table if exists t_company;

CREATE TABLE `t_company` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `companyName` varchar(32) DEFAULT NULL COMMENT '公司名称\r\n             ',
  `institutionTypeId` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '机构类型',
  `contactUser` varchar(32) NOT NULL COMMENT '联系人',
  `contactPhone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `adminUser` varchar(32) DEFAULT NULL COMMENT '管理员账号',
  `status` tinyint(3) NOT NULL COMMENT '状态(0:有效， 2：禁用）',
  `createUser` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdateUser` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后更新人名称',
  `lastUpdateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_accountNo` (`contactUser`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='公司（交易商）表';

drop table if exists t_institution;
CREATE TABLE `t_institution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '机构id',
  `institutionTypeId` varchar(48) DEFAULT NULL COMMENT '机构类型id',
  `detailInstitutionId` bigint(20) DEFAULT NULL COMMENT '机构关联id',
  `detailInstitutionName` varchar(255) DEFAULT NULL COMMENT '机构关联名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='机构表';

drop table if exists t_trade_global_config;

/*==============================================================*/
/* Table: t_trade_global_config                                 */
/*==============================================================*/
create table t_trade_global_config
(
   id                   bigint not null auto_increment  comment '主键标识',
   code                 varchar(32)  comment '配置项编号',
   category             varchar(32)  comment '分类编号(BASIC:基础配置， BUSINESS： 业务配置,  SYSTEM：系统项配置)',
   value                varchar(128)  comment '配置项的值',
   status               tinyint(2) not null  comment '状态(0:启用， 1：禁用）',
   primary key (id)
);

alter table t_trade_global_config comment '系统全局配置表';

/*==============================================================*/
/* Index: idx_key                                               */
/*==============================================================*/
create index idx_key on t_trade_global_config
(
   code
);

INSERT INTO `trade_stock`.`t_trade_global_config`(`id`, `code`, `category`, `value`, `status`) VALUES (1, 'REG_OPEN_ACCOUNT', 'SYSTEM', 'N', 0);