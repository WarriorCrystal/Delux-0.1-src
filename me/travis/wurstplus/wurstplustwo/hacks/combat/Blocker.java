//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.other.Gamesense.CrystalUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import me.travis.other.Gamesense.InventoryUtil;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import me.travis.other.Gamesense.BlockUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.other.Gamesense.PlacementUtil;
import me.travis.other.Gamesense.SpoofRotationUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Blocker extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting anvilBlocker;
    WurstplusSetting offHandObby;
    WurstplusSetting pistonBlocker;
    WurstplusSetting BlocksPerTick;
    WurstplusSetting blockPlaced;
    WurstplusSetting tickDelay;
    private int delayTimeTicks;
    private boolean noObby;
    private boolean noActive;
    private boolean activedBefore;
    
    public Blocker() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.anvilBlocker = this.create("Anvil", "Anvil", true);
        this.offHandObby = this.create("Off Hand Obby", "Off Hand Obby", true);
        this.pistonBlocker = this.create("Piston", "Piston", true);
        this.BlocksPerTick = this.create("Blocks Per Tick", "Blocks Per Tick", 4, 0, 10);
        this.blockPlaced = this.create("Block Place", "Block Place", "String", this.combobox("Pressure", "String"));
        this.tickDelay = this.create("Tick Delay", "Tick Delay", 5, 0, 10);
        this.delayTimeTicks = 0;
        this.name = "Blocker";
        this.tag = "Blocker";
        this.description = "Blocker";
    }
    
    @Override
    protected void enable() {
        SpoofRotationUtil.ROTATION_UTIL.enable();
        PlacementUtil.enable();
        if (Blocker.mc.player == null) {
            this.disable();
        }
        else {
            if (!this.anvilBlocker.get_value(true) && !this.pistonBlocker.get_value(true)) {
                this.noActive = true;
                this.disable();
            }
            this.noObby = false;
        }
    }
    
    @Override
    protected void disable() {
        SpoofRotationUtil.ROTATION_UTIL.disable();
        PlacementUtil.disable();
        if (Blocker.mc.player != null) {
            if (this.noActive) {
                WurstplusMessageUtil.send_client_message("Nothing is active... Blocker turned OFF!");
            }
            else if (this.noObby) {
                WurstplusMessageUtil.send_client_message("Obsidian not found... Blocker turned OFF!");
            }
        }
    }
    
    @Override
    public void update() {
        if (Blocker.mc.player == null) {
            this.disable();
        }
        else if (this.noObby) {
            this.disable();
        }
        else if (this.delayTimeTicks < this.tickDelay.get_value(5)) {
            ++this.delayTimeTicks;
        }
        else {
            SpoofRotationUtil.ROTATION_UTIL.shouldSpoofAngles(true);
            this.delayTimeTicks = 0;
            if (this.anvilBlocker.get_value(true)) {
                this.blockAnvil();
            }
            if (this.pistonBlocker.get_value(true)) {
                this.blockPiston();
            }
        }
    }
    
    private void blockAnvil() {
        boolean found = false;
        for (final Entity t : Blocker.mc.world.loadedEntityList) {
            if (t instanceof EntityFallingBlock) {
                final Block ex = (Block)((EntityFallingBlock)t).getBlock();
                if (!(ex instanceof BlockAnvil) || (int)t.posX != (int)Blocker.mc.player.posX || (int)t.posZ != (int)Blocker.mc.player.posZ || !(BlockUtil.getBlock(Blocker.mc.player.posX, Blocker.mc.player.posY + 2.0, Blocker.mc.player.posZ) instanceof BlockAir)) {
                    continue;
                }
                this.placeBlock(new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY + 2.0, Blocker.mc.player.posZ));
                WurstplusMessageUtil.send_client_message("AutoAnvil detected... Anvil Blocked!");
                found = true;
            }
        }
        if (!found && this.activedBefore) {
            this.activedBefore = false;
        }
    }
    
    private void blockPiston() {
        for (final Entity t : Blocker.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && t.posX >= Blocker.mc.player.posX - 1.5 && t.posX <= Blocker.mc.player.posX + 1.5 && t.posZ >= Blocker.mc.player.posZ - 1.5 && t.posZ <= Blocker.mc.player.posZ + 1.5) {
                for (int i = -2; i < 3; ++i) {
                    for (int j = -2; j < 3; ++j) {
                        if ((i == 0 || j == 0) && BlockUtil.getBlock(t.posX + i, t.posY, t.posZ + j) instanceof BlockPistonBase) {
                            this.breakCrystalPiston(t);
                            WurstplusMessageUtil.send_client_message("PistonCrystal detected... Destroyed crystal!");
                        }
                    }
                }
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        EnumHand handSwing = EnumHand.MAIN_HAND;
        final int obsidianSlot = InventoryUtil.findObsidianSlot(this.offHandObby.get_value(true), this.activedBefore);
        if (obsidianSlot == -1) {
            this.noObby = true;
        }
        else {
            if (obsidianSlot == 9) {
                this.activedBefore = true;
                if (!(Blocker.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) || !(((ItemBlock)Blocker.mc.player.getHeldItemOffhand().getItem()).getBlock() instanceof BlockObsidian)) {
                    return;
                }
                handSwing = EnumHand.OFF_HAND;
            }
            if (Blocker.mc.player.inventory.currentItem != obsidianSlot && obsidianSlot != 9) {
                Blocker.mc.player.inventory.currentItem = obsidianSlot;
            }
            PlacementUtil.place(pos, handSwing, this.rotate.get_value(false), true);
        }
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (this.rotate.get_value(false)) {
            SpoofRotationUtil.ROTATION_UTIL.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)Blocker.mc.player);
        }
        CrystalUtil.breakCrystal(crystal);
        if (this.rotate.get_value(false)) {
            SpoofRotationUtil.ROTATION_UTIL.resetRotation();
        }
    }
}
