//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBreakUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoMine extends WurstplusHack
{
    WurstplusSetting end_crystal;
    WurstplusSetting range;
    
    public WurstplusAutoMine() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.end_crystal = this.create("End Crystal", "MineEndCrystal", false);
        this.range = this.create("Range", "MineRange", 4, 0, 6);
        this.name = "Auto Mine";
        this.tag = "AutoMine";
        this.description = "jumpy is now never going to use the client again";
    }
    
    @Override
    protected void enable() {
        BlockPos target_block = null;
        for (final EntityPlayer player : WurstplusAutoMine.mc.world.playerEntities) {
            if (WurstplusAutoMine.mc.player.getDistance((Entity)player) > this.range.get_value(1)) {
                continue;
            }
            final BlockPos p = WurstplusEntityUtil.is_cityable(player, this.end_crystal.get_value(true));
            if (p == null) {
                continue;
            }
            target_block = p;
        }
        if (target_block == null) {
            WurstplusMessageUtil.send_client_message("cannot find block");
            this.disable();
        }
        WurstplusBreakUtil.set_current_block(target_block);
    }
    
    @Override
    protected void disable() {
        WurstplusBreakUtil.set_current_block(null);
    }
}
