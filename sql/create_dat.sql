/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2018/5/26 16:51:26                           */
/*==============================================================*/

/*==============================================================*/
/* Table: DAT_CHECK_DATA                                        */
/*==============================================================*/
create table DAT_CHECK_DATA 
(
   CHECK_NO             VARCHAR2(32)         not null,
   CHECK_TYPE           VARCHAR2(1)          not null,
   SITE_ID              VARCHAR2(32)         not null,
   EQUIP_ID             VARCHAR2(32),
   LINE                 VARCHAR2(2),
   VEHICLE_NO           VARCHAR2(12),
   VEHICLE_TYPE         VARCHAR2(4),
   AXLES                NUMBER(1),
   TYRES                NUMBER(2),
   CHECK_RESULT         VARCHAR2(1)          not null,
   CHECK_BY             VARCHAR2(40),
   CHECK_TIME           TIMESTAMP            not null,
   SPEED                NUMBER(5,2),
   LIMIT_TOTAL          NUMBER(6),
   OVER_TOTAL           NUMBER(6),
   TOTAL                NUMBER(6)            default 0 not null,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_DAT_CHECK_DATA primary key (CHECK_NO)
);

