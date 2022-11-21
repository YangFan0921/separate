/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100418 (10.4.18-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : separate

 Target Server Type    : MySQL
 Target Server Version : 100418 (10.4.18-MariaDB)
 File Encoding         : 65001

 Date: 21/11/2022 23:16:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_course`;
CREATE TABLE `sys_course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `score` int NULL DEFAULT NULL COMMENT '学分',
  `times` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课时',
  `state` tinyint(1) NULL DEFAULT 0 COMMENT '是否开课',
  `teacher_id` int NULL DEFAULT NULL COMMENT '授课老师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_course
-- ----------------------------
INSERT INTO `sys_course` VALUES (1, '马克思主义基本原理', 5, '40', 0, 19);
INSERT INTO `sys_course` VALUES (2, '高等数学', 5, '38', 1, NULL);
INSERT INTO `sys_course` VALUES (3, '大学英语', 3, '36', 0, 12);
INSERT INTO `sys_course` VALUES (4, '大学语文', 4, '40', 0, 2);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('home', 'el-icon-s-home', 'icon');
INSERT INTO `sys_dict` VALUES ('manage', 'el-icon-s-grid', 'icon');
INSERT INTO `sys_dict` VALUES ('user', 'el-icon-user', 'icon');
INSERT INTO `sys_dict` VALUES ('role', 'el-icon-s-custom', 'icon');
INSERT INTO `sys_dict` VALUES ('menu', 'el-icon-menu', 'icon');
INSERT INTO `sys_dict` VALUES ('file', 'el-icon-document', 'icon');
INSERT INTO `sys_dict` VALUES ('news', 'el-icon-news', 'icon');
INSERT INTO `sys_dict` VALUES ('course', 'el-icon-collection', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小(Kb)',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件md5',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (13, 'Happy.png', 'png', 682, 'http://localhost:9090/file/0fe43ac3-b759-4ba0-9c70-29c8b6c94014.png', 'bc78cfca040a47efa8f0f51f4b2fcd9d', 0, 0);
INSERT INTO `sys_file` VALUES (14, '4766d9b0-3171-4fa3-9494-1517f5df42fa.png', 'png', 682, 'http://localhost:9090/file/0fe43ac3-b759-4ba0-9c70-29c8b6c94014.png', 'bc78cfca040a47efa8f0f51f4b2fcd9d', 1, 0);
INSERT INTO `sys_file` VALUES (15, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 0, 1);
INSERT INTO `sys_file` VALUES (16, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (17, 'Niko.png', 'png', 916, 'http://localhost:9090/file/143e8882-97d7-4d8f-9bbf-ce124300704b.png', '30a672c3976bd11c79116e21880bfa54', 1, 1);
INSERT INTO `sys_file` VALUES (18, 'Navi.png', 'png', 879, 'http://localhost:9090/file/72b256fa-196f-45e6-b70a-8e27daab23c8.png', '07862d6cdd1738b61b0d1c5d3199ba22', 1, 1);
INSERT INTO `sys_file` VALUES (19, 'favorites_2021_11_27.html', 'html', 36, 'http://localhost:9090/file/d2d594f7-76e9-40bf-a9c4-658b0014ba3d.html', '633a96230707c3f7774e27a070334926', 0, 1);
INSERT INTO `sys_file` VALUES (20, 'hsconf', '', 0, 'http://localhost:9090/file/884ec935-7143-48ac-bf06-ebd4bddb2b85.', '3c06f1ac7d18b46853f5f972c22fb7bf', 1, 1);
INSERT INTO `sys_file` VALUES (21, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (23, 'ZywOo.png', 'png', 722, 'http://localhost:9090/file/01ab76b1-2c70-404c-8765-b3a03b29e1fa.png', '2f07ab5939276aff3cdd1dbc43df53e0', 0, 1);
INSERT INTO `sys_file` VALUES (24, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 0, 1);
INSERT INTO `sys_file` VALUES (25, 'Navi1.png', 'png', 476, 'http://localhost:9090/file/3b86a9ee-8d46-4bbe-989d-af96b2986bd1.png', '3a93517a091f653d213bdaa567af0ef4', 0, 1);
INSERT INTO `sys_file` VALUES (26, 'Navi1.png', 'png', 476, 'http://localhost:9090/file/3b86a9ee-8d46-4bbe-989d-af96b2986bd1.png', '3a93517a091f653d213bdaa567af0ef4', 0, 1);
INSERT INTO `sys_file` VALUES (27, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (28, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (29, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 0, 1);
INSERT INTO `sys_file` VALUES (30, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 0, 1);
INSERT INTO `sys_file` VALUES (31, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (32, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 0, 1);
INSERT INTO `sys_file` VALUES (33, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 1, 1);
INSERT INTO `sys_file` VALUES (34, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (35, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 1, 1);
INSERT INTO `sys_file` VALUES (36, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (37, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (38, 'Happy.png', 'png', 682, 'http://localhost:9090/file/0fe43ac3-b759-4ba0-9c70-29c8b6c94014.png', 'bc78cfca040a47efa8f0f51f4b2fcd9d', 1, 1);
INSERT INTO `sys_file` VALUES (39, 'Niko.png', 'png', 916, 'http://localhost:9090/file/143e8882-97d7-4d8f-9bbf-ce124300704b.png', '30a672c3976bd11c79116e21880bfa54', 0, 1);
INSERT INTO `sys_file` VALUES (40, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 0, 1);
INSERT INTO `sys_file` VALUES (41, 'Happy.png', 'png', 682, 'http://localhost:9090/file/0fe43ac3-b759-4ba0-9c70-29c8b6c94014.png', 'bc78cfca040a47efa8f0f51f4b2fcd9d', 1, 1);
INSERT INTO `sys_file` VALUES (42, 'Happy.png', 'png', 682, 'http://localhost:9090/file/0fe43ac3-b759-4ba0-9c70-29c8b6c94014.png', 'bc78cfca040a47efa8f0f51f4b2fcd9d', 0, 1);
INSERT INTO `sys_file` VALUES (43, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (44, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (45, 'ZywOo.png', 'png', 722, 'http://localhost:9090/file/01ab76b1-2c70-404c-8765-b3a03b29e1fa.png', '2f07ab5939276aff3cdd1dbc43df53e0', 1, 1);
INSERT INTO `sys_file` VALUES (46, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (47, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (48, 'Niko.png', 'png', 916, 'http://localhost:9090/file/143e8882-97d7-4d8f-9bbf-ce124300704b.png', '30a672c3976bd11c79116e21880bfa54', 1, 1);
INSERT INTO `sys_file` VALUES (49, 'Navi.png', 'png', 879, 'http://localhost:9090/file/72b256fa-196f-45e6-b70a-8e27daab23c8.png', '07862d6cdd1738b61b0d1c5d3199ba22', 1, 1);
INSERT INTO `sys_file` VALUES (50, 'Navi1.png', 'png', 476, 'http://localhost:9090/file/3b86a9ee-8d46-4bbe-989d-af96b2986bd1.png', '3a93517a091f653d213bdaa567af0ef4', 1, 1);
INSERT INTO `sys_file` VALUES (51, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 1, 1);
INSERT INTO `sys_file` VALUES (52, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (53, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (54, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 1, 1);
INSERT INTO `sys_file` VALUES (55, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (56, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (57, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (58, 'Niko.png', 'png', 916, 'http://localhost:9090/file/143e8882-97d7-4d8f-9bbf-ce124300704b.png', '30a672c3976bd11c79116e21880bfa54', 1, 1);
INSERT INTO `sys_file` VALUES (59, 'Navi1.png', 'png', 476, 'http://localhost:9090/file/3b86a9ee-8d46-4bbe-989d-af96b2986bd1.png', '3a93517a091f653d213bdaa567af0ef4', 1, 1);
INSERT INTO `sys_file` VALUES (60, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (61, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (62, '索隆.jpg', 'jpg', 62, 'http://localhost:9090/file/dfab8bb1-1f1f-4ea8-a7ac-5e8d49a39606.jpg', '0b738951f6dbaed658577144fb3a1461', 1, 1);
INSERT INTO `sys_file` VALUES (63, '1581438139315144.jpg', 'jpg', 55, 'http://localhost:9090/file/58d3d58c-72fc-4134-a917-50629989dc4e.jpg', 'fd1a3f86be020b198a2fe420aa3f0c05', 0, 1);
INSERT INTO `sys_file` VALUES (64, '路飞.jpg', 'jpg', 406, 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', '9752f7585796b0c1e8af2c1f8439eaf4', 1, 1);
INSERT INTO `sys_file` VALUES (65, '屏幕截图(13).png', 'png', 1210, 'http://localhost:9090/file/c2c7e8e8-df77-4c9d-9cd0-2945fef1722f.png', 'f740a1caaf73e442be0c08603e2c52de', 0, 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `pid` int NULL DEFAULT NULL COMMENT '父级id',
  `page_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '主页', '/home', 'el-icon-s-home', '主页', NULL, 'Home');
INSERT INTO `sys_menu` VALUES (4, '系统管理', NULL, 'el-icon-s-grid', '系统管理', NULL, 'Manage');
INSERT INTO `sys_menu` VALUES (5, '用户管理', '/user', 'el-icon-user', '用户管理', 4, 'User');
INSERT INTO `sys_menu` VALUES (7, '角色管理', '/role', 'el-icon-s-custom', '角色菜单', 4, 'Role');
INSERT INTO `sys_menu` VALUES (8, '菜单管理', '/menu', 'el-icon-menu', '菜单管理', 4, 'Menu');
INSERT INTO `sys_menu` VALUES (9, '文件菜单', '/file', 'el-icon-document', '文件菜单', 4, 'File');
INSERT INTO `sys_menu` VALUES (10, '新闻管理', '/news', 'el-icon-news', '新闻管理', NULL, 'News');
INSERT INTO `sys_menu` VALUES (14, '课程管理', '/course', 'el-icon-collection', '课程管理', NULL, 'Course');
INSERT INTO `sys_menu` VALUES (15, '我的课程', '/myCourse', 'el-icon-news', '我的课程', NULL, 'MyCourse');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `flag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, '学生', '学生', 'ROLE_STUDENT');
INSERT INTO `sys_role` VALUES (9, '老师', '老师', 'ROLE_TEACHER');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (143, 2, 1);
INSERT INTO `sys_role_menu` VALUES (144, 2, 4);
INSERT INTO `sys_role_menu` VALUES (145, 2, 9);
INSERT INTO `sys_role_menu` VALUES (146, 2, 14);
INSERT INTO `sys_role_menu` VALUES (147, 2, 15);
INSERT INTO `sys_role_menu` VALUES (148, 9, 1);
INSERT INTO `sys_role_menu` VALUES (149, 9, 4);
INSERT INTO `sys_role_menu` VALUES (150, 9, 9);
INSERT INTO `sys_role_menu` VALUES (151, 9, 14);
INSERT INTO `sys_role_menu` VALUES (152, 9, 15);
INSERT INTO `sys_role_menu` VALUES (153, 1, 1);
INSERT INTO `sys_role_menu` VALUES (154, 1, 4);
INSERT INTO `sys_role_menu` VALUES (155, 1, 5);
INSERT INTO `sys_role_menu` VALUES (156, 1, 7);
INSERT INTO `sys_role_menu` VALUES (157, 1, 8);
INSERT INTO `sys_role_menu` VALUES (158, 1, 9);
INSERT INTO `sys_role_menu` VALUES (159, 1, 14);
INSERT INTO `sys_role_menu` VALUES (160, 1, 15);

-- ----------------------------
-- Table structure for sys_student_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_student_course`;
CREATE TABLE `sys_student_course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` int NOT NULL COMMENT '学生id',
  `course_id` int NOT NULL COMMENT '课程id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生选课表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_student_course
-- ----------------------------
INSERT INTO `sys_student_course` VALUES (1, 22, 1);
INSERT INTO `sys_student_course` VALUES (2, 22, 3);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `createtime` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'xCF+9k6J7JBNmO79nvwa7w==', '管理员', 'admin@qq.com', '15813149635', '广西南宁', '2022-01-03 23:53:50', 'http://localhost:9090/file/9a8d556d-96b5-4113-886f-50db3f535187.jpg', 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (2, 'test', 'nQDDbBceoIcTAjI2XvtQhQ==', '测试者', 'test@qq.com', '13115863241', '湖北武汉', '2022-11-15 17:25:45', 'http://localhost:9090/file/c2c7e8e8-df77-4c9d-9cd0-2945fef1722f.png', 'ROLE_TEACHER');
INSERT INTO `sys_user` VALUES (6, 'zhu', '111', '猪八戒', '111@qq.com', '13965369872', '广西崇左', '2022-01-04 17:05:17', NULL, NULL);
INSERT INTO `sys_user` VALUES (7, 'sun', '222', '孙悟空', '222@qq.com', '15896359638', '广西崇左', '2022-02-04 17:06:06', NULL, NULL);
INSERT INTO `sys_user` VALUES (8, 'tang', '333', '唐僧', '333@qq.com', '13962359638', '广西梧州', '2022-03-04 17:06:57', NULL, NULL);
INSERT INTO `sys_user` VALUES (9, 'sha', '444', '沙僧', '444@qq.com', '16912359638', '广西河池', '2022-04-04 17:07:39', NULL, NULL);
INSERT INTO `sys_user` VALUES (10, 'bai', '555', '白龙马', '555@qq.com', '19623859638', '广西桂林', '2022-05-04 17:08:17', NULL, NULL);
INSERT INTO `sys_user` VALUES (12, 'suolong', 'nQDDbBceoIcTAjI2XvtQhQ==', '索隆', '2999@qq.com', '29996325879', '广西柳州', '2022-05-04 18:07:42', NULL, 'ROLE_TEACHER');
INSERT INTO `sys_user` VALUES (19, 'linchong', 'nQDDbBceoIcTAjI2XvtQhQ==', '林冲', '1081@gmail.com', '10811111111', '山东梁山', '2022-06-05 23:34:24', NULL, 'ROLE_TEACHER');
INSERT INTO `sys_user` VALUES (20, 'lixin', '888888', '李信', 'lixin@163.com', '13863269621', '四川成都', '2022-06-06 21:10:10', '', NULL);
INSERT INTO `sys_user` VALUES (21, 'yase', '111111', '亚瑟', '111111@163.com', '13022693214', '江苏南京', '2022-08-06 21:10:10', '', NULL);
INSERT INTO `sys_user` VALUES (22, 'daji', '/3DiFezXKV/Ihg7PoOQetg==', '妲己', 'daji@163.com', '14563259322', '浙江杭州', '2022-09-06 21:23:08', '', 'ROLE_STUDENT');
INSERT INTO `sys_user` VALUES (23, 'zhaoyun', '/3DiFezXKV/Ihg7PoOQetg==', '赵云', 'zhaoyun@163.com', '12039654875', '浙江温州', '2022-09-06 21:23:08', '', 'ROLE_STUDENT');
INSERT INTO `sys_user` VALUES (26, 'register', '/3DiFezXKV/Ihg7PoOQetg==', '注册者', NULL, NULL, NULL, '2022-11-07 18:37:29', NULL, 'ROLE_STUDENT');

SET FOREIGN_KEY_CHECKS = 1;
