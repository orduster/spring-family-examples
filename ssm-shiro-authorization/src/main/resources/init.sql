DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `permission`(`id`,`name`) values (1,'addProduct'),(2,'deleteProduct'),(3,'editProduct'),(4,'updateProduct'),(5,'listProduct'),(6,'addOrder'),(7,'deleteOrder'),(8,'editOrder'),(9,'updateOrder'),(10,'listOrder');


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

insert  into `role`(`id`,`name`,`description`) values (1,'admin','管理员'),(2,'productManager','产品管理'),(3,'orderManager','订单管理');



DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `rid` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  KEY `rid` (`rid`),
  KEY `pid` (`pid`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `role_permission`(`rid`,`pid`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(2,1),(2,2),(2,3),(2,4),(2,5),(3,6),(3,7),(3,8),(3,9),(3,10);



DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`name`,`password`) values (1,'zhangsan','5acef1d69ad53678948ff2dfb82b21d6'),(2,'lisi','df3e3296bb1c9e3fdf4b8284786bb991');



DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `uid` bigint(20) DEFAULT NULL,
  `rid` bigint(20) DEFAULT NULL,
  KEY `uid` (`uid`),
  KEY `rid` (`rid`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `user_role`(`uid`,`rid`) values (1,1),(2,2);

