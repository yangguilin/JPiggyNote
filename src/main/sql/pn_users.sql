/*
 Navicat Premium Data Transfer

 Source Server         : aliyun_nt_mysql_piggynote
 Source Server Type    : MySQL
 Source Server Version : 50615
 Source Host           : 115.29.44.125
 Source Database       : piggy_note

 Target Server Type    : MySQL
 Target Server Version : 50615
 File Encoding         : utf-8

 Date: 12/11/2014 11:07:31 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pn_users`
-- ----------------------------
DROP TABLE IF EXISTS `pn_users`;
CREATE TABLE `pn_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(40) NOT NULL,
  `nike_name` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile_phone` varchar(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `latest_login_date` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=123 DEFAULT CHARSET=gbk;

SET FOREIGN_KEY_CHECKS = 1;
