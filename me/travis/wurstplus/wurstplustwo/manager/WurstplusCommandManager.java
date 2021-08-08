//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.manager;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.Style;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommands;

public class WurstplusCommandManager
{
    public static WurstplusCommands command_list;
    
    public WurstplusCommandManager() {
        WurstplusCommandManager.command_list = new WurstplusCommands(new Style().setColor(TextFormatting.BLUE));
    }
    
    public static void set_prefix(final String new_prefix) {
        WurstplusCommandManager.command_list.set_prefix(new_prefix);
    }
    
    public static String get_prefix() {
        return WurstplusCommandManager.command_list.get_prefix();
    }
}
