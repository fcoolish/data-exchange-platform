function ajaxForm(formPanel, url, params, fnSuccess, noReturn) {
    formPanel.getForm().submit({
        url : url,
        params : params,
        method : "POST",
        waitMsg : noReturn ? null : "处理中...",
        timeout : 300000,
        clientValidation : true,
        success : function(form, action) {
            if (noReturn) {
                return;
            }
            if (fnSuccess != null && Ext.isFunction(fnSuccess)) {
                fnSuccess.call(this, form, action, url, params);
            } else {
                showInfo("处理完成。");
            }
        },
        failure : function(form, action) {
            if (noReturn) {
                return;
            }
            switch (action.failureType) {
            case Ext.form.Action.CLIENT_INVALID: {
                showAlert("请修改红线处的错误数据。");
                break;
            }
            case Ext.form.Action.SERVER_INVALID: {
                var title = "警告信息";
                if (action.result.itemName) {
                    title = title + " - " + action.result.itemName;
                }
                var message = action.result.message;
                if (action.result.itemValue) {
                    message = message + "<br/>（" + action.result.itemValue + "）";
                }
                Ext.MessageBox.show({
                    title : title,
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.WARNING,
                    msg : message,
                    animEl : action.result.itemCode
                });
                break;
            }
            default: {
                var msg = null;
                if (action.response.responseText) {
                    var responseData = decodeJson(action.response.responseText);
                    if (Ext.isObject(responseData)) {
                        msg = responseData.message;
                    } else {
                        msg = action.response.responseText;
                    }
                } else {
                    msg = "系统发生异常或处理超时。";
                }
                msg = msg + "<br/>" + action.response.statusText + "[" + action.response.status + "]";
                showError(msg);
            }
            }
            ;
        }
    });
}

function ajaxUrl(url, params, fnSuccess, noReturn) {
    Ext.Ajax.request({
        url : url,
        method : "POST",
        waitMsg : noReturn ? null : "处理中...",
        timeout : 300000,
        params : params,
        success : function(response, options) {
            if (noReturn) {
                return;
            }
            var responseData = decodeJson(response.responseText);
            if (!Ext.isObject(responseData)) {
                if (fnSuccess != null && Ext.isFunction(fnSuccess)) {
                    fnSuccess.call(this, response, url, params);
                } else {
                    showError(response.responseText);
                }
            } else if (responseData.success) {
                if (fnSuccess != null && Ext.isFunction(fnSuccess)) {
                    fnSuccess.call(this, responseData, url, params);
                } else {
                    showInfo("处理完成。");
                }
            } else {
                var title = "警告信息";
                if (responseData.itemName) {
                    title = title + " - " + responseData.itemName;
                }
                var message = responseData.message;
                if (responseData.itemValue) {
                    message = message + "<br/>（" + responseData.itemValue + "）";
                }
                Ext.MessageBox.show({
                    title : title,
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.WARNING,
                    msg : message,
                    animEl : responseData.itemCode
                });
            }
        },
        failure : function(response, options) {
            if (noReturn) {
                return;
            }
            var responseData = decodeJson(response.responseText);
            if (Ext.isObject(responseData)) {
                msg = responseData.message;
            } else if (response.responseText) {
                msg = response.responseText;
            } else {
                msg = "系统发生异常或处理超时。";
            }
            msg = msg + "<br/>" + response.statusText + "[" + response.status + "]";
            showError(msg);
        }
    });
}

function showInfo(message, func) {
    Ext.MessageBox.show({
        title : "通知信息",
        buttons : Ext.MessageBox.OK,
        icon : Ext.MessageBox.INFO,
        msg : message,
        fn : func
    });
}

function showAlert(message, func) {
    Ext.MessageBox.show({
        title : "警告信息",
        buttons : Ext.MessageBox.OK,
        icon : Ext.MessageBox.WARNING,
        msg : message,
        fn : func
    });
}

function showError(message, func) {
    Ext.MessageBox.show({
        title : "异常信息",
        buttons : Ext.MessageBox.OK,
        icon : Ext.MessageBox.ERROR,
        msg : message,
        fn : func
    });
}

function showConfirm(message, func) {
    Ext.MessageBox.show({
        title : "确认信息",
        buttons : Ext.MessageBox.OKCANCEL,
        icon : Ext.MessageBox.QUESTION,
        msg : message,
        fn : func
    });
}

function decodeJson(jsonText) {
    var jsonObject = null;
    try {
        jsonObject = Ext.decode(jsonText);
    } catch (e) {
    }
    return jsonObject;
}

function getTagNameElements(tag, name) {
    if (tag == null) {
        return null;
    }
    var path = tag;
    if (name != null && name != "") {
        path = path + "[@name='" + name + "']";
    }
    return Ext.query(path);
}

function getTagNameIndexElement(tag, name, index) {
    var elements = getTagNameElements(tag, name);
    if (elements == null || elements.length < 1) {
        return null;
    }
    if (index == null) {
        return elements[0];
    }
    return elements[index];
}

function parseUrlParams(url) {
    var textArray = url.split("?");
    if (textArray = null || textArray.length < 2) {
        return null;
    }
    var params = textArray[1];
    return Ext.urlDecode(params, false);
}

function formatUrlParams(url, params) {
    var ret = url;
    if (params) {
        if (Ext.isArray(params)) {
            for (var i = 0; i < params.length; i++) {
                ret = Ext.urlAppend(ret, Ext.urlEncode(params[i]));
            }
        } else {
            ret = Ext.urlAppend(url, Ext.urlEncode(params));
        }
    }
    return ret;
}

function updatePanelFrame(panel, src, params) {
    if (!panel) {
        return;
    }
    var href = formatUrlParams(src, params);
    var el = panel.getEl();
    if (el) {
        var target = panel.getEl().dom.getElementsByTagName("iframe");
        if (target && target.length > 0) {
            target[0].src = href;
            return;
        }
    }
    var html = "<iframe scrolling='auto' frameborder='0' width='100%' height='100%' src='" + href + "'></iframe>";
    panel.update(html);
}

function releasePanelFrame(panel) {
    if (!panel) {
        return;
    }
    var el = panel.getEl();
    if (el) {
        var target = panel.getEl().dom.getElementsByTagName("iframe");
        if (target) {
            for (var i = 0; i < target.length; i++) {
                target[i].src = "";
            }
            return;
        }
    }
    panel.update("");
}

function showWindow(wid, width, height, title, href, closeHandler) {
    var html = "<iframe scrolling='auto' frameborder='0' width='100%' height='100%' src='" + href + "'></iframe>";
    showHtmlWindow(wid, width, height, title, html, closeHandler);
}

function showHtmlWindow(wid, width, height, title, html, closeHandler) {
    var win = Ext.WindowMgr.get(wid);
    if (win) {
        win.close();
    }
    win = new Ext.Window({
        id : wid,
        title : title,
        layout : "fit",
        width : width,
        height : height,
        modal : true,
        plain : true,
        html : html
    });
    win.on("beforeclose", function() {
        releasePanelFrame(win);
    });
    if (closeHandler && Ext.isFunction(closeHandler)) {
        win.on("close", closeHandler);
    }
    win.show();
}

function showDownloadWindow(url, params, descInfo) {
    var panel = Ext.create("Ext.panel.Panel", {
        border : false,
        html : descInfo ? descInfo : "请点击“下载”按钮获取指定的文件 ..."
    });
    var wid = "downloadWin";
    var win = Ext.WindowMgr.get(wid);
    if (win) {
        win.close();
    }
    win = new Ext.Window({
        id : wid,
        title : "文件下载",
        width : 300,
        height : 100,
        modal : true,
        closable : false,
        plain : true,
        layout : "fit",
        items : [ panel ],
        buttonAlign : "center",
        buttons : [ Ext.create("Ext.button.Button", {
            text : "下载",
            width : 60,
            height : 24,
            handler : function(btn) {
                updatePanelFrame(panel, url, params);
            }
        }), Ext.create("Ext.button.Button", {
            text : "取消",
            width : 60,
            height : 24,
            handler : function() {
                win.close();
            }
        }) ]
    });
    win.on("beforeclose", function() {
        releasePanelFrame(win);
    });
    win.show();
}

function showUploadWindow(url, params, func) {
    var file = Ext.create("Ext.form.field.File", {
        xtype : "filefield",
        name : "file",
        anchor : "100%",
        editable : false,
        allowBlank : false,
        buttonText : "选择"
    });
    var form = Ext.create("Ext.form.Panel", {
        bodyPadding : 4,
        layout : "anchor",
        items : [ file ]
    });
    var wid = "uploadWin";
    var win = Ext.WindowMgr.get(wid);
    if (win) {
        win.close();
    }
    win = new Ext.Window({
        id : wid,
        title : "文件上传",
        border : 0,
        width : 460,
        height : 110,
        modal : true,
        closable : false,
        plain : true,
        layout : "fit",
        items : [ form ],
        buttonAlign : "center",
        buttons : [ Ext.create("Ext.button.Button", {
            text : "上传",
            width : 60,
            height : 24,
            handler : function(btn) {
                if (Ext.isEmpty(file.getValue())) {
                    showAlert("请选择要上传的文件！");
                    return;
                }
                ajaxForm(form, url, params, function(form, action) {
                    if (func && Ext.isFunction(func)) {
                        var data = decodeJson(action.response.responseText);
                        func.call(this, data);
                    }
                    win.close();
                });
            }
        }), Ext.create("Ext.button.Button", {
            text : "取消",
            width : 60,
            height : 24,
            handler : function() {
                win.close();
            }
        }) ]
    });
    win.on("beforeclose", function() {
        releasePanelFrame(win);
    });
    win.show();
}



//CommonComponents create

Ext.ns("dxp", "dxp.extjs", "dxp.extjs.field");
Ext.QuickTips.init();


Ext.define("dxp.extjs.field.DefaultComboBox", {

    alias : "widget.dxp_combo",

    extend : "Ext.form.field.ComboBox",

    constructor : function(config) {
        this.callParent([ config ]);
    },

    setValue : function(value, doSelect) {
        if (this.queryMode == "remote" && (this.store.getCount() == 0 || this.hasLoad == false)) {
            this.doQuery(this.allQuery, true);
            return;
        }
        this.callParent([ value, doSelect ]);
    },

    reload : function(params, func) {
        var me = this;
        var store = me.getStore();
        store.getProxy().extraParams = params;
        store.load({
            params : params,
            callback : function() {
                me.setValue(null);
                if (func) {
                    func.call(this, params);
                }
            }
        });
    }
});

Ext.define("dxp.extjs.field.Year", {

    alias : "widget.dxp_year",

    extend : "dxp.extjs.field.DefaultComboBox",

    constructor : function(config) {

        config = config || {};

        var allowBlank = true;
        if (config.allowBlank == false) {
            allowBlank = false;
        }

        var max = config.maxValue ? config.maxValue : (new Date()).getFullYear();
        var min = config.minValue ? config.minValue : 2010;
        var data = [];
        if (allowBlank) {
            data.push({
                year : "",
                text : config.blankText ? config.blankText : "任意"
            });
        }
        var suffix = config.suffix ? config.suffix : "";
        for (var y = max; y >= min; y--) {
            data.push({
                year : y,
                text : y + suffix
            });
        }

        var defaultConfig = {
            name : "year",
            queryMode : "local",
            valueField : "year",
            displayField : "text",
            fieldLabel : "年份",
            labelAlign : "right",
            labelWidth : 35,
            width : 95,
            editable : false,
            allowBlank : allowBlank,
            value : max,
            store : Ext.create("Ext.data.JsonStore", {
                fields : [ "year", "text" ],
                data : data
            })
        };
        config = Ext.applyIf(config, defaultConfig);

        this.callParent([ config ]);
    },

    getValue : function() {
        var v = this.callParent();
        if (Ext.isEmpty(v)) {
            v = null;
        }
        return v;
    }
});

Ext.define("dxp.extjs.field.Season", {

    alias : "widget.dxp_season",

    extend : "dxp.extjs.field.DefaultComboBox",

    constructor : function(config) {

        config = config || {};

        var allowBlank = true;
        if (config.allowBlank == false) {
            allowBlank = false;
        }

        var data = [];
        if (allowBlank) {
            data.push({
                season : "",
                text : config.blankText ? config.blankText : "任意"
            });
        }
        data.push({
            season : 1,
            text : "一季度"
        });
        data.push({
            season : 2,
            text : "二季度"
        });
        data.push({
            season : 3,
            text : "三季度"
        });
        data.push({
            season : 4,
            text : "四季度"
        });

        var defaultConfig = {
            name : "season",
            queryMode : "local",
            valueField : "season",
            displayField : "text",
            fieldLabel : "季度",
            labelAlign : "right",
            labelWidth : 35,
            width : 105,
            editable : false,
            allowBlank : allowBlank,
            store : Ext.create("Ext.data.JsonStore", {
                fields : [ "season", "text" ],
                data : data
            })
        };
        config = Ext.applyIf(config, defaultConfig);

        this.callParent([ config ]);
    },

    getValue : function() {
        var v = this.callParent();
        if (Ext.isEmpty(v)) {
            v = null;
        }
        return v;
    }
});

Ext.define("dxp.extjs.field.Month", {

    alias : "widget.dxp_month",

    extend : "dxp.extjs.field.DefaultComboBox",

    constructor : function(config) {

        config = config || {};

        var allowBlank = true;
        if (config.allowBlank == false) {
            allowBlank = false;
        }

        var data = [];
        if (allowBlank) {
            data.push({
                month : "",
                text : config.blankText ? config.blankText : "任意"
            });
        }
        for (var i = 1; i <= 12; i++) {
            data.push({
                month : i,
                text : i + "月"
            });
        }

        var defaultConfig = {
            name : "month",
            queryMode : "local",
            valueField : "month",
            displayField : "text",
            fieldLabel : "月份",
            labelAlign : "right",
            labelWidth : 35,
            width : 95,
            editable : false,
            allowBlank : allowBlank,
            store : Ext.create("Ext.data.JsonStore", {
                fields : [ "month", "text" ],
                data : data
            })
        };
        config = Ext.applyIf(config, defaultConfig);

        this.callParent([ config ]);
    },

    getValue : function() {
        var v = this.callParent();
        if (Ext.isEmpty(v)) {
            v = null;
        }
        return v;
    }
});

Ext.define("dxp.extjs.field.Check", {

    alias : "widget.dxp_check",

    extend : "dxp.extjs.field.DefaultComboBox",

    constructor : function(config) {

        config = config || {};

        var allowBlank = true;
        if (config.allowBlank == false) {
            allowBlank = false;
        }

        var data = [];
        if (allowBlank) {
            data.push({
                id : "",
                text : config.blankText ? config.blankText : "任意"
            });
        }
        data.push({
            id : "1",
            text : config.yesText ? config.yesText : "是"
        });
        data.push({
            id : "0",
            text : config.noText ? config.noText : "否"
        });

        var defaultConfig = {
            queryMode : "local",
            valueField : "id",
            displayField : "text",
            fieldLabel : "判断",
            labelAlign : "right",
            editable : false,
            allowBlank : allowBlank,
            labelWidth : 35,
            width : 95,
            store : Ext.create("Ext.data.JsonStore", {
                fields : [ "id", "text" ],
                data : data
            })
        };
        config = Ext.applyIf(config, defaultConfig);

        this.callParent([ config ]);
    },

    getValue : function() {
        var v = this.callParent();
        if (Ext.isEmpty(v)) {
            v = null;
        }
        return v;
    }
});

Ext.define("dxp.extjs.field.PageBar", {

    alias : "widget.dxp_page",

    extend : "Ext.toolbar.Paging",

    constructor : function(config) {

        var defaultPageSize = config.pageSize;
        if (!defaultPageSize || defaultPageSize < 1) {
            defaultPageSize = 20;
        }

        var dataStore = config.store;
        if (dataStore) {
            dataStore.pageSize = defaultPageSize;
        }

        var pageCombo = Ext.create("dxp.extjs.field.DefaultComboBox", {
            store : Ext.create("Ext.data.SimpleStore", {
                fields : [ "page" ],
                data : [ [ 10 ], [ 20 ], [ 50 ], [ 100 ], [ 150 ], [ 200 ] ]
            }),
            queryMode : "local",
            displayField : "page",
            valueField : "page",
            editable : false,
            allowBlank : false,
            width : 50,
            value : defaultPageSize,
            listeners : {
                select : function(comboBox, record, index) {
                    var target = comboBox.ownerCt;
                    var pSize = parseInt(comboBox.getValue());
                    target.getStore().pageSize = pSize;
                    target.pageSize = pSize;
                    target.doRefresh();
                }
            }
        });

        var defaultConfig = {
            pageSize : defaultPageSize,
            displayInfo : true,
            items : [ "-", "每页", pageCombo, "条" ]
        };

        config = Ext.applyIf(config, defaultConfig);
        this.callParent([ config ]);
    }
});


Ext.define("dxp.extjs.field.MstCode", {

    alias : "widget.dxp_mstcode",

    extend : "dxp.extjs.field.DefaultComboBox",

    constructor : function(config) {

        config = config || {};

        var allowBlank = true;
        if (config.allowBlank == false) {
            allowBlank = false;
        }

        var data = null;
        if (allowBlank) {
            data = [ {
                TYPE : config.type,
                CODE : "",
                NAME : config.blankText ? config.blankText : "任意"
            } ];
        }

        var store = Ext.create("Ext.data.JsonStore", {
            fields : [ "TYPE", "CODE", "NAME", "ATTR01", "ATTR02", "ATTR03", "ATTR04", "ATTR05", "ATTR06", "ATTR07",
                    "ATTR08", "ATTR09", "ATTR10" ],
            data : data,
            proxy : {
                type : "ajax",
                url : "comm/mst/code/list.json",
                extraParams : {
                    type : config.type,
                    code : config.code,
                    allowBlank : allowBlank,
                    expired : config.expired ? false : true
                }
            }
        });

        var me = this;
        store.on("load", function() {
            me.hasLoad = true;
            if (me.allowBlank) {
                store.insert(0, [ {
                    TYPE : me.type,
                    CODE : "",
                    NAME : config.blankText ? config.blankText : "任意"
                } ]);
            }
        });

        var defaultConfig = {
            name : "code",
            valueField : "CODE",
            displayField : "NAME",
            labelAlign : "right",
            labelWidth : 60,
            width : 140,
            editable : false,
            allowBlank : allowBlank,
            hasLoad : false,
            store : store
        };
        config = Ext.applyIf(config, defaultConfig);

        this.callParent([ config ]);
    }
});



Ext.define("dxp.extjs.field.Files", {

    alias : "widget.dxp_files",

    extend : "Ext.form.FieldContainer",

    initComponent : function() {

        var config = this;

        var me = this;

        var readOnly = false;
        if (me.readOnly == true) {
            readOnly = true;
        }

        var multi = true;
        if (me.multi == false) {
            multi = false;
        }

        var expired = true;
        if (me.expired == false) {
            expired = false;
        }

        var inline = true;
        if (me.inline == false) {
            inline = false;
        }

        var width = me.width ? me.width : 400;
        var height = (multi) ? 130 : 22;
        height = me.height ? me.height : height;

        var lwidth = 0;
        if (!Ext.isEmpty(me.fieldLabel)) {
            lwidth = (me.labelWidth ? me.labelWidth : 60) + 5;
        }

        var pct = Math.ceil(2400 / (width - lwidth));

        var store = Ext.create("Ext.data.JsonStore", {
            fields : [ "key", "name", "type", "bytes", "dataPath", "dataGroup", "expired" ],
            proxy : {
                type : "ajax",
                url : "comm/file/list.json"
            }
        });

        var hidden = Ext.create("Ext.form.field.Hidden", {
            name : me.name ? me.name : "files",
            getRawValue : function() {
                var v = null;
                store.each(function(rec) {
                    if (v == null) {
                        v = rec.get("key");
                    } else {
                        v = v + "," + rec.get("key");
                    }
                });
                return v;
            },
            setRawValue : function(v) {
                var keys = v;
                if (Ext.isEmpty(keys)) {
                    return;
                }
                if (!me.multi && keys.length > 32) {
                    keys = keys.substr(0, 32);
                }
                me.getStore().load({
                    params : {
                        keys : keys
                    }
                });
            }
        });

        var tools = Ext.create("Ext.toolbar.Toolbar", {
            padding : "0 1",
            height : 22,
            hidden : readOnly,
            baseCls : "backclear",
            items : [ {
                xtype : "button",
                text : multi ? "添加" : "上传",
                iconCls : "add",
                margin : 1,
                border : 1,
                style : {
                    borderColor : "#d1d1d1"
                },
                height : 20,
                handler : function() {
                    showUploadWindow("comm/file/put.json", {
                        type : me.type,
                        field : me.field,
                        expired : expired
                    }, function(ret) {
                        if (!me.multi) {
                            store.removeAll();
                        }
                        store.add({
                            key : ret.key,
                            type : ret.type,
                            name : ret.name,
                            bytes : ret.bytes,
                            dataPath: ret.dataPath,
                            dataGroup: ret.dataGroup,
                            expired : ret.expired
                        });
                    });
                }
            }, "->", {
                xtype : "button",
                text : multi ? "清空" : "删除",
                iconCls : multi ? "clear" : "delete",
                margin : 1,
                border : 1,
                style : {
                    borderColor : "#d1d1d1"
                },
                height : 20,
                handler : function() {
                    store.removeAll();
                }
            } ]
        });

        var grid = Ext.create("Ext.grid.Panel", {
            forceFit : true,
            hideHeaders : true,
            stripeRows : true,
            enableColumnResize : false,
            columnLines : multi,
            rowLines : multi,
            store : store,
            bbar : (multi ? tools : null),
            rbar : (multi ? null : tools),
            columns : [ {
                width : 100 - pct,
                renderer : function(v, meta, rec, row, col, store, view) {
                    var size = rec.get("bytes");
                    if (size) {
                        if (size >= 1048576) {
                            size = " [" + Math.round(size * 10 / 1048576) / 10 + "MB]";
                        } else if (size >= 1024) {
                            size = " [" + Math.round(size * 10 / 1024) / 10 + "KB]";
                        } else {
                            size = " [" + size + "B]";
                        }
                    } else {
                        size = "";
                    }
                    var ret = "<a href='" + rec.get("dataPath");
                    ret = ret  + "'";
                    if (me.inline) {
                        ret = ret + " target='blank'";
                    }
                    ret = ret + ">";
                    ret = ret + rec.get("name") + size + "<a>";
                    return ret;
                }
            }, {
                xtype : "actioncolumn",
                align : "center",
                width : pct,
                hidden : (readOnly || !multi),
                items : [ {
                    iconCls : "delete",
                    handler : function(grid, row, col) {
                        store.removeAt(row);
                    }
                } ]
            } ]
        });

        var defaultConfig = {
            name : "files",
            labelAlign : "right",
            labelWidth : 60,
            width : width,
            height : height,
            readOnly : readOnly,
            multi : multi,
            expired : expired,
            inline : inline,
            field : "file",
            layout : "fit",
            items : [ grid, hidden ]
        };

        me = Ext.applyIf(me, defaultConfig);
        this.callParent(arguments);

        me.setValue(me.value);
    },

    getStore : function() {
        return this.getComponent(0).getStore();
    },

    getValue : function() {
        return this.getComponent(1).getRawValue();
    },

    setValue : function(keys) {
        this.getComponent(1).setValue(keys);
    },

    setReadOnly : function(readOnly) {
        this.getComponent(0).down("toolbar").setVisible(!readOnly);
    }
});

//baseGrid
Ext.define('dxp.extjs.grid.BaseGrid',{
    
    extend: 'Ext.grid.Panel',
    
    alias: "widget.dxp_basegrid",
    
    fields: [],                 //is required
    
    storeUrl: undefined ,       //is required
    
    storeAutoLoad: true , 
	
    storeHasPaging: true , 
    
    width: '100%' , 
    
    columnLines: true , 
    
    initComponent: function() {
        
    	var me = this, model, store, paging;
        if(!me.store) {
            me.store = store = Ext.create('Ext.data.Store',{
                fields:me.fields , 
                proxy:{
                  type:'ajax' , 
                  url: me.storeUrl ,
                  reader:{
                      type: 'json' ,
                      root: 'rows' ,
                      totalProperty: 'total'
                  }
                },
                pageSize :20 ,
                start : 0,
                autoLoad :me.storeAutoLoad
            });           
        }
        
        if(me.storeHasPaging) {
        	
            me.dockedItems = [];
            paging = Ext.create('dxp.extjs.field.PageBar',{
                  id : "pageBar",
                  store : me.store ,
                  dock:'bottom' , 
                  displayInfo:true  
            });
            me.dockedItems.push(paging);
        }
        
        me.callParent(arguments);
    }
});

