CREATE OR REPLACE PACKAGE PKG_MST AUTHID CURRENT_USER IS
       
      /**************************************************************************
       -- Summary : 执行管理机构关系维护存储过程
       --           MST_ORG 和 MST_ORG_REF
      **************************************************************************/
      PROCEDURE PROC_ORG_CASE; 
END;



CREATE OR REPLACE PACKAGE BODY PKG_MST IS
       
      /**************************************************************************
       -- Summary : 执行管理机构关系维护存储过程
       --           MST_ORG 和 MST_ORG_REF
      **************************************************************************/
      PROCEDURE PROC_ORG_CASE IS 
            TYPE IDX_ORG_TABLE IS TABLE OF VARCHAR2(32) INDEX BY BINARY_INTEGER;  --定义表类型
            V_ORG_LIST IDX_ORG_TABLE;   --声明表类型变量 
            V_I BINARY_INTEGER;         --声明一个ineger变量 用于循环控制        
      BEGIN
            DELETE FROM MST_ORG_REF;  --删除全表
            --装载数据到表类型变量中去
            SELECT ORG_ID BULK COLLECT INTO V_ORG_LIST FROM MST_ORG  
                   START WITH PARENT_ID IS NULL
                   CONNECT BY PRIOR ORG_ID = PARENT_ID;
            --取得第一个索引值
            V_I := V_ORG_LIST.FIRST;    
            WHILE V_I IS NOT NULL LOOP
                 DECLARE 
                      V_ORGID MST_ORG.ORG_ID%TYPE := V_ORG_LIST(V_I);
                      --声明一个游标
                      CURSOR CUR_ORG_TREE IS
                             SELECT * FROM MST_ORG
                                      START WITH ORG_ID = V_ORGID     
                                      CONNECT BY PRIOR ORG_ID = PARENT_ID;
                      V_OT CUR_ORG_TREE%ROWTYPE;
                 BEGIN
                      -- FOR循环
                      FOR V_OT IN CUR_ORG_TREE LOOP
                          INSERT INTO MST_ORG_REF VALUES(V_OT.ORG_ID, V_ORGID);
                      END LOOP;       
                 END; 
                 -- 向下遍历                
                 V_I := V_ORG_LIST.NEXT(V_I);                 
            END LOOP;         
      END;


END;


