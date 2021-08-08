//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoWither extends WurstplusHack
{
    WurstplusSetting range;
    private int head_slot;
    private int sand_slot;
    
    public WurstplusAutoWither() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.range = this.create("Range", "WitherRange", 4, 0, 6);
        this.name = "Auto Wither";
        this.tag = "AutoWither";
        this.description = "makes withers";
    }
    
    @Override
    protected void enable() {
    }
    
    public boolean has_blocks() {
        int count = 0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWither.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockSoulSand) {
                    this.sand_slot = i;
                    ++count;
                    break;
                }
            }
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWither.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.SKULL && stack.getItemDamage() == 1) {
                this.head_slot = i;
                ++count;
                break;
            }
        }
        return count == 2;
    }
}
