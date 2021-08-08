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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusSurround extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting hybrid;
    WurstplusSetting triggerable;
    WurstplusSetting center;
    WurstplusSetting block_head;
    WurstplusSetting tick_for_place;
    WurstplusSetting tick_timeout;
    WurstplusSetting swing;
    private int y_level;
    private int tick_runs;
    private int offset_step;
    private Vec3d center_block;
    Vec3d[] surround_targets;
    Vec3d[] surround_targets_face;
    
    public WurstplusSurround() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "SurroundSmoth", true);
        this.hybrid = this.create("Hybrid", "SurroundHybrid", true);
        this.triggerable = this.create("Toggle", "SurroundToggle", true);
        this.center = this.create("Center", "SurroundCenter", false);
        this.block_head = this.create("Block Face", "SurroundBlockFace", false);
        this.tick_for_place = this.create("Blocks per tick", "SurroundTickToPlace", 2, 1, 8);
        this.tick_timeout = this.create("Ticks til timeout", "SurroundTicks", 20, 10, 50);
        this.swing = this.create("Swing", "SurroundSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.y_level = 0;
        this.tick_runs = 0;
        this.offset_step = 0;
        this.center_block = Vec3d.ZERO;
        this.surround_targets = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0) };
        this.surround_targets_face = new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0) };
        this.name = "Surround";
        this.tag = "Surround";
        this.description = "surround urself with obi and such";
    }
    
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
        if (WurstplusSurround.mc.player != null) {
            this.y_level = (int)Math.round(WurstplusSurround.mc.player.posY);
            this.center_block = this.get_center(WurstplusSurround.mc.player.posX, WurstplusSurround.mc.player.posY, WurstplusSurround.mc.player.posZ);
            if (this.center.get_value(true)) {
                WurstplusSurround.mc.player.motionX = 0.0;
                WurstplusSurround.mc.player.motionZ = 0.0;
            }
        }
    }
    
    @Override
    public void update() {
        if (WurstplusSurround.mc.player != null) {
            if (this.center_block != Vec3d.ZERO && this.center.get_value(true)) {
                final double x_diff = Math.abs(this.center_block.x - WurstplusSurround.mc.player.posX);
                final double z_diff = Math.abs(this.center_block.z - WurstplusSurround.mc.player.posZ);
                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    this.center_block = Vec3d.ZERO;
                }
                else {
                    final double motion_x = this.center_block.x - WurstplusSurround.mc.player.posX;
                    final double motion_z = this.center_block.z - WurstplusSurround.mc.player.posZ;
                    WurstplusSurround.mc.player.motionX = motion_x / 2.0;
                    WurstplusSurround.mc.player.motionZ = motion_z / 2.0;
                }
            }
            if ((int)Math.round(WurstplusSurround.mc.player.posY) != this.y_level && this.hybrid.get_value(true)) {
                this.set_disable();
                return;
            }
            if (!this.triggerable.get_value(true) && this.tick_runs >= this.tick_timeout.get_value(1)) {
                this.tick_runs = 0;
                this.set_disable();
                return;
            }
            int blocks_placed = 0;
            while (blocks_placed < this.tick_for_place.get_value(1)) {
                if (this.offset_step >= (this.block_head.get_value(true) ? this.surround_targets_face.length : this.surround_targets.length)) {
                    this.offset_step = 0;
                    break;
                }
                final BlockPos offsetPos = new BlockPos(this.block_head.get_value(true) ? this.surround_targets_face[this.offset_step] : this.surround_targets[this.offset_step]);
                final BlockPos targetPos = new BlockPos(WurstplusSurround.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                boolean try_to_place = true;
                if (!WurstplusSurround.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    try_to_place = false;
                }
                for (final Entity entity : WurstplusSurround.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos))) {
                    if (!(entity instanceof EntityItem)) {
                        if (entity instanceof EntityXPOrb) {
                            continue;
                        }
                        try_to_place = false;
                        break;
                    }
                }
                if (try_to_place && WurstplusBlockUtil.placeBlock(targetPos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                    ++blocks_placed;
                }
                ++this.offset_step;
            }
            ++this.tick_runs;
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSurround.mc.player.inventory.getStackInSlot(i);
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
    
    public Vec3d get_center(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}
