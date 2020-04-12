/*
 Navicat Premium Data Transfer

 Source Server         : 121.36.2.172
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 121.36.2.172:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 12/04/2020 15:47:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(1) NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  `score` decimal(5, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '小明', 1, 0, 100.00);
INSERT INTO `student` VALUES (2, '小明2', 0, 0, 12.38);
INSERT INTO `student` VALUES (3, '小明3', 1, 1, 56.00);

SET FOREIGN_KEY_CHECKS = 1;
