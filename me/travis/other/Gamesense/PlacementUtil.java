//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Gamesense;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.client.Minecraft;

public class PlacementUtil
{
    private static final Minecraft mc;
    private static int placementConnections;
    private static boolean isSneaking;
    
    public static void enable() {
        ++PlacementUtil.placementConnections;
    }
    
    public static void disable() {
        --PlacementUtil.placementConnections;
        if (PlacementUtil.placementConnections == 0 && PlacementUtil.isSneaking) {
            PlacementUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlacementUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            PlacementUtil.isSneaking = false;
        }
    }
    
    public static boolean placeBlock(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Class<? extends Block> blockToPlace) {
        final int oldSlot = PlacementUtil.mc.player.inventory.currentItem;
        final int newSlot = InventoryUtil.findFirstBlockSlot(blockToPlace, 0, 8);
        if (newSlot == -1) {
            return false;
        }
        PlacementUtil.mc.player.inventory.currentItem = newSlot;
        final boolean output = place(blockPos, hand, rotate);
        PlacementUtil.mc.player.inventory.currentItem = oldSlot;
        return output;
    }
    
    public static boolean placeItem(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Class<? extends Item> itemToPlace) {
        final int oldSlot = PlacementUtil.mc.player.inventory.currentItem;
        final int newSlot = InventoryUtil.findFirstItemSlot(itemToPlace, 0, 8);
        if (newSlot == -1) {
            return false;
        }
        PlacementUtil.mc.player.inventory.currentItem = newSlot;
        final boolean output = place(blockPos, hand, rotate);
        PlacementUtil.mc.player.inventory.currentItem = oldSlot;
        return output;
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate) {
        return placeBlock(blockPos, hand, rotate, true, null);
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final ArrayList<EnumFacing> forceSide) {
        return placeBlock(blockPos, hand, rotate, true, forceSide);
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final boolean checkAction) {
        return placeBlock(blockPos, hand, rotate, checkAction, null);
    }
    
    public static boolean placeBlock(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final boolean checkAction, final ArrayList<EnumFacing> forceSide) {
        final EntityPlayerSP player = PlacementUtil.mc.player;
        final WorldClient world = PlacementUtil.mc.world;
        final PlayerControllerMP playerController = PlacementUtil.mc.playerController;
        if (player == null || world == null || playerController == null) {
            return false;
        }
        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        final EnumFacing side = (forceSide != null) ? BlockUtil.getPlaceableSideExlude(blockPos, forceSide) : BlockUtil.getPlaceableSide(blockPos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = blockPos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = world.getBlockState(neighbour).getBlock();
        if ((!PlacementUtil.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            PlacementUtil.isSneaking = true;
        }
        final boolean stoppedAC = false;
        if (rotate) {
            BlockUtil.faceVectorPacketInstant(hitVec, true);
        }
        final EnumActionResult action = playerController.processRightClickBlock(player, world, neighbour, opposite, hitVec, hand);
        if (!checkAction || action == EnumActionResult.SUCCESS) {
            player.swingArm(hand);
            PlacementUtil.mc.rightClickDelayTimer = 4;
        }
        return action == EnumActionResult.SUCCESS;
    }
    
    public static boolean placePrecise(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Vec3d precise, final EnumFacing forceSide, final boolean onlyRotation, final boolean support) {
        final EntityPlayerSP player = PlacementUtil.mc.player;
        final WorldClient world = PlacementUtil.mc.world;
        final PlayerControllerMP playerController = PlacementUtil.mc.playerController;
        if (player == null || world == null || playerController == null) {
            return false;
        }
        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        final EnumFacing side = (forceSide == null) ? BlockUtil.getPlaceableSide(blockPos) : forceSide;
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = blockPos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = world.getBlockState(neighbour).getBlock();
        if ((!PlacementUtil.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            PlacementUtil.isSneaking = true;
        }
        final boolean stoppedAC = false;
        if (rotate && !support) {
            BlockUtil.faceVectorPacketInstant((precise == null) ? hitVec : precise, true);
        }
        if (!onlyRotation) {
            final EnumActionResult action = playerController.processRightClickBlock(player, world, neighbour, opposite, (precise == null) ? hitVec : precise, hand);
            if (action == EnumActionResult.SUCCESS) {
                player.swingArm(hand);
                PlacementUtil.mc.rightClickDelayTimer = 4;
            }
            return action == EnumActionResult.SUCCESS;
        }
        return true;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlacementUtil.placementConnections = 0;
        PlacementUtil.isSneaking = false;
    }
}
