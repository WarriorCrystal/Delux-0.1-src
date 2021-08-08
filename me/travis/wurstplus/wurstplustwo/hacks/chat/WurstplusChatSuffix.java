//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.Wurstplus;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusChatSuffix extends WurstplusHack
{
    WurstplusSetting ignore;
    WurstplusSetting type;
    boolean accept_suffix;
    boolean suffix_default;
    boolean suffix_random;
    StringBuilder suffix;
    String[] random_client_name;
    String[] random_client_finish;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;
    
    public WurstplusChatSuffix() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.ignore = this.create("Ignore", "ChatSuffixIgnore", true);
        this.type = this.create("Type", "ChatSuffixType", "Default", this.combobox("Default", "Random"));
        this.random_client_name = new String[] { "bitcoin", "miner", "beat", "gay+" };
        this.random_client_finish = new String[] { " plus", " epic", "god" };
        boolean ignore_prefix;
        String message;
        StringBuilder suffix_with_randoms;
        this.listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (!(event.get_packet() instanceof CPacketChatMessage)) {
                return;
            }
            else {
                this.accept_suffix = true;
                ignore_prefix = this.ignore.get_value(true);
                message = ((CPacketChatMessage)event.get_packet()).getMessage();
                if (message.startsWith("/") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("\\") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("!") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith(":") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith(";") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith(".") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith(",") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("@") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("&") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("*") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("$") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("#") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith("(") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (message.startsWith(")") && ignore_prefix) {
                    this.accept_suffix = false;
                }
                if (this.type.in("Default")) {
                    this.suffix_default = true;
                    this.suffix_random = false;
                }
                if (this.type.in("Random")) {
                    this.suffix_default = false;
                    this.suffix_random = true;
                }
                if (this.accept_suffix) {
                    if (this.suffix_default) {
                        message = message + " " + this.convert_base("bitcoin miner");
                    }
                    if (this.suffix_random) {
                        suffix_with_randoms = new StringBuilder();
                        suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_name)));
                        suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_finish)));
                        message = message + " " + suffix_with_randoms.toString();
                    }
                    if (message.length() >= 256) {
                        message.substring(0, 256);
                    }
                }
                ((CPacketChatMessage)event.get_packet()).message = message;
                return;
            }
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "Chat Suffix";
        this.tag = "ChatSuffix";
        this.description = "show off how cool u are";
    }
    
    public String random_string(final String[] list) {
        return list[new Random().nextInt(list.length)];
    }
    
    public String convert_base(final String base) {
        return Wurstplus.smoth(base);
    }
    
    @Override
    public String array_detail() {
        return this.type.get_current_value();
    }
}
