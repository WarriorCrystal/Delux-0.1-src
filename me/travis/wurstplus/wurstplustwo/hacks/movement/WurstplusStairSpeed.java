//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusStairSpeed extends WurstplusHack
{
    public WurstplusStairSpeed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "StairSpeed";
        this.tag = "StairSpeed";
        this.description = "StairSpeed";
    }
    
    @Override
    public void update() {
        if (WurstplusStairSpeed.mc.player.onGround && WurstplusStairSpeed.mc.player.posY - Math.floor(WurstplusStairSpeed.mc.player.posY) > 0.0 && WurstplusStairSpeed.mc.player.moveForward != 0.0f) {
            WurstplusStairSpeed.mc.player.jump();
        }
    }
}
