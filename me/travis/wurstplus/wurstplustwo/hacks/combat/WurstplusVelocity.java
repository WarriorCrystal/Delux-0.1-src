//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntity;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusVelocity extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> damage;
    @EventHandler
    private Listener<WurstplusEventEntity.WurstplusEventColision> explosion;
    
    public WurstplusVelocity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        SPacketEntityVelocity knockback;
        final SPacketEntityVelocity sPacketEntityVelocity;
        final SPacketEntityVelocity sPacketEntityVelocity2;
        final SPacketEntityVelocity sPacketEntityVelocity3;
        SPacketExplosion sPacketExplosion;
        SPacketExplosion knockback2;
        final SPacketExplosion sPacketExplosion2;
        final SPacketExplosion sPacketExplosion3;
        this.damage = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
                if (event.get_packet() instanceof SPacketEntityVelocity) {
                    knockback = (SPacketEntityVelocity)event.get_packet();
                    if (knockback.getEntityID() == WurstplusVelocity.mc.player.getEntityId()) {
                        event.cancel();
                        sPacketEntityVelocity.motionX *= (int)0.0f;
                        sPacketEntityVelocity2.motionY *= (int)0.0f;
                        sPacketEntityVelocity3.motionZ *= (int)0.0f;
                    }
                }
                else if (event.get_packet() instanceof SPacketExplosion) {
                    event.cancel();
                    knockback2 = (sPacketExplosion = (SPacketExplosion)event.get_packet());
                    sPacketExplosion.motionX *= 0.0f;
                    sPacketExplosion2.motionY *= 0.0f;
                    sPacketExplosion3.motionZ *= 0.0f;
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.explosion = new Listener<WurstplusEventEntity.WurstplusEventColision>(event -> {
            if (event.get_entity() == WurstplusVelocity.mc.player) {
                event.cancel();
            }
            return;
        }, (Predicate<WurstplusEventEntity.WurstplusEventColision>[])new Predicate[0]);
        this.name = "Velocity";
        this.tag = "Velocity";
        this.description = "No kockback";
    }
}
