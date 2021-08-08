//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Objects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import java.util.concurrent.ConcurrentHashMap;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoEz extends WurstplusHack
{
    int delay_count;
    WurstplusSetting discord;
    WurstplusSetting custom;
    private static final ConcurrentHashMap targeted_players;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    @EventHandler
    private Listener<LivingDeathEvent> living_death_listener;
    
    public WurstplusAutoEz() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.delay_count = 0;
        this.discord = this.create("Discord", "EzDiscord", false);
        this.custom = this.create("Custom", "EzCustom", false);
        CPacketUseEntity cPacketUseEntity;
        Entity target_entity;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (WurstplusAutoEz.mc.player == null) {
                return;
            }
            else {
                if (event.get_packet() instanceof CPacketUseEntity) {
                    cPacketUseEntity = (CPacketUseEntity)event.get_packet();
                    if (cPacketUseEntity.getAction().equals((Object)CPacketUseEntity.Action.ATTACK)) {
                        target_entity = cPacketUseEntity.getEntityFromWorld((World)WurstplusAutoEz.mc.world);
                        if (target_entity instanceof EntityPlayer) {
                            add_target(target_entity.getName());
                        }
                    }
                }
                return;
            }
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        EntityLivingBase e;
        EntityPlayer player;
        this.living_death_listener = new Listener<LivingDeathEvent>(event -> {
            if (WurstplusAutoEz.mc.player == null) {
                return;
            }
            else {
                e = event.getEntityLiving();
                if (e == null) {
                    return;
                }
                else {
                    if (e instanceof EntityPlayer) {
                        player = (EntityPlayer)e;
                        if (player.getHealth() <= 0.0f && WurstplusAutoEz.targeted_players.containsKey(player.getName())) {
                            this.announce(player.getName());
                        }
                    }
                    return;
                }
            }
        }, (Predicate<LivingDeathEvent>[])new Predicate[0]);
        this.name = "Auto Ez";
        this.tag = "AutoEz";
        this.description = "you just got nae nae'd by wurst+... 2";
    }
    
    @Override
    public void update() {
        for (final Entity entity : WurstplusAutoEz.mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)entity;
                if (player.getHealth() > 0.0f || !WurstplusAutoEz.targeted_players.containsKey(player.getName())) {
                    continue;
                }
                this.announce(player.getName());
            }
        }
        WurstplusAutoEz.targeted_players.forEach((name, timeout) -> {
            if (timeout <= 0) {
                WurstplusAutoEz.targeted_players.remove(name);
            }
            else {
                WurstplusAutoEz.targeted_players.put(name, timeout - 1);
            }
            return;
        });
        ++this.delay_count;
    }
    
    public void announce(final String name) {
        if (this.delay_count < 150) {
            return;
        }
        this.delay_count = 0;
        WurstplusAutoEz.targeted_players.remove(name);
        String message = "";
        if (this.custom.get_value(true)) {
            message += WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "");
        }
        else {
            message += "you just got nae nae'd by wurst+2";
        }
        if (this.discord.get_value(true)) {
            message += " - discord.gg/wurst";
        }
        WurstplusAutoEz.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(message));
    }
    
    public static void add_target(final String name) {
        if (!Objects.equals(name, WurstplusAutoEz.mc.player.getName())) {
            WurstplusAutoEz.targeted_players.put(name, 20);
        }
    }
    
    static {
        targeted_players = new ConcurrentHashMap();
    }
}
