// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Reach extends WurstplusHack
{
    WurstplusSetting reach;
    
    public Reach() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.reach = this.create("Reach", "Reach", 3.0, 0.5, 10.0);
        this.name = "Reach";
        this.tag = "Reach";
        this.description = "adds reach";
    }
}
