// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusSkyColour extends WurstplusHack
{
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting rainbow_mode;
    
    public WurstplusSkyColour() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.r = this.create("R", "SkyColourR", 255, 0, 255);
        this.g = this.create("G", "SkyColourG", 255, 0, 255);
        this.b = this.create("B", "SkyColourB", 255, 0, 255);
        this.rainbow_mode = this.create("Rainbow", "SkyColourRainbow", false);
        this.name = "Sky Colour";
        this.tag = "SkyColour";
        this.description = "Changes the sky's colour";
    }
    
    @SubscribeEvent
    public void fog_colour(final EntityViewRenderEvent.FogColors event) {
        event.setRed(this.r.get_value(1) / 255.0f);
        event.setGreen(this.g.get_value(1) / 255.0f);
        event.setBlue(this.b.get_value(1) / 255.0f);
    }
    
    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
    
    @Override
    protected void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    protected void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
}
