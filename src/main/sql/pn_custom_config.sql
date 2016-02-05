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

 Date: 12/11/2014 11:07:16 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pn_custom_config`
-- ----------------------------
DROP TABLE IF EXISTS `pn_custom_config`;
CREATE TABLE `pn_custom_config` (
  `user_name` varchar(50) COLLATE gbk_bin NOT NULL,
  `month_cost_plan` float(11,0) NOT NULL,
  `category_switch` tinyint(255) NOT NULL DEFAULT '0',
  `prepay_switch` tinyint(255) NOT NULL DEFAULT '0',
  `remark_amount` float NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COLLATE=gbk_bin;

SET FOREIGN_KEY_CHECKS = 1;
