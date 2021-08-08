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

public class WurstplusAutoToxic extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting spammer;
    List<String> chants;
    Random r;
    int tick_delay;
    
    public WurstplusAutoToxic() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.delay = this.create("Delay", "AutoToxicDelay", 10, 0, 100);
        this.spammer = this.create("Spammer", "AutoToxicSpammer", true);
        this.chants = new ArrayList<String>();
        this.r = new Random();
        this.name = "AutoToxic";
        this.tag = "AutoToxic";
        this.description = "Very toxic";
    }
    
    @Override
    protected void enable() {
        this.tick_delay = 0;
        this.chants.add("<player> shut up");
        this.chants.add("<player> haha, cry nn");
        this.chants.add("<player> lol, cope harder faNN");
        this.chants.add("<player> lol, you're so fucking ez");
        this.chants.add("<player> shut up nigga");
        this.chants.add("<player> shut up pedo");
        this.chants.add("<player> lol, you're so fucking bad");
        this.chants.add("<player> so fucking ez haha");
        this.chants.add("yall so bad");
        this.chants.add("<player> NN");
        this.chants.add("<player> cope noname");
    }
    
    @Override
    public void update() {
        if (this.spammer.get_value(true)) {
            ++this.tick_delay;
            if (this.tick_delay < this.delay.get_value(1) * 10) {
                return;
            }
            final String s = this.chants.get(this.r.nextInt(this.chants.size()));
            final String name = this.get_random_name();
            if (name.equals(WurstplusAutoToxic.mc.player.getName())) {
                return;
            }
            WurstplusAutoToxic.mc.player.sendChatMessage(s.replace("<player>", name));
            this.tick_delay = 0;
        }
    }
    
    public String get_random_name() {
        final List<EntityPlayer> players = (List<EntityPlayer>)WurstplusAutoToxic.mc.world.playerEntities;
        return players.get(this.r.nextInt(players.size())).getName();
    }
    
    public String random_string(final String[] list) {
        return list[this.r.nextInt(list.length)];
    }
}
