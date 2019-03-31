<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<html>
<head>
<base href='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>' />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/dxp-mst/css/common-neptune.css" />
<link rel="stylesheet" type="text/css" href="/dxp-mst/extjs/resources/ext-theme-neptune/ext-theme-neptune-all.css" />
<!-- 
<link rel="stylesheet" type="text/css" href="/dxp-mst/css/common.css" />
<link rel="stylesheet" type="text/css" href="/dxp-mst/extjs/resources/ext-theme-classic/ext-theme-classic-all.css" />
-->
<script type="text/javascript" charset="utf-8" src="/dxp-mst/extjs/ext-all.gzjs"></script>
<script type="text/javascript" charset="utf-8" src="/dxp-mst/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="/dxp-mst/js/common.js"></script>
<script type="text/javascript" charset="utf-8">



Ext.onReady(function(){
	Ext.define('SiteForm', {
	    extend: 'Ext.form.Panel',
	    flag: undefined, 
	    record: undefined, 
	    bodyPadding: '10 10 10 10', 
	    renderTo: document.body,
	    fit:true,
	    border: false,
	    initComponent: function(){
	        var me = this;
	        me.items = [{
	            xtype:'hidden',
	            name:'SITE_ID',
	            value: me.record ? me.record.SITE_ID : ''
	        },{
	            xtype: 'fieldset',
	            title: '站点基本信息',
	            collapsible: false, 
	            defaults:{
	                labelAlign: "right",
	                labelWidth: 80,
	                margin: 5                               
	            },
	            items:[{
	                xtype: 'textfield',
	                name: 'SITE_NAME',
	                fieldLabel: '站点名称',
	                allowBlank: false,
	                width:400,
	                maxLength: 40,
	                value: me.record ? me.record.SITE_NAME : ''
	            },{
	                xtype: 'dxp_mstcode',
	                name: 'SITE_TYPE',
	                fieldLabel: '站点类型', 
	                allowBlank: false,
	                type: "SITE_TYPE",
	                width:400,
	                value: me.record ? me.record.SITE_TYPE : ''
	            },{
	                xtype: 'textfield',
	                name: 'DIST_CODE',
	                fieldLabel: '行政区划',
	                width:250,
	                value: me.record ? me.record.DIST_CODE : ''                           
	            },{
	                xtype: 'textfield',
	                name: 'ORG_ID',
	                fieldLabel: '管理机构',
	                width:250,
	                value: me.record ? me.record.ORG_ID : ''                           
	            },{
	                xtype: 'numberfield',
	                name: 'LINE1',
	                fieldLabel: '初检车道数',
	                width:250, allowBlank: false,
	                value: me.record ? me.record.LINE1 : '' ,
	                minValue:0,  maxValue:99
	            },{
	                xtype: 'numberfield',
	                name: 'LINE2',
	                fieldLabel: '复检车道数',
	                width:250, allowBlank: false,
	                value: me.record ? me.record.LINE2 : '' ,
	                minValue:0,  maxValue:99
	            }]
	        },{
	            xtype: 'fieldset',
	            title: '其他信息',
	            collapsible: false, 
	            defaults:{
	                labelAlign: "right",
	                labelWidth: 80,
	                margin: 5                               
	            },
	            items:[{
	                xtype: 'textfield',
	                name: 'MASTER',
	                fieldLabel: '站长',
	                width:250,
	                value: me.record ? me.record.MASTER : ''                           
	            },{
	                xtype: 'textfield',
	                name: 'TEL',
	                fieldLabel: '联系电话', 
	                width:250,
	                value: me.record ? me.record.TEL : '',
	                regex: /^(\d{2,4}-?)?\d{7,8}(-\d{1,5})?$|^\d{11}$/,
	                regexText: '手机号码必须为11位数字,电话格式必須符合!'
	            },{
	                xtype: 'numberfield',
	                name: 'PARKS',
	                fieldLabel: '停车卸货场数 ',
	                width:250,
	                value: me.record ? me.record.PARKS : ''                           
	            },{
	                xtype: 'numberfield',
	                name: 'TOTAL_AREA',
	                fieldLabel: '总占地面积<br>(平米)',
	                width:250,
	                value: me.record ? me.record.TOTAL_AREA : '',
	                maxValue:9999, decimalPrecision: 1                                                    
	            },{
	                xtype: 'datefield',
	                name: 'BUILD_DATE',
	                fieldLabel: '建站日期', 
	                width:250,
	                format:'Y-m-d',
	                value: me.record ? me.record.BUILD_DATE : ''                       
	            },
	            Ext.create("dxp.extjs.field.Files", {
	                name: 'SITE_IMG', 
	                width:400,  
	                type: '2', 
	                fieldLabel: "站点图片", 
	                height: 100, 
	                labelAlign: 'right', 
	                labelWidth: 80,
	                value: me.record ? me.record.SITE_IMG : ''
	            }),
	            {
	                xtype: 'textarea',
	                name: 'DESC_INFO',
	                fieldLabel: '备注',
	                maxLength:200,
	                width:400,
	                value: me.record ? me.record.DESC_INFO : ''
	            }]
	        }];
	        me.tbar = ['->',{
	            xtype: 'fieldcontainer',
	            items:[{
	                xtype:'button',
	                text:'保存',
	                iconCls:'save',
	                handler:function(btn){
	                    var basic= btn.up('form').getForm();
	                    if(basic.isValid()){
	                        Ext.Ajax.request({
	                            url:'site/detail.json',
	                            method:'POST',
	                            params:{
	                                jsonData: Ext.encode(basic.getValues()),
	                                flag: me.flag
	                            },
	                            timeout:5000,
	                            success: function(response){
	                                var result = Ext.decode(response.responseText);
	                                if(result.success){
                                        window.parent.showInfo(result.message);	
                                        window.parent.siteGrid.getStore().load({
                                        	callBack:function(){
                                        	}
                                        });	                                        
                                        if(me.flag == 'insert'){
                                            window.parent.addWindow.close();
                                        }
                                        if(me.flag == 'update'){
                                        	console.info(window.parent.editWindow); 
                                        	window.parent.editWindow.close();
                                        }	                                	
	                                } else {
	                                	window.parent.showInfo(result.message);	
	                                }
	                            },
	                            failure: function(response){
	                                var result = Ext.decode(response.responseText);
	                            }
	                        });
	                    }
	                }
	            }]
	        }];
	        me.callParent(arguments);
	    }
	});
	
	var flag = '${flag}', record;
	if(flag === 'insert'){
		record = undefined;
	}
	if(flag === 'update'){
		record = '${jsonData}';
		record = Ext.JSON.decode(record);
	}
    Ext.create('SiteForm',{
        flag: flag,
        record: record
    });
    
    
    
    
});

</script>

</head>
<body>
</body>
</html>