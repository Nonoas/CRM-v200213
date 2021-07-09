/*
 Navicat Premium Data Transfer

 Source Server         : CRM系统
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 10/07/2021 02:00:52
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS "goods_info";
CREATE TABLE "goods_info" (
  "id" VARCHAR NOT NULL,
  "name" VARCHAR,
  "sell_price" DOUBLE,
  "purchase_price" DOUBLE,
  "quantity" DOUBLE,
  "min_discount" DOUBLE,
  "deduction" DOUBLE,
  "deduction_rate" DOUBLE,
  "base_unit" VARCHAR,
  "type" VARCHAR,
  "photo" VARCHAR,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO "goods_info" VALUES ('SP0005', '防晒霜A', 54.0, 20.0, 60.0, 0.8, 22.0, 0.1, 'g', '产品类', NULL);
INSERT INTO "goods_info" VALUES ('SP0009', '魔神章', 60.0, 0.0, 999950.0, 1.0, 0.0, 0.0, '次', '服务类', NULL);
INSERT INTO "goods_info" VALUES ('SP0001', '芦荟胶', 65.0, 50.0, 34.0, 0.0, 0.0, 0.0, '瓶', '产品类', NULL);
INSERT INTO "goods_info" VALUES ('0058', '萨丁按时大大撒大声地', 230.0, 0.0, 522.0, 0.0, 0.0, 0.0, '箱', '产品类', NULL);
INSERT INTO "goods_info" VALUES (5, 45, 54.0, 0.0, 0.0, 0.0, 0.0, 0.0, '', '产品类', NULL);

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS "goods_type";
CREATE TABLE "goods_type" (
  "id",
  "name",
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for login_info
-- ----------------------------
DROP TABLE IF EXISTS "login_info";
CREATE TABLE "login_info" (
  "id" VARCHAR (20) NOT NULL,
  "name" VARCHAR (10) NOT NULL,
  "password" VARCHAR,
  "root" INTEGER,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of login_info
-- ----------------------------
INSERT INTO "login_info" VALUES ('admin', '哈哈哈', 'ceb4f32325eda6142bd65215f4c0f371', 1);

-- ----------------------------
-- Table structure for order_details
-- ----------------------------
DROP TABLE IF EXISTS "order_details";
CREATE TABLE "order_details" (
  "order_id" VARCHAR(30) NOT NULL,
  "product_id" text(30) NOT NULL,
  "product_amount" integer(10),
  PRIMARY KEY ("order_id", "product_id")
);

-- ----------------------------
-- Records of order_details
-- ----------------------------
INSERT INTO "order_details" VALUES ('SP20200914134349105', 'SP0001', 1);
INSERT INTO "order_details" VALUES ('SP20200914134349105', '0058', 1);
INSERT INTO "order_details" VALUES ('SP20200914134854904', 'SP0009', 1);
INSERT INTO "order_details" VALUES ('SP20200914134854904', 'SP0001', 1);
INSERT INTO "order_details" VALUES ('SP20200922140701881', 'SP0009', 1);
INSERT INTO "order_details" VALUES ('SP20200922140701881', 'SP0001', 1);
INSERT INTO "order_details" VALUES ('SP20200927160640284', '0058', 1);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS "order_info";
CREATE TABLE "order_info" (
  "order_id" VARCHAR(30) NOT NULL,
  "user_id" varchar(30) NOT NULL,
  "datetime" datetime NOT NULL,
  "price" REAL NOT NULL,
  "transactor" TEXT(10) NOT NULL,
  "integral_cost" integer,
  "integral_get" integer,
  "pay_mode" TEXT(10),
  CONSTRAINT "order_pk" PRIMARY KEY ("order_id")
);

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO "order_info" VALUES ('SP20200914134349105', '0000', '2020-09-14 13:43:49', 295.0, '', 0, 0, '现金');
INSERT INTO "order_info" VALUES ('SP20200914134854904', '0000', '2020-09-14 13:48:54', 125.0, '', 0, 0, '现金');
INSERT INTO "order_info" VALUES ('SP20200922140701881', '0000', '2020-09-22 14:07:01', 125.0, '', 0, 0, '现金');
INSERT INTO "order_info" VALUES ('SP20200927160640284', '0000', '2020-09-27 16:06:40', 230.0, '', 0, 0, '现金');

-- ----------------------------
-- Table structure for package_content
-- ----------------------------
DROP TABLE IF EXISTS "package_content";
CREATE TABLE "package_content" (
  "pkg_id" VARCHAR NOT NULL,
  "goods_id" VARCHAR NOT NULL,
  "goods_amount" integer,
  PRIMARY KEY ("pkg_id", "goods_id")
);

-- ----------------------------
-- Records of package_content
-- ----------------------------
INSERT INTO "package_content" VALUES ('TC0002', 'SP0005', 3);
INSERT INTO "package_content" VALUES ('TC0002', 'SP0009', 6);
INSERT INTO "package_content" VALUES ('TC0003', '0058', 1);
INSERT INTO "package_content" VALUES ('TC84984', 'SP0005', 1);
INSERT INTO "package_content" VALUES ('TC84984', 'SP0009', 3);
INSERT INTO "package_content" VALUES ('TC84984', 'SP0001', 1);
INSERT INTO "package_content" VALUES ('TC84984', 5, 4);

-- ----------------------------
-- Table structure for package_info
-- ----------------------------
DROP TABLE IF EXISTS "package_info";
CREATE TABLE "package_info" (
  "id" VARCHAR NOT NULL,
  "name" VARCHAR,
  "integral_cost" INTEGER,
  "money_cost" DOUBLE,
  "min_discount" DOUBLE,
  "other" VARCHAR,
  "type" VARCHAR,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of package_info
-- ----------------------------
INSERT INTO "package_info" VALUES ('TC0002', '魔神掌B', 1000, 2480.0, 0.8, '', '服务类');
INSERT INTO "package_info" VALUES ('TC0003', '魔神掌A', 1000, 2480.0, 0.8, '六一儿童节项目', '服务类');
INSERT INTO "package_info" VALUES ('TC84984', '测试添加', 10, 789.0, 0.8, '', NULL);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE "sqlite_sequence" (
  "name",
  "seq"
);

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for staff_info
-- ----------------------------
DROP TABLE IF EXISTS "staff_info";
CREATE TABLE "staff_info" (
  "id",
  "name",
  "career",
  "telphone",
  "addrss",
  "other"
);

-- ----------------------------
-- Records of staff_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_goods
-- ----------------------------
DROP TABLE IF EXISTS "user_goods";
CREATE TABLE "user_goods" (
  "user_id" text NOT NULL,
  "goods_id" text NOT NULL,
  "amount" integer,
  PRIMARY KEY ("user_id", "goods_id")
);

-- ----------------------------
-- Records of user_goods
-- ----------------------------
INSERT INTO "user_goods" VALUES (20200029, 'SP0005', 8);

-- ----------------------------
-- Table structure for user_goods_order
-- ----------------------------
DROP TABLE IF EXISTS "user_goods_order";
CREATE TABLE "user_goods_order" (
  "user_id" text NOT NULL,
  "goods_id" TEXT,
  "amount" integer,
  "order_date" TEXT,
  "transactor" TEXT
);

-- ----------------------------
-- Records of user_goods_order
-- ----------------------------
INSERT INTO "user_goods_order" VALUES (20200029, 'SP0005', 1, '2020-08-21 15:35:38', 'HSS');
INSERT INTO "user_goods_order" VALUES (20200029, 'SP0009', 1, '2020-08-21 15:36:46', '');
INSERT INTO "user_goods_order" VALUES (20200029, 'SP0009', 7, '2020-08-21 15:38:31', 'AAQW');
INSERT INTO "user_goods_order" VALUES (20200030, 'SP0009', 1, '2021-06-10 12:46:08', '');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "user_info";
CREATE TABLE "user_info" (
  "id" VARCHAR (20) NOT NULL,
  "name" VARCHAR,
  "sex" VARCHAR,
  "birthday" DATE,
  "card_level" VARCHAR,
  "balance" DOUBLE (20,2),
  "discount" DOUBLE DEFAULT 1,
  "cumulative" DOUBLE,
  "address" VARCHAR,
  "integral" INTEGER,
  "telephone" VARCHAR,
  "idcard" VARCHAR (18),
  "career" VARCHAR,
  "email" VARCHAR,
  "other" VARCHAR,
  "admission_date" DATE,
  "photo" VARCHAR,
  PRIMARY KEY ("id"),
  UNIQUE ("id" ASC)
);

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO "user_info" VALUES (20200029, '小明', '保密', '1999-08-02', '普通会员', 10.33, 0.6, 190.0, '湖南省邵阳市', 100, 13569856547, 430527199905063245, '水果店老板娘', '1231@qq.com', '备注信息', '2020-02-02', NULL);
INSERT INTO "user_info" VALUES (20200030, '白展堂', '男', '1999-04-03', '超级会员', 9.36, 0.3, 987.0, '湖南省常德', 8, 15469857845, 420658198919893654, '无业游民', '8997@qq.com', '备注信息', '2020-02-05', NULL);
INSERT INTO "user_info" VALUES (7, '彭BY', '女', NULL, '普通会员', 0.0, 0.5, 0.0, '', 0, 18773950555, '', '', '', '', '2020-08-08', NULL);
INSERT INTO "user_info" VALUES ('A8', '阿萨德', '保密', NULL, '普通会员', 0.0, 1.0, 0.0, '', 0, 13569784512, '', '', '', '', '2020-08-21', NULL);
INSERT INTO "user_info" VALUES ('VIP9H5K2', '张三', '保密', '2020-08-22', '普通会员', 0.0, 0.97, 0.0, '', 0, 1356955215, '', '', '', '', '2020-08-22', NULL);
INSERT INTO "user_info" VALUES ('VIP9H5K3', '李四', '保密', NULL, '普通会员', 0.0, 0.86, 0.0, '', 0, 13596547888, '', '', '', '', '2020-08-22', NULL);

-- ----------------------------
-- Table structure for vip_level
-- ----------------------------
DROP TABLE IF EXISTS "vip_level";
CREATE TABLE "vip_level" (
  "id" VARCHAR NOT NULL,
  "name" VARCHAR NOT NULL,
  "yuan_per_integral" DOUBLE,
  "discount" DOUBLE DEFAULT (1),
  PRIMARY KEY ("id", "name")
);

-- ----------------------------
-- Records of vip_level
-- ----------------------------
INSERT INTO "vip_level" VALUES ('001', '普通会员', 50.0, 1.0);
INSERT INTO "vip_level" VALUES ('002', '超级会员', 20.0, 0.85);

-- ----------------------------
-- View structure for order_record
-- ----------------------------
DROP VIEW IF EXISTS "order_record";
CREATE VIEW "order_record" AS SELECT
	oi.order_id,
	oi.user_id,
	ui.name user_name,
	oi.datetime,
	oi.price,
	oi.integral_cost,
	oi.integral_get,
	oi.transactor,
	oi.pay_mode 
FROM
	order_info oi,
	user_info ui 
WHERE
	ui.id = oi.user_id;

-- ----------------------------
-- View structure for ug_order_record
-- ----------------------------
DROP VIEW IF EXISTS "ug_order_record";
CREATE VIEW "ug_order_record" AS SELECT
	ugo.order_date,
	ugo.user_id,
	ui.name user_name,
	'[' || gi.id || '] ' || gi.name goods,
	ugo.amount,
	ugo.transactor 
FROM
	user_goods_order ugo,
	goods_info gi,
	user_info ui 
WHERE
	ugo.user_id = ui.id 
	AND gi.id = ugo.goods_id;

-- ----------------------------
-- Triggers structure for table order_info
-- ----------------------------
CREATE TRIGGER "orderTigger"
AFTER DELETE
ON "order_info"
BEGIN

  DELETE from order_details WHERE order_details.order_id=old.order_id;
	
END;

PRAGMA foreign_keys = true;
