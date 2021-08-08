//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.world.GameType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.CPacketPlayer;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class VClip extends WurstplusHack
{
    WurstplusSetting offset;
    WurstplusSetting delay;
    WurstplusTimer timer;
    int lastHotbarSlot;
    int playerHotbarSlot;
    boolean isSneaking;
    
    public VClip() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.offset = this.create("Strength", "Strength", -5.599999904632568, -20.0, 20.0);
        this.delay = this.create("Delay", "Delay", 100, 0, 1000);
        this.timer = new WurstplusTimer();
        this.name = "VClip";
        this.tag = "VClip";
        this.description = "tps you to y 0";
    }
    
    @Override
    protected void enable() {
        if (VClip.mc.player != null) {
            if (VClip.mc.isSingleplayer()) {
                WurstplusMessageUtil.send_client_error_message("You are in singleplayer");
                this.set_disable();
            }
            else {
                final BlockPos pos = new BlockPos(VClip.mc.player.posX, VClip.mc.player.posY, VClip.mc.player.posZ);
                if (this.intersectsWithEntity(pos)) {
                    WurstplusMessageUtil.send_client_error_message("Intercepted by entity");
                }
                this.playerHotbarSlot = VClip.mc.player.inventory.currentItem;
                this.lastHotbarSlot = -1;
                VClip.mc.player.jump();
                this.timer.reset();
            }
        }
    }
    
    @Override
    protected void disable() {
        if (VClip.mc.player != null) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                VClip.mc.player.inventory.currentItem = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            this.playerHotbarSlot = -1;
            this.lastHotbarSlot = -1;
        }
    }
    
    @Override
    public void update() {
        if (this.timer.passed(this.delay.get_value(1))) {}
        if (this.isSneaking) {
            VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        Wurstplus.get_hack_manager().get_module_with_tag("NoVoid").set_active(true);
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + this.offset.get_value(1), VClip.mc.player.posZ, false));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 0.41999998688698, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 0.7531999805211997, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 1.00133597911214, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 1.16610926093821, VClip.mc.player.posZ, true));
        final BlockPos offsetPos = new BlockPos(0, -1, 0);
        final BlockPos targetPos = new BlockPos(VClip.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
        if (this.placeBlock(targetPos)) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                VClip.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + this.offset.get_value(1), VClip.mc.player.posZ, false));
        }
        this.set_disable();
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final Block block = VClip.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        final EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!WurstplusBlockInteractHelper.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = VClip.mc.world.getBlockState(neighbour).getBlock();
        final int obiSlot = this.find_in_hotbar();
        if (obiSlot == -1) {
            this.set_disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            VClip.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock)) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
            VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        VClip.mc.playerController.processRightClickBlock(VClip.mc.player, VClip.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        VClip.mc.player.swingArm(EnumHand.MAIN_HAND);
        VClip.mc.rightClickDelayTimer = 4;
        if (VClip.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
        }
        return true;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = VClip.mc.player.inventory.getStackInSlot(i);
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
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : VClip.mc.world.loadedEntityList) {
            if (!entity.equals((Object)VClip.mc.player) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
}
