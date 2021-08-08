//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusFastUtil extends WurstplusHack
{
    WurstplusSetting fast_place;
    WurstplusSetting fast_break;
    WurstplusSetting crystal;
    WurstplusSetting exp;
    
    public WurstplusFastUtil() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.fast_place = this.create("Place", "WurstplusFastPlace", false);
        this.fast_break = this.create("Break", "WurstplusFastBreak", false);
        this.crystal = this.create("Crystal", "WurstplusFastCrystal", false);
        this.exp = this.create("EXP", "WurstplusFastExp", true);
        this.name = "Fast Util";
        this.tag = "FastUtil";
        this.description = "use things faster";
    }
    
    @Override
    public void update() {
        final Item main = WurstplusFastUtil.mc.player.getHeldItemMainhand().getItem();
        final Item off = WurstplusFastUtil.mc.player.getHeldItemOffhand().getItem();
        final boolean main_exp = main instanceof ItemExpBottle;
        final boolean off_exp = off instanceof ItemExpBottle;
        final boolean main_cry = main instanceof ItemEndCrystal;
        final boolean off_cry = off instanceof ItemEndCrystal;
        if ((main_exp | off_exp) && this.exp.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if ((main_cry | off_cry) && this.crystal.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if (!(main_exp | off_exp | main_cry | off_cry) && this.fast_place.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if (this.fast_break.get_value(true)) {
            WurstplusFastUtil.mc.playerController.blockHitDelay = 0;
        }
    }
}
