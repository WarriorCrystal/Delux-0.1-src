// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventEntityRemoved extends WurstplusEventCancellable
{
    private final Entity entity;
    
    public WurstplusEventEntityRemoved(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity get_entity() {
        return this.entity;
    }
}
