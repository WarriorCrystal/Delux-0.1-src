//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Items;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class LegCrystals extends WurstplusHack
{
    WurstplusSetting range;
    boolean switchCooldown;
    
    public LegCrystals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.range = this.create("Range", "Range", 1.0, 5.5, 10.0);
        this.name = "LegCrystals";
        this.tag = "LegCrystals";
        this.description = "LegCrystals";
    }
    
    @Override
    public void update() {
        if (LegCrystals.mc.player != null) {
            int crystalSlot = -1;
            if (LegCrystals.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                crystalSlot = LegCrystals.mc.player.inventory.currentItem;
            }
            else {
                for (int slot = 0; slot < 9; ++slot) {
                    if (LegCrystals.mc.player.inventory.getStackInSlot(slot).getItem() == Items.END_CRYSTAL) {
                        crystalSlot = slot;
                        break;
                    }
                }
            }
            if (crystalSlot != -1) {
                final EntityPlayer closestTarget = this.findClosestTarget();
                if (closestTarget != null) {
                    final Vec3d targetVector = this.findPlaceableBlock(closestTarget.getPositionVector().add(0.0, -1.0, 0.0));
                    if (targetVector != null) {
                        final BlockPos targetBlock = new BlockPos(targetVector);
                        if (LegCrystals.mc.player.inventory.currentItem != crystalSlot) {
                            LegCrystals.mc.player.inventory.currentItem = crystalSlot;
                            this.switchCooldown = true;
                        }
                        else if (this.switchCooldown) {
                            this.switchCooldown = false;
                        }
                        else {
                            LegCrystals.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetBlock, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        }
                    }
                }
            }
        }
    }
    
    private Vec3d findPlaceableBlock(final Vec3d startPos) {
        if (this.canPlaceCrystal(startPos.add(Offsets.NORTH2)) && !this.isExplosionProof(startPos.add(Offsets.NORTH1).add(0.0, 1.0, 0.0))) {
            return startPos.add(Offsets.NORTH2);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.NORTH1))) {
            return startPos.add(Offsets.NORTH1);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.EAST2)) && !this.isExplosionProof(startPos.add(Offsets.EAST1).add(0.0, 1.0, 0.0))) {
            return startPos.add(Offsets.EAST2);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.EAST1))) {
            return startPos.add(Offsets.EAST1);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.SOUTH2)) && !this.isExplosionProof(startPos.add(Offsets.SOUTH1).add(0.0, 1.0, 0.0))) {
            return startPos.add(Offsets.SOUTH2);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.SOUTH1))) {
            return startPos.add(Offsets.SOUTH1);
        }
        if (this.canPlaceCrystal(startPos.add(Offsets.WEST2)) && !this.isExplosionProof(startPos.add(Offsets.WEST1).add(0.0, 1.0, 0.0))) {
            return startPos.add(Offsets.WEST2);
        }
        return this.canPlaceCrystal(startPos.add(Offsets.WEST1)) ? startPos.add(Offsets.WEST1) : null;
    }
    
    private EntityPlayer findClosestTarget() {
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : LegCrystals.mc.world.playerEntities) {
            if (target != LegCrystals.mc.player && !WurstplusFriendUtil.isFriend(target.getName()) && WurstplusEntityUtil.isLiving((Entity)target) && target.getHealth() > 0.0f && LegCrystals.mc.player.getDistance((Entity)target) <= this.range.get_value(1.0)) {
                if (closestTarget == null) {
                    closestTarget = target;
                }
                else {
                    if (LegCrystals.mc.player.getDistance((Entity)target) >= LegCrystals.mc.player.getDistance((Entity)closestTarget)) {
                        continue;
                    }
                    closestTarget = target;
                }
            }
        }
        return closestTarget;
    }
    
    private boolean canPlaceCrystal(final Vec3d vec3d) {
        final BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (LegCrystals.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || LegCrystals.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && LegCrystals.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && LegCrystals.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && LegCrystals.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && LegCrystals.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    private boolean isExplosionProof(final Vec3d vec3d) {
        final BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        final Block block = LegCrystals.mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ANVIL || block == Blocks.ENDER_CHEST || block == Blocks.BARRIER;
    }
    
    private static class Offsets
    {
        private static final Vec3d NORTH1;
        private static final Vec3d NORTH2;
        private static final Vec3d EAST1;
        private static final Vec3d EAST2;
        private static final Vec3d SOUTH1;
        private static final Vec3d SOUTH2;
        private static final Vec3d WEST1;
        private static final Vec3d WEST2;
        
        static {
            NORTH1 = new Vec3d(0.0, 0.0, -1.0);
            NORTH2 = new Vec3d(0.0, 0.0, -2.0);
            EAST1 = new Vec3d(1.0, 0.0, 0.0);
            EAST2 = new Vec3d(2.0, 0.0, 0.0);
            SOUTH1 = new Vec3d(0.0, 0.0, 1.0);
            SOUTH2 = new Vec3d(0.0, 0.0, 2.0);
            WEST1 = new Vec3d(-1.0, 0.0, 0.0);
            WEST2 = new Vec3d(-2.0, 0.0, 0.0);
        }
    }
}
