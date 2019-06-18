Ext.ux.PortletPlugin = {
    AUTO_IDs : {},
    init: function(panel) {
        //if you rely on auto-id's, then the ID in initComponent is invalid
        //the reason for this is that the ID is reset here during the init of the plugin, which
        //happens after initComponent is called.
        //The issue is, as your page grows, or if people simply come to the portal later from
        //another portion of the application, then the ext-comp id's may have already surpassed
        //the ids for the stored portlets.  This implementation gives the control an id of xtype-n
        //allowing you to use multiple portlets of the same type, but allowing them to save state
        //without risk of duplicating ids.
        //If you need to use auto-ids in initComponent, just copy this block of code to your
        //initComponent, changing this to Ext.ux.PortletPlugin
        //You may have issues if you have multiple portals using the same portlets.  If this is the
        //case, you will need to design your own ID system to handle it.  This is necessarily external
        //to the Portlet, because Portlets are created before being added to any particular portal,
        //and the best id system is probably something like PortalID-PortletXType-N
    	
    	//wei: comment this, no need auto ids
//        if (!this.AUTO_IDs[panel.xtype])
//            this.AUTO_IDs[panel.xtype] = 0;
//        if (panel.id.substring(0, 8) == 'ext-comp')
//            panel.id = panel.xtype + '-' + (++this.AUTO_IDs[panel.xtype]);
//        else if (panel.id.substring(0, panel.xtype.length) == panel.xtype)
//            this.AUTO_IDs[panel.xtype] = Math.max(this.AUTO_IDs[panel.xtype], parseInt(panel.id.substr(panel.xtype.length+1)) + 1);

        //required settings - these will override anything set up in config or initComponent of extension
        //these attributes are required to make a portlet a portlet
        Ext.apply(panel, {
            anchor: '100%',
            frame:true,
            stateful: false,//wei: portlet don't need state for now
            //draggable: {ddGroup:'portal'},
            hideBorders:true,
            cls:'x-portlet'
        });
        //optional settings
        Ext.applyIf(panel, {
            collapsible:true,
            settings: false,
            settingHandler: Ext.emptyFn,
            closeable: false,//wei: change to false for now
            resizeable: false, //wei: change to false for now
            tools: []
        });
        
        if (panel.settings)
            panel.tools.push({
                id:'gear',
                handler: panel.settingHandler,
                scope: panel
            });
        if (panel.closeable)
            panel.tools.push({
                id:'close',
                handler: function(e, target, panel){
                    panel.ownerCt.remove(panel, true);
                }
            });

        //tell the Portal to doLayout after expanding or collapsing in case scrollbars appeared/disappeared
        panel.on('expand', function() {
        	(function() {
            panel.initialConfig.collapsed = false;
            panel.ownerCt.ownerCt.saveState();
            panel.ownerCt.ownerCt.doLayout();
        }).defer(100)});

        //the collapse event is deferred because panels that start collapsed fire the collapse event before rendering
        panel.on.defer(50, panel, ['collapse', function() {
            panel.initialConfig.collapsed = true;
            panel.ownerCt.ownerCt.saveState();
            panel.ownerCt.ownerCt.doLayout();
        }]);
/*
        if (panel.resizeable)
        {
            panel.on('render', function() {
                panel.resizer = new Ext.Resizable(panel.el, {
                    handles: 's',
                    minHeight: 100,
                    maxHeight:800,
                    pinned: true,
                    resizeElement: function() {
                        var box = this.proxy.getBox();
                        panel.setSize(box);
                        panel.initialConfig.height = box.height;
                        //remove and reappend element to fix IE6 bug
                        var parent = this.south.el.parent();
                        this.south.el.remove();
                        parent.appendChild(this.south.el);
                        panel.ownerCt.ownerCt.saveState();
                        return box;
                    }
                });
                
                //bug fix - Resizers place their proxy in the same container with the panel, messing with layouts
                //this removes the proxy from the container and moves it to the end of the document. fixed in 2.2
                if (Ext.version < 2.2)
                {
                    panel.resizer.proxy.remove();
                    panel.resizer.proxy.appendTo(Ext.getBody());
                }
            });
        }
*/        
    }
};
Ext.ux.Portlet = Ext.extend(Ext.Panel, {
	titleCollapse: true,
	draggable: {ddGroup:'portal'},
    plugins: Ext.ux.PortletPlugin
});

Ext.reg('portlet', Ext.ux.Portlet);
