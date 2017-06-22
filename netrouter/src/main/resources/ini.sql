/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.6.29 : Database - netrouter
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`netrouter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `netrouter`;

/*Table structure for table `dev_live_resource` */

CREATE TABLE `dev_live_resource` (
  `resourceId` bigint(20) NOT NULL,
  `deviceId` bigint(20) NOT NULL,
  `url` varchar(300) DEFAULT NULL,
  `bitrate` int(11) DEFAULT NULL,
  `maxSlaveCnt` int(11) DEFAULT NULL,
  `currentSlaveCnt` int(11) DEFAULT NULL,
  PRIMARY KEY (`resourceId`,`deviceId`),
  KEY `deviceId` (`deviceId`),
  CONSTRAINT `dev_live_resource_ibfk_1` FOREIGN KEY (`resourceId`) REFERENCES `live_info` (`resourceId`),
  CONSTRAINT `dev_live_resource_ibfk_2` FOREIGN KEY (`deviceId`) REFERENCES `iot_device` (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dev_live_resource` */

/*Table structure for table `dev_ntw_topo` */

CREATE TABLE `dev_ntw_topo` (
  `deviceId` bigint(20) NOT NULL,
  `topoGroupId` bigint(20) NOT NULL,
  `metricList` varchar(150) DEFAULT NULL,
  `publicIpAddr` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`deviceId`,`topoGroupId`),
  KEY `topoGroupId` (`topoGroupId`),
  CONSTRAINT `dev_ntw_topo_ibfk_1` FOREIGN KEY (`deviceId`) REFERENCES `iot_device` (`deviceId`),
  CONSTRAINT `dev_ntw_topo_ibfk_2` FOREIGN KEY (`topoGroupId`) REFERENCES `topo_group` (`topoGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dev_ntw_topo` */

insert  into `dev_ntw_topo`(`deviceId`,`topoGroupId`,`metricList`,`publicIpAddr`) values (11,0,NULL,'192.168.22.2');

/*Table structure for table `dev_warning` */

CREATE TABLE `dev_warning` (
  `deviceId` bigint(20) DEFAULT NULL,
  `warningId` bigint(20) DEFAULT NULL,
  KEY `deviceId` (`deviceId`),
  CONSTRAINT `dev_warning_ibfk_1` FOREIGN KEY (`deviceId`) REFERENCES `iot_device` (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dev_warning` */

insert  into `dev_warning`(`deviceId`,`warningId`) values (11,1);

/*Table structure for table `iot_device` */

CREATE TABLE `iot_device` (
  `deviceId` bigint(50) NOT NULL AUTO_INCREMENT,
  `manufacture` varchar(64) DEFAULT NULL,
  `manufactureSN` varchar(256) DEFAULT NULL,
  `online` tinyint(1) DEFAULT '0',
  `firstOnlineTime` timestamp NULL DEFAULT NULL,
  `latestOnlineTime` timestamp NULL DEFAULT NULL,
  `ipAddr` varchar(20) DEFAULT NULL,
  `uplinkBw` int(11) DEFAULT NULL,
  `downlinkBw` int(11) DEFAULT NULL,
  `beMaster` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`deviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `iot_device` */

insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (1,'fx','123',1,'2017-06-07 18:45:53','2017-06-21 15:12:50',NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (11,'abc','456',1,NULL,'2017-06-20 10:45:16','192.168.22.2',2,33,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (12,'def','789',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (13,'def','5589',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (14,'dfff','5589',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (15,'dfff','5589',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (16,'fsdf','454',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (17,'sdfsf','555',0,NULL,NULL,NULL,NULL,NULL,0);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (18,'我们的大中国','3434',1,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (19,'狗日的小日本','123456',1,NULL,'2017-06-17 15:11:29',NULL,NULL,NULL,NULL);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (20,'小日本','123456111',1,'2017-06-17 16:21:32','2017-06-17 16:54:24',NULL,NULL,NULL,NULL);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (21,'德国','6111',1,'2017-06-17 16:56:51','2017-06-20 18:53:37',NULL,NULL,NULL,NULL);
insert  into `iot_device`(`deviceId`,`manufacture`,`manufactureSN`,`online`,`firstOnlineTime`,`latestOnlineTime`,`ipAddr`,`uplinkBw`,`downlinkBw`,`beMaster`) values (22,'寰峰浗','6111',1,'2017-06-20 18:58:37','2017-06-20 18:58:37',NULL,NULL,NULL,NULL);

/*Table structure for table `live_info` */

CREATE TABLE `live_info` (
  `resourceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `provider` varchar(20) DEFAULT NULL,
  `path` varchar(20) DEFAULT NULL,
  `host` varchar(10) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `live_info` */

/*Table structure for table `topo_group` */

CREATE TABLE `topo_group` (
  `topoGroupId` bigint(50) NOT NULL,
  `avgdelay` int(11) DEFAULT NULL,
  `ISP` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`topoGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `topo_group` */

insert  into `topo_group`(`topoGroupId`,`avgdelay`,`ISP`) values (0,NULL,NULL);

/*Table structure for table `transfer_resource` */

CREATE TABLE `transfer_resource` (
  `deviceId` bigint(20) NOT NULL,
  `dst` bigint(20) NOT NULL,
  `resourceId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `transfer_resource` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
