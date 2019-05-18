/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2018/5/27 15:18:22                           */
/*==============================================================*/


alter table MST_EQUIP
   drop constraint FK_MST_EQUIP_SITE;

alter table MST_ORG
   drop constraint FK_MST_ORG_DIST;

alter table MST_ORG
   drop constraint FK_MST_ORG_ORG;

alter table MST_ORG_REF
   drop constraint FK_MST_ORG_REF_ORG;

alter table MST_ORG_REF
   drop constraint FK_MST_ORG_REF_PARENT;

alter table MST_SITE
   drop constraint FK_MST_SITE_DIST;

alter table MST_SITE
   drop constraint FK_MST_SITE_ORG;

drop table MST_CODE cascade constraints;

drop table MST_DIST cascade constraints;

drop table MST_EQUIP cascade constraints;

drop table MST_ORG cascade constraints;

drop table MST_ORG_REF cascade constraints;

drop table MST_SITE cascade constraints;

/*==============================================================*/
/* Table: MST_CODE                                              */
/*==============================================================*/
create table MST_CODE 
(
   TYPE                 VARCHAR2(40)         not null,
   CODE                 VARCHAR2(40)         not null,
   NAME                 VARCHAR2(100)        not null,
   ATTR01               VARCHAR2(200),
   ATTR02               VARCHAR2(200),
   ATTR03               VARCHAR2(200),
   ATTR04               VARCHAR2(200),
   ATTR05               VARCHAR2(200),
   ATTR06               VARCHAR2(200),
   ATTR07               VARCHAR2(200),
   ATTR08               VARCHAR2(200),
   ATTR09               VARCHAR2(200),
   ATTR10               VARCHAR2(200),
   ORDER_SEQ            NUMBER(6),
   EXPIRED              TIMESTAMP,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MST_CODE primary key (TYPE, CODE)
);

/*==============================================================*/
/* Table: MST_DIST                                              */
/*==============================================================*/
create table MST_DIST 
(
   DIST_CODE            VARCHAR2(6)          not null,
   SJDM                 VARCHAR2(2)          not null,
   DSDM                 VARCHAR2(2)          not null,
   QXDM                 VARCHAR2(2)          not null,
   DIST_NAME            VARCHAR2(100)        not null,
   SJMC                 VARCHAR2(100),
   DSMC                 VARCHAR2(100),
   QXMC                 VARCHAR2(100),
   SHORT                VARCHAR2(40),
   LEAF                 VARCHAR2(1)          not null,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MST_DIST primary key (DIST_CODE)
);

/*==============================================================*/
/* Table: MST_EQUIP                                             */
/*==============================================================*/
create table MST_EQUIP 
(
   EQUIP_ID             VARCHAR2(32)         not null,
   EQUIP_TYPE           VARCHAR2(1)          not null,
   EQUIP_STATUS         VARCHAR2(1)          not null,
   SITE_ID              VARCHAR2(32)         not null,
   MODEL                VARCHAR2(40)         not null,
   MAKER                VARCHAR2(40)         not null,
   CONTACT              VARCHAR2(40),
   TEL                  VARCHAR2(24),
   INST_DATE            TIMESTAMP,
   CHECK_DATE           TIMESTAMP,
   CHECK_LINE           VARCHAR2(1),
   LAST_IP              VARCHAR2(40),
   LAST_TIME            TIMESTAMP,
   EXPIRED              TIMESTAMP,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MST_EQUIP primary key (EQUIP_ID)
);

/*==============================================================*/
/* Table: MST_ORG                                               */
/*==============================================================*/
create table MST_ORG 
(
   ORG_ID               VARCHAR2(32)         not null,
   ORG_CODE             VARCHAR2(11)         not null,
   ORG_NAME             VARCHAR2(100)        not null,
   ORG_TYPE             VARCHAR2(2)          not null,
   DIST_CODE            VARCHAR2(6)          not null,
   PARENT_ID            VARCHAR2(32),
   CONTACT              VARCHAR2(40),
   TEL                  VARCHAR2(24),
   FAX                  VARCHAR2(24),
   MAIL                 VARCHAR2(100),
   ADDRESS              VARCHAR2(100),
   POSTCODE             VARCHAR2(20),
   LEAF                 VARCHAR2(1)          not null,
   EXPIRED              TIMESTAMP,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MST_ORG primary key (ORG_ID)
);

/*==============================================================*/
/* Table: MST_ORG_REF                                           */
/*==============================================================*/
create table MST_ORG_REF 
(
   ORG_ID               VARCHAR2(32)         not null,
   PARENT_ID            VARCHAR2(32)         not null,
   constraint PK_MST_ORG_REF primary key (ORG_ID, PARENT_ID)
);

/*==============================================================*/
/* Table: MST_SITE                                              */
/*==============================================================*/
create table MST_SITE 
(
   SITE_ID              VARCHAR2(32)         not null,
   SITE_NAME            VARCHAR2(40)         not null,
   SITE_TYPE            VARCHAR2(1)          not null,
   DIST_CODE            VARCHAR2(6),
   ORG_ID               VARCHAR2(32)         not null,
   MASTER               VARCHAR2(40),
   LINE1                NUMBER(2)            not null,
   LINE2                NUMBER(2),
   PARKS                NUMBER(2),
   TOTAL_AREA           NUMBER(5,1),
   SITE_IMG             VARCHAR2(1000),
   TEL                  VARCHAR2(24),
   BUILD_DATE           TIMESTAMP,
   EXPIRED              TIMESTAMP,
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MST_SITE primary key (SITE_ID),
   constraint AK_MST_SITE_01 unique (SITE_NAME)
);

comment on column MST_SITE.SITE_TYPE is
'������ࣺ·������[LXLX]';

alter table MST_EQUIP
   add constraint FK_MST_EQUIP_SITE foreign key (SITE_ID)
      references MST_SITE (SITE_ID);

alter table MST_ORG
   add constraint FK_MST_ORG_DIST foreign key (DIST_CODE)
      references MST_DIST (DIST_CODE);

alter table MST_ORG
   add constraint FK_MST_ORG_ORG foreign key (PARENT_ID)
      references MST_ORG (ORG_ID);

alter table MST_ORG_REF
   add constraint FK_MST_ORG_REF_ORG foreign key (ORG_ID)
      references MST_ORG (ORG_ID);

alter table MST_ORG_REF
   add constraint FK_MST_ORG_REF_PARENT foreign key (PARENT_ID)
      references MST_ORG (ORG_ID);

alter table MST_SITE
   add constraint FK_MST_SITE_DIST foreign key (DIST_CODE)
      references MST_DIST (DIST_CODE);

alter table MST_SITE
   add constraint FK_MST_SITE_ORG foreign key (ORG_ID)
      references MST_ORG (ORG_ID);

