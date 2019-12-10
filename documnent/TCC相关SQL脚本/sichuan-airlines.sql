/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : rm-uf6a2ll88vlt359t5no.mysql.rds.aliyuncs.com:3306
 Source Schema         : sichuan-airlines

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 08/12/2019 01:18:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for air_info
-- ----------------------------
DROP TABLE IF EXISTS `air_info`;
CREATE TABLE `air_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `air_line` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航班号',
  `left_ticket_num` int(11) NOT NULL COMMENT '剩余票数',
  `fr` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '始发地',
  `des` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目的地',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of air_info
-- ----------------------------
INSERT INTO `air_info` VALUES (1, 'B001', 1, '上海', '昆明');

-- ----------------------------
-- Table structure for ticket_order
-- ----------------------------
DROP TABLE IF EXISTS `ticket_order`;
CREATE TABLE `ticket_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购票人姓名',
  `order_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `ticket_id` int(11) NULL DEFAULT NULL COMMENT '对应air_info的主键',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态：0-锁定 1-失效 2-有效 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
