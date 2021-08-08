//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.Random;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AntiAim extends WurstplusHack
{
    public AntiAim() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "AntiAim";
        this.tag = "AntiAim";
        this.description = "Makes it hard to target you";
    }
    
    @Override
    public void update() {
        final Random r = new Random();
        AntiAim.mc.player.rotationPitch = (float)r.nextInt(150);
        AntiAim.mc.player.rotationYaw = (float)r.nextInt(150);
    }
}
