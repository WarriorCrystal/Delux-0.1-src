//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class WurstplusBreakUtil
{
    private static final Minecraft mc;
    private static BlockPos current_block;
    private static boolean is_mining;
    
    public static void set_current_block(final BlockPos pos) {
        WurstplusBreakUtil.current_block = pos;
    }
    
    private static boolean is_done(final IBlockState state) {
        return state.getBlock() == Blocks.BEDROCK || state.getBlock() == Blocks.AIR || state.getBlock() instanceof BlockLiquid;
    }
    
    public static boolean update(final float range, final boolean ray_trace) {
        if (WurstplusBreakUtil.current_block == null) {
            return false;
        }
        final IBlockState state = WurstplusBreakUtil.mc.world.getBlockState(WurstplusBreakUtil.current_block);
        if (is_done(state) || WurstplusBreakUtil.mc.player.getDistanceSq(WurstplusBreakUtil.current_block) > range * range) {
            WurstplusBreakUtil.current_block = null;
            return false;
        }
        WurstplusBreakUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        EnumFacing facing = EnumFacing.UP;
        if (ray_trace) {
            final RayTraceResult r = WurstplusBreakUtil.mc.world.rayTraceBlocks(new Vec3d(WurstplusBreakUtil.mc.player.posX, WurstplusBreakUtil.mc.player.posY + WurstplusBreakUtil.mc.player.getEyeHeight(), WurstplusBreakUtil.mc.player.posZ), new Vec3d(WurstplusBreakUtil.current_block.getX() + 0.5, WurstplusBreakUtil.current_block.getY() - 0.5, WurstplusBreakUtil.current_block.getZ() + 0.5));
            if (r != null && r.sideHit != null) {
                facing = r.sideHit;
            }
        }
        if (!WurstplusBreakUtil.is_mining) {
            WurstplusBreakUtil.is_mining = true;
            WurstplusBreakUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, WurstplusBreakUtil.current_block, facing));
        }
        else {
            WurstplusBreakUtil.mc.playerController.onPlayerDamageBlock(WurstplusBreakUtil.current_block, facing);
        }
        return true;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        WurstplusBreakUtil.current_block = null;
        WurstplusBreakUtil.is_mining = false;
    }
}
