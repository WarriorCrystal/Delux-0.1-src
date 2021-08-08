//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import net.minecraft.util.math.MathHelper;
import java.text.DecimalFormat;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusSpeedometer extends WurstplusPinnable
{
    public WurstplusSpeedometer() {
        super("Speedometer", "Speedometer", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final double x = this.mc.player.posX - this.mc.player.prevPosX;
        final double z = this.mc.player.posZ - this.mc.player.prevPosZ;
        final float tr = this.mc.timer.tickLength / 1000.0f;
        final String bps = "M/s: " + new DecimalFormat("#.#").format(MathHelper.sqrt(x * x + z * z) / tr);
        this.create_line(bps, this.docking(1, bps), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(bps, "width") + 2);
        this.set_height(this.get(bps, "height") + 2);
    }
}
