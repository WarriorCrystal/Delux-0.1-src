//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class CreativeBypass extends WurstplusHack
{
    public CreativeBypass() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "CreativeBypass";
        this.tag = "CreativeBypass";
        this.description = "";
    }
    
    @Override
    protected void enable() {
        if (CreativeBypass.mc.player != null) {
            CreativeBypass.mc.player.sendChatMessage("Soy un NewFag, un FitFag, soy un retrasado, me encanta chupar vergas y tragarme la leche si.");
        }
        CreativeBypass.mc.player.sendChatMessage("/kill");
    }
}
