//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.concurrent.ConcurrentHashMap;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class BurrowAlert extends WurstplusHack
{
    private final ConcurrentHashMap<EntityPlayer, Integer> players;
    List<EntityPlayer> anti_spam;
    List<Entity> burrowedPlayers;
    
    public BurrowAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.players = new ConcurrentHashMap<EntityPlayer, Integer>();
        this.anti_spam = new ArrayList<EntityPlayer>();
        this.burrowedPlayers = new ArrayList<Entity>();
        this.name = "BurrowAlert";
        this.tag = "BurrowAlert";
        this.description = "BurrowAlert";
    }
    
    @Override
    protected void enable() {
        this.players.clear();
        this.anti_spam.clear();
    }
    
    @Override
    public void update() {
        if (BurrowAlert.mc.player == null || BurrowAlert.mc.world == null) {
            return;
        }
        for (final Entity entity : (List)BurrowAlert.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).collect(Collectors.toList())) {
            if (!(entity instanceof EntityPlayer)) {
                continue;
            }
            if (!this.burrowedPlayers.contains(entity) && this.isBurrowed(entity)) {
                this.burrowedPlayers.add(entity);
                WurstplusMessageUtil.send_client_message(ChatFormatting.RED + entity.getName() + " has just burrowed!");
            }
            else {
                if (!this.burrowedPlayers.contains(entity)) {
                    continue;
                }
                if (this.isBurrowed(entity)) {
                    continue;
                }
                this.burrowedPlayers.remove(entity);
                WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + entity.getName() + " is no longer burrowed!");
            }
        }
    }
    
    private boolean isBurrowed(final Entity entity) {
        final BlockPos entityPos = new BlockPos(this.roundValueToCenter(entity.posX), entity.posY + 0.2, this.roundValueToCenter(entity.posZ));
        return BurrowAlert.mc.world.getBlockState(entityPos).getBlock() == Blocks.OBSIDIAN || BurrowAlert.mc.world.getBlockState(entityPos).getBlock() == Blocks.ENDER_CHEST;
    }
    
    private double roundValueToCenter(final double inputVal) {
        double roundVal = (double)Math.round(inputVal);
        if (roundVal > inputVal) {
            roundVal -= 0.5;
        }
        else if (roundVal <= inputVal) {
            roundVal += 0.5;
        }
        return roundVal;
    }
}
