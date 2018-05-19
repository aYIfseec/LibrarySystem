/*
Navicat SQLite Data Transfer

Source Server         : sqlite
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2018-05-19 14:46:19
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for Administrator
-- ----------------------------
DROP TABLE IF EXISTS "main"."Administrator";
CREATE TABLE Administrator(
id integer primary key autoincrement,
password varchar);

-- ----------------------------
-- Table structure for Book
-- ----------------------------
DROP TABLE IF EXISTS "main"."Book";
CREATE TABLE "Book" (
"id"  integer PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  varchar NOT NULL,
"count"  integer,
"type"  varchar,
"author"  varchar,
"discount"  integer,
"hasLended"  integer,
"address"  varchar
);

-- ----------------------------
-- Table structure for Record
-- ----------------------------
DROP TABLE IF EXISTS "main"."Record";
CREATE TABLE "Record"(
id integer primary key autoincrement,
uid integer,
bid integer,
lendTime varchar,
returnTime varchar);

-- ----------------------------
-- Table structure for Remind
-- ----------------------------
DROP TABLE IF EXISTS "main"."Remind";
CREATE TABLE "Remind" (
"id"  integer PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT NULL,
"uid"  integer,
"bid"  integer,
"isRead"  INTEGER DEFAULT 0,
CONSTRAINT "uid" FOREIGN KEY ("uid") REFERENCES "User" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT "bid" FOREIGN KEY ("bid") REFERENCES "Book" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "main"."sqlite_sequence";
CREATE TABLE sqlite_sequence(name,seq);

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS "main"."User";
CREATE TABLE "User" (
"id"  integer NOT NULL,
"name"  varchar,
"password"  varchar,
"point"  integer,
PRIMARY KEY ("id" ASC)
);
