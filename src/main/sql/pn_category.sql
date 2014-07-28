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

 Date: 07/18/2014 16:06:39 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pn_category`
-- ----------------------------
DROP TABLE IF EXISTS `pn_category`;
CREATE TABLE `pn_category` (
  `user_name` varchar(50) COLLATE gbk_bin NOT NULL,
  `category_xml` varchar(5000) COLLATE gbk_bin NOT NULL,
  `category_xml_sorted` varchar(5000) COLLATE gbk_bin DEFAULT NULL,
  `latest_modified_date` datetime NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COLLATE=gbk_bin;

SET FOREIGN_KEY_CHECKS = 1;
