// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommands;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusHelp extends WurstplusCommand
{
    public WurstplusHelp() {
        super("help", "helps people");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        String type = "null";
        if (message.length == 1) {
            type = "list";
        }
        if (message.length > 1) {
            type = message[1];
        }
        if (message.length > 2) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "help <List/NameCommand>");
            return true;
        }
        if (type.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "help <List/NameCommand>");
            return true;
        }
        if (type.equalsIgnoreCase("list")) {
            for (final WurstplusCommand commands : WurstplusCommands.get_pure_command_list()) {
                WurstplusMessageUtil.send_client_message(commands.get_name());
            }
            return true;
        }
        final WurstplusCommand command_requested = WurstplusCommands.get_command_with_name(type);
        if (command_requested == null) {
            WurstplusMessageUtil.send_client_error_message("This command does not exist.");
            return true;
        }
        WurstplusMessageUtil.send_client_message(command_requested.get_name() + " - " + command_requested.get_description());
        return true;
    }
}
