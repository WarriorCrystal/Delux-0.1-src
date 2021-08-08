//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import net.minecraft.client.gui.GuiScreen;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.client.Minecraft;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class GetIP extends WurstplusHack
{
    public GetIP() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Get Server IP";
        this.tag = "GetIP";
        this.description = "Copy the ip of the server!";
    }
    
    public void enable() {
        if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
            WurstplusMessageUtil.send_client_message("You\u00c2´re not connected to a server!");
        }
        else {
            final String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
            GuiScreen.setClipboardString(ip);
            WurstplusMessageUtil.send_client_message(ip + " copied to your clipboard.");
        }
    }
}
