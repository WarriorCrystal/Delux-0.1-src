// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.client.entity.AbstractClientPlayer;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventRenderName extends WurstplusEventCancellable
{
    public AbstractClientPlayer Entity;
    public double X;
    public double Y;
    public double Z;
    public String Name;
    public double DistanceSq;
    
    public WurstplusEventRenderName(final AbstractClientPlayer entityIn, double x, double y, double z, final String name, final double distanceSq) {
        this.Entity = entityIn;
        x = this.X;
        y = this.Y;
        z = this.Z;
        this.Name = name;
        this.DistanceSq = distanceSq;
    }
}
