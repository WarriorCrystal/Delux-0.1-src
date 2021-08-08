// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.util.EnumHand;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventSwing extends WurstplusEventCancellable
{
    public EnumHand hand;
    
    public WurstplusEventSwing(final EnumHand hand) {
        this.hand = hand;
    }
}
