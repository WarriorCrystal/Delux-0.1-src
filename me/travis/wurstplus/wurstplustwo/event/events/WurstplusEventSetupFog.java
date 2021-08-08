// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventSetupFog extends WurstplusEventCancellable
{
    public int start_coords;
    public float partial_ticks;
    
    public WurstplusEventSetupFog(final int coords, final float ticks) {
        this.start_coords = coords;
        this.partial_ticks = ticks;
    }
}
