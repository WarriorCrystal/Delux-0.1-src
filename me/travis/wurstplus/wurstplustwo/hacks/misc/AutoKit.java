//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoKit extends WurstplusHack
{
    private final WurstplusSetting kit;
    
    public AutoKit() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.kit = this.create("", "", false);
        this.name = "AutoKit";
        this.tag = "AutoKit";
        this.description = "auto kit";
    }
    
    @Override
    protected void enable() {
        if (this.kit.get_value(true)) {
            AutoKit.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kit"));
            this.kit.set_value(true);
            this.toggle();
        }
        else {
            AutoKit.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kit"));
        }
        this.toggle();
    }
}
