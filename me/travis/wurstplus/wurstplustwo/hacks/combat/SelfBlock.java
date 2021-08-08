//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.block.BlockWeb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class SelfBlock extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting always_on;
    WurstplusSetting rotate;
    WurstplusSetting range;
    int new_slot;
    boolean sneak;
    
    public SelfBlock() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "Mode", "Webs", this.combobox("Webs", "Skulls"));
        this.always_on = this.create("Toggle", "Toggle", false);
        this.rotate = this.create("Rotate", "AutoWebRotate", true);
        this.range = this.create("Enemy Range", "AutoWebRange", 4, 0, 8);
        this.new_slot = -1;
        this.sneak = false;
        this.name = "Self Block";
        this.tag = "SelfBlock";
        this.description = "places blocks at ur feet";
    }
    
    @Override
    protected void enable() {
        if (SelfBlock.mc.player != null) {
            this.new_slot = this.find_in_hotbar();
            final String needed = this.mode.get_current_value();
            if (this.new_slot == -1) {
                WurstplusMessageUtil.send_client_error_message("cannot find " + needed + " in hotbar");
                this.set_active(false);
            }
        }
    }
    
    @Override
    protected void disable() {
        if (SelfBlock.mc.player != null && this.sneak) {
            SelfBlock.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfBlock.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneak = false;
        }
    }
    
    @Override
    public void update() {
        if (SelfBlock.mc.player == null) {
            return;
        }
        if (this.always_on.get_value(true)) {
            final EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (SelfBlock.mc.player.getDistance((Entity)target) < this.range.get_value(1) && this.is_surround()) {
                final int last_slot = SelfBlock.mc.player.inventory.currentItem;
                SelfBlock.mc.player.inventory.currentItem = this.new_slot;
                SelfBlock.mc.playerController.updateController();
                this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
                SelfBlock.mc.player.inventory.currentItem = last_slot;
            }
        }
        else {
            final int last_slot2 = SelfBlock.mc.player.inventory.currentItem;
            SelfBlock.mc.player.inventory.currentItem = this.new_slot;
            SelfBlock.mc.playerController.updateController();
            this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
            SelfBlock.mc.player.inventory.currentItem = last_slot2;
            this.set_disable();
        }
    }
    
    public EntityPlayer find_closest_target() {
        if (SelfBlock.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : SelfBlock.mc.world.playerEntities) {
            if (target == SelfBlock.mc.player) {
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
            if (closestTarget != null && SelfBlock.mc.player.getDistance((Entity)target) > SelfBlock.mc.player.getDistance((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            if (this.mode.in("Webs")) {
                final ItemStack stack = SelfBlock.mc.player.inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockWeb) {
                        return i;
                    }
                }
            }
            if (this.mode.in("Skulls")) {
                final ItemStack stack = SelfBlock.mc.player.inventory.getStackInSlot(i);
                if (stack.getItem() == Items.SKULL) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return SelfBlock.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && SelfBlock.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && SelfBlock.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && SelfBlock.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR && SelfBlock.mc.world.getBlockState(player_block).getBlock() == Blocks.AIR;
    }
    
    private void place_blocks(final BlockPos pos) {
        if (!SelfBlock.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                if (WurstplusBlockInteractHelper.blackList.contains(SelfBlock.mc.world.getBlockState(neighbor).getBlock())) {
                    SelfBlock.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfBlock.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (this.rotate.get_value(true)) {
                    WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                }
                SelfBlock.mc.playerController.processRightClickBlock(SelfBlock.mc.player, SelfBlock.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                SelfBlock.mc.player.swingArm(EnumHand.MAIN_HAND);
                return;
            }
        }
    }
}
