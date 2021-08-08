//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class FOVSlider extends WurstplusHack
{
    private float fov;
    WurstplusSetting FOV;
    WurstplusSetting mode;
    
    public FOVSlider() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.FOV = this.create("FOV", "FOV", 110, 90, 200);
        this.mode = this.create("Mode", "Mode", "FOV Changer", this.combobox("FOV Changer", "Hand Changer"));
        this.name = "FOVSlider";
        this.tag = "FOVSlider";
        this.description = "Changes FOV";
    }
    
    @SubscribeEvent
    public void fovOn(final EntityViewRenderEvent.FOVModifier e) {
        if (this.mode.in("Hand Changer")) {
            e.setFOV((float)this.FOV.get_value(1));
        }
    }
    
    public void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.fov = FOVSlider.mc.gameSettings.fovSetting;
    }
    
    public void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        FOVSlider.mc.gameSettings.fovSetting = this.fov;
    }
    
    @Override
    public void update() {
        if (FOVSlider.mc.world == null) {
            return;
        }
        if (this.mode.in("FOV Changer")) {
            FOVSlider.mc.gameSettings.fovSetting = (float)this.FOV.get_value(1);
        }
    }
}
