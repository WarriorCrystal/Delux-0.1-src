//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class SmallShield extends WurstplusHack
{
    private static SmallShield INSTANCE;
    public WurstplusSetting normalOffset;
    public WurstplusSetting offset;
    public WurstplusSetting offX;
    public WurstplusSetting offY;
    public WurstplusSetting mainX;
    public WurstplusSetting mainY;
    
    public SmallShield() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.normalOffset = this.create("OffNormal", "OffNormal", false);
        this.offset = this.create("Offset", "Offset", 0.699999988079071, 0.0, 1.0);
        this.offX = this.create("OffX", "OffX", 0.0, -1.0, 1.0);
        this.offY = this.create("OffY", "OffY", 0.0, -1.0, 1.0);
        this.mainX = this.create("MainX", "MainX", 0.0, -1.0, 1.0);
        this.mainY = this.create("MainY", "MainY", 0.0, -1.0, 1.0);
        this.name = "SmallShield";
        this.tag = "SmallShield";
        this.description = "Small poop";
        this.setInstance();
    }
    
    public static SmallShield getINSTANCE() {
        if (SmallShield.INSTANCE == null) {
            SmallShield.INSTANCE = new SmallShield();
        }
        return SmallShield.INSTANCE;
    }
    
    private void setInstance() {
        SmallShield.INSTANCE = this;
    }
    
    @Override
    public void update() {
        if (this.normalOffset.get_value(false)) {
            SmallShield.mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float)this.offset.get_value(0.699999988079071);
        }
    }
    
    static {
        SmallShield.INSTANCE = new SmallShield();
    }
}
