/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi.spout;

import java.util.HashMap;
import java.util.Map;

public class SpoutSelectionManager {
    
    private static Map<String, SpoutSelectionPlayer> players = new HashMap<String, SpoutSelectionPlayer>();
    
    public static SpoutSelectionPlayer getSelectionPlayer(String name) {
        if (!players.containsKey(name)) {
            players.put(name, new SpoutSelectionPlayer(name));
        }
        return players.get(name);
    }
    
}
