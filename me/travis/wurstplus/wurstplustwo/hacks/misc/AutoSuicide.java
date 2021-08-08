//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoSuicide extends WurstplusHack
{
    public AutoSuicide() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "AutoSuicide";
        this.tag = "AutoSuicide";
        this.description = "auto suicide";
    }
    
    @Override
    protected void enable() {
        AutoSuicide.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kill"));
        this.disable();
    }
}
