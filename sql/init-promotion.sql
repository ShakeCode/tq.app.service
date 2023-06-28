/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:5306
 Source Schema         : promotion

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 28/06/2023 23:13:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity_flow_m
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_flow_m`;
CREATE TABLE `t_activity_flow_m`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '活动编码',
  `flow_info_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '流程信息ID',
  `flow_version_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '流程版本ID',
  `current_node` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '当前节点名称',
  `node1_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '一级节点名称',
  `node2_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '二级节点名称',
  `node1_approve_user_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '一级审批人userid',
  `node2_approve_user_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '二级审批人userid',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `update_date` datetime(0) NOT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uqx_activity_flow_m_activity_code`(`activity_code`) USING BTREE,
  INDEX `inx_activity_flow_m_create_by`(`create_by`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '活动审批信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_info_e
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_info_e`;
CREATE TABLE `t_activity_info_e`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '活动编码',
  `setup_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开摊时间',
  `setup_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结摊时间',
  `activity_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '促销类型(sqcx,jtcx)',
  `audit_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '稽核层级(level0,level1,level2)',
  `resource_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源类型(uptown, enterprise)',
  `resource_code` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源编码',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `update_date` datetime(0) NOT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uqx_activity_info_e_activity_code`(`activity_code`) USING BTREE,
  INDEX `inx_activity_info_e_create_by`(`create_by`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1237208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '活动信息扩展表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_info_m
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_info_m`;
CREATE TABLE `t_activity_info_m`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '活动名称',
  `theme` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '活动主题',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `task_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务编码',
  `start_time` datetime(0) NOT NULL COMMENT '開始時間',
  `end_time` datetime(0) NOT NULL COMMENT '结束时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '状态(approval-待审批, waitExecute-待执行, execute-执行中,finished-已完成)',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地址',
  `location` geometry NULL COMMENT '经纬度',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '备注',
  `promotion_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '携带促销预热编码',
  `city_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地市编码',
  `district_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '区县编码',
  `area_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '网格编码',
  `area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '网格名称',
  `source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '来源(stall-摆摊, sandbox-沙箱)',
  `create_way` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建来源(1-手机, 2-web单个新增,3-pc导入)',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `update_date` datetime(0) NOT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uqx_activity_info_m_code`(`code`) USING BTREE,
  INDEX `inx_activity_info_status`(`status`) USING BTREE,
  INDEX `inx_activity_info_area_code`(`area_code`) USING BTREE,
  INDEX `inx_activity_info_city_code`(`city_code`) USING BTREE,
  INDEX `inx_activity_info_district_code`(`district_code`) USING BTREE,
  INDEX `inx_activity_info_create_by`(`create_by`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1251858 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '活动信息主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_relation_m
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_relation_m`;
CREATE TABLE `t_activity_relation_m`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '活动编码',
  `organization_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组织id',
  `organization_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组织区域编码',
  `organization_level` int(0) NULL DEFAULT NULL COMMENT '组织区域层级',
  `organization_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组织名称',
  `participant_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参与人useId',
  `participant_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参与人工号',
  `participant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参与人姓名',
  `charge` bit(1) NOT NULL COMMENT '是否活动负责人(0-否,1-是)',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `update_date` datetime(0) NOT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx_t_activity_relation_m_create_by`(`create_by`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3450000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '活动参与人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_api_m
-- ----------------------------
DROP TABLE IF EXISTS `t_api_m`;
CREATE TABLE `t_api_m`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `client_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '终端编码',
  `tenant_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '租户编码',
  `datasource_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数据源编码',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名字',
  `sql` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'sql',
  `status` bit(1) NULL DEFAULT NULL COMMENT '有效',
  `effect_date` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  `location` geometry NULL COMMENT '经纬度',
  `price` decimal(6, 4) NULL DEFAULT NULL COMMENT '价格',
  `describe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `miscro_service` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所述微服务',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `modify_date` datetime(0) NOT NULL COMMENT '修改时间',
  `extend_column` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_unique_t_api_code`(`tenant_code`, `code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'api编排定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_api_m
-- ----------------------------
INSERT INTO `t_api_m` VALUES (1, '1111', '112', 'stall-assistant', 'getUptownDataByCode', '根据编码获取小区数据', 'select\r\ncode, \r\nname \r\nfrom \r\nt_uptown_m \r\nwhere code=[code]', b'1', '2022-10-02 19:02:14', NULL, 12.3450, '根据编码获取小区数据', 'stall', 'zlj', '2022-08-28 15:30:54', 'zlj', '2022-08-28 15:31:08', NULL);

-- ----------------------------
-- Table structure for t_grid_m
-- ----------------------------
DROP TABLE IF EXISTS `t_grid_m`;
CREATE TABLE `t_grid_m`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
  `level` int(0) NULL DEFAULT NULL COMMENT '层级',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父级编码',
  `all_parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父级全路径编码',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(0) NULL DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '区域信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_grid_m
-- ----------------------------
INSERT INTO `t_grid_m` VALUES (1, 'GD', '广东', 1, '-1', '-1,GD,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 1);
INSERT INTO `t_grid_m` VALUES (2, 'DG', '东莞', 2, 'GD', '-1,GD,DG,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 1);
INSERT INTO `t_grid_m` VALUES (3, 'DG06', '东莞东部分公司', 3, 'DG', '-1,GD,DG,DG06,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 1);
INSERT INTO `t_grid_m` VALUES (4, 'DG0602', '大朗', 4, 'DG06', '-1,GD,DG,DG06,DG0602,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 1);
INSERT INTO `t_grid_m` VALUES (5, 'DG060201', '大朗责任田', 5, 'DG0602', '-1,GD,DG,DG06,DG0602,DG060201,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 1);
INSERT INTO `t_grid_m` VALUES (6, 'GZ', '广州', 2, 'GD', '-1,GD,GZ,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 0);
INSERT INTO `t_grid_m` VALUES (7, 'GZ06', '广州东部分公司', 3, 'GZ', '-1,GD,GZ,GZ06,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 0);
INSERT INTO `t_grid_m` VALUES (8, 'GZ0602', '天河', 4, 'GZ06', '-1,GD,GZ,GZ06,GZ0602,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 0);
INSERT INTO `t_grid_m` VALUES (9, 'GZ060201', '天河责任田', 5, 'GZ0602', '-1,GD,GZ,GZ06,GZ0602,GZ060201,', 'system', '2022-09-18 13:15:35', 'system', '2022-09-18 13:15:35', 1, 0);

-- ----------------------------
-- Table structure for t_uptown_t
-- ----------------------------
DROP TABLE IF EXISTS `t_uptown_t`;
CREATE TABLE `t_uptown_t`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `area_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_date` datetime(0) NOT NULL,
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `update_date` datetime(0) NOT NULL,
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `extend_config` json NULL COMMENT '扩展信息',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_index_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_uptown_t
-- ----------------------------
INSERT INTO `t_uptown_t` VALUES (2, '德馨小区', '12', 120, '', 'DG', '2021-12-17 18:17:49', 'lijun', '2021-12-17 18:17:57', 'lijun', NULL);
INSERT INTO `t_uptown_t` VALUES (3, '光华小区', '13', 121, '', 'GZ', '2021-12-17 18:17:49', 'lijun', '2021-12-17 18:17:57', 'lijun', NULL);
INSERT INTO `t_uptown_t` VALUES (4, '泰隆小区', '14', 121, '', 'ST', '2021-12-17 18:17:49', 'lijun', '2021-12-17 18:17:57', 'lijun', NULL);
INSERT INTO `t_uptown_t` VALUES (5, '猎德小区', '15', 121, '', 'GZ', '2021-12-17 18:17:49', 'lijun', '2021-12-17 18:17:57', 'lijun', NULL);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` int(0) NULL DEFAULT NULL,
  `birthday` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_m
-- ----------------------------
DROP TABLE IF EXISTS `t_user_m`;
CREATE TABLE `t_user_m`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地址',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `modify_date` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_m
-- ----------------------------
INSERT INTO `t_user_m` VALUES (1, 'jack', 12, 'shenzhen', 'system', '2022-11-27 17:36:38', 'system', '2022-11-27 17:36:43');
INSERT INTO `t_user_m` VALUES (2, 'brace', 20, 'dg', 'system', '2022-11-27 17:36:38', 'system', '2022-11-27 17:36:43');

-- ----------------------------
-- Procedure structure for split
-- ----------------------------
DROP PROCEDURE IF EXISTS `split`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `split`(in _string varchar(300))
BEGIN

-- 测试数据：  "-1,GD,DG,DG06,DG0602,DG060201"

# 求分割符号','的位置
declare _index int;
declare tem_id int;
 


#使用临时表存储分割后的结果
drop temporary table if exists tmp_strs;



create temporary table tmp_strs(
`id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID', 
level int(10) DEFAULT NULL  COMMENT '层级', 
str VARCHAR(50) NOT NULL  COMMENT '编码',
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='分割结果表';

-- 返回字符串,中第一次出现子字符串的位置
set _index = locate(',',_string);
set tem_id=1;
while _index > 0
do
#将子字符串存入临时表  -1, 
insert into tmp_strs values(tem_id,_index, left(_string,_index-1));
# -1,GD,DG,DG06,DG0602,DG060201 -> GD,DG,DG06,DG0602,DG060201
set _string =substr(_string from _index+1);
set _index = locate(',',_string);
set tem_id =tem_id+1;
end while;
 
-- 最后一个数据
if length(_string) >= 0 then
insert into tmp_strs values(tem_id ,_index,_string);
end if;

select * from tmp_strs order by LENGTH(str);

 
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for split_grid_code
-- ----------------------------
DROP PROCEDURE IF EXISTS `split_grid_code`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `split_grid_code`()
BEGIN


declare tem_code VARCHAR(50);
declare tem_name VARCHAR(50);
declare tem_level VARCHAR(50);
declare tem_parent_code VARCHAR(50);
declare tem_all_parent_code VARCHAR(50);

DECLARE done INT DEFAULT FALSE;

-- 声明游标
DECLARE cusr CURSOR FOR select code, name, level, parent_code, all_parent_code from tmp_grid_m where level ;

-- 声明游标停止事件标识
DECLARE
    CONTINUE HANDLER FOR NOT FOUND
SET
    done = TRUE;



#区域信息临时表
drop temporary table if exists tmp_grid_m;
create temporary table tmp_grid_m(
 `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `level` int DEFAULT NULL COMMENT '层级',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父级编码',
  `all_parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父级全路径编码'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='区域信息临时表';

-- 同步区域数据
insert into tmp_grid_m select code,name, level, parent_code,  all_parent_code from t_grid_m; 

select * from tmp_grid_m;

#区域转换结果信息临时表, 取出地市和区县编码为横表字段
drop temporary table if exists grid_result_m;
create temporary table grid_result_m(
  `city_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地市编码',
  `district_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区县编码',
	`grid_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
	`name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
	`level` int(11) DEFAULT NULL COMMENT '层级',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父级编码',
  `all_parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父级全路径编码'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='区域转换结果信息临时表';

-- 打开游标
open cusr;
	grid_loop: loop
    FETCH cusr INTO tem_code,tem_name,tem_level,tem_parent_code,tem_all_parent_code;
		if done then 
			leave grid_loop;
		end if;
		-- 插入基础数据
		insert into grid_result_m(grid_code,name,level,parent_code, all_parent_code) values(tem_code,tem_name,tem_level, tem_parent_code, tem_all_parent_code);


		# 转换地市编码,转换区县编码

		set @grid_code :=tem_code;
		set @level_num :=tem_level;

		update grid_result_m bb,
		(
		SELECT 
		id,temCode, parent_code, level+1 as fianl_level
		FROM ( 
				SELECT 
						@code AS temCode, 
						(SELECT @code := parent_code FROM t_grid_m WHERE code = temCode) AS parent_code, 
						 @level :=  @level -1 AS  level,
						 @id :=  @id +1 AS  id
				FROM 
						(SELECT @code :=@grid_code ,  @level :=@level_num, @id := 0) vars, 
						t_grid_m h 
				WHERE level >= 0
		) 
		T1 
		where level = 2
		) aa set bb.district_code= aa.temCode , bb.city_code = aa.parent_code where bb.grid_code=@grid_code;

	end loop;

-- 关闭游标
close cusr;

select * from grid_result_m;

drop temporary table if exists tmp_grid_m;
drop temporary table if exists grid_result_m;
 
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
