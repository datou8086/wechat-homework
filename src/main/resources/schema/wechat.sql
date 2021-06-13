/*
 Navicat Premium Data Transfer

 Source Server         : xxx.xx.xx.xx
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : xxx.xx.xx.xx:3306
 Source Schema         : wechat

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 27/08/2020 09:43:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_nickname` (`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `local_auth`;
CREATE TABLE `local_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT 'user表主键id',
  `login_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录id',
  `login_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录id类型，用户名、手机号码、邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户hash密码',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `idx_login_id` (`login_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT 'user表主键id',
  `oauth_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '第三方平台id',
  `oauth_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '第三方平台标识(qq、wechat、weibo)',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '第三方获取的access_token,校验使用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `idx_oauth_id_type` (`oauth_id`, `oauth_type`) USING BTREE,
  KEY `idx_access_token` (`access_token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
