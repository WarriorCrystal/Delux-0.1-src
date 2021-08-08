// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.entity.MoverType;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventMove extends WurstplusEventCancellable
{
    private MoverType move_type;
    public double x;
    public double y;
    public double z;
    
    public WurstplusEventMove(final MoverType type, final double x, final double y, final double z) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set_move_type(final MoverType type) {
        this.move_type = type;
    }
    
    public void set_x(final double x) {
        this.x = x;
    }
    
    public void set_y(final double y) {
        this.y = y;
    }
    
    public void set_z(final double z) {
        this.z = z;
    }
    
    public MoverType get_move_type() {
        return this.move_type;
    }
    
    public double get_x() {
        return this.x;
    }
    
    public double get_y() {
        return this.y;
    }
    
    public double get_z() {
        return this.z;
    }
}
