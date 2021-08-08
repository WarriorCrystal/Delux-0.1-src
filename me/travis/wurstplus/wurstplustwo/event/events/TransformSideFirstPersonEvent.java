// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.util.EnumHandSide;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class TransformSideFirstPersonEvent extends WurstplusEventCancellable
{
    private final EnumHandSide enumHandSide;
    
    public TransformSideFirstPersonEvent(final EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }
    
    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}
