//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event;

import net.minecraft.client.Minecraft;
import me.zero.alpine.fork.event.type.Cancellable;

public class WurstplusEventCancellable extends Cancellable
{
    private final Era era_switch;
    private final float partial_ticks;
    
    public WurstplusEventCancellable() {
        this.era_switch = Era.EVENT_PRE;
        this.partial_ticks = Minecraft.getMinecraft().getRenderPartialTicks();
    }
    
    public Era get_era() {
        return this.era_switch;
    }
    
    public float get_partial_ticks() {
        return this.partial_ticks;
    }
    
    public enum Era
    {
        EVENT_PRE, 
        EVENT_PERI, 
        EVENT_POST;
    }
}
