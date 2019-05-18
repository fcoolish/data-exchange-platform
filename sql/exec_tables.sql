/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2018/12/5 10:29:07                           */
/*==============================================================*/


alter table SYS_ROLE_FUNC
   drop constraint FK_SYS_ROLE_FUNC_FUNC;

alter table SYS_ROLE_FUNC
   drop constraint FK_SYS_ROLE_FUNC_ROLE;

alter table SYS_USER
   drop constraint FK_SYS_USER_ROLE;

drop table SYS_FILE cascade constraints;

drop table SYS_FUNC cascade constraints;

drop table SYS_LOG cascade constraints;

drop table SYS_PARAM cascade constraints;

drop table SYS_ROLE cascade constraints;

drop table SYS_ROLE_FUNC cascade constraints;

drop table SYS_TASK cascade constraints;

drop table SYS_USER cascade constraints;

/*==============================================================*/
/* Table: SYS_FILE                                              */
/*==============================================================*/
create table SYS_FILE 
(
   KEY                  VARCHAR2(32)         not null,
   TYPE                 VARCHAR2(1)          not null,
   NAME                 VARCHAR2(80)         not null,
   EXT                  VARCHAR2(8),
   BYTES                NUMBER(12)           not null,
   DATA                 BLOB                 not null,
   EXPIRED              TIMESTAMP,
   DESC_INFO            VARCHAR2(200),
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_FILE primary key (KEY)
);

/*==============================================================*/
/* Table: SYS_FUNC                                              */
/*==============================================================*/
create table SYS_FUNC 
(
   FUNC_CODE            VARCHAR2(40)         not null,
   FUNC_NAME            VARCHAR2(40),
   FUNC_TYPE            VARCHAR2(40),
   FUNC_PATH            VARCHAR2(40),
   ORDER_SEQ            NUMBER(10),
   DISABLE_FLAH         VARCHAR2(1),
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_FUNC primary key (FUNC_CODE)
);

/*==============================================================*/
/* Table: SYS_LOG                                               */
/*==============================================================*/
create table SYS_LOG 
(
   LOG_TASK             VARCHAR2(100)        not null,
   LOG_TIME             TIMESTAMP            not null,
   LOG_TEXT             VARCHAR2(1000)       not null,
   REF01                VARCHAR2(200),
   REF02                VARCHAR2(200),
   REF03                VARCHAR2(200),
   REF04                VARCHAR2(200),
   REF05                VARCHAR2(200),
   REF06                VARCHAR2(200),
   REF07                VARCHAR2(200),
   REF08                VARCHAR2(200),
   REF09                VARCHAR2(200),
   REF10                VARCHAR2(200)
);

/*==============================================================*/
/* Table: SYS_PARAM                                             */
/*==============================================================*/
create table SYS_PARAM 
(
   PARAM_CODE           VARCHAR2(40)         not null,
   PARAM_NAME           VARCHAR2(40),
   PARAM_VALUE          VARCHAR2(40),
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_PARAM primary key (PARAM_CODE)
);

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE 
(
   ROLE_CODE            VARCHAR(40)          not null,
   ROLE_NAME            VARCHAR(40),
   DISABLE_FLAG         VARCHAR(1),
   DESC_INFO            VARCHAR(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_ROLE primary key (ROLE_CODE)
);

/*==============================================================*/
/* Table: SYS_ROLE_FUNC                                         */
/*==============================================================*/
create table SYS_ROLE_FUNC 
(
   ROLE_CODE            VARCHAR2(40)         not null,
   FUNC_CODE            VARCHAR2(40)         not null,
   constraint PK_SYS_ROLE_FUNC primary key (ROLE_CODE, FUNC_CODE)
);

/*==============================================================*/
/* Table: SYS_TASK                                              */
/*==============================================================*/
create table SYS_TASK 
(
   TASK_CODE            VARCHAR2(40)         not null,
   TASK_NAME            VARCHAR2(100)        not null,
   LAST_TIME            TIMESTAMP,
   constraint AK_SYS_TASK_01 unique (TASK_CODE)
);

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER 
(
   USER_ID              VARCHAR2(40)         not null,
   PASSWORD             VARCHAR2(128)        not null,
   USER_NAME            VARCHAR2(40)         not null,
   ROLE_CODE            VARCHAR2(40),
   ORG_ID               VARCHAR2(32),
   EMAIL                VARCHAR2(40),
   LOGIN_COUNT          NUMBER(10),
   LAST_LOGIN_TIME      TIMESTAMP,
   LAST_LOGIN_IP        VARCHAR2(40),
   DISABLE_FLAG         VARCHAR2(1),
   DESC_INFO            VARCHAR2(200),
   CREATE_BY            VARCHAR2(40)         not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR2(40)         not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_USER primary key (USER_ID),
   constraint AK_SYS_USER_01 unique (USER_NAME)
);

alter table SYS_ROLE_FUNC
   add constraint FK_SYS_ROLE_FUNC_FUNC foreign key (FUNC_CODE)
      references SYS_FUNC (FUNC_CODE);

alter table SYS_ROLE_FUNC
   add constraint FK_SYS_ROLE_FUNC_ROLE foreign key (ROLE_CODE)
      references SYS_ROLE (ROLE_CODE);

alter table SYS_USER
   add constraint FK_SYS_USER_ROLE foreign key (ROLE_CODE)
      references SYS_ROLE (ROLE_CODE);

