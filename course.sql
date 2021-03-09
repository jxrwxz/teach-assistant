/*
 Navicat Premium Data Transfer

 Source Server         : teachassistant
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : teachassistant

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 09/03/2021 13:56:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CREATED_AT` date NULL DEFAULT NULL,
  `PROVED` int(2) NOT NULL,
  `NUMBER_OF_STUDENTS` int(10) NOT NULL,
  `INTRODUCTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `TEACHER_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'JAVA基础', '2021-03-09', 0, 0, '这是一门课程。这是一门课程。这是一门课程。这是一门课程。', 'jxr');
INSERT INTO `course` VALUES (2, '高等数学1', '2021-03-09', 0, 0, '高数有点难，但还是要学。高数有点难，但还是要学。高数有点难，但还是要学。', 'jxr');
INSERT INTO `course` VALUES (3, '离散数学', '2021-03-09', 0, 0, '咕咕咕咕咕咕。咕咕咕咕咕咕。咕咕咕咕咕咕。', 'jxr');

SET FOREIGN_KEY_CHECKS = 1;
