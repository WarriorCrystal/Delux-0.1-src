//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusReverseStep extends WurstplusHack
{
    public WurstplusReverseStep() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "ReverseStep";
        this.tag = "ReverseStep";
        this.description = "ReverseStep";
    }
    
    @Override
    public void update() {
        if (WurstplusReverseStep.mc.player.onGround) {
            final EntityPlayerSP player = WurstplusReverseStep.mc.player;
            --player.motionY;
        }
    }
}
