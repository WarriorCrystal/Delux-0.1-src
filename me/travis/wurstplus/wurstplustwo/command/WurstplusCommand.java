// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command;

import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;

public class WurstplusCommand
{
    String name;
    String description;
    
    public WurstplusCommand(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    
    public boolean get_message(final String[] message) {
        return false;
    }
    
    public String get_name() {
        return this.name;
    }
    
    public String get_description() {
        return this.description;
    }
    
    public String current_prefix() {
        return WurstplusCommandManager.get_prefix();
    }
}
