//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoChad extends WurstplusHack
{
    public WurstplusAutoChad() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "AutoChad";
        this.tag = "AutoChad";
        this.description = "Lol";
    }
    
    @Override
    protected void enable() {
        super.enable();
        WurstplusAutoChad.mc.player.sendChatMessage("Delux owns me and all, cope");
        this.set_disable();
    }
}
