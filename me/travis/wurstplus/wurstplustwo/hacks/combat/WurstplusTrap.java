//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusTrap extends WurstplusHack
{
    WurstplusSetting place_mode;
    WurstplusSetting blocks_per_tick;
    WurstplusSetting rotate;
    WurstplusSetting chad_mode;
    WurstplusSetting range;
    WurstplusSetting swing;
    private final Vec3d[] offsets_default;
    private final Vec3d[] offsets_face;
    private final Vec3d[] offsets_feet;
    private final Vec3d[] offsets_extra;
    private String last_tick_target_name;
    private int offset_step;
    private int timeout_ticker;
    private int y_level;
    private boolean first_run;
    
    public WurstplusTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.place_mode = this.create("Place Mode", "TrapPlaceMode", "Extra", this.combobox("Extra", "Face", "Normal", "Feet"));
        this.blocks_per_tick = this.create("Speed", "TrapSpeed", 4, 0, 8);
        this.rotate = this.create("Rotation", "TrapRotation", true);
        this.chad_mode = this.create("Chad Mode", "TrapChadMode", true);
        this.range = this.create("Range", "TrapRange", 4, 1, 6);
        this.swing = this.create("Swing", "TrapSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.offsets_default = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_face = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_feet = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsets_extra = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0) };
        this.last_tick_target_name = "";
        this.offset_step = 0;
        this.timeout_ticker = 0;
        this.first_run = true;
        this.name = "Trap";
        this.tag = "Trap";
        this.description = "cover people in obsidian :o";
    }
    
    public void enable() {
        this.timeout_ticker = 0;
        this.y_level = (int)Math.round(WurstplusTrap.mc.player.posY);
        this.first_run = true;
        if (this.find_obi_in_hotbar() == -1) {
            this.set_disable();
        }
    }
    
    @Override
    public void update() {
        final int timeout_ticks = 20;
        if (this.timeout_ticker > timeout_ticks && this.chad_mode.get_value(true)) {
            this.timeout_ticker = 0;
            this.set_disable();
            return;
        }
        final EntityPlayer closest_target = this.find_closest_target();
        if (closest_target == null) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }
        if (this.chad_mode.get_value(true) && (int)Math.round(WurstplusTrap.mc.player.posY) != this.y_level) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }
        if (this.first_run) {
            this.first_run = false;
            this.last_tick_target_name = closest_target.getName();
        }
        else if (!this.last_tick_target_name.equals(closest_target.getName())) {
            this.last_tick_target_name = closest_target.getName();
            this.offset_step = 0;
        }
        final List<Vec3d> place_targets = new ArrayList<Vec3d>();
        if (this.place_mode.in("Normal")) {
            Collections.addAll(place_targets, this.offsets_default);
        }
        else if (this.place_mode.in("Extra")) {
            Collections.addAll(place_targets, this.offsets_extra);
        }
        else if (this.place_mode.in("Feet")) {
            Collections.addAll(place_targets, this.offsets_feet);
        }
        else {
            Collections.addAll(place_targets, this.offsets_face);
        }
        int blocks_placed = 0;
        while (blocks_placed < this.blocks_per_tick.get_value(1)) {
            if (this.offset_step >= place_targets.size()) {
                this.offset_step = 0;
                break;
            }
            final BlockPos offset_pos = new BlockPos((Vec3d)place_targets.get(this.offset_step));
            final BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = true;
            if (!WurstplusTrap.mc.world.getBlockState(target_pos).getMaterial().isReplaceable()) {
                should_try_place = false;
            }
            for (final Entity entity : WurstplusTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(target_pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    should_try_place = false;
                    break;
                }
            }
            if (should_try_place && WurstplusBlockUtil.placeBlock(target_pos, this.find_obi_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                ++blocks_placed;
            }
            ++this.offset_step;
        }
        ++this.timeout_ticker;
    }
    
    private int find_obi_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusTrap.mc.player.inventory.getStackInSlot(i);
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
    
    public EntityPlayer find_closest_target() {
        if (WurstplusTrap.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : WurstplusTrap.mc.world.playerEntities) {
            if (target == WurstplusTrap.mc.player) {
                continue;
            }
            if (WurstplusFriendUtil.isFriend(target.getName())) {
                continue;
            }
            if (!WurstplusEntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && WurstplusTrap.mc.player.getDistance((Entity)target) > WurstplusTrap.mc.player.getDistance((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
}
