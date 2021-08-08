//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusHoleUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemEndCrystal;
import java.util.Objects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.CPacketUseEntity;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.block.BlockObsidian;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventDamageBlock;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class CevBreaker extends WurstplusHack
{
    WurstplusSetting target;
    WurstplusSetting breakCrystal;
    WurstplusSetting placeCrystal;
    WurstplusSetting enemyRange;
    WurstplusSetting supDelay;
    WurstplusSetting endDelay;
    WurstplusSetting hitDelay;
    WurstplusSetting confirmBreak;
    WurstplusSetting confirmPlace;
    WurstplusSetting crystalDelay;
    WurstplusSetting antiWeakness;
    WurstplusSetting antiStep;
    WurstplusSetting trapPlayer;
    WurstplusSetting fastPlace;
    WurstplusSetting blocksPerTick;
    WurstplusSetting pickSwitchTick;
    WurstplusSetting switchSword;
    WurstplusSetting rotate;
    WurstplusSetting midHitDelay;
    WurstplusSetting chatMsg;
    private boolean noMaterials;
    private boolean hasMoved;
    private boolean isSneaking;
    private boolean isHole;
    private boolean enoughSpace;
    private boolean broken;
    private boolean stoppedCa;
    private boolean deadPl;
    private boolean rotationPlayerMoved;
    private boolean prevBreak;
    private int oldSlot;
    private int stage;
    private int delayTimeTicks;
    private int hitTryTick;
    private int tickPick;
    private final int[][] model;
    private int[] slot_mat;
    private int[] delayTable;
    private int[] enemyCoordsInt;
    private double[] enemyCoordsDouble;
    private structureTemp toPlace;
    Double[][] sur_block;
    private EntityPlayer aimTarget;
    @EventHandler
    public Listener<WurstplusEventDamageBlock> listener2;
    
    public CevBreaker() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.target = this.create("Target", "Target", "Nearest", this.combobox("Nearest", "Looking"));
        this.breakCrystal = this.create("Break Crystal", "BreakMode", "Packet", this.combobox("Vanilla", "Packet", "None"));
        this.placeCrystal = this.create("Place Crystal", "PlaceCrystal", true);
        this.enemyRange = this.create("Range", "CivBreakerEnemyRange", 4.9, 1.0, 6.0);
        this.supDelay = this.create("Sup Delay", "SupDelay", 1, 0, 4);
        this.endDelay = this.create("End Delay", "EndDelay", 1, 0, 4);
        this.hitDelay = this.create("Hit Delay", "HitDelay", 2, 0, 20);
        this.confirmBreak = this.create("Confirm Break", "ConfirmBreak", true);
        this.confirmPlace = this.create("Confirm Place", "ConfirmPlace", true);
        this.crystalDelay = this.create("Crystal Delay", "CrystalDelay", 2, 0, 20);
        this.antiWeakness = this.create("Anti Weakness", "AntiWeakness", false);
        this.antiStep = this.create("Anti Step", "AntiStep", false);
        this.trapPlayer = this.create("Trap Player", "TrapPlayer", false);
        this.fastPlace = this.create("Fast Place", "FastPlace", false);
        this.blocksPerTick = this.create("Blocks Per Tick", "BPS", 4, 2, 6);
        this.pickSwitchTick = this.create("PickSwitchTick", "PickSwitchTick", 100, 0, 500);
        this.switchSword = this.create("Switch Sword", "SwitchSword", false);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.midHitDelay = this.create("Mid HitDelay", "MidHitDelay", 1, 0, 5);
        this.chatMsg = this.create("Chat Messages", "Messages", true);
        this.noMaterials = false;
        this.hasMoved = false;
        this.isSneaking = false;
        this.isHole = true;
        this.enoughSpace = true;
        this.oldSlot = -1;
        this.model = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 0, 1, 1 }, { 0, 1, -1 } };
        this.sur_block = new Double[4][3];
        this.listener2 = new Listener<WurstplusEventDamageBlock>(event -> {
            if (this.enemyCoordsInt != null) {
                this.destroyCrystalAlgo();
            }
            return;
        }, (Predicate<WurstplusEventDamageBlock>[])new Predicate[0]);
        this.name = "Cev Breaker";
        this.tag = "Cev Breaker";
        this.description = "crystals an opponent from above their heads";
    }
    
    @Override
    protected void enable() {
        this.initValues();
        if (!this.getAimTarget()) {
            this.playerChecks();
        }
    }
    
    private boolean getAimTarget() {
        if (this.target.in("Nearest")) {
            this.aimTarget = WurstplusEntityUtil.findClosestTarget(this.enemyRange.get_value(1), this.aimTarget);
        }
        else {
            this.aimTarget = WurstplusPlayerUtil.findLookingPlayer(this.enemyRange.get_value(1));
        }
        if (this.aimTarget == null || !this.target.in("Looking")) {
            if (!this.target.in("Looking") && this.aimTarget == null) {
                this.set_disable();
            }
            if (this.aimTarget == null) {
                return true;
            }
        }
        return false;
    }
    
    private void playerChecks() {
        if (this.getMaterialsSlot()) {
            if (this.is_in_hole()) {
                this.enemyCoordsDouble = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
                this.enemyCoordsInt = new int[] { (int)this.enemyCoordsDouble[0], (int)this.enemyCoordsDouble[1], (int)this.enemyCoordsDouble[2] };
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
    
    private void initValues() {
        this.aimTarget = null;
        this.delayTable = new int[] { this.supDelay.get_value(1), this.crystalDelay.get_value(1), this.hitDelay.get_value(1), this.endDelay.get_value(1) };
        this.toPlace = new structureTemp(0.0, 0, new ArrayList<Vec3d>());
        this.isHole = true;
        final boolean b = false;
        this.broken = b;
        this.deadPl = b;
        this.rotationPlayerMoved = b;
        this.hasMoved = b;
        this.slot_mat = new int[] { -1, -1, -1, -1 };
        final int n = 0;
        this.delayTimeTicks = n;
        this.stage = n;
        if (CevBreaker.mc.player == null) {
            this.set_disable();
        }
        else {
            if (this.chatMsg.get_value(true)) {
                WurstplusMessageUtil.send_client_error_message("CevBreaker off");
            }
            this.oldSlot = CevBreaker.mc.player.inventory.currentItem;
            this.stoppedCa = false;
        }
    }
    
    @Override
    protected void disable() {
        if (CevBreaker.mc.player != null) {
            if (this.chatMsg.get_value(true)) {
                String output = "";
                String materialsNeeded = "";
                if (this.aimTarget == null) {
                    output = "No target found";
                }
                else if (this.noMaterials) {
                    output = "No Materials Detected";
                    materialsNeeded = this.getMissingMaterials();
                }
                else if (!this.isHole) {
                    output = "Enemy is not in hole";
                }
                else if (!this.enoughSpace) {
                    output = "Not enough space";
                }
                else if (this.hasMoved) {
                    output = "Out of range";
                }
                else if (this.deadPl) {
                    output = "Enemy is dead ";
                }
                WurstplusMessageUtil.send_client_error_message(output + "");
                if (!materialsNeeded.equals("")) {
                    WurstplusMessageUtil.send_client_error_message("Materials missing:" + materialsNeeded);
                }
            }
            if (this.isSneaking) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            if (this.oldSlot != CevBreaker.mc.player.inventory.currentItem && this.oldSlot != -1) {
                CevBreaker.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            this.noMaterials = false;
        }
    }
    
    private String getMissingMaterials() {
        final StringBuilder output = new StringBuilder();
        if (this.slot_mat[0] == -1) {
            output.append(" Obsidian");
        }
        if (this.slot_mat[1] == -1) {
            output.append(" Crystal");
        }
        if ((this.antiWeakness.get_value(true) || this.switchSword.get_value(true)) && this.slot_mat[3] == -1) {
            output.append(" Sword");
        }
        if (this.slot_mat[2] == -1) {
            output.append(" Pickaxe");
        }
        return output.toString();
    }
    
    @Override
    public void update() {
        if (CevBreaker.mc.player != null && !CevBreaker.mc.player.isDead) {
            if (this.delayTimeTicks < this.delayTable[this.stage]) {
                ++this.delayTimeTicks;
            }
            else {
                this.delayTimeTicks = 0;
                if (this.enemyCoordsDouble != null && this.aimTarget != null) {
                    if (this.aimTarget.isDead) {
                        this.deadPl = true;
                    }
                    if ((int)this.aimTarget.posX != (int)this.enemyCoordsDouble[0] || (int)this.aimTarget.posZ != (int)this.enemyCoordsDouble[2]) {
                        this.hasMoved = true;
                    }
                    if (!this.checkVariable() && this.placeSupport()) {
                        switch (this.stage) {
                            case 1: {
                                this.placeBlockThings(this.stage);
                                if (this.fastPlace.get_value(true)) {
                                    this.placeCrystal();
                                }
                                this.prevBreak = false;
                                this.tickPick = 0;
                                break;
                            }
                            case 2: {
                                if (this.confirmPlace.get_value(true) && !(WurstplusBlockInteractHelper.getBlock(this.compactBlockPos(0)) instanceof BlockObsidian)) {
                                    --this.stage;
                                    return;
                                }
                                this.placeCrystal();
                                break;
                            }
                            case 3: {
                                if (this.confirmPlace.get_value(true) && this.getCrystal() == null) {
                                    --this.stage;
                                    return;
                                }
                                int switchValue = 3;
                                if (!this.switchSword.get_value(true) || this.tickPick == this.pickSwitchTick.get_value(1) || this.tickPick++ == 0) {
                                    switchValue = 2;
                                }
                                if (CevBreaker.mc.player.inventory.currentItem != this.slot_mat[switchValue]) {
                                    CevBreaker.mc.player.inventory.currentItem = this.slot_mat[switchValue];
                                }
                                final BlockPos obbyBreak = new BlockPos(this.enemyCoordsDouble[0], (double)(this.enemyCoordsInt[1] + 2), this.enemyCoordsDouble[2]);
                                if (WurstplusBlockInteractHelper.getBlock(obbyBreak) instanceof BlockObsidian) {
                                    final EnumFacing sideBreak = WurstplusBlockInteractHelper.getPlaceableSide(obbyBreak);
                                    if (sideBreak != null) {
                                        if (this.breakCrystal.in("Packet")) {
                                            if (!this.prevBreak) {
                                                CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                                                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, obbyBreak, sideBreak));
                                                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, obbyBreak, sideBreak));
                                                this.prevBreak = true;
                                            }
                                        }
                                        else if (this.breakCrystal.in("Normal")) {
                                            CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                                            CevBreaker.mc.playerController.onPlayerDamageBlock(obbyBreak, sideBreak);
                                        }
                                    }
                                    break;
                                }
                                this.destroyCrystalAlgo();
                                break;
                            }
                        }
                    }
                }
                else if (this.aimTarget == null) {
                    this.aimTarget = WurstplusPlayerUtil.findLookingPlayer(this.enemyRange.get_value(1));
                    if (this.aimTarget != null) {
                        this.playerChecks();
                    }
                }
                else {
                    this.checkVariable();
                }
            }
        }
        else {
            this.set_disable();
        }
    }
    
    private void placeCrystal() {
        this.placeBlockThings(this.stage);
    }
    
    private Entity getCrystal() {
        for (final Entity t : CevBreaker.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && (int)t.posX == this.enemyCoordsInt[0] && (int)t.posZ == this.enemyCoordsInt[2] && t.posY - this.enemyCoordsInt[1] == 3.0) {
                return t;
            }
        }
        return null;
    }
    
    public void destroyCrystalAlgo() {
        final Entity crystal = this.getCrystal();
        if (this.confirmBreak.get_value(true) && this.broken && crystal == null) {
            this.stage = 0;
            this.broken = false;
        }
        if (crystal != null) {
            this.breakCrystalPiston(crystal);
            if (this.confirmBreak.get_value(true)) {
                this.broken = true;
            }
            else {
                this.stage = 0;
            }
        }
        else {
            this.stage = 0;
        }
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (this.hitTryTick++ >= this.midHitDelay.get_value(1)) {
            this.hitTryTick = 0;
            if (this.antiWeakness.get_value(true)) {
                CevBreaker.mc.player.inventory.currentItem = this.slot_mat[3];
            }
            if (this.breakCrystal.in("Vanilla")) {
                WurstplusEntityUtil.attackEntity(crystal);
            }
            else if (this.breakCrystal.in("Packet")) {
                try {
                    CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                    CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                catch (NullPointerException ex) {}
            }
        }
    }
    
    private boolean placeSupport() {
        int checksDone = 0;
        int blockDone = 0;
        if (this.toPlace.supportBlock > 0) {
            do {
                final BlockPos targetPos = this.getTargetPos(checksDone);
                if (this.placeBlock(targetPos, 0) && ++blockDone == this.blocksPerTick.get_value(1)) {
                    return false;
                }
            } while (++checksDone != this.toPlace.supportBlock);
        }
        this.stage = ((this.stage == 0) ? 1 : this.stage);
        return true;
    }
    
    private boolean placeBlock(final BlockPos pos, final int step) {
        final Block block = CevBreaker.mc.world.getBlockState(pos).getBlock();
        final EnumFacing side = WurstplusBlockInteractHelper.getPlaceableSide(pos);
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!WurstplusBlockInteractHelper.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = CevBreaker.mc.world.getBlockState(neighbour).getBlock();
        if (this.slot_mat[step] != 11 && CevBreaker.mc.player.inventory.getStackInSlot(this.slot_mat[step]) == ItemStack.EMPTY) {
            this.noMaterials = true;
            return false;
        }
        if (CevBreaker.mc.player.inventory.currentItem != this.slot_mat[step]) {
            CevBreaker.mc.player.inventory.currentItem = ((this.slot_mat[step] == 11) ? CevBreaker.mc.player.inventory.currentItem : this.slot_mat[step]);
        }
        if ((!this.isSneaking && WurstplusBlockInteractHelper.blackList.contains(neighbourBlock)) || WurstplusBlockInteractHelper.shulkerList.contains(neighbourBlock)) {
            CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.get_value(true)) {
            WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec, true);
        }
        EnumHand handSwing = EnumHand.MAIN_HAND;
        if (this.slot_mat[step] == 11) {
            handSwing = EnumHand.OFF_HAND;
        }
        CevBreaker.mc.playerController.processRightClickBlock(CevBreaker.mc.player, CevBreaker.mc.world, neighbour, opposite, hitVec, handSwing);
        CevBreaker.mc.player.swingArm(handSwing);
        return true;
    }
    
    public void placeBlockThings(int step) {
        if (step != 1 || this.placeCrystal.get_value(true)) {
            --step;
            final BlockPos targetPos = this.compactBlockPos(step);
            this.placeBlock(targetPos, step);
        }
        ++this.stage;
    }
    
    public BlockPos compactBlockPos(final int step) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private BlockPos getTargetPos(final int idx) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(idx));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private boolean checkVariable() {
        if (!this.noMaterials && this.isHole && this.enoughSpace && !this.hasMoved && !this.deadPl && !this.rotationPlayerMoved) {
            return false;
        }
        this.set_disable();
        return true;
    }
    
    private boolean createStructure() {
        if (!Objects.requireNonNull(WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 2.0, this.enemyCoordsDouble[2]).getRegistryName()).toString().toLowerCase().contains("bedrock") && WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 3.0, this.enemyCoordsDouble[2]) instanceof BlockAir && WurstplusBlockInteractHelper.getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 4.0, this.enemyCoordsDouble[2]) instanceof BlockAir) {
            double min_found = Double.MAX_VALUE;
            int cor = 0;
            int i = 0;
            for (final Double[] cord_b : this.sur_block) {
                final double distance_now;
                if ((distance_now = CevBreaker.mc.player.getDistanceSq(new BlockPos((double)cord_b[0], (double)cord_b[1], (double)cord_b[2]))) < min_found) {
                    min_found = distance_now;
                    cor = i;
                }
                ++i;
            }
            final int bias = (this.enemyCoordsInt[0] != (int)CevBreaker.mc.player.posX && this.enemyCoordsInt[2] != (int)CevBreaker.mc.player.posZ) ? 1 : -1;
            this.toPlace.to_place.add(new Vec3d((double)(this.model[cor][0] * bias), 1.0, (double)(this.model[cor][2] * bias)));
            this.toPlace.to_place.add(new Vec3d((double)(this.model[cor][0] * bias), 2.0, (double)(this.model[cor][2] * bias)));
            this.toPlace.supportBlock = 2;
            if (this.trapPlayer.get_value(true) || this.antiStep.get_value(true)) {
                for (int high = 1; high < 3; ++high) {
                    if (high != 2 || this.antiStep.get_value(true)) {
                        for (final int[] modelBas : this.model) {
                            final Vec3d toAdd = new Vec3d((double)modelBas[0], (double)high, (double)modelBas[2]);
                            if (!this.toPlace.to_place.contains(toAdd)) {
                                this.toPlace.to_place.add(toAdd);
                                final structureTemp toPlace = this.toPlace;
                                ++toPlace.supportBlock;
                            }
                        }
                    }
                }
            }
            this.toPlace.to_place.add(new Vec3d(0.0, 2.0, 0.0));
            this.toPlace.to_place.add(new Vec3d(0.0, 3.0, 0.0));
            return true;
        }
        return false;
    }
    
    private boolean getMaterialsSlot() {
        if (CevBreaker.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            this.slot_mat[1] = 11;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = CevBreaker.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (this.slot_mat[1] == -1 && stack.getItem() instanceof ItemEndCrystal) {
                    this.slot_mat[1] = i;
                }
                else if ((this.antiWeakness.get_value(true) || this.switchSword.get_value(true)) && stack.getItem() instanceof ItemSword) {
                    this.slot_mat[3] = i;
                }
                else if (stack.getItem() instanceof ItemPickaxe) {
                    this.slot_mat[2] = i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        this.slot_mat[0] = i;
                    }
                }
            }
        }
        int i = 0;
        for (final int val : this.slot_mat) {
            if (val != -1) {
                ++i;
            }
        }
        return i >= 3 + ((this.antiWeakness.get_value(true) || this.switchSword.get_value(true)) ? 1 : 0);
    }
    
    private boolean is_in_hole() {
        this.sur_block = new Double[][] { { this.aimTarget.posX + 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX - 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ + 1.0 }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ - 1.0 } };
        return WurstplusHoleUtil.isHole(WurstplusEntityUtil.getPosition((Entity)this.aimTarget), true, true).getType() != WurstplusHoleUtil.HoleType.NONE;
    }
    
    private static class structureTemp
    {
        public double distance;
        public int supportBlock;
        public ArrayList<Vec3d> to_place;
        public int direction;
        
        public structureTemp(final double distance, final int supportBlock, final ArrayList<Vec3d> to_place) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = -1;
        }
    }
}
