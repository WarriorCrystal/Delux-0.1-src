//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import net.minecraft.util.math.MathHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusDirection extends WurstplusPinnable
{
    public WurstplusDirection() {
        super("Direction", "Direction", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final String direction = String.format("%s " + ChatFormatting.GRAY + "%s", this.getFacing(), this.getTowards());
        this.create_line(direction, this.docking(1, direction), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(direction, "width") + 2);
        this.set_height(this.get(direction, "height") + 2);
    }
    
    private String getFacing() {
        switch (MathHelper.floor(this.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "South";
            }
            case 1: {
                return "South West";
            }
            case 2: {
                return "West";
            }
            case 3: {
                return "North West";
            }
            case 4: {
                return "North";
            }
            case 5: {
                return "North East";
            }
            case 6: {
                return "East";
            }
            case 7: {
                return "South East";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    private String getTowards() {
        switch (MathHelper.floor(this.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "+Z";
            }
            case 1: {
                return "-X +Z";
            }
            case 2: {
                return "-X";
            }
            case 3: {
                return "-X -Z";
            }
            case 4: {
                return "-Z";
            }
            case 5: {
                return "+X -Z";
            }
            case 6: {
                return "+X";
            }
            case 7: {
                return "+X +Z";
            }
            default: {
                return "Invalid";
            }
        }
    }
}
