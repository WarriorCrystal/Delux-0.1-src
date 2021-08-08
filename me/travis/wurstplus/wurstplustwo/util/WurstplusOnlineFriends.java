//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import java.util.Collection;
import net.minecraft.entity.Entity;
import java.util.List;

public class WurstplusOnlineFriends
{
    public static List<Entity> entities;
    
    public static List<Entity> getFriends() {
        WurstplusOnlineFriends.entities.clear();
        WurstplusOnlineFriends.entities.addAll((Collection<? extends Entity>)Minecraft.getMinecraft().world.playerEntities.stream().filter(entityPlayer -> WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        return WurstplusOnlineFriends.entities;
    }
    
    static {
        WurstplusOnlineFriends.entities = new ArrayList<Entity>();
    }
}
