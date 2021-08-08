//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.world.GameType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AntiCity extends WurstplusHack
{
    private final WurstplusSetting triggerable;
    private final WurstplusSetting turnOffCauras;
    private final WurstplusSetting timeoutTicks;
    private final WurstplusSetting blocksPerTick;
    private final WurstplusSetting tickDelay;
    private final WurstplusSetting rotate;
    private final WurstplusSetting noGlitchBlocks;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private int offsetStep;
    private int delayStep;
    private int totalTicksRunning;
    private boolean firstRun;
    private boolean isSneaking;
    double oldY;
    int cDelay;
    String caura;
    boolean isDisabling;
    boolean hasDisabled;
    
    public AntiCity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.triggerable = this.create("Triggerable", "Triggerable", true);
        this.turnOffCauras = this.create("Toggle Other Cauras", "Toggle Other Cauras", true);
        this.timeoutTicks = this.create("TimeoutTicks", "TimeoutTicks", 40, 1, 100);
        this.blocksPerTick = this.create("BlocksPerTick", "BlocksPerTick", 4, 1, 9);
        this.tickDelay = this.create("TickDelay", "TickDelay", 0, 0, 10);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.noGlitchBlocks = this.create("NoGlitchBlocks", "NoGlitchBlocks", true);
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.offsetStep = 0;
        this.delayStep = 0;
        this.totalTicksRunning = 0;
        this.isSneaking = false;
        this.cDelay = 0;
        this.name = "AntiCity";
        this.tag = "AntiCity";
        this.description = "";
    }
    
    @Override
    protected void enable() {
        if (WurstplusSurround.mc.player == null) {
            this.disable();
            return;
        }
        this.hasDisabled = false;
        this.oldY = AntiCity.mc.player.posY;
        this.firstRun = true;
        this.playerHotbarSlot = WurstplusSurround.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }
    
    @Override
    protected void disable() {
        if (WurstplusSurround.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            WurstplusSurround.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            WurstplusSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusSurround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
    
    @Override
    public void update() {
        if (this.cDelay > 0) {
            --this.cDelay;
        }
        if (this.cDelay == 0 && this.isDisabling) {
            Wurstplus.get_hack_manager().get_module_with_tag(this.caura).toggle();
            this.isDisabling = false;
            this.hasDisabled = true;
        }
        if (WurstplusSurround.mc.player != null) {
            if (!Wurstplus.get_hack_manager().get_module_with_tag("Freecam").is_active()) {
                if (Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal") != null && Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active() && this.turnOffCauras.get_value(true) && !this.hasDisabled) {
                    this.caura = "AutoCrystal";
                    this.cDelay = 19;
                    this.isDisabling = true;
                    Wurstplus.get_hack_manager().get_module_with_tag(this.caura).toggle();
                }
                if (this.triggerable.get_value(true) && this.totalTicksRunning >= this.timeoutTicks.get_value(40)) {
                    this.totalTicksRunning = 0;
                    this.disable();
                    return;
                }
                if (AntiCity.mc.player.posY != this.oldY) {
                    this.disable();
                    return;
                }
                if (!this.firstRun) {
                    if (this.delayStep < this.tickDelay.get_value(0)) {
                        ++this.delayStep;
                        return;
                    }
                    this.delayStep = 0;
                }
                if (this.firstRun) {
                    this.firstRun = false;
                }
                int blocksPlaced = 0;
                while (blocksPlaced < this.blocksPerTick.get_value(4)) {
                    Vec3d[] offsetPattern = new Vec3d[0];
                    int maxSteps = 0;
                    offsetPattern = Offsets.WurstplusSurround;
                    maxSteps = Offsets.WurstplusSurround.length;
                    if (this.offsetStep >= maxSteps) {
                        this.offsetStep = 0;
                        break;
                    }
                    final BlockPos offsetPos = new BlockPos(offsetPattern[this.offsetStep]);
                    final BlockPos targetPos = new BlockPos(WurstplusSurround.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                    if (this.placeBlock(targetPos)) {
                        ++blocksPlaced;
                    }
                    ++this.offsetStep;
                }
                if (blocksPlaced > 0) {
                    if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                        WurstplusSurround.mc.player.inventory.currentItem = this.playerHotbarSlot;
                        this.lastHotbarSlot = this.playerHotbarSlot;
                    }
                    if (this.isSneaking) {
                        WurstplusSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusSurround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        this.isSneaking = false;
                    }
                }
                ++this.totalTicksRunning;
            }
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final Block block = WurstplusSurround.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : WurstplusSurround.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem)) {
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                return false;
            }
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
        final Block neighbourBlock = WurstplusSurround.mc.world.getBlockState(neighbour).getBlock();
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            WurstplusSurround.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock)) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
            WurstplusSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusSurround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.get_value(true)) {
            WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
        }
        WurstplusSurround.mc.playerController.processRightClickBlock(WurstplusSurround.mc.player, WurstplusSurround.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        WurstplusSurround.mc.player.swingArm(EnumHand.MAIN_HAND);
        WurstplusSurround.mc.rightClickDelayTimer = 4;
        if (this.noGlitchBlocks.get_value(true) && !WurstplusSurround.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            WurstplusSurround.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
        }
        return true;
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSurround.mc.player.inventory.getStackInSlot(i);
            final Block block;
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && (block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    private static class Offsets
    {
        private static final Vec3d[] WurstplusSurround;
        
        static {
            WurstplusSurround = new Vec3d[] { new Vec3d(2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -2.0) };
        }
    }
}
