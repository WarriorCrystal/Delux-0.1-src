//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.item.EntityEnderPearl;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.entity.Entity;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusPearlAlert extends WurstplusHack
{
    private final HashMap<EntityPlayer, UUID> list;
    private Entity enderPearl;
    private boolean flag;
    
    public WurstplusPearlAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Pearl Alert";
        this.tag = "PearlAlert";
        this.description = "Notifys pearl throws";
        this.list = new HashMap<EntityPlayer, UUID>();
    }
    
    @Override
    protected void enable() {
        this.flag = true;
    }
    
    @Override
    public void update() {
        if (WurstplusPearlAlert.mc.world != null && WurstplusPearlAlert.mc.player != null) {
            this.enderPearl = null;
            for (final Entity e : WurstplusPearlAlert.mc.world.loadedEntityList) {
                if (e instanceof EntityEnderPearl) {
                    this.enderPearl = e;
                    break;
                }
            }
            if (this.enderPearl == null) {
                this.flag = true;
            }
            else {
                EntityPlayer closestPlayer = null;
                for (final EntityPlayer entity : WurstplusPearlAlert.mc.world.playerEntities) {
                    if (closestPlayer == null) {
                        closestPlayer = entity;
                    }
                    else {
                        if (closestPlayer.getDistance(this.enderPearl) <= entity.getDistance(this.enderPearl)) {
                            continue;
                        }
                        closestPlayer = entity;
                    }
                }
                if (closestPlayer == WurstplusPearlAlert.mc.player) {
                    this.flag = false;
                }
                if (closestPlayer != null && this.flag) {
                    String faceing = this.enderPearl.getHorizontalFacing().toString();
                    if (faceing.equals("west")) {
                        faceing = "east";
                    }
                    else if (faceing.equals("east")) {
                        faceing = "west";
                    }
                    WurstplusMessageUtil.send_client_message(ChatFormatting.RED + closestPlayer.getName() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + " ");
                    this.flag = false;
                }
            }
        }
    }
}
