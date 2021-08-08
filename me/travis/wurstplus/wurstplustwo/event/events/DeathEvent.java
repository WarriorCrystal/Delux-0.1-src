// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class DeathEvent extends WurstplusEventCancellable
{
    public EntityPlayer player;
    
    public DeathEvent(final EntityPlayer player) {
        this.player = player;
    }
}
