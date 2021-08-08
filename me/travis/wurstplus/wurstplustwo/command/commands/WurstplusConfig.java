// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusConfig extends WurstplusCommand
{
    public WurstplusConfig() {
        super("config", "changes which config is loaded");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("config needed");
            return true;
        }
        if (message.length == 2) {
            final String config = message[1];
            if (Wurstplus.get_config_manager().set_active_config_folder(config + "/")) {
                WurstplusMessageUtil.send_client_message("new config folder set as " + config);
            }
            else {
                WurstplusMessageUtil.send_client_error_message("cannot set folder to " + config);
            }
            return true;
        }
        WurstplusMessageUtil.send_client_error_message("config path may only be one word");
        return true;
    }
}
