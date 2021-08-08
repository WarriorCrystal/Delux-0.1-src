//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoWeather extends WurstplusHack
{
    public NoWeather() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "No Weather";
        this.tag = "NoWeather";
        this.description = "Disables weather render";
    }
    
    @Override
    public void update() {
        if (NoWeather.mc.world != null) {
            if (NoWeather.mc.world.isRaining()) {
                NoWeather.mc.world.setRainStrength(0.0f);
            }
            if (NoWeather.mc.world.isThundering()) {
                NoWeather.mc.world.setThunderStrength(0.0f);
            }
        }
    }
}
