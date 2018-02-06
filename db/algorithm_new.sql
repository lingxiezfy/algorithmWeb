/*
Navicat MySQL Data Transfer

Source Server         : eee
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : algorithm

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-02-06 22:29:05
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', 'admin');

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
  CONSTRAINT `FK_question_q_id_c_code` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_c_code
-- ----------------------------
INSERT INTO `t_c_code` VALUES ('1', '1', '测试c');

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
  CONSTRAINT `FK_question_q_id_java_code` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_java_code
-- ----------------------------
INSERT INTO `t_java_code` VALUES ('1', '1', '测试java');

-- ----------------------------
-- Table structure for `t_question`
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL,
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
  CONSTRAINT `FK_rank_r_id_question` FOREIGN KEY (`r_id`) REFERENCES `t_rank` (`r_id`),
  CONSTRAINT `FK_type_t_id_question` FOREIGN KEY (`t_id`) REFERENCES `t_type` (`t_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES ('1', '1', '1', '1', 0x31, '测试', '测试', '测试', '1000', '1000', '测试', '测试');
INSERT INTO `t_question` VALUES ('2', '1', '1', '1', 0x33, '测试2', '测试2', '测试2', '1000', '1000', '测试2', '测试2');

-- ----------------------------
-- Table structure for `t_rank`
-- ----------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE TABLE `t_rank` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
  CONSTRAINT `FK_question_q_id_sample` FOREIGN KEY (`q_id`) REFERENCES `t_question` (`q_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sample
-- ----------------------------
INSERT INTO `t_sample` VALUES ('1', '1', 'test', 'test');

-- ----------------------------
-- Table structure for `t_source`
-- ----------------------------
DROP TABLE IF EXISTS `t_source`;
CREATE TABLE `t_source` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_source
-- ----------------------------
INSERT INTO `t_source` VALUES ('1', '蓝桥杯');
INSERT INTO `t_source` VALUES ('2', '测试');

-- ----------------------------
-- Table structure for `t_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) DEFAULT NULL,
  `tag_index` longblob,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('1', '测试', 0x31);
INSERT INTO `t_tag` VALUES ('2', '修改的', 0x32);

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
  CONSTRAINT `FK_source_s_id_type` FOREIGN KEY (`s_id`) REFERENCES `t_source` (`s_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '1', '入门训练');

-- ----------------------------
-- Procedure structure for `get_question_title_list`
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_question_title_list`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_question_title_list`(IN curr_page INT ,IN page_size INT,IN r_id_in INT ,IN t_id_in INT ,IN s_id_in INT,IN tag_in LONGBLOB, IN search_title VARCHAR(50))
BEGIN
    DECLARE r_id_tmp VARCHAR(50);
    DECLARE t_id_tmp VARCHAR(50);
    DECLARE s_id_tmp VARCHAR(50);
    DECLARE tag_tmp VARCHAR(200);
    DECLARE title_tmp VARCHAR(100);
    DECLARE sql_limit VARCHAR(50);
    DECLARE s INT;
    SET s = (curr_page-1)*page_size;

    IF r_id_in <= 0 THEN
      SET r_id_tmp = NULL ;
    ELSE
      SET r_id_tmp = concat(' r_id = ',r_id_in,' ');
    END IF ;
    IF t_id_in <= 0 THEN
      SET t_id_tmp = NULL ;
    ELSE
      SET t_id_in = concat(' t_id = ',t_id_in,' ');
    END IF;
    IF s_id_in <= 0 THEN
      SET s_id_tmp = NULL ;
    ELSE
      SET s_id_tmp = concat(' s_id = ',s_id_in,' ');
    END IF;
    IF tag_in <= 0 THEN
      SET tag_tmp = NULL ;
    ELSE
      SET tag_tmp = concat(' tag ^ ',tag_in,' < tag ');
    END IF;
    SET title_tmp = concat(' q_title like ','\'%',search_title,'%\'');
    IF curr_page <= 0 THEN
      SET sql_limit = '';
    ELSE
      SET sql_limit = concat(' LIMIT ',s,',',page_size);
    END IF;
    SET @sql_header = 'SELECT t_question.q_id,t_question.r_id,t_question.t_id,t_question.s_id,t_rank.r_name,t_type.t_name,t_source.s_name,t_question.tag,t_question.q_title FROM t_question RIGHT JOIN t_rank ON t_question.r_id = t_rank.r_id RIGHT JOIN t_type ON t_question.t_id = t_type.t_id RIGHT JOIN t_source on t_type.s_id = t_source.s_id where ';
    SET @sql_key = concat_ws('and',r_id_tmp,t_id_tmp,s_id_tmp,tag_tmp,title_tmp);
    SET @sql_order = ' ORDER BY t_question.s_id,t_question.t_id,t_question.r_id,q_title ';

    SET @my_sql = concat(@sql_header,@sql_key,@sql_order,sql_limit);

    /*  SELECT @my_sql; */

    PREPARE stmt FROM @my_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `get_question_title_list_count`
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_question_title_list_count`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_question_title_list_count`(IN r_id_in INT ,IN t_id_in INT ,IN s_id_in INT,IN tag_in LONGBLOB, IN search_title VARCHAR(50))
BEGIN
    DECLARE r_id_tmp VARCHAR(50);
    DECLARE t_id_tmp VARCHAR(50);
    DECLARE s_id_tmp VARCHAR(50);
    DECLARE tag_tmp VARCHAR(200);
    DECLARE title_tmp VARCHAR(100);

    IF r_id_in <= 0 THEN
      SET r_id_tmp = NULL ;
    ELSE
      SET r_id_tmp = concat(' r_id = ',r_id_in,' ');
    END IF ;
    IF t_id_in <= 0 THEN
      SET t_id_tmp = NULL ;
    ELSE
      SET t_id_in = concat(' t_id = ',t_id_in,' ');
    END IF;
    IF s_id_in <= 0 THEN
      SET s_id_tmp = NULL ;
    ELSE
      SET s_id_tmp = concat(' s_id = ',s_id_in,' ');
    END IF;
    IF tag_in <= 0 THEN
      SET tag_tmp = NULL ;
    ELSE
      SET tag_tmp = concat(' tag ^ ',tag_in,' < tag ');
    END IF;
    SET title_tmp = concat(' q_title like ','\'%',search_title,'%\'');
    
    SET @sql_header = 'SELECT count(*) FROM t_question RIGHT JOIN t_rank ON t_question.r_id = t_rank.r_id RIGHT JOIN t_type ON t_question.t_id = t_type.t_id RIGHT JOIN t_source on t_type.s_id = t_source.s_id where ';
    SET @sql_key = concat_ws('and',r_id_tmp,t_id_tmp,s_id_tmp,tag_tmp,title_tmp);

    SET @my_sql = concat(@sql_header,@sql_key);

    /*  SELECT @my_sql; */

    PREPARE stmt FROM @my_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `get_type_by_source_id`
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_type_by_source_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_type_by_source_id`(IN s_id_in INT)
BEGIN
  IF s_id_in <= 0 THEN
    SELECT t_type.t_id,t_source.s_id,t_source.s_name,t_type.t_name FROM t_type RIGHT JOIN t_source
        ON t_source.s_id = t_type.s_id ORDER BY t_source.s_id,t_name;
  ELSE
    SELECT t_type.t_id,t_source.s_id,t_source.s_name,t_type.t_name FROM t_type RIGHT JOIN t_source
        ON t_source.s_id = t_type.s_id WHERE t_source.s_id = s_id_in ORDER BY t_name;
  END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `is_exist_code`
-- ----------------------------
DROP PROCEDURE IF EXISTS `is_exist_code`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `is_exist_code`(IN q_id_in INT)
BEGIN
    DECLARE result INT;
    DECLARE c_count INT;
    DECLARE java_count INT;
    SET result = 0;
    SELECT count(*) INTO c_count FROM t_c_code WHERE q_id = q_id_in;
    IF c_count > 0
      THEN SET result = 1;
    END IF ;
    SELECT count(*) INTO java_count FROM t_java_code WHERE q_id = q_id_in;
    IF java_count >0
      THEN SET result = 2;
    END IF ;
    IF c_count > 0 AND java_count > 0
      THEN SET result = 3;
    END IF ;
    
    SELECT result;
    
  END
;;
DELIMITER ;
DELIMITER ;;
CREATE TRIGGER `trigger_calculate_source_id` BEFORE INSERT ON `t_question` FOR EACH ROW BEGIN
    DECLARE id INT;
    SELECT s_id INTO id FROM t_type WHERE t_type.t_id = NEW.t_id;
    SET new.s_id = id;
  END
;;
DELIMITER ;
DELIMITER ;;
CREATE TRIGGER `trigger_edit_source_id` BEFORE UPDATE ON `t_question` FOR EACH ROW BEGIN
    DECLARE id INT;
    SELECT s_id INTO id FROM t_type WHERE t_type.t_id = NEW.t_id;
    SET new.s_id = id;
  END
;;
DELIMITER ;
DELIMITER ;;
CREATE TRIGGER `trigger_calculate_tag_index` BEFORE INSERT ON `t_tag` FOR EACH ROW begin
    DECLARE max_id INT;
    SET max_id = 0;
    SELECT tag_id INTO max_id FROM t_tag ORDER BY tag_id DESC LIMIT 0,1;
    set new.tag_index = 1<< max_id;
  end
;;
DELIMITER ;
