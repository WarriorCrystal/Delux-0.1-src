//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Gamesense;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class CrystalUtil
{
    private static final Minecraft mc;
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean newPlacement) {
        if (notValidBlock(CrystalUtil.mc.world.getBlockState(blockPos).getBlock())) {
            return false;
        }
        final BlockPos posUp = blockPos.up();
        if (newPlacement) {
            if (!CrystalUtil.mc.world.isAirBlock(posUp)) {
                return false;
            }
        }
        else if (notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp).getMaterial()) || notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp.up()).getMaterial())) {
            return false;
        }
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        return CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, Entity::isEntityAlive).isEmpty();
    }
    
    public static boolean canPlaceCrystalExcludingCrystals(final BlockPos blockPos, final boolean newPlacement) {
        if (notValidBlock(CrystalUtil.mc.world.getBlockState(blockPos).getBlock())) {
            return false;
        }
        final BlockPos posUp = blockPos.up();
        if (newPlacement) {
            if (!CrystalUtil.mc.world.isAirBlock(posUp)) {
                return false;
            }
        }
        else if (notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp).getMaterial()) || notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp.up()).getMaterial())) {
            return false;
        }
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        return CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, entity -> !entity.isDead && !(entity instanceof EntityEnderCrystal)).isEmpty();
    }
    
    public static boolean notValidBlock(final Block block) {
        return block != Blocks.BEDROCK && block != Blocks.OBSIDIAN;
    }
    
    public static boolean notValidMaterial(final Material material) {
        return material.isLiquid() || !material.isReplaceable();
    }
    
    public static List<BlockPos> findCrystalBlocks(final float placeRange, final boolean mode) {
        return EntityUtil.getSphere(WurstplusPlayerUtil.getPlayerPos(), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, mode)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
    }
    
    public static List<BlockPos> findCrystalBlocksExcludingCrystals(final float placeRange, final boolean mode) {
        return EntityUtil.getSphere(WurstplusPlayerUtil.getPlayerPos(), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystalExcludingCrystals(pos, mode)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
    }
    
    public static void breakCrystal(final Entity crystal) {
        CrystalUtil.mc.playerController.attackEntity((EntityPlayer)CrystalUtil.mc.player, crystal);
        CrystalUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public static void breakCrystalPacket(final Entity crystal) {
        CrystalUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        CrystalUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
