//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusCoordinates extends WurstplusPinnable
{
    ChatFormatting dg;
    ChatFormatting db;
    ChatFormatting dr;
    
    public WurstplusCoordinates() {
        super("Coordinates", "Coordinates", 1.0f, 0, 0);
        this.dg = ChatFormatting.DARK_GRAY;
        this.db = ChatFormatting.DARK_BLUE;
        this.dr = ChatFormatting.DARK_RED;
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final String x = Wurstplus.g + "[" + Wurstplus.r + Integer.toString((int)this.mc.player.posX) + Wurstplus.g + "]" + Wurstplus.r;
        final String y = Wurstplus.g + "[" + Wurstplus.r + Integer.toString((int)this.mc.player.posY) + Wurstplus.g + "]" + Wurstplus.r;
        final String z = Wurstplus.g + "[" + Wurstplus.r + Integer.toString((int)this.mc.player.posZ) + Wurstplus.g + "]" + Wurstplus.r;
        final String x_nether = Wurstplus.g + "[" + Wurstplus.r + Long.toString(Math.round((this.mc.player.dimension != -1) ? (this.mc.player.posX / 8.0) : (this.mc.player.posX * 8.0))) + Wurstplus.g + "]" + Wurstplus.r;
        final String z_nether = Wurstplus.g + "[" + Wurstplus.r + Long.toString(Math.round((this.mc.player.dimension != -1) ? (this.mc.player.posZ / 8.0) : (this.mc.player.posZ * 8.0))) + Wurstplus.g + "]" + Wurstplus.r;
        final String line = "XYZ " + x + y + z + " XZ " + x_nether + z_nether;
        this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(line, "width"));
        this.set_height(this.get(line, "height") + 2);
    }
}
