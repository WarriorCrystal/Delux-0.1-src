//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import java.util.Random;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoDox extends WurstplusHack
{
    WurstplusSetting delay;
    List<String> chants;
    Random r;
    int tick_delay;
    
    public WurstplusAutoDox() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.delay = this.create("Delay", "AutoDoxDelay", 20, 0, 100);
        this.chants = new ArrayList<String>();
        this.r = new Random();
        this.name = "Auto Dox";
        this.tag = "AutoDox";
        this.description = "Auto dox people";
    }
    
    @Override
    protected void enable() {
        this.tick_delay = 0;
        this.chants.add("https://doxbin.org/upload/<player>");
    }
    
    @Override
    public void update() {
        ++this.tick_delay;
        if (this.tick_delay >= this.delay.get_value(1) * 10) {
            final String s = this.chants.get(this.r.nextInt(this.chants.size()));
            final String name = this.get_random_name();
            if (!name.equals(WurstplusAutoDox.mc.player.getName())) {
                WurstplusAutoDox.mc.player.sendChatMessage(s.replace("<player>", name));
                this.tick_delay = 0;
            }
        }
    }
    
    public String get_random_name() {
        final List<EntityPlayer> players = (List<EntityPlayer>)WurstplusAutoDox.mc.world.playerEntities;
        return players.get(this.r.nextInt(players.size())).getName();
    }
    
    public String random_string(final String[] list) {
        return list[this.r.nextInt(list.length)];
    }
}
