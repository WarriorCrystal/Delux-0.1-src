//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.Arrays;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.client.Minecraft;

public class WurstplusBlockUtil
{
    private static final Minecraft mc;
    public static List<Block> emptyBlocks;
    public static List<Block> rightclickableBlocks;
    
    public static List<BlockPos> getSphere(final float radius) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(WurstplusBlockUtil.mc.player.posX, WurstplusBlockUtil.mc.player.posY, WurstplusBlockUtil.mc.player.posZ);
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        for (int x = posX - (int)radius; x <= posX + radius; ++x) {
            for (int z = posZ - (int)radius; z <= posZ + radius; ++z) {
                for (int y = posY - (int)radius; y < posY + radius; ++y) {
                    if ((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y) < radius * radius) {
                        sphere.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return sphere;
    }
    
    public static boolean canSeeBlock(final BlockPos p_Pos) {
        return WurstplusBlockUtil.mc.player != null && WurstplusBlockUtil.mc.world.rayTraceBlocks(new Vec3d(WurstplusBlockUtil.mc.player.posX, WurstplusBlockUtil.mc.player.posY + WurstplusBlockUtil.mc.player.getEyeHeight(), WurstplusBlockUtil.mc.player.posZ), new Vec3d((double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ()), false, true, false) == null;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
        final RayTraceResult result = WurstplusBlockUtil.mc.world.rayTraceBlocks(new Vec3d(WurstplusBlockUtil.mc.player.posX, WurstplusBlockUtil.mc.player.posY + WurstplusBlockUtil.mc.player.getEyeHeight(), WurstplusBlockUtil.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        WurstplusBlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || WurstplusBlockUtil.mc.world.rayTraceBlocks(new Vec3d(WurstplusBlockUtil.mc.player.posX, WurstplusBlockUtil.mc.player.posY + WurstplusBlockUtil.mc.player.getEyeHeight(), WurstplusBlockUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static void openBlock(final BlockPos pos) {
        final EnumFacing[] values;
        final EnumFacing[] facings = values = EnumFacing.values();
        for (final EnumFacing f : values) {
            final Block neighborBlock = WurstplusBlockUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
            if (WurstplusBlockUtil.emptyBlocks.contains(neighborBlock)) {
                WurstplusBlockUtil.mc.playerController.processRightClickBlock(WurstplusBlockUtil.mc.player, WurstplusBlockUtil.mc.world, pos, f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                return;
            }
        }
    }
    
    public static void swingArm(final WurstplusSetting setting) {
        if (setting.in("Mainhand") || setting.in("Both")) {
            WurstplusBlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (setting.in("Offhand") || setting.in("Both")) {
            WurstplusBlockUtil.mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }
    
    public static boolean placeBlock(final BlockPos pos, final int slot, final boolean rotate, final boolean rotateBack, final WurstplusSetting setting) {
        if (isBlockEmpty(pos)) {
            int old_slot = -1;
            if (slot != WurstplusBlockUtil.mc.player.inventory.currentItem) {
                old_slot = WurstplusBlockUtil.mc.player.inventory.currentItem;
                WurstplusBlockUtil.mc.player.inventory.currentItem = slot;
            }
            final EnumFacing[] values;
            final EnumFacing[] facings = values = EnumFacing.values();
            for (final EnumFacing f : values) {
                final Block neighborBlock = WurstplusBlockUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5);
                if (!WurstplusBlockUtil.emptyBlocks.contains(neighborBlock) && WurstplusBlockUtil.mc.player.getPositionEyes(WurstplusBlockUtil.mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25) {
                    final float[] rot = { WurstplusBlockUtil.mc.player.rotationYaw, WurstplusBlockUtil.mc.player.rotationPitch };
                    if (rotate) {
                        rotatePacket(vec.x, vec.y, vec.z);
                    }
                    if (WurstplusBlockUtil.rightclickableBlocks.contains(neighborBlock)) {
                        WurstplusBlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    WurstplusBlockUtil.mc.playerController.processRightClickBlock(WurstplusBlockUtil.mc.player, WurstplusBlockUtil.mc.world, pos.offset(f), f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                    if (WurstplusBlockUtil.rightclickableBlocks.contains(neighborBlock)) {
                        WurstplusBlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    if (rotateBack) {
                        WurstplusBlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], WurstplusBlockUtil.mc.player.onGround));
                    }
                    swingArm(setting);
                    if (old_slot != -1) {
                        WurstplusBlockUtil.mc.player.inventory.currentItem = old_slot;
                    }
                    return true;
                }
            }
            if (old_slot != -1) {
                WurstplusBlockUtil.mc.player.inventory.currentItem = old_slot;
            }
        }
        return false;
    }
    
    public static boolean isBlockEmpty(final BlockPos pos) {
        try {
            if (WurstplusBlockUtil.emptyBlocks.contains(WurstplusBlockUtil.mc.world.getBlockState(pos).getBlock())) {
                final AxisAlignedBB box = new AxisAlignedBB(pos);
                for (final Entity e : WurstplusBlockUtil.mc.world.loadedEntityList) {
                    if (e instanceof EntityLivingBase && box.intersects(e.getEntityBoundingBox())) {
                        return false;
                    }
                }
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static boolean canPlaceBlock(final BlockPos pos) {
        if (isBlockEmpty(pos)) {
            final EnumFacing[] values;
            final EnumFacing[] facings = values = EnumFacing.values();
            for (final EnumFacing f : values) {
                if (!WurstplusBlockUtil.emptyBlocks.contains(WurstplusBlockUtil.mc.world.getBlockState(pos.offset(f)).getBlock()) && WurstplusBlockUtil.mc.player.getPositionEyes(WurstplusBlockUtil.mc.getRenderPartialTicks()).distanceTo(new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5)) <= 4.25) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void rotatePacket(final double x, final double y, final double z) {
        final double diffX = x - WurstplusBlockUtil.mc.player.posX;
        final double diffY = y - (WurstplusBlockUtil.mc.player.posY + WurstplusBlockUtil.mc.player.getEyeHeight());
        final double diffZ = z - WurstplusBlockUtil.mc.player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WurstplusBlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, WurstplusBlockUtil.mc.player.onGround));
    }
    
    static {
        mc = Minecraft.getMinecraft();
        WurstplusBlockUtil.emptyBlocks = Arrays.asList(Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, (Block)Blocks.TALLGRASS, (Block)Blocks.FIRE);
        WurstplusBlockUtil.rightclickableBlocks = Arrays.asList((Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, (Block)Blocks.BEACON, Blocks.BED, Blocks.FURNACE, (Block)Blocks.OAK_DOOR, (Block)Blocks.SPRUCE_DOOR, (Block)Blocks.BIRCH_DOOR, (Block)Blocks.JUNGLE_DOOR, (Block)Blocks.ACACIA_DOOR, (Block)Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, (Block)Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }
}
