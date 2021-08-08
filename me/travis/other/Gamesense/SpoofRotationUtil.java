//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Gamesense;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;

public class SpoofRotationUtil
{
    private static final Minecraft mc;
    public static final SpoofRotationUtil ROTATION_UTIL;
    private int rotationConnections;
    private boolean shouldSpoofAngles;
    private boolean isSpoofingAngles;
    private double yaw;
    private double pitch;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    private SpoofRotationUtil() {
        this.rotationConnections = 0;
        final Packet[] packet = { null };
        final Object o;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            o[0] = event.get_packet();
            if (o[0] instanceof CPacketPlayer && this.shouldSpoofAngles && this.isSpoofingAngles) {
                ((CPacketPlayer)o[0]).yaw = (float)this.yaw;
                ((CPacketPlayer)o[0]).pitch = (float)this.pitch;
            }
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
    }
    
    public void enable() {
        ++this.rotationConnections;
        if (this.rotationConnections == 1) {
            WurstplusEventBus.EVENT_BUS.subscribe(this.send_listener);
        }
    }
    
    public void disable() {
        --this.rotationConnections;
        if (this.rotationConnections == 0) {
            WurstplusEventBus.EVENT_BUS.unsubscribe(this.send_listener);
        }
    }
    
    public void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, (Entity)me);
        this.setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    public void setYawAndPitch(final float yaw1, final float pitch1) {
        this.yaw = yaw1;
        this.pitch = pitch1;
        this.isSpoofingAngles = true;
    }
    
    public void resetRotation() {
        if (this.isSpoofingAngles) {
            this.yaw = SpoofRotationUtil.mc.player.rotationYaw;
            this.pitch = SpoofRotationUtil.mc.player.rotationPitch;
            this.isSpoofingAngles = false;
        }
    }
    
    public void shouldSpoofAngles(final boolean e) {
        this.shouldSpoofAngles = e;
    }
    
    public boolean isSpoofingAngles() {
        return this.isSpoofingAngles;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        ROTATION_UTIL = new SpoofRotationUtil();
    }
}
