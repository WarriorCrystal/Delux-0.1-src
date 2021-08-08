// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event;

import me.zero.alpine.fork.bus.EventManager;
import me.zero.alpine.fork.bus.EventBus;

public class WurstplusEventBus
{
    public static final EventBus EVENT_BUS;
    
    static {
        EVENT_BUS = new EventManager();
    }
}
