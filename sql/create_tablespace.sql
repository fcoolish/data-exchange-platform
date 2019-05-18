-- 01 创建临时表空间
create temporary tablespace bhz_temp 
tempfile 'D:\006_design\bhz_temp_01_20151130.dbf' size 100m autoextend on next 50m maxsize 200m;
--drop  tablespace bhz_temp including contents and datafiles;

-- 02 创建表空间
create tablespace bhz
datafile 'D:\006_design\bhz_01_20151130.dbf' size 200m autoextend on next 100m maxsize 400m;
--drop  tablespace bhz including contents and datafiles;
--alter tablespace bhz add datafile 'D:\006_design\bhz_02_20151130.dbf' size 200m autoextend on;

-- 03 创建用户并制定表空间
create user bhz identified by bhz
default tablespace bhz
temporary tablespace bhz_temp;

-- 04 赋权
grant dba to bhz;


 
