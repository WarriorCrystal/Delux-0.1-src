//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import java.util.Collection;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusHoleFill extends WurstplusHack
{
    WurstplusSetting hole_toggle;
    WurstplusSetting hole_rotate;
    WurstplusSetting hole_range;
    WurstplusSetting swing;
    private final ArrayList<BlockPos> holes;
    
    public WurstplusHoleFill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.hole_toggle = this.create("Toggle", "HoleFillToggle", true);
        this.hole_rotate = this.create("Rotate", "HoleFillRotate", true);
        this.hole_range = this.create("Range", "HoleFillRange", 4, 1, 6);
        this.swing = this.create("Swing", "HoleFillSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.holes = new ArrayList<BlockPos>();
        this.name = "Hole Fill";
        this.tag = "HoleFill";
        this.description = "Turn holes into floors";
    }
    
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
        this.find_new_holes();
    }
    
    public void disable() {
        this.holes.clear();
    }
    
    @Override
    public void update() {
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (this.holes.isEmpty()) {
            if (!this.hole_toggle.get_value(true)) {
                this.set_disable();
                WurstplusMessageUtil.toggle_message(this);
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) {
                continue;
            }
            final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result == WurstplusBlockInteractHelper.ValidResult.Ok) {
                pos_to_fill = pos;
                break;
            }
            this.holes.remove(pos);
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (pos_to_fill != null && WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.hole_rotate.get_value(true), this.hole_rotate.get_value(true), this.swing)) {
            this.holes.remove(pos_to_fill);
        }
    }
    
    public void find_new_holes() {
        this.holes.clear();
        for (final BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), (float)this.hole_range.get_value(1), this.hole_range.get_value(1), false, true, 0)) {
            if (!WurstplusHoleFill.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!WurstplusHoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!WurstplusHoleFill.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            boolean possible = true;
            for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                final Block block = WurstplusHoleFill.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                continue;
            }
            this.holes.add(pos);
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusHoleFill.mc.player.inventory.getStackInSlot(i);
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
