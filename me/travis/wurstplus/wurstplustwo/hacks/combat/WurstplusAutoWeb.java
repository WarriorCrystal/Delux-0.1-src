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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
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

public class WurstplusAutoWeb extends WurstplusHack
{
    WurstplusSetting always_on;
    WurstplusSetting rotate;
    WurstplusSetting range;
    int new_slot;
    boolean sneak;
    
    public WurstplusAutoWeb() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.always_on = this.create("Always On", "AlwaysOn", true);
        this.rotate = this.create("Rotate", "AutoWebRotate", true);
        this.range = this.create("Enemy Range", "AutoWebRange", 4, 0, 8);
        this.new_slot = -1;
        this.sneak = false;
        this.name = "Auto Self Web";
        this.tag = "AutoSelfWeb";
        this.description = "places fuckin webs at ur feet";
    }
    
    public void enable() {
        if (WurstplusAutoWeb.mc.player != null) {
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                WurstplusMessageUtil.send_client_error_message("cannot find webs in hotbar");
                this.set_active(false);
            }
        }
    }
    
    public void disable() {
        if (WurstplusAutoWeb.mc.player != null && this.sneak) {
            WurstplusAutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusAutoWeb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneak = false;
        }
    }
    
    @Override
    public void update() {
        if (WurstplusAutoWeb.mc.player == null) {
            return;
        }
        if (this.always_on.get_value(true)) {
            final EntityPlayer target = this.find_closest_target();
            if (target == null) {
                return;
            }
            if (WurstplusAutoWeb.mc.player.getDistance((Entity)target) < this.range.get_value(1) && this.is_surround()) {
                final int last_slot = WurstplusAutoWeb.mc.player.inventory.currentItem;
                WurstplusAutoWeb.mc.player.inventory.currentItem = this.new_slot;
                WurstplusAutoWeb.mc.playerController.updateController();
                this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
                WurstplusAutoWeb.mc.player.inventory.currentItem = last_slot;
            }
        }
        else {
            final int last_slot2 = WurstplusAutoWeb.mc.player.inventory.currentItem;
            WurstplusAutoWeb.mc.player.inventory.currentItem = this.new_slot;
            WurstplusAutoWeb.mc.playerController.updateController();
            this.place_blocks(WurstplusPlayerUtil.GetLocalPlayerPosFloored());
            WurstplusAutoWeb.mc.player.inventory.currentItem = last_slot2;
            this.set_disable();
        }
    }
    
    public EntityPlayer find_closest_target() {
        if (WurstplusAutoWeb.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : WurstplusAutoWeb.mc.world.playerEntities) {
            if (target == WurstplusAutoWeb.mc.player) {
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
            if (closestTarget != null && WurstplusAutoWeb.mc.player.getDistance((Entity)target) > WurstplusAutoWeb.mc.player.getDistance((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWeb.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Item.getItemById(30)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return WurstplusAutoWeb.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && WurstplusAutoWeb.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && WurstplusAutoWeb.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && WurstplusAutoWeb.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR && WurstplusAutoWeb.mc.world.getBlockState(player_block).getBlock() == Blocks.AIR;
    }
    
    private void place_blocks(final BlockPos pos) {
        if (!WurstplusAutoWeb.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (WurstplusBlockInteractHelper.canBeClicked(neighbor)) {
                if (WurstplusBlockInteractHelper.blackList.contains(WurstplusAutoWeb.mc.world.getBlockState(neighbor).getBlock())) {
                    WurstplusAutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusAutoWeb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.sneak = true;
                }
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (this.rotate.get_value(true)) {
                    WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
                }
                WurstplusAutoWeb.mc.playerController.processRightClickBlock(WurstplusAutoWeb.mc.player, WurstplusAutoWeb.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                WurstplusAutoWeb.mc.player.swingArm(EnumHand.MAIN_HAND);
                return;
            }
        }
    }
}
