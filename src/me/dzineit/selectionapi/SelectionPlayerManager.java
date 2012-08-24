/*
 * This file is part of SelectionAPI.
 *
 * Copyright (c) 2012-2012, DziNeIT <http://github.com/DziNeIT/>
 * SelectionAPI is licensed under the VolumetricPixels License Version 1.
 */
package me.dzineit.selectionapi;

import java.util.HashMap;
import java.util.Map;


public class SelectionPlayerManager {
    
    private static Map<String, SelectionPlayer> players = new HashMap<String, SelectionPlayer>();
    
    public static SelectionPlayer getSelectionPlayer(String name) {
        if (!players.containsKey(name)) {
            players.put(name, new SelectionPlayer(name));
        }
        return players.get(name);
    }
    
}
