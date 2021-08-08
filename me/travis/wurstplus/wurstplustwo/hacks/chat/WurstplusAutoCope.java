//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Random;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoCope extends WurstplusHack
{
    int diedTime;
    
    public WurstplusAutoCope() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.diedTime = 0;
        this.name = "Auto Cope";
        this.tag = "AutoCope";
        this.description = "Cope faNN";
    }
    
    @Override
    public void update() {
        if (this.diedTime > 0) {
            --this.diedTime;
        }
        if (WurstplusAutoCope.mc.player.isDead) {
            this.diedTime = 500;
        }
        if (!WurstplusAutoCope.mc.player.isDead && this.diedTime > 0) {
            final Random rand = new Random();
            final int randomNum = rand.nextInt(6) + 1;
            if (randomNum == 1) {
                WurstplusAutoCope.mc.player.sendChatMessage("I'm desync");
            }
            if (randomNum == 2) {
                WurstplusAutoCope.mc.player.sendChatMessage("i have bad config :)");
            }
            if (randomNum == 3) {
                WurstplusAutoCope.mc.player.sendChatMessage("shitty client");
            }
            if (randomNum == 4) {
                WurstplusAutoCope.mc.player.sendChatMessage("i have lag D:");
            }
            if (randomNum == 5) {
                WurstplusAutoCope.mc.player.sendChatMessage("i havent play in 2 weeks");
            }
            if (randomNum == 6) {
                WurstplusAutoCope.mc.player.sendChatMessage("shut up nn");
            }
            if (randomNum == 7) {
                WurstplusAutoCope.mc.player.sendChatMessage("shitty server");
            }
            if (randomNum == 8) {
                WurstplusAutoCope.mc.player.sendChatMessage("faNN");
            }
            this.diedTime = 0;
        }
    }
}
