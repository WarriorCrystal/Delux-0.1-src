// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import java.util.Iterator;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusDrawn extends WurstplusCommand
{
    public WurstplusDrawn() {
        super("drawn", "Hide elements of the array list");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("module name needed");
            return true;
        }
        if (message.length == 2) {
            if (this.is_module(message[1])) {
                WurstplusDrawnUtil.add_remove_item(message[1]);
                Wurstplus.get_config_manager().save_settings();
            }
            else {
                WurstplusMessageUtil.send_client_error_message("cannot find module by name: " + message[1]);
            }
            return true;
        }
        return false;
    }
    
    public boolean is_module(final String s) {
        final List<WurstplusHack> modules = Wurstplus.get_hack_manager().get_array_hacks();
        for (final WurstplusHack module : modules) {
            if (module.get_tag().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
