// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventPlayerJump extends WurstplusEventCancellable
{
    public double motion_x;
    public double motion_y;
    
    public WurstplusEventPlayerJump(final double motion_x, final double motion_y) {
        this.motion_x = motion_x;
        this.motion_y = motion_y;
    }
}
