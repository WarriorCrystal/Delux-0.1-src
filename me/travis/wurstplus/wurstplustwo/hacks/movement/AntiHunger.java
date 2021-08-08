//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AntiHunger extends WurstplusHack
{
    public final WurstplusSetting Sprint;
    public final WurstplusSetting Ground;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    public AntiHunger() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.Sprint = this.create("Sprint", "Sprint", false);
        this.Ground = this.create("Ground", "Ground", true);
        final CPacketPlayer[] l_Packet = { null };
        final CPacketEntityAction[] l_Packet2 = { null };
        final Object o;
        final Object o2;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_packet() instanceof CPacketPlayer && this.Ground.get_value(false) && !AntiHunger.mc.player.isElytraFlying()) {
                o[0] = (CPacketPlayer)event.get_packet();
                if (AntiHunger.mc.player.fallDistance > 0.0f || AntiHunger.mc.playerController.isHittingBlock) {
                    o[0].onGround = true;
                }
                else {
                    o[0].onGround = false;
                }
            }
            if (event.get_packet() instanceof CPacketEntityAction && this.Sprint.get_value(true)) {
                o2[0] = (CPacketEntityAction)event.get_packet();
                if (o2[0].getAction() == CPacketEntityAction.Action.START_SPRINTING || o2[0].getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                    event.cancel();
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "AntiHunger";
        this.tag = "AntiHunger";
        this.description = "Prevents hunger loss by spoofing on ground state";
    }
}
