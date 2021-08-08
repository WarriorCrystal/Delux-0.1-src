//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoFall extends WurstplusHack
{
    public NoFall() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "NoFall";
        this.tag = "NoFall";
        this.description = "fall go brrn't";
        this.toggle_message = false;
    }
    
    @Override
    protected void enable() {
        if (NoFall.mc.player != null) {
            NoFall.mc.player.fallDistance = 0.0f;
        }
    }
    
    @Override
    protected void disable() {
        if (NoFall.mc.player != null) {
            NoFall.mc.player.fallDistance = 0.0f;
        }
    }
    
    @Override
    public void update() {
        if (NoFall.mc.player.fallDistance != 0.0f) {
            NoFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer(true));
        }
    }
}
