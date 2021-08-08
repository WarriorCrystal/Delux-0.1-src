//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Flight extends WurstplusHack
{
    public Flight() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Flight";
        this.tag = "Flight";
        this.description = "flight go brrr";
    }
    
    @Override
    protected void enable() {
        Flight.mc.player.capabilities.isFlying = true;
    }
    
    @Override
    protected void disable() {
        if (Flight.mc.player != null) {
            Flight.mc.player.capabilities.isFlying = false;
        }
    }
    
    @Override
    public void update() {
        Flight.mc.player.capabilities.isFlying = true;
    }
}
