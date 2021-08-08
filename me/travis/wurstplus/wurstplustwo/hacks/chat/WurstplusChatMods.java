//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public final class WurstplusChatMods extends WurstplusHack
{
    WurstplusSetting timestamps;
    WurstplusSetting dateformat;
    WurstplusSetting name_highlight;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> PacketEvent;
    
    public WurstplusChatMods() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.timestamps = this.create("Timestamps", "ChatModsTimeStamps", true);
        this.dateformat = this.create("Date Format", "ChatModsDateFormat", "24HR", this.combobox("24HR", "12HR"));
        this.name_highlight = this.create("Name Highlight", "ChatModsNameHighlight", true);
        SPacketChat packet;
        TextComponentString component;
        String date;
        String text;
        this.PacketEvent = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketChat) {
                packet = (SPacketChat)event.get_packet();
                if (packet.getChatComponent() instanceof TextComponentString) {
                    component = (TextComponentString)packet.getChatComponent();
                    if (this.timestamps.get_value(true)) {
                        date = "";
                        if (this.dateformat.in("12HR")) {
                            date = new SimpleDateFormat("h:mm a").format(new Date());
                        }
                        if (this.dateformat.in("24HR")) {
                            date = new SimpleDateFormat("k:mm").format(new Date());
                        }
                        component.text = "§7[" + date + "]§r " + component.text;
                    }
                    text = component.getFormattedText();
                    if (!text.contains("combat for")) {
                        if (this.name_highlight.get_value(true) && WurstplusChatMods.mc.player != null && text.toLowerCase().contains(WurstplusChatMods.mc.player.getName().toLowerCase())) {
                            text = text.replaceAll("(?i)" + WurstplusChatMods.mc.player.getName(), ChatFormatting.GOLD + WurstplusChatMods.mc.player.getName() + ChatFormatting.RESET);
                        }
                        event.cancel();
                        WurstplusMessageUtil.client_message(text);
                    }
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.name = "Chat Modifications";
        this.tag = "ChatModifications";
        this.description = "this breaks things";
    }
}
