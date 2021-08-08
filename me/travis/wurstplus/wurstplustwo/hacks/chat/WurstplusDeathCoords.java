//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusDeathCoords extends WurstplusHack
{
    public WurstplusDeathCoords() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Death Coords";
        this.tag = "DeathCoords";
        this.description = "tells you where you died";
    }
    
    @Override
    public void update() {
        if (WurstplusDeathCoords.mc.player.isDead) {
            WurstplusMessageUtil.send_client_message("You just died" + ChatFormatting.WHITE + " at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(WurstplusDeathCoords.mc.player.posX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(WurstplusDeathCoords.mc.player.posY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(WurstplusDeathCoords.mc.player.posZ) + ChatFormatting.GRAY + "]");
            this.set_disable();
        }
    }
}
