// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import me.travis.wurstplus.Wurstplus;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusSettings extends WurstplusCommand
{
    public WurstplusSettings() {
        super("settings", "To save/load settings.");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        String msg = "null";
        if (message.length > 1) {
            msg = message[1];
        }
        if (msg.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "settings <save/load>");
            return true;
        }
        final ChatFormatting c = ChatFormatting.GRAY;
        if (msg.equalsIgnoreCase("save")) {
            Wurstplus.get_config_manager().save_settings();
            WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "saved!");
        }
        else {
            if (!msg.equalsIgnoreCase("load")) {
                WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "settings <save/load>");
                return true;
            }
            Wurstplus.get_config_manager().load_settings();
            WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "loaded!");
        }
        return true;
    }
}
