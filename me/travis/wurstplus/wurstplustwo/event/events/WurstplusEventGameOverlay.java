// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.client.gui.ScaledResolution;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventGameOverlay extends WurstplusEventCancellable
{
    public float partial_ticks;
    private ScaledResolution scaled_resolution;
    
    public WurstplusEventGameOverlay(final float partial_ticks, final ScaledResolution scaled_resolution) {
        this.partial_ticks = partial_ticks;
        this.scaled_resolution = scaled_resolution;
    }
    
    public ScaledResolution get_scaled_resoltion() {
        return this.scaled_resolution;
    }
}
