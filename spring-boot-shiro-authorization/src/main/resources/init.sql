DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `URL` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

insert  into `t_permission`(`ID`,`URL`,`name`) values (1,'/user','user:user'),(2,'/user/add','user:add'),(3,'/user/delete','user:delete');


DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `memo` varchar(32) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

insert  into `t_role`(`id`,`name`,`memo`) values (1,'admin','超级管理员'),(2,'test','测试用户 ');


DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `RID` bigint(20) DEFAULT NULL COMMENT '角色id',
  `PID` bigint(20) DEFAULT NULL COMMENT '权限id',
  KEY `RID` (`RID`),
  KEY `PID` (`PID`),
  CONSTRAINT `t_role_permission_ibfk_1` FOREIGN KEY (`RID`) REFERENCES `t_role` (`id`),
  CONSTRAINT `t_role_permission_ibfk_2` FOREIGN KEY (`PID`) REFERENCES `t_permission` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `t_role_permission`(`RID`,`PID`) values (1,2),(1,3),(1,1),(2,1);


DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USERNAME` varchar(50) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(128) NOT NULL COMMENT '密码',
  `STATUS` char(1) NOT NULL COMMENT '状态 0锁定 1有效',
  `CRATE_TIME` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

insert  into `t_user`(`ID`,`USERNAME`,`PASSWORD`,`STATUS`,`CRATE_TIME`) values (1,'ordust','93e4a60023701f52b8e8cb50472ff210','1','2018-12-31'),(2,'12345','cd1191e3c54919fc5fed64516bbb53fe','1','2018-12-31');


DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `RID` bigint(20) DEFAULT NULL COMMENT '角色id',
  KEY `user_id` (`user_id`),
  KEY `RID` (`RID`),
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`ID`),
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`RID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `t_user_role`(`user_id`,`RID`) values (1,1),(2,2);
