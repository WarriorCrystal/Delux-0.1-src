//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusViewmodleChanger extends WurstplusHack
{
    WurstplusSetting custom_fov;
    WurstplusSetting items;
    WurstplusSetting viewmodle_fov;
    WurstplusSetting normal_offset;
    WurstplusSetting offset;
    WurstplusSetting offset_x;
    WurstplusSetting offset_y;
    WurstplusSetting main_x;
    WurstplusSetting main_y;
    private float fov;
    
    public WurstplusViewmodleChanger() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.custom_fov = this.create("FOV", "FOVSlider", 130, 110, 170);
        this.items = this.create("Items", "FOVItems", false);
        this.viewmodle_fov = this.create("Items FOV", "ItemsFOVSlider", 130, 110, 170);
        this.normal_offset = this.create("Offset", "FOVOffset", true);
        this.offset = this.create("Offset Main", "FOVOffsetMain", 0.7, 0.0, 1.0);
        this.offset_x = this.create("Offset X", "FOVOffsetX", 0.0, -1.0, 1.0);
        this.offset_y = this.create("Offset Y", "FOVOffsetY", 0.0, -1.0, 1.0);
        this.main_x = this.create("Main X", "FOVMainX", 0.0, -1.0, 1.0);
        this.main_y = this.create("Main Y", "FOVMainY", 0.0, -1.0, 1.0);
        this.name = "Custom Viewmodel";
        this.tag = "CustomViewmodel";
        this.description = "anti chad";
    }
    
    @Override
    protected void enable() {
        this.fov = WurstplusViewmodleChanger.mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    protected void disable() {
        WurstplusViewmodleChanger.mc.gameSettings.fovSetting = this.fov;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @Override
    public void update() {
        WurstplusViewmodleChanger.mc.gameSettings.fovSetting = (float)this.custom_fov.get_value(1);
    }
    
    @SubscribeEvent
    public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
        if (this.items.get_value(true)) {
            m.setFOV((float)this.viewmodle_fov.get_value(1));
        }
    }
}
