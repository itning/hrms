/*
 Navicat Premium Data Transfer

 Source Server         : dockervm
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : dockervm:3306
 Source Schema         : hrms

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 30/01/2021 17:24:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dep_department
-- ----------------------------
DROP TABLE IF EXISTS `dep_department`;
CREATE TABLE `dep_department`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dep_grassroot
-- ----------------------------
DROP TABLE IF EXISTS `dep_grassroot`;
CREATE TABLE `dep_grassroot`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `grassroot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK38wb3ft4um89xlflivi2vofxd`(`grassroot`) USING BTREE,
  CONSTRAINT `FK38wb3ft4um89xlflivi2vofxd` FOREIGN KEY (`grassroot`) REFERENCES `dep_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emp_form
-- ----------------------------
DROP TABLE IF EXISTS `emp_form`;
CREATE TABLE `emp_form`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fixed_ethnic
-- ----------------------------
DROP TABLE IF EXISTS `fixed_ethnic`;
CREATE TABLE `fixed_ethnic`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fixed_ethnic
-- ----------------------------
INSERT INTO `fixed_ethnic` VALUES ('01', '汉族');
INSERT INTO `fixed_ethnic` VALUES ('02', '蒙古族');
INSERT INTO `fixed_ethnic` VALUES ('03', '回族');
INSERT INTO `fixed_ethnic` VALUES ('04', '藏族');
INSERT INTO `fixed_ethnic` VALUES ('05', '维吾尔族');
INSERT INTO `fixed_ethnic` VALUES ('06', '苗族');
INSERT INTO `fixed_ethnic` VALUES ('07', '彝族');
INSERT INTO `fixed_ethnic` VALUES ('08', '壮族');
INSERT INTO `fixed_ethnic` VALUES ('09', '布依族');
INSERT INTO `fixed_ethnic` VALUES ('10', '朝鲜族');
INSERT INTO `fixed_ethnic` VALUES ('11', '满族');
INSERT INTO `fixed_ethnic` VALUES ('12', '侗族');
INSERT INTO `fixed_ethnic` VALUES ('13', '瑶族');
INSERT INTO `fixed_ethnic` VALUES ('14', '白族');
INSERT INTO `fixed_ethnic` VALUES ('15', '土家族');
INSERT INTO `fixed_ethnic` VALUES ('16', '哈尼族');
INSERT INTO `fixed_ethnic` VALUES ('17', '哈萨克族');
INSERT INTO `fixed_ethnic` VALUES ('18', '傣族');
INSERT INTO `fixed_ethnic` VALUES ('19', '黎族');
INSERT INTO `fixed_ethnic` VALUES ('20', '傈僳族');
INSERT INTO `fixed_ethnic` VALUES ('21', '佤族');
INSERT INTO `fixed_ethnic` VALUES ('22', '畲族');
INSERT INTO `fixed_ethnic` VALUES ('23', '高山族');
INSERT INTO `fixed_ethnic` VALUES ('24', '拉祜族');
INSERT INTO `fixed_ethnic` VALUES ('25', '水族');
INSERT INTO `fixed_ethnic` VALUES ('26', '东乡族');
INSERT INTO `fixed_ethnic` VALUES ('27', '纳西族');
INSERT INTO `fixed_ethnic` VALUES ('28', '景颇族');
INSERT INTO `fixed_ethnic` VALUES ('29', '柯尔克孜族');
INSERT INTO `fixed_ethnic` VALUES ('30', '土族');
INSERT INTO `fixed_ethnic` VALUES ('31', '达斡尔族');
INSERT INTO `fixed_ethnic` VALUES ('32', '仫佬族');
INSERT INTO `fixed_ethnic` VALUES ('33', '羌族');
INSERT INTO `fixed_ethnic` VALUES ('34', '布朗族');
INSERT INTO `fixed_ethnic` VALUES ('35', '撒拉族');
INSERT INTO `fixed_ethnic` VALUES ('36', '毛难族');
INSERT INTO `fixed_ethnic` VALUES ('37', '仡佬族');
INSERT INTO `fixed_ethnic` VALUES ('38', '锡伯族');
INSERT INTO `fixed_ethnic` VALUES ('39', '阿昌族');
INSERT INTO `fixed_ethnic` VALUES ('40', '普米族');
INSERT INTO `fixed_ethnic` VALUES ('41', '塔吉克族');
INSERT INTO `fixed_ethnic` VALUES ('42', '怒族');
INSERT INTO `fixed_ethnic` VALUES ('43', '乌孜别克族');
INSERT INTO `fixed_ethnic` VALUES ('44', '俄罗斯族');
INSERT INTO `fixed_ethnic` VALUES ('45', '鄂温克族');
INSERT INTO `fixed_ethnic` VALUES ('46', '崩龙族');
INSERT INTO `fixed_ethnic` VALUES ('47', '保安族');
INSERT INTO `fixed_ethnic` VALUES ('48', '裕固族');
INSERT INTO `fixed_ethnic` VALUES ('49', '京族');
INSERT INTO `fixed_ethnic` VALUES ('50', '塔塔尔族');
INSERT INTO `fixed_ethnic` VALUES ('51', '独龙族');
INSERT INTO `fixed_ethnic` VALUES ('52', '鄂伦春族');
INSERT INTO `fixed_ethnic` VALUES ('53', '赫哲族');
INSERT INTO `fixed_ethnic` VALUES ('54', '门巴族');
INSERT INTO `fixed_ethnic` VALUES ('55', '珞巴族');
INSERT INTO `fixed_ethnic` VALUES ('56', '基诺族');
INSERT INTO `fixed_ethnic` VALUES ('97', '其他');
INSERT INTO `fixed_ethnic` VALUES ('98', '外国血统');

-- ----------------------------
-- Table structure for fixed_ps
-- ----------------------------
DROP TABLE IF EXISTS `fixed_ps`;
CREATE TABLE `fixed_ps`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fixed_ps
-- ----------------------------
INSERT INTO `fixed_ps` VALUES ('01', '中共党员');
INSERT INTO `fixed_ps` VALUES ('02', '中共预备党员');
INSERT INTO `fixed_ps` VALUES ('03', '共青团员');
INSERT INTO `fixed_ps` VALUES ('04', '民革党员');
INSERT INTO `fixed_ps` VALUES ('05', '民盟盟员');
INSERT INTO `fixed_ps` VALUES ('06', '民建会员');
INSERT INTO `fixed_ps` VALUES ('07', '民进会员');
INSERT INTO `fixed_ps` VALUES ('08', '农工党党员');
INSERT INTO `fixed_ps` VALUES ('09', '致公党党员');
INSERT INTO `fixed_ps` VALUES ('10', '九三学社社员');
INSERT INTO `fixed_ps` VALUES ('11', '台盟盟员');
INSERT INTO `fixed_ps` VALUES ('12', '无党派人士');
INSERT INTO `fixed_ps` VALUES ('13', '群众');

-- ----------------------------
-- Table structure for job_level
-- ----------------------------
DROP TABLE IF EXISTS `job_level`;
CREATE TABLE `job_level`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_title
-- ----------------------------
DROP TABLE IF EXISTS `job_title`;
CREATE TABLE `job_title`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_category
-- ----------------------------
DROP TABLE IF EXISTS `post_category`;
CREATE TABLE `post_category`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_title
-- ----------------------------
DROP TABLE IF EXISTS `post_title`;
CREATE TABLE `post_title`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bankid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `birthday` datetime(0) NOT NULL,
  `bs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `certified_time` datetime(0) NULL DEFAULT NULL,
  `come_date` datetime(0) NOT NULL,
  `cp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ducation1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `duty_allowance` int(11) NULL DEFAULT NULL,
  `e_start_date` datetime(0) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fl_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `foreign_language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `graduated_school1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `graduated_school2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `graduation_time1` datetime(0) NULL DEFAULT NULL,
  `graduation_time2` datetime(0) NULL DEFAULT NULL,
  `grants` int(11) NULL DEFAULT NULL,
  `has_housing_fund` bit(1) NOT NULL,
  `hghest_degree` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `highest_education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `issuing_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `labor_contract1` datetime(0) NULL DEFAULT NULL,
  `labor_contract1end` datetime(0) NULL DEFAULT NULL,
  `labor_contract2` datetime(0) NULL DEFAULT NULL,
  `labor_contract2end` datetime(0) NULL DEFAULT NULL,
  `labor_contract3` datetime(0) NULL DEFAULT NULL,
  `labor_contract3end` datetime(0) NULL DEFAULT NULL,
  `m_allowance` int(11) NULL DEFAULT NULL,
  `marks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `naddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nature1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nature2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `oqc1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `oqc1time` datetime(0) NULL DEFAULT NULL,
  `oqc2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `other_certificates` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `p_subsidies` int(11) NULL DEFAULT NULL,
  `performance_pay` int(11) NOT NULL,
  `professional_title1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `professional_title2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ptc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ptc_time` datetime(0) NULL DEFAULT NULL,
  `rta` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `start_date` datetime(0) NOT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `th` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `wage` int(11) NOT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `employment_form` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ethnic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `grassroot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `position_category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `position_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ps` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKhtkbmaa9n0hutykbd06gk1cok`(`department`) USING BTREE,
  INDEX `FKel7t1x5rpisxp40fqvwh88oop`(`employment_form`) USING BTREE,
  INDEX `FK3mk1hd3bpd7ykhl4dnq5y7qyx`(`ethnic`) USING BTREE,
  INDEX `FKgxgiwnma1mi5r2c51hik676vu`(`grassroot`) USING BTREE,
  INDEX `FK9y26ymuyfptpq4wsr8f6sogow`(`job_level`) USING BTREE,
  INDEX `FK9d5299h1h9c8m7o3msqjle31y`(`job_title`) USING BTREE,
  INDEX `FKfymuyyw49l4ir51sfd991hcg5`(`position_category`) USING BTREE,
  INDEX `FKcbyrge2p7hc41t6dc26fa8su7`(`position_title`) USING BTREE,
  INDEX `FK2baa90xko2sl7pxcdyubmbqyp`(`ps`) USING BTREE,
  CONSTRAINT `FK2baa90xko2sl7pxcdyubmbqyp` FOREIGN KEY (`ps`) REFERENCES `fixed_ps` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK3mk1hd3bpd7ykhl4dnq5y7qyx` FOREIGN KEY (`ethnic`) REFERENCES `fixed_ethnic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK9d5299h1h9c8m7o3msqjle31y` FOREIGN KEY (`job_title`) REFERENCES `job_title` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK9y26ymuyfptpq4wsr8f6sogow` FOREIGN KEY (`job_level`) REFERENCES `job_level` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKcbyrge2p7hc41t6dc26fa8su7` FOREIGN KEY (`position_title`) REFERENCES `post_title` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKel7t1x5rpisxp40fqvwh88oop` FOREIGN KEY (`employment_form`) REFERENCES `emp_form` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfymuyyw49l4ir51sfd991hcg5` FOREIGN KEY (`position_category`) REFERENCES `post_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKgxgiwnma1mi5r2c51hik676vu` FOREIGN KEY (`grassroot`) REFERENCES `dep_grassroot` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhtkbmaa9n0hutykbd06gk1cok` FOREIGN KEY (`department`) REFERENCES `dep_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wage
-- ----------------------------
DROP TABLE IF EXISTS `wage`;
CREATE TABLE `wage`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `after_tax_real_hair` int(11) NOT NULL,
  `bonus` int(11) NOT NULL,
  `charge_back` int(11) NOT NULL,
  `housing_fund` int(11) NOT NULL,
  `medical_insurance` int(11) NOT NULL,
  `month` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `on_duty_subsidies` int(11) NOT NULL,
  `other_benefits` int(11) NOT NULL,
  `overtime_assistance` int(11) NOT NULL,
  `pension_insurance` int(11) NOT NULL,
  `personal_income_tax` int(11) NOT NULL,
  `replenishment` int(11) NOT NULL,
  `school_grant` int(11) NOT NULL,
  `should_made` int(11) NOT NULL,
  `student_allowance` int(11) NOT NULL,
  `teacher_day_allowance` int(11) NOT NULL,
  `teaching_bonus` int(11) NOT NULL,
  `time8` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `total1` int(11) NOT NULL,
  `total2` int(11) NOT NULL,
  `total_benefits` int(11) NOT NULL,
  `unemployment_insurance` int(11) NOT NULL,
  `unit_housing_fund` int(11) NOT NULL,
  `unit_injury_insurance` int(11) NOT NULL,
  `unit_maternity_insurance` int(11) NOT NULL,
  `unit_medical_insurance` int(11) NOT NULL,
  `unit_pension_insurance` int(11) NOT NULL,
  `unit_unemployment_insurance` int(11) NOT NULL,
  `withholding` int(11) NOT NULL,
  `year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `staff` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK66i2kirn20w1hj3ss0imio1oa`(`staff`) USING BTREE,
  CONSTRAINT `FK66i2kirn20w1hj3ss0imio1oa` FOREIGN KEY (`staff`) REFERENCES `staff` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
