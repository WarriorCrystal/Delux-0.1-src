// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusCapes extends WurstplusHack
{
    WurstplusSetting cape;
    
    public WurstplusCapes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.cape = this.create("Cape", "CapeCape", "New", this.combobox("New", "OG"));
        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }
}
