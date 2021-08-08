//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Fullbright extends WurstplusHack
{
    float old_gamma_value;
    WurstplusSetting mode;
    
    public Fullbright() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "Mode", "Gamma", this.combobox("Gamma", "Potion"));
        this.name = "Brightness";
        this.tag = "Brightness";
        this.description = "popi braitnes";
    }
    
    public void enable() {
        this.old_gamma_value = Fullbright.mc.gameSettings.gammaSetting;
    }
    
    @Override
    public void update() {
        if (this.mode.in("Gamma")) {
            Fullbright.mc.gameSettings.gammaSetting = 20.0f;
        }
        else {
            Fullbright.mc.gameSettings.gammaSetting = 1.0f;
            Fullbright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }
    
    public void disable() {
        Fullbright.mc.gameSettings.gammaSetting = this.old_gamma_value;
        Fullbright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
    }
}
