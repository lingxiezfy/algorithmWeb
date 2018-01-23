/*
Navicat MySQL Data Transfer

Source Server         : eee
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : algorithm

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-01-23 23:44:54
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_account` varchar(20) DEFAULT NULL,
  `a_password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------

-- ----------------------------
-- Table structure for `t_c_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_c_code`;
CREATE TABLE `t_c_code` (
  `c_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `q_id` int(11) DEFAULT NULL,
  `c_code_content` text,
  PRIMARY KEY (`c_code_id`),
  KEY `FK_question_q_id_c_code` (`q_id`),
  CONSTRAINT `FK_question_q_id_c_code` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_c_code
-- ----------------------------

-- ----------------------------
-- Table structure for `t_java_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_java_code`;
CREATE TABLE `t_java_code` (
  `java_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `q_id` int(11) DEFAULT NULL,
  `java_code` text,
  PRIMARY KEY (`java_code_id`),
  KEY `FK_question_q_id_java_code` (`q_id`),
  CONSTRAINT `FK_question_q_id_java_code` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_java_code
-- ----------------------------

-- ----------------------------
-- Table structure for `t_question`
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `tag` longblob,
  `q_title` varchar(50) DEFAULT NULL,
  `q_description` text,
  `q_author` varchar(20) DEFAULT NULL,
  `q_time_limit` int(11) DEFAULT NULL,
  `q_space_limit` int(11) DEFAULT NULL,
  `q_input` text,
  `q_output` text,
  PRIMARY KEY (`q_id`),
  KEY `FK_rank_r_id_question` (`r_id`),
  KEY `FK_type_t_id_question` (`t_id`),
  CONSTRAINT `FK_type_t_id_question` FOREIGN KEY (`t_id`) REFERENCES `t_type` (`t_id`),
  CONSTRAINT `FK_rank_r_id_question` FOREIGN KEY (`r_id`) REFERENCES `t_rank` (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES ('1', '1', '1', 0x31, '测试', '测试', '测试', '1000', '1000', '测试', '测试');

-- ----------------------------
-- Table structure for `t_rank`
-- ----------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE TABLE `t_rank` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rank
-- ----------------------------
INSERT INTO `t_rank` VALUES ('1', '简单');
INSERT INTO `t_rank` VALUES ('2', '中等');
INSERT INTO `t_rank` VALUES ('3', '困难');

-- ----------------------------
-- Table structure for `t_sample`
-- ----------------------------
DROP TABLE IF EXISTS `t_sample`;
CREATE TABLE `t_sample` (
  `sample_id` int(11) NOT NULL AUTO_INCREMENT,
  `q_id` int(11) DEFAULT NULL,
  `sample_input` varchar(50) DEFAULT NULL,
  `sample_output` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sample_id`),
  KEY `FK_question_q_id_sample` (`q_id`),
  CONSTRAINT `FK_question_q_id_sample` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sample
-- ----------------------------

-- ----------------------------
-- Table structure for `t_source`
-- ----------------------------
DROP TABLE IF EXISTS `t_source`;
CREATE TABLE `t_source` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_source
-- ----------------------------
INSERT INTO `t_source` VALUES ('1', '蓝桥杯');

-- ----------------------------
-- Table structure for `t_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) DEFAULT NULL,
  `tag_index` longblob,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('1', '测试触发器', 0x31);

-- ----------------------------
-- Table structure for `t_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL,
  `t_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`t_id`),
  KEY `FK_source_s_id_type` (`s_id`),
  CONSTRAINT `FK_source_s_id_type` FOREIGN KEY (`s_id`) REFERENCES `t_source` (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '1', '入门训练');
DELIMITER ;;
CREATE TRIGGER `trigger_calculate_tag_index` BEFORE INSERT ON `t_tag` FOR EACH ROW begin
    DECLARE max_id INT;
    SET max_id = 0;
    SELECT tag_id INTO max_id FROM t_tag ORDER BY tag_id DESC LIMIT 0,1;
    set new.tag_index = 1<< max_id;
  end
;;
DELIMITER ;
