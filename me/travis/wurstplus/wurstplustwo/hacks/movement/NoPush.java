// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.EventPlayerPushOutOfBlocks;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoPush extends WurstplusHack
{
    @EventHandler
    private Listener<EventPlayerPushOutOfBlocks> PushOutOfBlocks;
    
    public NoPush() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.PushOutOfBlocks = new Listener<EventPlayerPushOutOfBlocks>(p_Event -> p_Event.cancel(), (Predicate<EventPlayerPushOutOfBlocks>[])new Predicate[0]);
        this.name = "NoPush";
        this.tag = "NoPush";
        this.description = "prevents you getting raped by being stuck in a block";
        this.toggle_message = false;
    }
}
