//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.client.gui.GuiGameOver;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoRespawn extends WurstplusHack
{
    public AutoRespawn() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto Respawn";
        this.tag = "AutoRespawn";
        this.description = "automatically respawns";
    }
    
    @Override
    public void update() {
        if (AutoRespawn.mc.player.isDead && AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            AutoRespawn.mc.player.respawnPlayer();
        }
    }
}
