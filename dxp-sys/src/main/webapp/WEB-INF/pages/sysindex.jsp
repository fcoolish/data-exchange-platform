<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<html>
<head>
<base href='<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>' />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/dxp-sys/css/common-neptune.css" />
<link rel="stylesheet" type="text/css" href="/dxp-sys/extjs/resources/ext-theme-neptune/ext-theme-neptune-all.css" />
<!-- 
<link rel="stylesheet" type="text/css" href="/dxp-sys/css/common.css" />
<link rel="stylesheet" type="text/css" href="/dxp-sys/extjs/resources/ext-theme-classic/ext-theme-classic-all.css" />
-->
<script type="text/javascript" charset="utf-8" src="/dxp-sys/extjs/ext-all.gzjs"></script>
<script type="text/javascript" charset="utf-8" src="/dxp-sys/extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" charset="utf-8">

Ext.onReady(function(){

	Ext.onReady(function(){
		Ext.QuickTips.init();
		Ext.Loader.setConfig({
			enabled:true
		});
		/**
		 * Menu数据模型
		 */
	  	Ext.define('Menu',{
	  		extend:'Ext.data.Model', 
	  		fields:['id' ,'text' , 'type' , 'leaf', 'url']
	  	});
		/**
		 * Menu数据集合
		 */
	  	Ext.define('MenuStore',{
	  		extend:'Ext.data.TreeStore' , 
			model:'Menu' , 
			//nodeParam:'id' , 		//指定节点参数名称
			proxy:{
				type:'ajax' , 
				url:'sysFuncList.json' , 
				reader:'json' 
			},
			autoLoad:false
	  	});
		/**
		 * treepanel组件
		 */
		Ext.define('MenuTree',{
			extend:'Ext.tree.Panel' ,
			title:'功能管理',
			border:false ,
			height:400,
			rootVisible:false ,
			listeners:{		
				'itemclick':function(view, record , item , index , e ){
					var panel = Ext.getCmp('main');			//取得主页面的main tabpanel面板
					if(panel.down('#tab_'+record.get('id'))){			//如果存在tab组件,设置为激活的状态
						panel.setActiveTab(panel.down('#tab_'+record.get('id')));
					} else {								//如果不存在tab组件 新增一个
						var tab = panel.add({
							id:'tab_'+record.get('id') ,
							title:record.get('text') ,
							html:'<iframe src= '+record.get('url') +' width=100% height=100%  marginwidth=0 marginheight=0 hspace=0 frameborder =0 allowtransparency=yes></iframe>' ,
							closable:true 
						});			
						panel.setActiveTab(tab);			//把tab设置为当前激活的状态
					}
				}
			},
			tools : [{
						type:'refresh',
						qtip: '刷新',
						handler: function(event, toolEl, header){
							this.up('treepanel').store.load();
						}
			}],				
			initComponent:function(arguments){
				var self = this; 
				self.callParent(arguments);
			}
		});	
		
		
		var funcList = '${funcList}';
		var accordionList = Ext.JSON.decode(funcList);
		var menuTreePanelArr = [];
		Ext.Array.each(accordionList,function(item){
			var menuStore = Ext.create('MenuStore');
			menuStore.proxy.extraParams = {id : item.FUNC_CODE} ;
			menuStore.load();
			var menuTreePanel = Ext.create('MenuTree',{
				title: item.FUNC_NAME,
				iconCls:'func',
				store: menuStore
			});
			menuTreePanelArr.push(menuTreePanel);
		});
		
		Ext.create('Ext.container.Viewport',{
			layout:'border' , 				//设置Border布局
			title:'数据中心' , 
			defaults:{
				collapsible: true , 		//展开形式
				split:true ,				//是否能被拖拽
				bodyStyle:'padding:1px' 	//边距
			},
			items:[{
				title:'数据交换中心平台' ,  
				region:'north' ,
				height:100 ,
				html:'<br><center><font size=6>数据交换中心平台</font></center>'
			},{
				title:'菜单' ,
				xtype:'panel',
				layout:'accordion',
				items: menuTreePanelArr ,
				//bodyStyle:'padding:0px' , 
				region: 'west' ,
				width:250
			},{
				title:'主界面' ,
				layout:'fit' ,				//fit布局填充主页面
				region:'center' ,
				id:'main',
				xtype:'tabpanel'
			}]
		});

		
	});
	
});

</script>

</head>
<body>
</body>
</html>