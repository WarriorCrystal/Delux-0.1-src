//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.TextComponentBase;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

public class WurstplusMessageUtil
{
    public static final Minecraft mc;
    public static ChatFormatting g;
    public static ChatFormatting b;
    public static ChatFormatting a;
    public static ChatFormatting r;
    public static String opener;
    
    public static void toggle_message(final WurstplusHack module) {
        if (module.is_active()) {
            if (module.get_tag().equals("AutoCrystal")) {
                client_message_simple(WurstplusMessageUtil.opener + ChatFormatting.LIGHT_PURPLE + "Delux" + ChatFormatting.AQUA + " Mode" + ChatFormatting.GRAY + " ON");
            }
            else {
                client_message_simple(WurstplusMessageUtil.opener + WurstplusMessageUtil.r + module.get_name() + ChatFormatting.BLUE + " Enabled");
            }
        }
        else if (module.get_tag().equals("AutoCrystal")) {
            client_message_simple(WurstplusMessageUtil.opener + ChatFormatting.BOLD + "Delux" + ChatFormatting.RED + " Mode" + WurstplusMessageUtil.r + ChatFormatting.DARK_GRAY + " OFF");
        }
        else {
            client_message_simple(WurstplusMessageUtil.opener + WurstplusMessageUtil.r + module.get_name() + ChatFormatting.RED + " Disabled");
        }
    }
    
    public static void client_message_simple(final String message) {
        if (WurstplusMessageUtil.mc.player != null) {
            final ITextComponent itc = new TextComponentString(message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("frank alachi"))));
            WurstplusMessageUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
        }
    }
    
    public static void client_message(final String message) {
        if (WurstplusMessageUtil.mc.player != null) {
            WurstplusMessageUtil.mc.player.sendMessage((ITextComponent)new ChatMessage(message));
        }
    }
    
    public static void send_client_message_simple(final String message) {
        client_message(ChatFormatting.GOLD + Wurstplus.WURSTPLUS_NAME + " " + WurstplusMessageUtil.r + message);
    }
    
    public static void send_client_message(final String message) {
        client_message(ChatFormatting.GOLD + Wurstplus.WURSTPLUS_NAME + " " + WurstplusMessageUtil.r + message);
    }
    
    public static void send_client_error_message(final String message) {
        client_message(ChatFormatting.RED + Wurstplus.WURSTPLUS_NAME + " " + WurstplusMessageUtil.r + message);
    }
    
    static {
        mc = Minecraft.getMinecraft();
        WurstplusMessageUtil.g = ChatFormatting.GOLD;
        WurstplusMessageUtil.b = ChatFormatting.BLUE;
        WurstplusMessageUtil.a = ChatFormatting.DARK_AQUA;
        WurstplusMessageUtil.r = ChatFormatting.RESET;
        WurstplusMessageUtil.opener = WurstplusMessageUtil.g + Wurstplus.WURSTPLUS_NAME + ChatFormatting.GRAY + " > " + WurstplusMessageUtil.r;
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        String message_input;
        
        public ChatMessage(final String message) {
            final Pattern p = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher m = p.matcher(message);
            final StringBuffer sb = new StringBuffer();
            while (m.find()) {
                final String replacement = "§" + m.group().substring(1);
                m.appendReplacement(sb, replacement);
            }
            m.appendTail(sb);
            this.message_input = sb.toString();
        }
        
        public String getUnformattedComponentText() {
            return this.message_input;
        }
        
        public ITextComponent createCopy() {
            return (ITextComponent)new ChatMessage(this.message_input);
        }
    }
}
