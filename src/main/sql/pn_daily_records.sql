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

 Date: 12/11/2014 11:07:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pn_daily_records`
-- ----------------------------
DROP TABLE IF EXISTS `pn_daily_records`;
CREATE TABLE `pn_daily_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) COLLATE gbk_bin NOT NULL,
  `money_type` varchar(10) CHARACTER SET gbk NOT NULL,
  `stat_type` int(11) NOT NULL DEFAULT '1' COMMENT '1=normal,2=stage,3=big',
  `category_id` varchar(11) COLLATE gbk_bin NOT NULL,
  `category_name` varchar(50) COLLATE gbk_bin NOT NULL,
  `amount` float NOT NULL,
  `remark` varchar(100) COLLATE gbk_bin DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `latest_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2795 DEFAULT CHARSET=gbk COLLATE=gbk_bin;

SET FOREIGN_KEY_CHECKS = 1;
