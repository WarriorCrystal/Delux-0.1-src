// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusFriend extends WurstplusCommand
{
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    
    public WurstplusFriend() {
        super("friend", "To add friends");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        if (message.length == 1) {
            WurstplusMessageUtil.send_client_message("Add - add friend");
            WurstplusMessageUtil.send_client_message("Del - delete friend");
            WurstplusMessageUtil.send_client_message("List - list friends");
            return true;
        }
        if (message.length != 2) {
            if (message.length >= 3) {
                if (message[1].equalsIgnoreCase("add")) {
                    if (WurstplusFriendUtil.isFriend(message[2])) {
                        WurstplusMessageUtil.send_client_message("Player " + WurstplusFriend.green + WurstplusFriend.bold + message[2] + WurstplusFriend.reset + " is already your friend :D");
                        return true;
                    }
                    final WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object(message[2]);
                    if (f == null) {
                        WurstplusMessageUtil.send_client_error_message("Cannot find " + WurstplusFriend.red + WurstplusFriend.bold + "UUID" + WurstplusFriend.reset + " for that player :(");
                        return true;
                    }
                    WurstplusFriendUtil.friends.add(f);
                    WurstplusMessageUtil.send_client_message("Player " + WurstplusFriend.green + WurstplusFriend.bold + message[2] + WurstplusFriend.reset + " is now your friend :D");
                    return true;
                }
                else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                    if (!WurstplusFriendUtil.isFriend(message[2])) {
                        WurstplusMessageUtil.send_client_message("Player " + WurstplusFriend.red + WurstplusFriend.bold + message[2] + WurstplusFriend.reset + " is already not your friend :/");
                        return true;
                    }
                    final WurstplusFriendUtil.Friend f = WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(message[2])).findFirst().get();
                    WurstplusFriendUtil.friends.remove(f);
                    WurstplusMessageUtil.send_client_message("Player " + WurstplusFriend.red + WurstplusFriend.bold + message[2] + WurstplusFriend.reset + " is now not your friend :(");
                    return true;
                }
            }
            return true;
        }
        if (message[1].equalsIgnoreCase("list")) {
            if (WurstplusFriendUtil.friends.isEmpty()) {
                WurstplusMessageUtil.send_client_message("You appear to have " + WurstplusFriend.red + WurstplusFriend.bold + "no" + WurstplusFriend.reset + " friends :(");
            }
            else {
                for (final WurstplusFriendUtil.Friend friend2 : WurstplusFriendUtil.friends) {
                    WurstplusMessageUtil.send_client_message("" + WurstplusFriend.green + WurstplusFriend.bold + friend2.getUsername());
                }
            }
            return true;
        }
        if (WurstplusFriendUtil.isFriend(message[1])) {
            WurstplusMessageUtil.send_client_message("Player " + WurstplusFriend.green + WurstplusFriend.bold + message[1] + WurstplusFriend.reset + " is your friend :D");
            return true;
        }
        WurstplusMessageUtil.send_client_error_message("Player " + WurstplusFriend.red + WurstplusFriend.bold + message[1] + WurstplusFriend.reset + " is not your friend :(");
        return true;
    }
    
    static {
        WurstplusFriend.red = ChatFormatting.RED;
        WurstplusFriend.green = ChatFormatting.GREEN;
        WurstplusFriend.bold = ChatFormatting.BOLD;
        WurstplusFriend.reset = ChatFormatting.RESET;
    }
}
