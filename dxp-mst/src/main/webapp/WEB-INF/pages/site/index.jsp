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

var addWindow, editWindow, siteGrid;

Ext.onReady(function(){
	
	//1数据模型
      Ext.define('SiteModel',{
          extend:'Ext.data.Model' , 
          fields:[
            {name:'SITE_ID' ,       type:'string' },       //
            {name:'SITE_NAME',      type:'string' },       //站点名称
            {name:'SITE_TYPE' ,     type:'string' },       //站点类型
            {name:'DIST_CODE',      type:'string' },       //行政区划代码
            {name:'ORG_ID' ,        type:'string' },       //机构标识
            {name:'MASTER',         type:'string' },       //站长 
            {name:'LINE1',          type:'string' },       //初检车道数 
            {name:'LINE2',          type:'string' },       //复检车道数 
            {name:'PARKS',          type:'string' },       //停车卸货场数 
            {name:'TOTAL_AREA',     type:'string' },       //总占地面积(平米) 
            {name:'SITE_IMG',       type:'string' },       //站点图片附件信息
            {name:'TEL',            type:'string' },       //联系电话
            {name:'BUILD_DATE',     type:'date' , dateFormat:'Y-m-d' },       //建站日期
            {name:'EXPIRED',        type:'string' },       //过期时间
            {name:'DESC_INFO',      type:'string' },       //备注
            {name:'CREATE_BY' ,     type:'string' },       //
            {name:'CREATE_TIME' ,   type:'string' },       //  
            {name:'UPDATE_BY' ,     type:'string' },       //
            {name:'UPDATE_TIME' ,   type:'string' },       //
            {name:'DIST_NAME'   ,   type:'string' },       //行政区划名称
            {name:'SITE_TYPE_NAME', type:'string' },       //站点类型名称
            {name:'ORG_NAME',		type:'string'}
          ]
      });	
	
	//2数据集合
    var siteStore = Ext.create('Ext.data.Store',{
        model: 'SiteModel',
        proxy: {
            type: 'ajax',
            url: 'site/query.json', 
            reader: {
                type: 'json',
                root: 'rows',
                totalProperty: 'total'
            }
        },
        pageSize :20,
        start : 0,        
        autoLoad:true
    });	
	
	//3数据列表
	siteGrid = Ext.create('Ext.grid.Panel',{
		region:'center',
		store: siteStore,
		title:'站点列表信息',
		columnLines: true ,
        selType:'checkboxmodel',
        multiSelect:true ,		
		columns:[
			{xtype: 'rownumberer', text: '序号', align: 'center', width: 45 },
			{text:'站点名称', dataIndex:'SITE_NAME', align:'center', width:100},
			{text:'站点类型', dataIndex:'SITE_TYPE_NAME', align:'center', width:100},
			{text:'行政区划', dataIndex:'DIST_NAME', align:'center', width:100},
			{text:'所属机构', dataIndex:'ORG_NAME', align:'center', width:100},
			{text:'建站日期', dataIndex:'BUILD_DATE', align:'center', width: 150 , xtype:'datecolumn', format:'Y-m-d'},
			{text:'初检车道数', dataIndex:'LINE1', align:'center', width:100},
			{text:'复检车道数', dataIndex:'LINE2', align:'center', width:100},
			{text:'停车卸货场数', dataIndex:'PARKS', align:'center', width:100},
			{text:'总占地面积(平米)', dataIndex:'TOTAL_AREA', align:'center', width:100},
			{text:'站长', dataIndex:'MASTER', align:'center', width:100},
			{text:'联系电话', dataIndex:'TEL', align:'center', width:100}
		],
		dockedItems:[{
			xtype:'pagingtoolbar' ,
			dock:'bottom' ,
			store:siteStore,
			displayInfo:true
		}],
		tbar:[
		      '站点名称:',
		      { xtype: 'textfield', name: 'SITE_NAME', width: 80 },
		      '站点类型:',
		      { xtype: 'dxp_mstcode', name: 'SITE_TYPE', type: "SITE_TYPE", value: '', width: 70,
                defaultListConfig: { loadingHeight: 70, minWidth: 220, maxHeight: 300, shadow: 'sides'} 
              },
              '数据选择:',{ xtype: 'combobox', width: 80, name: 'EXPIRED', displayField: 'expiredValue', valueField: 'expiredId',
                  editable: false, queryMode: 'local', value: '', store: Ext.create('Ext.data.Store', {
                       fields: ['expiredId', 'expiredValue'], 
                       data: [{"expiredId": "0", "expiredValue": "启用数据"},{"expiredId": "1", "expiredValue": "禁用数据"}],
           	  })},              
	          { xtype: 'button', text: '清空', iconCls: 'clear', handler: function(btn){
                  var tb = btn.up('toolbar');
                  tb.down('[name=SITE_NAME]').reset();
                  tb.down('[name=SITE_TYPE]').reset();
                  tb.down('[name=EXPIRED]').reset();
	          }},			
			  { xtype: 'button', text: '查询', iconCls: 'query', handler: function(btn){
                  var tb = btn.up('toolbar'), 
                  siteName = tb.down('[name=SITE_NAME]').getValue(),
                  siteType = tb.down('[name=SITE_TYPE]').getValue();
                  expired = tb.down('[name=EXPIRED]').getValue();
	              btn.up('gridpanel').getStore().proxy.extraParams = {
	                  jsonData: Ext.encode({
	                	  siteName: siteName,
	                	  siteType: siteType,
	                	  expired: expired
	                  })
	              };
	              btn.up('gridpanel').getStore().loadPage(1);            	  
			  }},
			  '->',
			  {xtype:'button', text:'新增', iconCls:'add', handler:function(btn){
				  var href = '${pageContext.request.contextPath}/site/insert.html';
				  addWindow = Ext.widget('window', {title: '新增站点信息', modal: true, constrain: true, width: 500, height: 400,
                      html: "<iframe scrolling='auto' frameborder='0' width='100%' height='100%' src='" + href + "'></iframe>"
                  }).show();
			  }},
			  {xtype:'button', text:'编辑', iconCls:'edit', handler:function(btn){
                  var datas = btn.up('gridpanel').getSelectionModel().getSelection();
                  if (datas.length == 1) {
                      var record = datas[0];
                      var href = '${pageContext.request.contextPath}/site/update.html?jsonData=' + Ext.encode(record.getData());
                      editWindow = Ext.widget('window', {title: '编辑站点信息', modal: true, constrain: true, width: 500, height: 400, 
                          html: "<iframe scrolling='auto' frameborder='0' width='100%' height='100%' src='" + href + "'></iframe>"
                      }).show();                                       
                  } else {
                      showAlert('请选择一条记录进行编辑!');
                      return ;
                  }				  
			  }},
			  {xtype:'button', text:'禁用', iconCls:'remove', handler:function(){
				  
			  }},
        ]
	});
	
	//4viewport渲染
	var viewport = Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[siteGrid]
	});
	
	
	
});

</script>

</head>
<body>
</body>
</html>