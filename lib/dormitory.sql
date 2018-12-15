/*
Navicat MySQL Data Transfer

Source Server         : yzw
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : dormitory

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-12-03 17:09:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admininfo`
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------
INSERT INTO `admininfo` VALUES ('admin', '123');

-- ----------------------------
-- Table structure for `roominfo`
-- ----------------------------
DROP TABLE IF EXISTS `roominfo`;
CREATE TABLE `roominfo` (
  `roomId` varchar(255) NOT NULL DEFAULT '',
  `bed1` varchar(255) NOT NULL,
  `bed2` varchar(255) NOT NULL,
  `bed3` varchar(255) NOT NULL,
  `bed4` varchar(255) NOT NULL,
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roominfo
-- ----------------------------
INSERT INTO `roominfo` VALUES ('101', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('102', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('103', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('104', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('105', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('106', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('107', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('108', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('109', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('110', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('201', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('202', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('203', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('204', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('205', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('206', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('207', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('208', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('209', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('210', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('301', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('302', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('303', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('304', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('305', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('306', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('307', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('308', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('309', '无', '无', '无', '无');
INSERT INTO `roominfo` VALUES ('310', '无', '无', '无', '无');

-- ----------------------------
-- Table structure for `studentinfo`
-- ----------------------------
DROP TABLE IF EXISTS `studentinfo`;
CREATE TABLE `studentinfo` (
  `stuId` varchar(255) NOT NULL DEFAULT '',
  `stuName` varchar(255) NOT NULL,
  `colleget` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `class` varchar(255) NOT NULL,
  `isCheckIn` tinyint(4) NOT NULL,
  PRIMARY KEY (`stuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentinfo
-- ----------------------------
INSERT INTO `studentinfo` VALUES ('2018001', '大猫', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018002', '大鹿', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018003', '大象', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018004', '大狼', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018005', '大鸟', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018006', '大熊', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018007', '大鸭', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018008', '大狗', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018009', '大猴', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018010', '大虎', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018011', '大兔', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018012', '大鸡', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018013', '大蛇', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018014', '大猪', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018015', '大羊', '生科院', '生物化学', '生化181', '0');
INSERT INTO `studentinfo` VALUES ('2018016', '大牛', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018017', '大狐', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018018', '大龙', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018019', '大鼠', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018020', '大马', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018021', '中猫', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018022', '中鹿', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018023', '中象', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018024', '中鸟', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018025', '中狼', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018026', '中熊', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018027', '中鸭', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018028', '中狗', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018029', '中猴', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018030', '中虎', '生科院', '生物化学', '生化182', '0');
INSERT INTO `studentinfo` VALUES ('2018031', '中兔', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018032', '中鸡', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018033', '中蛇', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018034', '中猪', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018035', '中羊', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018036', '中牛', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018037', '中狐', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018038', '中龙', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018039', '中鼠', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018040', '中马', '生科院', '生物医学', '生医181', '0');
INSERT INTO `studentinfo` VALUES ('2018041', '小猫', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018042', '小鹿', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018043', '小象', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018044', '小鱼', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018045', '小鸟', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018046', '小狼', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018047', '小熊', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018048', '小鸭', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018049', '小狗', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018050', '小猴', '生科院', '生物医学', '生医182', '0');
INSERT INTO `studentinfo` VALUES ('2018051', '小虎', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018052', '小兔', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018053', '小鸡', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018054', '小蛇', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018055', '小猪', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018056', '小羊', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018057', '小牛', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018058', '小龙', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018059', '小鼠', '健康学院', '健康管理', '健管181', '0');
INSERT INTO `studentinfo` VALUES ('2018060', '小马', '健康学院', '健康管理', '健管181', '0');
