//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusSocks extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting swing;
    
    public WurstplusSocks() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "SocksRotate", false);
        this.swing = this.create("Swing", "SocksSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.name = "Socks";
        this.tag = "Socks";
        this.description = "Protects your feet";
    }
    
    @Override
    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
    }
    
    @Override
    public void update() {
        final int slot = this.find_in_hotbar();
        if (slot == -1) {
            return;
        }
        final BlockPos center_pos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        final ArrayList<BlockPos> blocks_to_fill = new ArrayList<BlockPos>();
        switch (WurstplusPlayerUtil.GetFacing()) {
            case East: {
                blocks_to_fill.add(center_pos.east().east());
                blocks_to_fill.add(center_pos.east().east().up());
                blocks_to_fill.add(center_pos.east().east().east());
                blocks_to_fill.add(center_pos.east().east().east().up());
                break;
            }
            case North: {
                blocks_to_fill.add(center_pos.north().north());
                blocks_to_fill.add(center_pos.north().north().up());
                blocks_to_fill.add(center_pos.north().north().north());
                blocks_to_fill.add(center_pos.north().north().north().up());
                break;
            }
            case South: {
                blocks_to_fill.add(center_pos.south().south());
                blocks_to_fill.add(center_pos.south().south().up());
                blocks_to_fill.add(center_pos.south().south().south());
                blocks_to_fill.add(center_pos.south().south().south().up());
                break;
            }
            case West: {
                blocks_to_fill.add(center_pos.west().west());
                blocks_to_fill.add(center_pos.west().west().up());
                blocks_to_fill.add(center_pos.west().west().west());
                blocks_to_fill.add(center_pos.west().west().west().up());
                break;
            }
        }
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : blocks_to_fill) {
            final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok) {
                continue;
            }
            if (pos == null) {
                continue;
            }
            pos_to_fill = pos;
            break;
        }
        WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSocks.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
}
