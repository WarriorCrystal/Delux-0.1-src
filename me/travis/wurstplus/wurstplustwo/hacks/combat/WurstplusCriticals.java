//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusCriticals extends WurstplusHack
{
    WurstplusSetting mode;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;
    
    public WurstplusCriticals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "CriticalsMode", "Packet", this.combobox("Packet", "Jump"));
        CPacketUseEntity event_entity;
        this.listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_packet() instanceof CPacketUseEntity) {
                event_entity = (CPacketUseEntity)event.get_packet();
                if (event_entity.getAction() == CPacketUseEntity.Action.ATTACK && WurstplusCriticals.mc.player.onGround) {
                    if (this.mode.in("Packet")) {
                        WurstplusCriticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.player.posX, WurstplusCriticals.mc.player.posY + 0.10000000149011612, WurstplusCriticals.mc.player.posZ, false));
                        WurstplusCriticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.player.posX, WurstplusCriticals.mc.player.posY, WurstplusCriticals.mc.player.posZ, false));
                    }
                    else if (this.mode.in("Jump")) {
                        WurstplusCriticals.mc.player.jump();
                    }
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "Criticals";
        this.tag = "Criticals";
        this.description = "You can hit with criticals when attack.";
    }
    
    @Override
    public String array_detail() {
        return this.mode.get_current_value();
    }
}
