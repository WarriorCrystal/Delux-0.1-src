//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class WurstplusEntityUtil
{
    public static final Minecraft mc;
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plusY) {
        final List<BlockPos> circleBlocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plusY, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }
    
    public static void attackEntity(final Entity entity, final boolean packet, final WurstplusSetting setting) {
        if (packet) {
            WurstplusEntityUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        }
        else {
            WurstplusEntityUtil.mc.playerController.attackEntity((EntityPlayer)WurstplusEntityUtil.mc.player, entity);
        }
        if (setting.in("Mainhand") || setting.in("Both")) {
            WurstplusEntityUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (setting.in("Offhand") || setting.in("Both")) {
            WurstplusEntityUtil.mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }
    
    public static void attackEntity(final Entity entity) {
        WurstplusEntityUtil.mc.playerController.attackEntity((EntityPlayer)WurstplusEntityUtil.mc.player, entity);
        WurstplusEntityUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final Entity me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static EntityPlayer findClosestTarget(double rangeMax, final EntityPlayer aimTarget) {
        rangeMax *= rangeMax;
        final List<EntityPlayer> playerList = (List<EntityPlayer>)WurstplusEntityUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (!basicChecksEntity((Entity)entityPlayer)) {
                if (aimTarget == null && WurstplusEntityUtil.mc.player.getDistanceSq((Entity)entityPlayer) <= rangeMax) {
                    closestTarget = entityPlayer;
                }
                else {
                    if (aimTarget == null || WurstplusEntityUtil.mc.player.getDistanceSq((Entity)entityPlayer) > rangeMax || WurstplusEntityUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= WurstplusEntityUtil.mc.player.getDistanceSq((Entity)aimTarget)) {
                        continue;
                    }
                    closestTarget = entityPlayer;
                }
            }
        }
        return closestTarget;
    }
    
    public static boolean basicChecksEntity(final Entity pl) {
        return pl.getName().equals(WurstplusEntityUtil.mc.player.getName()) || WurstplusFriendUtil.isFriend(pl.getName()) || pl.isDead;
    }
    
    public static double getDirection() {
        float rotationYaw = WurstplusEntityUtil.mc.player.rotationYaw;
        if (WurstplusEntityUtil.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (WurstplusEntityUtil.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (WurstplusEntityUtil.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (WurstplusEntityUtil.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (WurstplusEntityUtil.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, 0.0 * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).subtract(WurstplusEntityUtil.mc.getRenderManager().renderPosX, WurstplusEntityUtil.mc.getRenderManager().renderPosY, WurstplusEntityUtil.mc.getRenderManager().renderPosZ);
    }
    
    public static BlockPos is_cityable(final EntityPlayer player, final boolean end_crystal) {
        final BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
        if (WurstplusEntityUtil.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.north();
            }
            if (WurstplusEntityUtil.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR) {
                return pos.north();
            }
        }
        if (WurstplusEntityUtil.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.east();
            }
            if (WurstplusEntityUtil.mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR) {
                return pos.east();
            }
        }
        if (WurstplusEntityUtil.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.south();
            }
            if (WurstplusEntityUtil.mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR) {
                return pos.south();
            }
        }
        if (WurstplusEntityUtil.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.west();
            }
            if (WurstplusEntityUtil.mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.AIR) {
                return pos.west();
            }
        }
        return null;
    }
    
    public static BlockPos getPosition(final Entity pl) {
        return new BlockPos(Math.floor(pl.posX), Math.floor(pl.posY), Math.floor(pl.posZ));
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
