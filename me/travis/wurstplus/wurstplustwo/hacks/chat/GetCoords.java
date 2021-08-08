//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import net.minecraft.client.gui.GuiScreen;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class GetCoords extends WurstplusHack
{
    public GetCoords() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Get Coords";
        this.tag = "GetCoords";
        this.description = "Copy the ip of the server!";
    }
    
    public void enable() {
        final String coords = this.getFormattedCoordinates((Entity)Minecraft.getMinecraft().player);
        WurstplusMessageUtil.send_client_message("Coords copied to your clipboard.");
        GuiScreen.setClipboardString(coords);
    }
    
    private String getFormattedCoordinates(final Entity entity) {
        return (int)entity.posX + ", " + (int)entity.posY + ", " + (int)entity.posZ;
    }
}
