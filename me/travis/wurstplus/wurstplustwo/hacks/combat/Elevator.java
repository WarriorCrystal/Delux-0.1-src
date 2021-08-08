//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import me.travis.wurstplus.wurstplustwo.util.WurstplusHoleUtil;
import me.travis.other.Gamesense.EntityUtil;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemPickaxe;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockLiquid;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAutoEz;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.travis.other.Gamesense.SpoofRotationUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import me.travis.other.Gamesense.BlockUtil;
import java.util.function.Predicate;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.EnumFacing;
import java.util.ArrayList;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.other.Gamesense.BlockChangeEvent;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Elevator extends WurstplusHack
{
    WurstplusSetting target;
    WurstplusSetting placeMode;
    WurstplusSetting supportDelay;
    WurstplusSetting pistonDelay;
    WurstplusSetting redstoneDelay;
    WurstplusSetting blocksPerTick;
    WurstplusSetting tickBreakRedstone;
    WurstplusSetting enemyRange;
    WurstplusSetting debugMode;
    WurstplusSetting trapMode;
    WurstplusSetting trapAfter;
    WurstplusSetting rotate;
    WurstplusSetting forceBurrow;
    EntityPlayer aimTarget;
    double[][] sur_block;
    double[] enemyCoordsDouble;
    int[][] disp_surblock;
    int[] slot_mat;
    int[] enemyCoordsInt;
    int[] meCoordsInt;
    int lastStage;
    int blockPlaced;
    int tickPassedRedstone;
    int delayTimeTicks;
    boolean redstoneBlockMode;
    boolean enoughSpace;
    boolean isHole;
    boolean noMaterials;
    boolean redstoneAbovePiston;
    boolean isSneaking;
    boolean redstonePlaced;
    structureTemp toPlace;
    @EventHandler
    private final Listener<BlockChangeEvent> blockChangeEventListener;
    final ArrayList<EnumFacing> exd;
    
    public Elevator() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Elevator";
        this.tag = "Elevator";
        this.description = "Elevator";
        this.target = this.create("Target", "Target", "Nearest", this.combobox("Nearest", "Looking"));
        this.placeMode = this.create("Place", "Place", "Torch", this.combobox("Torch", "Block", "Both"));
        this.supportDelay = this.create("Support Delay", "Support Delay", 0, 0, 8);
        this.pistonDelay = this.create("Piston Delay", "Piston Delay", 0, 0, 8);
        this.redstoneDelay = this.create("Redstone Delay", "Redstone Delay", 0, 0, 8);
        this.blocksPerTick = this.create("Blocks per Tick", "Blocks per Tick", 4, 1, 8);
        this.tickBreakRedstone = this.create("Tick Break Redstone", "Tick Break Redstone", 2, 0, 10);
        this.enemyRange = this.create("Range", "Range", 4.9, 0.0, 6.0);
        this.debugMode = this.create("Debug Mode", "Debug Mode", false);
        this.trapMode = this.create("Trap Mode", "Trap Mode", false);
        this.trapAfter = this.create("Trap After", "Trap After", false);
        this.rotate = this.create("Rotate", "Rotate", false);
        this.forceBurrow = this.create("Force Burrow", "Force Burrow", false);
        this.disp_surblock = new int[][] { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 0, 1 }, { 0, 0, -1 } };
        final BlockPos[] temp = { null };
        final int n = 0;
        final BlockPos blockPos = null;
        final Object o;
        final BlockPos blockPos2;
        this.blockChangeEventListener = new Listener<BlockChangeEvent>(event -> {
            if (Elevator.mc.player == null || Elevator.mc.world == null) {
                return;
            }
            else if (event.getBlock() == null || event.getPosition() == null) {
                return;
            }
            else {
                event.getPosition().getX();
                o[0] = this.compactBlockPos(2);
                if (0 == blockPos2.getX() && event.getPosition().getY() == o[0].getY() && event.getPosition().getZ() == o[0].getZ()) {
                    if (event.getBlock() instanceof BlockRedstoneTorch) {
                        if (this.tickBreakRedstone.get_value(2) == 0) {
                            this.breakBlock(o[0]);
                            this.lastStage = 2;
                        }
                        else {
                            this.lastStage = 3;
                        }
                    }
                    else if (event.getBlock() instanceof BlockAir && this.redstoneDelay.get_value(0) == 0) {
                        this.placeBlock(o[0], 0.0, 0.0, 0.0, false, false, this.slot_mat[2], -1);
                        Elevator.mc.world.setBlockState(o[0], Blocks.REDSTONE_TORCH.getDefaultState());
                    }
                }
                return;
            }
        }, (Predicate<BlockChangeEvent>[])new Predicate[0]);
        this.exd = new ArrayList<EnumFacing>() {
            {
                this.add(EnumFacing.DOWN);
            }
        };
    }
    
    private void breakBlock(final BlockPos pos) {
        if (this.redstoneBlockMode) {
            Elevator.mc.player.inventory.currentItem = this.slot_mat[3];
        }
        final EnumFacing side = BlockUtil.getPlaceableSide(pos);
        if (side != null) {
            if (this.rotate.get_value(false)) {
                final BlockPos neighbour = pos.offset(side);
                final EnumFacing opposite = side.getOpposite();
                final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.0, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                BlockUtil.faceVectorPacketInstant(hitVec, true);
            }
            Elevator.mc.player.swingArm(EnumHand.MAIN_HAND);
            Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
            Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
        }
    }
    
    @Override
    protected void enable() {
        SpoofRotationUtil.ROTATION_UTIL.enable();
        this.initValues();
        if (this.getAimTarget()) {
            return;
        }
        this.playerChecks();
    }
    
    @Override
    protected void disable() {
        if (this.isSneaking) {
            Elevator.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Elevator.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        String output = "";
        String materialsNeeded = "";
        if (this.aimTarget == null) {
            output = "No target found...";
        }
        else if (!this.isHole) {
            output = "The enemy is not in a hole...";
        }
        else if (!this.enoughSpace) {
            output = "Not enough space...";
        }
        else if (this.noMaterials) {
            output = "No materials detected...";
            materialsNeeded = this.getMissingMaterials();
        }
        WurstplusMessageUtil.send_client_message(output + "Elevator turned OFF!");
        if (!materialsNeeded.equals("")) {
            WurstplusMessageUtil.send_client_message("Materials missing:" + materialsNeeded);
        }
    }
    
    String getMissingMaterials() {
        final StringBuilder output = new StringBuilder();
        if (this.slot_mat[0] == -1) {
            output.append(" Obsidian");
        }
        if (this.slot_mat[1] == -1) {
            output.append(" Piston");
        }
        if (this.slot_mat[2] == -1) {
            output.append(" Redstone");
        }
        if (this.slot_mat[3] == -1 && this.redstoneBlockMode) {
            output.append(" Pick");
        }
        if (this.slot_mat[4] == -1 && this.forceBurrow.get_value(false)) {
            output.append(" Skull");
        }
        return output.toString();
    }
    
    @Override
    public void update() {
        if (Elevator.mc.player == null) {
            this.disable();
            return;
        }
        int toWait = 0;
        switch (this.lastStage) {
            case 0: {
                toWait = this.supportDelay.get_value(0);
                break;
            }
            case 1: {
                toWait = this.pistonDelay.get_value(0);
                break;
            }
            case 2: {
                toWait = this.redstoneDelay.get_value(0);
                break;
            }
            case 3: {
                toWait = this.tickBreakRedstone.get_value(2);
                break;
            }
            default: {
                toWait = 0;
                break;
            }
        }
        if (this.delayTimeTicks < toWait) {
            ++this.delayTimeTicks;
            return;
        }
        SpoofRotationUtil.ROTATION_UTIL.shouldSpoofAngles(true);
        if (this.enemyCoordsDouble == null || this.aimTarget == null) {
            if (this.aimTarget == null) {
                this.aimTarget = WurstplusPlayerUtil.findLookingPlayer(this.enemyRange.get_value(4.9));
                if (this.aimTarget != null) {
                    if (Wurstplus.get_hack_manager().get_module_with_tag("AutoEz").is_active()) {
                        WurstplusAutoEz.add_target(this.aimTarget.getName());
                    }
                    this.playerChecks();
                }
            }
            return;
        }
        if (this.checkVariable()) {
            return;
        }
        if (this.placeSupport()) {
            BlockPos temp;
            if (BlockUtil.getBlock(temp = this.compactBlockPos(1)) instanceof BlockAir) {
                this.placeBlock(temp, this.toPlace.offsetX, this.toPlace.offsetY, this.toPlace.offsetZ, false, true, this.slot_mat[1], this.toPlace.position);
                if (this.continueBlock()) {
                    this.lastStage = 1;
                    return;
                }
            }
            if (BlockUtil.getBlock(temp = this.compactBlockPos(2)) instanceof BlockAir) {
                this.placeBlock(temp, 0.0, 0.0, 0.0, false, false, this.slot_mat[2], -1);
                this.lastStage = 2;
                return;
            }
            if (this.lastStage == 3) {
                this.breakBlock(this.compactBlockPos(2));
                this.lastStage = 2;
            }
        }
    }
    
    boolean continueBlock() {
        return ++this.blockPlaced == this.blocksPerTick.get_value(4);
    }
    
    boolean placeSupport() {
        if (this.toPlace.supportBlock > 0) {
            if (this.forceBurrow.get_value(false) && BlockUtil.getBlock(this.aimTarget.getPosition()) instanceof BlockAir) {
                final boolean temp = this.redstoneAbovePiston;
                this.redstoneAbovePiston = true;
                this.placeBlock(this.aimTarget.getPosition(), 0.0, 0.0, 0.0, true, false, this.slot_mat[4], -1);
                this.redstoneAbovePiston = temp;
                if (this.continueBlock()) {
                    this.lastStage = 0;
                    return false;
                }
            }
            for (int i = 0; i < this.toPlace.supportBlock; ++i) {
                final BlockPos targetPos = this.getTargetPos(i);
                if (BlockUtil.getBlock(targetPos) instanceof BlockAir) {
                    this.placeBlock(targetPos, 0.0, 0.0, 0.0, false, false, this.slot_mat[0], -1);
                    if (this.continueBlock()) {
                        this.lastStage = 0;
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    boolean placeBlock(final BlockPos pos, final double offsetX, final double offsetY, final double offsetZ, final boolean redstone, final boolean piston, final int slot, final int position) {
        final Block block = Elevator.mc.world.getBlockState(pos).getBlock();
        EnumFacing side;
        if (redstone && this.redstoneAbovePiston) {
            side = BlockUtil.getPlaceableSideExlude(pos, this.exd);
        }
        else {
            side = BlockUtil.getPlaceableSide(pos);
        }
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5 + offsetX, 0.5, 0.5 + offsetZ).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = Elevator.mc.world.getBlockState(neighbour).getBlock();
        if (Elevator.mc.player.inventory.getStackInSlot(slot) != ItemStack.EMPTY && Elevator.mc.player.inventory.currentItem != slot) {
            if (slot == -1) {
                this.noMaterials = true;
                return false;
            }
            Elevator.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            Elevator.mc.player.inventory.currentItem = slot;
        }
        if ((!this.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            Elevator.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Elevator.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.get_value(false)) {
            BlockUtil.faceVectorPacketInstant(hitVec, true);
        }
        else if (piston) {
            switch (position) {
                case 0: {
                    Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, Elevator.mc.player.onGround));
                    break;
                }
                case 1: {
                    Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, Elevator.mc.player.onGround));
                    break;
                }
                case 2: {
                    Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, Elevator.mc.player.onGround));
                    break;
                }
                default: {
                    Elevator.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, Elevator.mc.player.onGround));
                    break;
                }
            }
        }
        Elevator.mc.playerController.processRightClickBlock(Elevator.mc.player, Elevator.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Elevator.mc.player.swingArm(EnumHand.MAIN_HAND);
        return true;
    }
    
    BlockPos getTargetPos(final int idx) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(idx));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    public BlockPos compactBlockPos(final int step) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step - 1));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    boolean checkVariable() {
        if (this.noMaterials || !this.isHole || !this.enoughSpace) {
            this.disable();
            return true;
        }
        return false;
    }
    
    void initValues() {
        this.sur_block = new double[4][3];
        this.slot_mat = new int[] { -1, -1, -1, -1, -1 };
        this.enemyCoordsDouble = new double[3];
        this.toPlace = new structureTemp(0.0, 0, null, -1);
        final boolean redstoneBlockMode = false;
        this.redstonePlaced = false;
        this.noMaterials = false;
        this.redstoneBlockMode = false;
        this.isHole = true;
        this.aimTarget = null;
        this.lastStage = -1;
        this.delayTimeTicks = 0;
    }
    
    boolean getMaterialsSlot() {
        if (this.placeMode.in("Block")) {
            this.redstoneBlockMode = true;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Elevator.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemPickaxe) {
                    this.slot_mat[3] = i;
                }
                else if (this.forceBurrow.get_value(false) && stack.getItem() instanceof ItemSkull) {
                    this.slot_mat[4] = i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        this.slot_mat[0] = i;
                    }
                    else if (block instanceof BlockPistonBase) {
                        this.slot_mat[1] = i;
                    }
                    else if (!this.placeMode.in("Block") && block instanceof BlockRedstoneTorch) {
                        this.slot_mat[2] = i;
                        this.redstoneBlockMode = false;
                    }
                    else if (!this.placeMode.in("Torch") && block.translationKey.equals("blockRedstone")) {
                        this.slot_mat[2] = i;
                        this.redstoneBlockMode = true;
                    }
                }
            }
        }
        int count = 0;
        for (final int val : this.slot_mat) {
            if (val != -1) {
                ++count;
            }
        }
        if (this.debugMode.get_value(false)) {
            WurstplusMessageUtil.send_client_error_message(String.format("%d %d %d %d", this.slot_mat[0], this.slot_mat[1], this.slot_mat[2], this.slot_mat[3]));
        }
        return count >= 3 + (this.redstoneBlockMode ? 1 : 0) + (this.forceBurrow.get_value(false) ? 1 : 0);
    }
    
    boolean getAimTarget() {
        if (this.target.in("Nearest")) {
            this.aimTarget = WurstplusPlayerUtil.findClosestTarget(this.enemyRange.get_value(4.9), this.aimTarget);
        }
        else {
            this.aimTarget = WurstplusPlayerUtil.findLookingPlayer(this.enemyRange.get_value(4.9));
        }
        if (this.aimTarget == null || !this.target.in("Looking")) {
            if (!this.target.in("Looking") && this.aimTarget == null) {
                this.disable();
            }
            return this.aimTarget == null;
        }
        return false;
    }
    
    void playerChecks() {
        if (this.getMaterialsSlot()) {
            if (this.is_in_hole()) {
                this.enemyCoordsDouble = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
                this.enemyCoordsInt = new int[] { (int)this.enemyCoordsDouble[0], (int)this.enemyCoordsDouble[1], (int)this.enemyCoordsDouble[2] };
                this.meCoordsInt = new int[] { (int)Elevator.mc.player.posX, (int)Elevator.mc.player.posY, (int)Elevator.mc.player.posZ };
                this.enoughSpace = this.createStructure();
            }
            else {
                this.isHole = false;
            }
        }
        else {
            this.noMaterials = true;
        }
    }
    
    boolean is_in_hole() {
        this.sur_block = new double[][] { { this.aimTarget.posX + 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX - 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ + 1.0 }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ - 1.0 } };
        return WurstplusHoleUtil.isHole(EntityUtil.getPosition((Entity)this.aimTarget), true, true).getType() != WurstplusHoleUtil.HoleType.NONE;
    }
    
    boolean createStructure() {
        final structureTemp addedStructure = new structureTemp(Double.MAX_VALUE, 0, null, -1);
        for (int i = 0; i < 4; ++i) {
            final double[] pistonCoordsAbs = { this.sur_block[i][0], this.sur_block[i][1] + 1.0, this.sur_block[i][2] };
            final int[] pistonCoordsRel = { this.disp_surblock[i][0], this.disp_surblock[i][1] + 1, this.disp_surblock[i][2] };
            final double distanceNowCrystal;
            if ((distanceNowCrystal = Elevator.mc.player.getDistance(pistonCoordsAbs[0], pistonCoordsAbs[1], pistonCoordsAbs[2])) < addedStructure.distance && (BlockUtil.getBlock(pistonCoordsAbs[0], pistonCoordsAbs[1], pistonCoordsAbs[2]) instanceof BlockAir || BlockUtil.getBlock(pistonCoordsAbs[0], pistonCoordsAbs[1], pistonCoordsAbs[2]) instanceof BlockPistonBase)) {
                double[] redstoneCoordsAbs = new double[3];
                int[] redstoneCoordsRel = new int[3];
                double minFound = 1000.0;
                double minNow = -1.0;
                boolean foundOne = false;
                for (final int[] pos : this.disp_surblock) {
                    final double[] torchCoords = { pistonCoordsAbs[0] + pos[0], pistonCoordsAbs[1], pistonCoordsAbs[2] + pos[2] };
                    if ((minNow = Elevator.mc.player.getDistance(torchCoords[0], torchCoords[1], torchCoords[2])) <= minFound && !AutoPiston.someoneInCoords(torchCoords[0], torchCoords[2]) && (BlockUtil.getBlock(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockRedstoneTorch || BlockUtil.getBlock(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockAir)) {
                        redstoneCoordsAbs = new double[] { torchCoords[0], torchCoords[1], torchCoords[2] };
                        redstoneCoordsRel = new int[] { pistonCoordsRel[0] + pos[0], pistonCoordsRel[1], pistonCoordsRel[2] + pos[2] };
                        foundOne = true;
                        minFound = minNow;
                    }
                }
                this.redstoneAbovePiston = false;
                if (!foundOne) {
                    if (!this.redstoneBlockMode && BlockUtil.getBlock(pistonCoordsAbs[0], pistonCoordsAbs[1] + 1.0, pistonCoordsAbs[2]) instanceof BlockAir) {
                        redstoneCoordsAbs = new double[] { pistonCoordsAbs[0], pistonCoordsAbs[1] + 1.0, pistonCoordsAbs[2] };
                        redstoneCoordsRel = new int[] { pistonCoordsRel[0], pistonCoordsRel[1] + 1, pistonCoordsRel[2] };
                        this.redstoneAbovePiston = true;
                    }
                    if (!this.redstoneAbovePiston) {
                        continue;
                    }
                }
                final List<Vec3d> toPlaceTemp = new ArrayList<Vec3d>();
                int supportBlock = 0;
                if (!this.redstoneBlockMode) {
                    if (this.redstoneAbovePiston) {
                        int[] toAdd;
                        if (this.enemyCoordsInt[0] == (int)pistonCoordsAbs[0] && this.enemyCoordsInt[2] == (int)pistonCoordsAbs[2]) {
                            toAdd = new int[] { pistonCoordsRel[0], pistonCoordsRel[1], 0 };
                        }
                        else {
                            toAdd = new int[] { pistonCoordsRel[0], pistonCoordsRel[1], pistonCoordsRel[2] };
                        }
                        for (int hight = -1; hight < 2; ++hight) {
                            if (!AutoPiston.someoneInCoords(pistonCoordsAbs[0] + toAdd[0], pistonCoordsAbs[2] + toAdd[2]) && BlockUtil.getBlock(pistonCoordsAbs[0] + toAdd[0], pistonCoordsAbs[1] + hight, pistonCoordsAbs[2] + toAdd[2]) instanceof BlockAir) {
                                toPlaceTemp.add(new Vec3d((double)(pistonCoordsRel[0] + toAdd[0]), (double)(pistonCoordsRel[1] + hight), (double)(pistonCoordsRel[2] + toAdd[2])));
                                ++supportBlock;
                            }
                        }
                    }
                    else if (BlockUtil.getBlock(redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0, redstoneCoordsAbs[2]) instanceof BlockAir) {
                        toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)(redstoneCoordsRel[1] - 1), (double)redstoneCoordsRel[2]));
                        ++supportBlock;
                    }
                }
                if (this.trapMode.get_value(false)) {
                    toPlaceTemp.addAll(Arrays.asList(new Vec3d(-1.0, -1.0, -1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(-1.0, 1.0, -1.0), new Vec3d(-1.0, 2.0, -1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 1.0), new Vec3d(0.0, 2.0, 1.0)));
                    supportBlock += 10;
                }
                toPlaceTemp.add(new Vec3d((double)pistonCoordsRel[0], (double)pistonCoordsRel[1], (double)pistonCoordsRel[2]));
                toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)redstoneCoordsRel[1], (double)redstoneCoordsRel[2]));
                int position;
                if (this.disp_surblock[i][0] == 0) {
                    if (this.disp_surblock[i][2] == 1) {
                        position = 0;
                    }
                    else {
                        position = 1;
                    }
                }
                else if (this.disp_surblock[i][0] == 1) {
                    position = 2;
                }
                else {
                    position = 3;
                }
                float offsetX;
                float offsetZ;
                if (this.disp_surblock[i][0] != 0) {
                    offsetX = (float)this.disp_surblock[i][0];
                    if (Elevator.mc.player.getDistanceSq(pistonCoordsAbs[0], pistonCoordsAbs[1], pistonCoordsAbs[2] + 0.5) > Elevator.mc.player.getDistanceSq(pistonCoordsAbs[0], pistonCoordsAbs[1], pistonCoordsAbs[2] - 0.5)) {
                        offsetZ = 0.5f;
                    }
                    else {
                        offsetZ = -0.5f;
                    }
                }
                else {
                    offsetZ = (float)this.disp_surblock[i][2];
                    if (Elevator.mc.player.getDistanceSq(pistonCoordsAbs[0] + 0.5, pistonCoordsAbs[1], pistonCoordsAbs[2]) > Elevator.mc.player.getDistanceSq(pistonCoordsAbs[0] - 0.5, pistonCoordsAbs[1], pistonCoordsAbs[2])) {
                        offsetX = 0.5f;
                    }
                    else {
                        offsetX = -0.5f;
                    }
                }
                final float offsetY = (this.meCoordsInt[1] - this.enemyCoordsInt[1] == -1) ? 0.0f : 1.0f;
                addedStructure.replaceValues(distanceNowCrystal, supportBlock, toPlaceTemp, -1, offsetX, offsetZ, offsetY, position);
                this.toPlace = addedStructure;
            }
        }
        if (this.debugMode.get_value(false) && addedStructure.to_place != null) {
            WurstplusMessageUtil.send_client_error_message("Skeleton structure:");
            for (final Vec3d parte : addedStructure.to_place) {
                WurstplusMessageUtil.send_client_error_message(String.format("%f %f %f", parte.x, parte.y, parte.z));
            }
            WurstplusMessageUtil.send_client_error_message(String.format("X: %f Y: %f Z: %f", this.toPlace.offsetX, this.toPlace.offsetY, this.toPlace.offsetZ));
        }
        return addedStructure.to_place != null;
    }
    
    class structureTemp
    {
        public double distance;
        public int supportBlock;
        public List<Vec3d> to_place;
        public int direction;
        public float offsetX;
        public float offsetY;
        public float offsetZ;
        public int position;
        
        public structureTemp(final double distance, final int supportBlock, final List<Vec3d> to_place, final int position) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = -1;
            this.position = position;
        }
        
        public void replaceValues(final double distance, final int supportBlock, final List<Vec3d> to_place, final int direction, final float offsetX, final float offsetZ, final float offsetY, final int position) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = direction;
            this.offsetX = offsetX;
            this.offsetZ = offsetZ;
            this.offsetY = offsetY;
            this.position = position;
        }
    }
}
