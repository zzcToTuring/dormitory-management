/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80024
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 80024
File Encoding         : 65001

Date: 2023-07-09 08:44:21
bug提醒：删除了一些无关紧要的数据(表结构无删除)，需要数据模拟请根据表结构自信添加
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `address_id` int NOT NULL AUTO_INCREMENT COMMENT '地址编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址名称',
  `house` int NOT NULL COMMENT '楼栋',
  `unit` int NOT NULL COMMENT '宿舍号',
  `resident_count` int NOT NULL DEFAULT '0' COMMENT '居住数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `address_status` int NOT NULL DEFAULT '0' COMMENT '地址状态',
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='寝室信息表';

-- ----------------------------
-- Table structure for tb_address_resident
-- ----------------------------
DROP TABLE IF EXISTS `tb_address_resident`;
CREATE TABLE `tb_address_resident` (
  `add_res_id` int NOT NULL AUTO_INCREMENT COMMENT '中间表--编号',
  `address_id` int DEFAULT NULL COMMENT '寝室编号',
  `res_id` int DEFAULT NULL COMMENT '对应学生的编号',
  PRIMARY KEY (`add_res_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='中间表：学生---寝室号的关联';


-- ----------------------------
-- Table structure for tb_code
-- ----------------------------
DROP TABLE IF EXISTS `tb_code`;
CREATE TABLE `tb_code` (
  `code_id` int NOT NULL AUTO_INCREMENT,
  `code_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  `role_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='注册码管理';

-- ----------------------------
-- Records of tb_code
-- ----------------------------
INSERT INTO `tb_code` VALUES ('1', 'aJLF2&=uIZHZxeODUi34FMrdfdVVhyh-zoO6', '1', '1', '2');
INSERT INTO `tb_code` VALUES ('2', 'jb%iJB+hbORqbUD$gbmMuKcP%xR+q=s&XbTr', '0', '1', null);
INSERT INTO `tb_code` VALUES ('3', 'W%%VQJ0i)%_ygzrC+hu1w8^7yYT%rOUFSY2v', '1', '2', '3');
INSERT INTO `tb_code` VALUES ('4', 'WIu~rHJ%jpQD#t@X6G$qU(B+ksVsXLTc@9We', '0', '1', null);
INSERT INTO `tb_code` VALUES ('5', '8yyvxC8$ciHFuL-NLRNpHaW$J19ctONj&wp0', '0', '2', null);
INSERT INTO `tb_code` VALUES ('6', 'admin_code', '1', '1', '1');
INSERT INTO `tb_code` VALUES ('7', 'q2D9j_o+URr*#58lxKvUo7@6fKobre_0~qwd', '0', '2', null);

-- ----------------------------
-- Table structure for tb_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_file`;
CREATE TABLE `tb_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `old_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `new_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `enable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='文件管理(用户信息，主动上传文件)';

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` int DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `page_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `state` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单管理（Aside界面）';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES ('1', '系统一览', null, 'el-icon-pie-chart', 'Home', 'Home', '2022-03-21 16:03:35', '0');
INSERT INTO `tb_menu` VALUES ('2', '学生入住管理', null, 'el-icon-user', null, null, '2022-03-06 15:00:23', '0');
INSERT INTO `tb_menu` VALUES ('3', '寝室管理', null, 'el-icon-s-promotion', null, null, '2022-03-06 15:00:44', '0');
INSERT INTO `tb_menu` VALUES ('4', '信息管理', null, 'el-icon-s-data', null, null, '2022-03-06 15:00:49', '0');
INSERT INTO `tb_menu` VALUES ('5', '系统管理', null, 'el-icon-setting', null, null, '2022-03-06 15:00:58', '0');
INSERT INTO `tb_menu` VALUES ('6', '通知管理', null, 'el-icon-s-unfold', null, null, '2022-03-06 15:01:00', '0');
INSERT INTO `tb_menu` VALUES ('7', '公告栏', '6', 'el-icon-folder-opened', 'other/File', 'file', '2022-03-06 15:01:00', '0');
INSERT INTO `tb_menu` VALUES ('8', '普通学生', '2', 'el-icon-s-custom', 'resident/ResidentList', 'residentList', '2022-03-06 15:01:15', '0');
INSERT INTO `tb_menu` VALUES ('9', '标记学生', '2', 'el-icon-link', 'resident/HighRisk', 'highRisk', '2022-03-06 15:01:39', '0');
INSERT INTO `tb_menu` VALUES ('10', '寝室维修登记', '3', 'el-icon-menu', 'access/ResidentAccess', 'residentAccess', '2022-03-06 15:01:43', '0');
INSERT INTO `tb_menu` VALUES ('11', '来访登记', '3', 'el-icon-key', 'access/VisitorAccess', 'visitorAccess', '2022-03-06 15:01:46', '0');
INSERT INTO `tb_menu` VALUES ('12', '寝室维修管理', '4', 'el-icon-upload', 'access/ResidentInfo', 'residentInfo', '2022-03-06 15:01:50', '0');
INSERT INTO `tb_menu` VALUES ('13', '来访信息管理', '4', 'el-icon-map-location', 'visitor/VisitorInformation', 'visitorInformation', '2022-03-06 15:01:55', '0');
INSERT INTO `tb_menu` VALUES ('16', '寝室信息管理', '4', 'el-icon-s-home', 'other/Address', 'address', '2022-03-06 15:02:07', '0');
INSERT INTO `tb_menu` VALUES ('17', '历史信息查询', null, 'el-icon-folder-delete', null, null, '2022-03-19 16:06:36', '0');
INSERT INTO `tb_menu` VALUES ('18', '来访信息', '17', 'el-icon-position', 'dustbin/VisitorInformationDustbin', 'visitorInformationDustbin', '2022-03-19 16:07:02', '0');
INSERT INTO `tb_menu` VALUES ('19', '迁出学生信息', '17', 'el-icon-data-analysis', 'dustbin/ResidentListDustbin', 'residentListDustbin', '2022-03-19 16:07:19', '0');
INSERT INTO `tb_menu` VALUES ('20', '寝室维护信息', '17', 'el-icon-brush', 'dustbin/ResidentInfoDustbin', 'residentInfoDustbin', '2022-03-19 16:07:36', '0');
INSERT INTO `tb_menu` VALUES ('21', '用户管理', '5', 'el-icon-user', 'sys/User', 'user', '2023-03-19 13:37:46', '0');
INSERT INTO `tb_menu` VALUES ('22', '注册码管理', '5', 'el-icon-menu', 'sys/Code', 'code', '2023-03-19 13:38:05', '0');

-- ----------------------------
-- Table structure for tb_resident
-- ----------------------------
DROP TABLE IF EXISTS `tb_resident`;
CREATE TABLE `tb_resident` (
  `res_id` int NOT NULL AUTO_INCREMENT,
  `res_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `res_sex` int NOT NULL DEFAULT '1' COMMENT '0女1男',
  `res_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `res_intotime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '入住时间',
  `res_remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `res_state` int DEFAULT '0' COMMENT '学生状态标记：\r\n0正常 1晚归 2旷课 3内务乱 4多次或严重违纪 5其他情况 6虽多次违纪但近期表现良好',
  `res_status` int DEFAULT '0' COMMENT '0 正常 1 被删除',
  PRIMARY KEY (`res_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20044 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='学生表管理';


-- ----------------------------
-- Table structure for tb_resident_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_resident_info`;
CREATE TABLE `tb_resident_info` (
  `res_info_id` int NOT NULL AUTO_INCREMENT,
  `res_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `res_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `res_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `res_address_id` int NOT NULL,
  `info_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `info_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `info_status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`res_info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='学生的报修信息';



-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', 'admin');
INSERT INTO `tb_role` VALUES ('2', 'user');

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单管理--->设置id为X的菜单需要权限为1 | 0 才能在系统中进行采用';

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES ('1', '1', '1');
INSERT INTO `tb_role_menu` VALUES ('2', '2', '1');
INSERT INTO `tb_role_menu` VALUES ('3', '3', '1');
INSERT INTO `tb_role_menu` VALUES ('4', '4', '1');
INSERT INTO `tb_role_menu` VALUES ('5', '5', '1');
INSERT INTO `tb_role_menu` VALUES ('6', '6', '1');
INSERT INTO `tb_role_menu` VALUES ('7', '7', '1');
INSERT INTO `tb_role_menu` VALUES ('8', '8', '1');
INSERT INTO `tb_role_menu` VALUES ('9', '9', '1');
INSERT INTO `tb_role_menu` VALUES ('10', '10', '1');
INSERT INTO `tb_role_menu` VALUES ('11', '11', '1');
INSERT INTO `tb_role_menu` VALUES ('12', '12', '1');
INSERT INTO `tb_role_menu` VALUES ('13', '13', '1');
INSERT INTO `tb_role_menu` VALUES ('14', '14', '1');
INSERT INTO `tb_role_menu` VALUES ('15', '15', '1');
INSERT INTO `tb_role_menu` VALUES ('16', '16', '1');
INSERT INTO `tb_role_menu` VALUES ('17', '17', '1');
INSERT INTO `tb_role_menu` VALUES ('18', '18', '1');
INSERT INTO `tb_role_menu` VALUES ('19', '19', '1');
INSERT INTO `tb_role_menu` VALUES ('20', '20', '1');
INSERT INTO `tb_role_menu` VALUES ('21', '1', '2');
INSERT INTO `tb_role_menu` VALUES ('22', '2', '2');
INSERT INTO `tb_role_menu` VALUES ('23', '3', '2');
INSERT INTO `tb_role_menu` VALUES ('24', '4', '2');
INSERT INTO `tb_role_menu` VALUES ('25', '6', '2');
INSERT INTO `tb_role_menu` VALUES ('26', '7', '2');
INSERT INTO `tb_role_menu` VALUES ('27', '8', '2');
INSERT INTO `tb_role_menu` VALUES ('28', '9', '2');
INSERT INTO `tb_role_menu` VALUES ('29', '10', '2');
INSERT INTO `tb_role_menu` VALUES ('30', '11', '2');
INSERT INTO `tb_role_menu` VALUES ('31', '12', '2');
INSERT INTO `tb_role_menu` VALUES ('32', '13', '2');
INSERT INTO `tb_role_menu` VALUES ('33', '16', '2');
INSERT INTO `tb_role_menu` VALUES ('34', '17', '2');
INSERT INTO `tb_role_menu` VALUES ('35', '18', '2');
INSERT INTO `tb_role_menu` VALUES ('36', '19', '2');
INSERT INTO `tb_role_menu` VALUES ('37', '20', '2');
INSERT INTO `tb_role_menu` VALUES ('38', '21', '1');
INSERT INTO `tb_role_menu` VALUES ('39', '22', '1');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户管理---->能操作此系统的用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'admin', '74a33b018eed35ef359017a5a8420ab3', '系统管理员', null, '', '13789452389', '0', 'http://127.0.0.1:8088/file/6e41ba6046074eff94b15bc82777309e.jpg');
INSERT INTO `tb_user` VALUES ('2', 'user1', '60996b0823cef64f142f7899388fcb95', '用户一', null, null, '17800000000', '0', 'http://127.0.0.1:8088/file/67d5f4d1d49945eabd5d3b8e94c4e3fe.png');
INSERT INTO `tb_user` VALUES ('3', 'user2', '370bd9c39cf74b742a4fffa17c5abb97', '用户二', null, null, '17800001111', '0', 'http://127.0.0.1:8088/file/b64b738ca6574b77a510b74e01a0d532.jpg');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户角色表--->根据不同的用户分配不同的权限';


-- ----------------------------
-- Table structure for tb_visitor
-- ----------------------------
DROP TABLE IF EXISTS `tb_visitor`;
CREATE TABLE `tb_visitor` (
  `visitor_id` int NOT NULL AUTO_INCREMENT,
  `address_id` int NOT NULL,
  `vis_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vis_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vis_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vis_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访客的地址',
  `vis_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `vis_status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`visitor_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='访客管理';
