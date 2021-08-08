//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.Arrays;
import java.util.ArrayList;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.BlockSlab;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import java.util.List;

public class WurstplusBlockInteractHelper
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static final Minecraft mc;
    
    public static void placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockInteractHelper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                WurstplusBlockInteractHelper.mc.playerController.processRightClickBlock(WurstplusBlockInteractHelper.mc.player, WurstplusBlockInteractHelper.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
                WurstplusBlockInteractHelper.mc.player.swingArm(EnumHand.MAIN_HAND);
                WurstplusBlockInteractHelper.mc.rightClickDelayTimer = 0;
                WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockInteractHelper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return;
            }
        }
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return WurstplusBlockInteractHelper.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static void placeBlock(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                WurstplusBlockInteractHelper.mc.playerController.processRightClickBlock(WurstplusBlockInteractHelper.mc.player, WurstplusBlockInteractHelper.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
                WurstplusBlockInteractHelper.mc.player.swingArm(EnumHand.MAIN_HAND);
                WurstplusBlockInteractHelper.mc.rightClickDelayTimer = 0;
                return;
            }
        }
    }
    
    public static PlaceResult place(final BlockPos pos, final float p_Distance, final boolean p_Rotate, final boolean p_UseSlabRule) {
        final IBlockState l_State = WurstplusBlockInteractHelper.mc.world.getBlockState(pos);
        final boolean l_Replaceable = l_State.getMaterial().isReplaceable();
        final boolean l_IsSlabAtBlock = l_State.getBlock() instanceof BlockSlab;
        if (!l_Replaceable && !l_IsSlabAtBlock) {
            return PlaceResult.NotReplaceable;
        }
        if (!checkForNeighbours(pos)) {
            return PlaceResult.Neighbors;
        }
        if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.isFullCube()) {
            return PlaceResult.CantPlace;
        }
        final Vec3d eyesPos = new Vec3d(WurstplusBlockInteractHelper.mc.player.posX, WurstplusBlockInteractHelper.mc.player.posY + WurstplusBlockInteractHelper.mc.player.getEyeHeight(), WurstplusBlockInteractHelper.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (WurstplusBlockInteractHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(WurstplusBlockInteractHelper.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= p_Distance) {
                    final Block neighborPos = WurstplusBlockInteractHelper.mc.world.getBlockState(neighbor).getBlock();
                    final boolean activated = neighborPos.onBlockActivated((World)WurstplusBlockInteractHelper.mc.world, pos, WurstplusBlockInteractHelper.mc.world.getBlockState(pos), (EntityPlayer)WurstplusBlockInteractHelper.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
                    if (WurstplusBlockInteractHelper.blackList.contains(neighborPos) || WurstplusBlockInteractHelper.shulkerList.contains(neighborPos) || activated) {
                        WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockInteractHelper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    if (p_Rotate) {
                        faceVectorPacketInstant(hitVec);
                    }
                    final EnumActionResult l_Result2 = WurstplusBlockInteractHelper.mc.playerController.processRightClickBlock(WurstplusBlockInteractHelper.mc.player, WurstplusBlockInteractHelper.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    if (l_Result2 != EnumActionResult.FAIL) {
                        WurstplusBlockInteractHelper.mc.player.swingArm(EnumHand.MAIN_HAND);
                        if (activated) {
                            WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockInteractHelper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        return PlaceResult.Placed;
                    }
                }
            }
        }
        return PlaceResult.CantPlace;
    }
    
    public static ValidResult valid(final BlockPos pos) {
        if (!WurstplusBlockInteractHelper.mc.world.checkNoEntityCollision(new AxisAlignedBB(pos))) {
            return ValidResult.NoEntityCollision;
        }
        if (!checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        final IBlockState l_State = WurstplusBlockInteractHelper.mc.world.getBlockState(pos);
        if (l_State.getBlock() != Blocks.AIR) {
            return ValidResult.AlreadyBlockThere;
        }
        final BlockPos[] var3;
        final BlockPos[] l_Blocks = var3 = new BlockPos[] { pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down() };
        for (int var4 = l_Blocks.length, var5 = 0; var5 < var4; ++var5) {
            final BlockPos l_Pos = var3[var5];
            final IBlockState l_State2 = WurstplusBlockInteractHelper.mc.world.getBlockState(l_Pos);
            if (l_State2.getBlock() != Blocks.AIR) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbor = pos.offset(side);
                    if (WurstplusBlockInteractHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(WurstplusBlockInteractHelper.mc.world.getBlockState(neighbor), false)) {
                        return ValidResult.Ok;
                    }
                }
            }
        }
        return ValidResult.NoNeighbors;
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { WurstplusBlockInteractHelper.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - WurstplusBlockInteractHelper.mc.player.rotationYaw), WurstplusBlockInteractHelper.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - WurstplusBlockInteractHelper.mc.player.rotationPitch) };
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(WurstplusBlockInteractHelper.mc.player.posX, WurstplusBlockInteractHelper.mc.player.posY + WurstplusBlockInteractHelper.mc.player.getEyeHeight(), WurstplusBlockInteractHelper.mc.player.posZ);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], WurstplusBlockInteractHelper.mc.player.onGround));
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec, final Boolean roundAngles) {
        final float[] rotations = getNeededRotations2(vec);
        WurstplusBlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], ((boolean)roundAngles) ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], WurstplusBlockInteractHelper.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { WurstplusBlockInteractHelper.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - WurstplusBlockInteractHelper.mc.player.rotationYaw), WurstplusBlockInteractHelper.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - WurstplusBlockInteractHelper.mc.player.rotationPitch) };
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return WurstplusBlockInteractHelper.mc.world.getBlockState(pos);
    }
    
    public static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (WurstplusBlockInteractHelper.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(WurstplusBlockInteractHelper.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = WurstplusBlockInteractHelper.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    private static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!WurstplusBlockInteractHelper.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = sphere ? (cy + r) : ((float)(cy + h));
                    if (y >= f) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }
    
    public enum ValidResult
    {
        NoEntityCollision, 
        AlreadyBlockThere, 
        NoNeighbors, 
        Ok;
    }
    
    public enum PlaceResult
    {
        NotReplaceable, 
        Neighbors, 
        CantPlace, 
        Placed;
    }
}
