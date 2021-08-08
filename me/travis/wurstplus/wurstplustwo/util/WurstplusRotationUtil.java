//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class WurstplusRotationUtil
{
    private static final Minecraft mc;
    private static float yaw;
    private static float pitch;
    
    public static void updateRotations() {
        WurstplusRotationUtil.yaw = WurstplusRotationUtil.mc.player.rotationYaw;
        WurstplusRotationUtil.pitch = WurstplusRotationUtil.mc.player.rotationPitch;
    }
    
    public static void restoreRotations() {
        WurstplusRotationUtil.mc.player.rotationYaw = WurstplusRotationUtil.yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = WurstplusRotationUtil.yaw;
        WurstplusRotationUtil.mc.player.rotationPitch = WurstplusRotationUtil.pitch;
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        WurstplusRotationUtil.mc.player.rotationYaw = yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = yaw;
        WurstplusRotationUtil.mc.player.rotationPitch = pitch;
    }
    
    public void setPlayerYaw(final float yaw) {
        WurstplusRotationUtil.mc.player.rotationYaw = yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = yaw;
    }
    
    public void lookAtPos(final BlockPos pos) {
        final float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(WurstplusRotationUtil.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
        setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(WurstplusRotationUtil.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final double x, final double y, final double z) {
        final Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
    }
    
    public void lookAtEntity(final Entity entity) {
        final float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(WurstplusRotationUtil.mc.getRenderPartialTicks()), entity.getPositionEyes(WurstplusRotationUtil.mc.getRenderPartialTicks()));
        setPlayerRotations(angle[0], angle[1]);
    }
    
    public void setPlayerPitch(final float pitch) {
        WurstplusRotationUtil.mc.player.rotationPitch = pitch;
    }
    
    public float getYaw() {
        return WurstplusRotationUtil.yaw;
    }
    
    public void setYaw(final float yaw) {
        WurstplusRotationUtil.yaw = yaw;
    }
    
    public float getPitch() {
        return WurstplusRotationUtil.pitch;
    }
    
    public void setPitch(final float pitch) {
        WurstplusRotationUtil.pitch = pitch;
    }
    
    public int getDirection4D() {
        return this.getDirection4D();
    }
    
    public String getDirection4D(final boolean northRed) {
        return this.getDirection4D(northRed);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
