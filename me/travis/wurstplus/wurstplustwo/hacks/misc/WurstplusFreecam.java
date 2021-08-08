//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusFreecam extends WurstplusHack
{
    WurstplusSetting speed_move;
    WurstplusSetting speed_up;
    double x;
    double y;
    double z;
    float pitch;
    float yaw;
    EntityOtherPlayerMP soul;
    Entity riding_entity;
    boolean is_riding;
    @EventHandler
    Listener<WurstplusEventMove> listener_move;
    @EventHandler
    Listener<PlayerSPPushOutOfBlocksEvent> listener_push;
    @EventHandler
    Listener<WurstplusEventPacket.SendPacket> listener_packet;
    
    public WurstplusFreecam() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Freecam";
        this.tag = "Freecam";
        this.description = "let you become ghost";
        this.speed_move = this.create("Speed", "SpeedMove", 1.0, 1.0, 4.0);
        this.speed_up = this.create("Vertical Speed", "VertSpeed", 0.5, 0.0, 1.0);
        this.listener_move = new Listener<WurstplusEventMove>(event -> WurstplusFreecam.mc.player.noClip = true, (Predicate<WurstplusEventMove>[])new Predicate[0]);
        this.listener_push = new Listener<PlayerSPPushOutOfBlocksEvent>(event -> event.setCanceled(true), (Predicate<PlayerSPPushOutOfBlocksEvent>[])new Predicate[0]);
        this.listener_packet = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput) {
                event.cancel();
            }
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
    }
    
    @Override
    protected void enable() {
        if (WurstplusFreecam.mc.player != null && WurstplusFreecam.mc.world != null) {
            this.is_riding = (WurstplusFreecam.mc.player.getRidingEntity() != null);
            if (WurstplusFreecam.mc.player.getRidingEntity() == null) {
                this.x = WurstplusFreecam.mc.player.posX;
                this.y = WurstplusFreecam.mc.player.posY;
                this.z = WurstplusFreecam.mc.player.posZ;
            }
            else {
                this.riding_entity = WurstplusFreecam.mc.player.getRidingEntity();
                WurstplusFreecam.mc.player.dismountRidingEntity();
            }
            this.pitch = WurstplusFreecam.mc.player.rotationPitch;
            this.yaw = WurstplusFreecam.mc.player.rotationYaw;
            (this.soul = new EntityOtherPlayerMP((World)WurstplusFreecam.mc.world, WurstplusFreecam.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)WurstplusFreecam.mc.player);
            this.soul.rotationYawHead = WurstplusFreecam.mc.player.rotationYawHead;
            WurstplusFreecam.mc.world.addEntityToWorld(-100, (Entity)this.soul);
            WurstplusFreecam.mc.player.noClip = true;
        }
    }
    
    @Override
    protected void disable() {
        if (WurstplusFreecam.mc.player != null && WurstplusFreecam.mc.world != null) {
            WurstplusFreecam.mc.player.setPositionAndRotation(this.x, this.y, this.z, this.yaw, this.pitch);
            WurstplusFreecam.mc.world.removeEntityFromWorld(-100);
            this.soul = null;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            final EntityPlayerSP player = WurstplusFreecam.mc.player;
            final EntityPlayerSP player2 = WurstplusFreecam.mc.player;
            final EntityPlayerSP player3 = WurstplusFreecam.mc.player;
            final double motionX = 0.0;
            player3.motionZ = 0.0;
            player2.motionY = 0.0;
            player.motionX = 0.0;
            if (this.is_riding) {
                WurstplusFreecam.mc.player.startRiding(this.riding_entity, true);
            }
        }
    }
    
    @Override
    public void update() {
        if (WurstplusFreecam.mc.player != null && WurstplusFreecam.mc.world != null) {
            WurstplusFreecam.mc.player.noClip = true;
            WurstplusFreecam.mc.player.setVelocity(0.0, 0.0, 0.0);
            WurstplusFreecam.mc.player.renderArmPitch = 5000.0f;
            WurstplusFreecam.mc.player.jumpMovementFactor = 0.5f;
            final double[] static_mov = WurstplusMathUtil.movement_speed(this.speed_move.get_value(1.0) / 2.0);
            if (WurstplusFreecam.mc.player.movementInput.moveStrafe != 0.0f || WurstplusFreecam.mc.player.movementInput.moveForward != 0.0f) {
                WurstplusFreecam.mc.player.motionX = static_mov[0];
                WurstplusFreecam.mc.player.motionZ = static_mov[1];
            }
            else {
                WurstplusFreecam.mc.player.motionX = 0.0;
                WurstplusFreecam.mc.player.motionZ = 0.0;
            }
            WurstplusFreecam.mc.player.setSprinting(false);
            if (WurstplusFreecam.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player;
                final EntityPlayerSP player = player = WurstplusFreecam.mc.player;
                player.motionY += this.speed_up.get_value(1.0);
            }
            if (WurstplusFreecam.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player2;
                final EntityPlayerSP player2 = player2 = WurstplusFreecam.mc.player;
                player2.motionY -= this.speed_up.get_value(1.0);
            }
        }
    }
}
