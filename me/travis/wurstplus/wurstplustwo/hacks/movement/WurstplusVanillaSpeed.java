//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusVanillaSpeed extends WurstplusHack
{
    public WurstplusSetting speed;
    
    public WurstplusVanillaSpeed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.speed = this.create("Speed", "Speed", 1.0, 1.0, 10.0);
        this.name = "VanillaSpeed";
        this.tag = "VanillaSpeed";
        this.description = "VanillaSpeed";
    }
    
    @Override
    public void update() {
        if (WurstplusVanillaSpeed.mc.player == null || WurstplusVanillaSpeed.mc.world == null) {
            return;
        }
        final double[] calc = WurstplusMathUtil.directionSpeed(this.speed.get_value(1.0) / 10.0);
        WurstplusVanillaSpeed.mc.player.motionX = calc[0];
        WurstplusVanillaSpeed.mc.player.motionZ = calc[1];
    }
}
