/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import java.io.File;
import java.util.logging.Logger;


import org.spout.api.Spout;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class SelectionAPI extends CommonPlugin {
    
    private Logger log;
    private PluginDescriptionFile pdf;
    private YamlConfiguration yConf;

    @Override
    public void onEnable() {
        this.log = getLogger();
        this.pdf = getDescription();
        this.yConf = new YamlConfiguration(new File(getDataFolder(), "config.yml"));
        
        yConf.setWritesDefaults(true);
        Spout.getEventManager().registerEvents(new SelectionListener(yConf.getNode("Selector-Tool-Id").getShort((short) 256)), this);
        
        log.info("[SelectorAPI] v" + pdf.getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        log.info("[SelectorAPI] v" + pdf.getVersion() + " disabled!");
    }
    
}
