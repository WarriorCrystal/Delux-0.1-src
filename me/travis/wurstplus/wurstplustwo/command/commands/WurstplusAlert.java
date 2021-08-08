// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusAlert extends WurstplusCommand
{
    public WurstplusAlert() {
        super("alert", "if the module should spam chat or not");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        String module = "null";
        String state = "null";
        if (message.length > 1) {
            module = message[1];
        }
        if (message.length > 2) {
            state = message[2];
        }
        if (message.length > 3) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
            return true;
        }
        if (module.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
            return true;
        }
        if (state.equals("null")) {
            WurstplusMessageUtil.send_client_error_message(this.current_prefix() + "t <ModuleName> <True/On/False/Off>");
            return true;
        }
        module = module.toLowerCase();
        state = state.toLowerCase();
        final WurstplusHack module_requested = Wurstplus.get_hack_manager().get_module_with_tag(module);
        if (module_requested == null) {
            WurstplusMessageUtil.send_client_error_message("This module does not exist.");
            return true;
        }
        boolean value = true;
        if (state.equals("true") || state.equals("on")) {
            value = true;
        }
        else {
            if (!state.equals("false") && !state.equals("off")) {
                WurstplusMessageUtil.send_client_error_message("This value does not exist. <True/On/False/Off>");
                return true;
            }
            value = false;
        }
        module_requested.set_if_can_send_message_toggle(value);
        WurstplusMessageUtil.send_client_message("The actual value of " + module_requested.get_name() + " is " + Boolean.toString(module_requested.can_send_message_when_toggle()) + ".");
        return true;
    }
}
