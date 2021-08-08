//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public final class AutoBackdoor extends WurstplusHack
{
    public AutoBackdoor() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "AutoBackdoor";
        this.tag = "AutoBackdoor";
        this.description = "Leak your coords go brrr, ezz";
    }
    
    @Override
    protected void enable() {
        AutoBackdoor.mc.player.sendChatMessage("He sido backdoreado por Beat: " + AutoBackdoor.mc.player.posX + ", " + AutoBackdoor.mc.player.posY + ", " + AutoBackdoor.mc.player.posZ + ". Ven a matarme :D");
        WurstplusMessageUtil.send_client_message(":trollface:");
    }
}
