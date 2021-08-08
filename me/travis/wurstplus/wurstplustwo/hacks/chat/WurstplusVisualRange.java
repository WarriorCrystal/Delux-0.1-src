//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusVisualRange extends WurstplusHack
{
    private List<String> people;
    
    public WurstplusVisualRange() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Visual Range";
        this.tag = "VisualRange";
        this.description = "bc using ur eyes is overrated";
    }
    
    public void enable() {
        this.people = new ArrayList<String>();
    }
    
    @Override
    public void update() {
        if (WurstplusVisualRange.mc.world == null | WurstplusVisualRange.mc.player == null) {
            return;
        }
        final List<String> peoplenew = new ArrayList<String>();
        final List<EntityPlayer> playerEntities = (List<EntityPlayer>)WurstplusVisualRange.mc.world.playerEntities;
        for (final Entity e : playerEntities) {
            if (e.getName().equals(WurstplusVisualRange.mc.player.getName())) {
                continue;
            }
            peoplenew.add(e.getName());
        }
        if (peoplenew.size() > 0) {
            for (final String name : peoplenew) {
                if (!this.people.contains(name)) {
                    if (WurstplusFriendUtil.isFriend(name)) {
                        WurstplusMessageUtil.send_client_message("I see an epic dude called " + ChatFormatting.RESET + ChatFormatting.GREEN + name + ChatFormatting.RESET + " :D");
                    }
                    else {
                        WurstplusMessageUtil.send_client_message("I see a dude called " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". Yuk");
                    }
                    this.people.add(name);
                }
            }
        }
    }
}
