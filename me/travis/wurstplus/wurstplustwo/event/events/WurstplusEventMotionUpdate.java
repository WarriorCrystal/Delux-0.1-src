// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventMotionUpdate extends WurstplusEventCancellable
{
    public int stage;
    
    public WurstplusEventMotionUpdate(final int stage) {
        this.stage = stage;
    }
}
