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

public class WurstplusPlayerAlert extends WurstplusHack
{
    private List<String> people;
    
    public WurstplusPlayerAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Player Alert";
        this.tag = "PlayerAlert";
        this.description = "aea";
    }
    
    @Override
    protected void enable() {
        this.people = new ArrayList<String>();
    }
    
    @Override
    public void update() {
        if (!(WurstplusPlayerAlert.mc.world == null | WurstplusPlayerAlert.mc.player == null)) {
            final List<String> peoplenew = new ArrayList<String>();
            final List<EntityPlayer> playerEntities = (List<EntityPlayer>)WurstplusPlayerAlert.mc.world.playerEntities;
            for (final Entity e : playerEntities) {
                if (!e.getName().equals(WurstplusPlayerAlert.mc.player.getName())) {
                    peoplenew.add(e.getName());
                }
            }
            if (peoplenew.size() > 0) {
                for (final String name : peoplenew) {
                    if (!this.people.contains(name)) {
                        if (WurstplusFriendUtil.isFriend(name)) {
                            WurstplusMessageUtil.send_client_message("There is " + ChatFormatting.RESET + ChatFormatting.AQUA + name + ChatFormatting.RESET + " :D");
                        }
                        else {
                            WurstplusMessageUtil.send_client_message("There is " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". Yuk");
                        }
                        this.people.add(name);
                    }
                }
            }
        }
    }
}
