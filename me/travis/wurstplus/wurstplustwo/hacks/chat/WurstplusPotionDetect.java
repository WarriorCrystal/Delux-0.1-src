//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.init.MobEffects;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Set;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusPotionDetect extends WurstplusHack
{
    private final Set<EntityPlayer> str;
    public static final Minecraft mc;
    
    public WurstplusPotionDetect() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "PotionDetect";
        this.tag = "PotionDetect";
        this.description = "Detects Potion Effects";
        this.str = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    @Override
    public void update() {
        for (final EntityPlayer player : WurstplusPotionDetect.mc.world.playerEntities) {
            if (player.equals((Object)WurstplusPotionDetect.mc.player)) {
                continue;
            }
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                WurstplusMessageUtil.client_message_simple(TextFormatting.DARK_RED + player.getDisplayNameString() + " Has Strength");
                this.str.add(player);
            }
            if (!this.str.contains(player)) {
                continue;
            }
            if (player.isPotionActive(MobEffects.STRENGTH)) {
                continue;
            }
            WurstplusMessageUtil.client_message_simple(TextFormatting.DARK_RED + player.getDisplayNameString() + " Has Ran Out Of Strength");
            this.str.remove(player);
        }
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
