// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class EventPlayerPushOutOfBlocks extends WurstplusEventCancellable
{
    public double X;
    public double Y;
    public double Z;
    
    public EventPlayerPushOutOfBlocks(final double p_X, final double p_Y, final double p_Z) {
        this.X = p_X;
        this.Y = p_Y;
        this.Z = p_Z;
    }
}
