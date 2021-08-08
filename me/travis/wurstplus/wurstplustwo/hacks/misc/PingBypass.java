//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class PingBypass extends WurstplusHack
{
    public PingBypass() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Ping Bypass";
        this.tag = "Pingbypass";
        this.description = "get gud ping";
    }
    
    @Override
    protected void enable() {
        super.enable();
        PingBypass.mc.player.sendChatMessage("My ping is now 5 ms, thanks to Beat and BitcoinMinerHack");
        this.set_disable();
    }
}
