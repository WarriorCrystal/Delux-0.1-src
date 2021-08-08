//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusTotempop extends WurstplusHack
{
    public static final HashMap<String, Integer> totem_pop_counter;
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting gold;
    public static ChatFormatting grey;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> packet_event;
    
    public WurstplusTotempop() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        SPacketEntityStatus packet;
        Entity entity;
        int count;
        this.packet_event = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketEntityStatus) {
                packet = (SPacketEntityStatus)event.get_packet();
                if (packet.getOpCode() == 35) {
                    entity = packet.getEntity((World)WurstplusTotempop.mc.world);
                    count = 1;
                    if (WurstplusTotempop.totem_pop_counter.containsKey(entity.getName())) {
                        count = WurstplusTotempop.totem_pop_counter.get(entity.getName());
                        WurstplusTotempop.totem_pop_counter.put(entity.getName(), ++count);
                    }
                    else {
                        WurstplusTotempop.totem_pop_counter.put(entity.getName(), count);
                    }
                    if (entity != WurstplusTotempop.mc.player) {
                        if (WurstplusFriendUtil.isFriend(entity.getName())) {
                            WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "NoOoOoOo, " + WurstplusTotempop.bold + WurstplusTotempop.green + entity.getName() + WurstplusTotempop.reset + " ha poppeado" + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. q ez");
                        }
                        else {
                            WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "NoOoOoOo, " + WurstplusTotempop.bold + WurstplusTotempop.red + entity.getName() + WurstplusTotempop.reset + " ha poppeado" + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. q nub");
                        }
                    }
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.name = "Totem Pop Counter";
        this.tag = "TotemPopCounter";
        this.description = "dude idk wurst+ is just outdated";
    }
    
    @Override
    public void update() {
        for (final EntityPlayer player : WurstplusTotempop.mc.world.playerEntities) {
            if (!WurstplusTotempop.totem_pop_counter.containsKey(player.getName())) {
                continue;
            }
            if (!player.isDead && player.getHealth() > 0.0f) {
                continue;
            }
            final int count = WurstplusTotempop.totem_pop_counter.get(player.getName());
            WurstplusTotempop.totem_pop_counter.remove(player.getName());
            if (player == WurstplusTotempop.mc.player) {
                continue;
            }
            if (WurstplusFriendUtil.isFriend(player.getName())) {
                WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "Hey, " + WurstplusTotempop.bold + WurstplusTotempop.green + player.getName() + WurstplusTotempop.reset + " ha muerto de poppear " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. hahaha big ez");
            }
            else {
                WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "Hey, " + WurstplusTotempop.bold + WurstplusTotempop.red + player.getName() + WurstplusTotempop.reset + " ha muerto de poppear " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. cope");
            }
        }
    }
    
    static {
        totem_pop_counter = new HashMap<String, Integer>();
        WurstplusTotempop.red = ChatFormatting.RED;
        WurstplusTotempop.green = ChatFormatting.GREEN;
        WurstplusTotempop.gold = ChatFormatting.GOLD;
        WurstplusTotempop.grey = ChatFormatting.GRAY;
        WurstplusTotempop.bold = ChatFormatting.BOLD;
        WurstplusTotempop.reset = ChatFormatting.RESET;
    }
}
