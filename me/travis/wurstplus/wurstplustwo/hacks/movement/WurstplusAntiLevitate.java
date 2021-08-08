//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.Objects;
import net.minecraft.potion.Potion;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAntiLevitate extends WurstplusHack
{
    public WurstplusAntiLevitate() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "AntiLevitate";
        this.tag = "AntiLevitate";
        this.description = "Removes shulker levitation";
    }
    
    @Override
    public void update() {
        if (WurstplusAntiLevitate.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("levitation")))) {
            WurstplusAntiLevitate.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("levitation"));
        }
    }
}
