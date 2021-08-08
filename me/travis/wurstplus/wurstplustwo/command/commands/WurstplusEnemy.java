// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command.commands;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;

public class WurstplusEnemy extends WurstplusCommand
{
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    
    public WurstplusEnemy() {
        super("enemy", "To add enemy");
    }
    
    @Override
    public boolean get_message(final String[] message) {
        if (message.length == 1) {
            WurstplusMessageUtil.send_client_message("Add - add enemy");
            WurstplusMessageUtil.send_client_message("Del - delete enemy");
            WurstplusMessageUtil.send_client_message("List - list enemies");
            return true;
        }
        if (message.length != 2) {
            if (message.length >= 3) {
                if (message[1].equalsIgnoreCase("add")) {
                    if (WurstplusEnemyUtil.isEnemy(message[2])) {
                        WurstplusMessageUtil.send_client_message("Player " + WurstplusEnemy.green + WurstplusEnemy.bold + message[2] + WurstplusEnemy.reset + " is already your Enemy D:");
                        return true;
                    }
                    final WurstplusEnemyUtil.Enemy f = WurstplusEnemyUtil.get_enemy_object(message[2]);
                    if (f == null) {
                        WurstplusMessageUtil.send_client_error_message("Cannot find " + WurstplusEnemy.red + WurstplusEnemy.bold + "UUID" + WurstplusEnemy.reset + " for that player :(");
                        return true;
                    }
                    WurstplusEnemyUtil.enemies.add(f);
                    WurstplusMessageUtil.send_client_message("Player " + WurstplusEnemy.green + WurstplusEnemy.bold + message[2] + WurstplusEnemy.reset + " is now your Enemy D:");
                    return true;
                }
                else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                    if (!WurstplusEnemyUtil.isEnemy(message[2])) {
                        WurstplusMessageUtil.send_client_message("Player " + WurstplusEnemy.red + WurstplusEnemy.bold + message[2] + WurstplusEnemy.reset + " is already not your Enemy :/");
                        return true;
                    }
                    final WurstplusEnemyUtil.Enemy f = WurstplusEnemyUtil.enemies.stream().filter(Enemy -> Enemy.getUsername().equalsIgnoreCase(message[2])).findFirst().get();
                    WurstplusEnemyUtil.enemies.remove(f);
                    WurstplusMessageUtil.send_client_message("Player " + WurstplusEnemy.red + WurstplusEnemy.bold + message[2] + WurstplusEnemy.reset + " is now not your Enemy :)");
                    return true;
                }
            }
            return true;
        }
        if (message[1].equalsIgnoreCase("list")) {
            if (WurstplusEnemyUtil.enemies.isEmpty()) {
                WurstplusMessageUtil.send_client_message("You appear to have " + WurstplusEnemy.red + WurstplusEnemy.bold + "no" + WurstplusEnemy.reset + " enemies :)");
            }
            else {
                for (final WurstplusEnemyUtil.Enemy Enemy2 : WurstplusEnemyUtil.enemies) {
                    WurstplusMessageUtil.send_client_message("" + WurstplusEnemy.green + WurstplusEnemy.bold + Enemy2.getUsername());
                }
            }
            return true;
        }
        if (WurstplusEnemyUtil.isEnemy(message[1])) {
            WurstplusMessageUtil.send_client_message("Player " + WurstplusEnemy.green + WurstplusEnemy.bold + message[1] + WurstplusEnemy.reset + " is your Enemy D:");
            return true;
        }
        WurstplusMessageUtil.send_client_error_message("Player " + WurstplusEnemy.red + WurstplusEnemy.bold + message[1] + WurstplusEnemy.reset + " is not your Enemy :)");
        return true;
    }
    
    static {
        WurstplusEnemy.red = ChatFormatting.GREEN;
        WurstplusEnemy.green = ChatFormatting.RED;
        WurstplusEnemy.bold = ChatFormatting.BOLD;
        WurstplusEnemy.reset = ChatFormatting.RESET;
    }
}
