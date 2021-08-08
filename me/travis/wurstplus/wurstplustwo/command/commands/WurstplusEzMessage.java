// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import me.travis.wurstplus.Wurstplus;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusEzMessage extends WurstplusCommand
{
    public WurstplusEzMessage() {
        super("ezmessage", "Set ez mode");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        if (message.length == 1) {
            WurstplusMessageUtil.send_client_error_message("message needed");
            return true;
        }
        if (message.length >= 2) {
            final StringBuilder ez = new StringBuilder();
            boolean flag = true;
            for (final String word : message) {
                if (flag) {
                    flag = false;
                }
                else {
                    ez.append(word).append(" ");
                }
            }
            WurstplusEzMessageUtil.set_message(ez.toString());
            WurstplusMessageUtil.send_client_message("ez message changed to " + ChatFormatting.BOLD + ez.toString());
            Wurstplus.get_config_manager().save_settings();
            return true;
        }
        return false;
    }
}
