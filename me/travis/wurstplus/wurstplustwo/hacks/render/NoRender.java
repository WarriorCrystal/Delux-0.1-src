//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.Iterator;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoRender extends WurstplusHack
{
    WurstplusSetting falling;
    WurstplusSetting weather;
    WurstplusSetting potionef;
    WurstplusSetting pumpkin;
    WurstplusSetting portal;
    WurstplusSetting fireworks;
    WurstplusSetting advancements;
    WurstplusSetting fire;
    WurstplusSetting hurtcam;
    WurstplusSetting armor;
    WurstplusSetting noboss;
    
    public NoRender() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.falling = this.create("Falling Blocks", "Falling", true);
        this.weather = this.create("Wheather", "Weather", true);
        this.potionef = this.create("Potion Icons", "PotionIcons", true);
        this.pumpkin = this.create("Pumpkin Overlay", "Pumpkin", true);
        this.portal = this.create("Portal Overlay", "PortalOverlay", true);
        this.fireworks = this.create("FireWorks", "FireWorks", false);
        this.advancements = this.create("Advancements", "Advancements", false);
        this.fire = this.create("Fire", "Fire", true);
        this.hurtcam = this.create("Hurt Cam", "HurtCam", true);
        this.armor = this.create("Armor", "Armor", false);
        this.noboss = this.create("NoBossOverlay", "NoBossOverlay", false);
        this.name = "No Render";
        this.tag = "NoRender";
        this.description = "stop the renderization of some things";
    }
    
    @Override
    public void update() {
        if (this.falling.get_value(true)) {
            for (final Entity e : NoRender.mc.world.loadedEntityList) {
                if (e instanceof EntityFallingBlock) {
                    NoRender.mc.world.removeEntity(e);
                    if (NoRender.mc.world == null) {
                        return;
                    }
                    continue;
                }
            }
        }
        if (this.weather.get_value(true)) {
            if (NoRender.mc.world == null) {
                return;
            }
            if (NoRender.mc.world.isRaining()) {
                NoRender.mc.world.setRainStrength(0.0f);
            }
            if (NoRender.mc.world.isThundering()) {
                NoRender.mc.world.setThunderStrength(0.0f);
            }
        }
    }
}
