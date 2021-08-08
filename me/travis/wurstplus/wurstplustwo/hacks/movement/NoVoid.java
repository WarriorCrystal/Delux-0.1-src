//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.hacks.Wrapper;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoVoid extends WurstplusHack
{
    WurstplusSetting height;
    
    public NoVoid() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.height = this.create("Height", "Height", 0, 0, 256);
        this.name = "NoVoid";
        this.tag = "NoVoid";
        this.description = "avoids getting voided";
    }
    
    @Override
    public void update() {
        if (Wrapper.mc.world != null) {
            if (Wrapper.mc.player.noClip || Wrapper.mc.player.posY > this.height.get_value(1)) {
                return;
            }
            final RayTraceResult trace = Wrapper.mc.world.rayTraceBlocks(Wrapper.mc.player.getPositionVector(), new Vec3d(Wrapper.mc.player.posX, 0.0, Wrapper.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
    }
}
