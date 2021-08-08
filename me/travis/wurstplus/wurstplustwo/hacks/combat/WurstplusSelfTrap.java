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
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusSelfTrap extends WurstplusHack
{
    WurstplusSetting toggle;
    WurstplusSetting rotate;
    WurstplusSetting swing;
    private BlockPos trap_pos;
    
    public WurstplusSelfTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.toggle = this.create("Toggle", "SelfTrapToggle", false);
        this.rotate = this.create("Rotate", "SelfTrapRotate", false);
        this.swing = this.create("Swing", "SelfTrapSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.name = "Self Trap";
        this.tag = "SelfTrap";
        this.description = "oh 'eck, ive trapped me sen again";
    }
    
    @Override
    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
    }
    
    @Override
    public void update() {
        final Vec3d pos = WurstplusMathUtil.interpolateEntity((Entity)WurstplusSelfTrap.mc.player, WurstplusSelfTrap.mc.getRenderPartialTicks());
        this.trap_pos = new BlockPos(pos.x, pos.y + 2.0, pos.z);
        if (this.is_trapped() && !this.toggle.get_value(true)) {
            this.toggle();
            return;
        }
        final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(this.trap_pos);
        if (result == WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere && !WurstplusSelfTrap.mc.world.getBlockState(this.trap_pos).getMaterial().isReplaceable()) {
            return;
        }
        if (result == WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
            final BlockPos[] array;
            final BlockPos[] tests = array = new BlockPos[] { this.trap_pos.north(), this.trap_pos.south(), this.trap_pos.east(), this.trap_pos.west(), this.trap_pos.up(), this.trap_pos.down().west() };
            for (final BlockPos pos_ : array) {
                final WurstplusBlockInteractHelper.ValidResult result_ = WurstplusBlockInteractHelper.valid(pos_);
                if (result_ != WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
                    if (result_ != WurstplusBlockInteractHelper.ValidResult.NoEntityCollision) {
                        if (WurstplusBlockUtil.placeBlock(pos_, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                            return;
                        }
                    }
                }
            }
            return;
        }
        WurstplusBlockUtil.placeBlock(this.trap_pos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }
    
    public boolean is_trapped() {
        if (this.trap_pos == null) {
            return false;
        }
        final IBlockState state = WurstplusSelfTrap.mc.world.getBlockState(this.trap_pos);
        return state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.LAVA;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSelfTrap.mc.player.inventory.getStackInSlot(i);
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
