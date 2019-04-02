CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssm`;


DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `user_role`;


CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc_` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
insert  into `permission`(`id`,`name`,`desc_`,`url`) values (1,'addProduct','增加产品','/addProduct'),(2,'deleteProduct','删除产品','/deleteProduct'),(3,'editeProduct','编辑产品','/editeProduct'),(4,'updateProduct','修改产品','/updateProduct'),(5,'listProduct','查看产品','/listProduct'),(6,'addOrder','增加订单','/addOrder'),(7,'deleteOrder','删除订单','/deleteOrder'),(8,'editeOrder','编辑订单','/editeOrder'),(9,'updateOrder','修改订单','/updateOrder'),(10,'listOrder','查看订单','/listOrder');



CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc_` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
insert  into `role`(`id`,`name`,`desc_`) values (1,'admin','超级管理员'),(2,'productManager','产品管理员'),(3,'orderManager','订单管理员');



CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
insert  into `role_permission`(`id`,`rid`,`pid`) values (11,2,1),(12,2,2),(13,2,3),(14,2,4),(15,2,5),(50,3,10),(51,3,9),(52,3,8),(53,3,7),(54,3,6),(55,3,1),(56,5,10),(57,1,10),(58,1,9),(59,1,8),(60,1,7),(61,1,6),(62,1,5),(63,1,4),(64,1,3),(65,1,2),(66,1,1);



CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
insert  into `user`(`id`,`name`,`password`,`salt`) values (1,'zhangsan','a7d59dfc5332749cb801f86a24f5f590','e5ykFiNwShfCXvBRPr3wXg=='),(2,'lisi','d1a3cd4a2b004a54a90c47684304064f','qvwA3lE/mdtdiCQHNbnDjg==');


CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL,
  `rid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
insert  into `user_role`(`id`,`uid`,`rid`) values (46,1,3),(47,1,2),(48,1,1),(52,2,3),(53,2,2),(54,2,1);