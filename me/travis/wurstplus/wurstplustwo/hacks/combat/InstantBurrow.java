//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.lang.reflect.Field;
import net.minecraft.util.Timer;
import me.travis.other.WurstPlusThree.MappingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import me.travis.other.WurstPlusThree.BlockUtil;
import net.minecraft.util.EnumHand;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockEnderChest;
import me.travis.other.Phobos.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import me.travis.other.WurstPlusThree.PlayerUtil;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class InstantBurrow extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting type;
    WurstplusSetting block;
    WurstplusSetting force;
    WurstplusSetting instant;
    WurstplusSetting center;
    WurstplusSetting bypass;
    WurstplusSetting needhole;
    WurstplusSetting bypass_blocks;
    int swapBlock;
    Vec3d centerBlock;
    BlockPos oldPos;
    Block blockW;
    boolean flag;
    
    public InstantBurrow() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.type = this.create("Type", "Type", "Packet", this.combobox("Packet", "Normal"));
        this.block = this.create("Block", "Block", "Obby", this.combobox("Bypass", "EChest", "Obby", "EC&Oby"));
        this.force = this.create("Force", "Force", 1.5, -5.0, 10.0);
        this.instant = this.create("Instant", "Instant", true);
        this.center = this.create("Center", "Center", false);
        this.bypass = this.create("Bypass", "Bypass", false);
        this.needhole = this.create("OnlyInHole", "OnlyInHole", true);
        this.bypass_blocks = this.create("BlocksForBypass", "BypassBlocks", "PistonItems", this.combobox("PistonItems", "Chest", "Anvil", "Sand"));
        this.swapBlock = -1;
        this.centerBlock = Vec3d.ZERO;
        this.blockW = Blocks.OBSIDIAN;
        this.name = "Instant Burrow";
        this.tag = "InstantBurrow";
        this.description = "WP3 Burrow";
    }
    
    @Override
    protected void enable() {
        this.flag = false;
        InstantBurrow.mc.player.motionX = 0.0;
        InstantBurrow.mc.player.motionZ = 0.0;
        this.centerBlock = this.getCenter(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ);
        if (this.centerBlock != Vec3d.ZERO && this.center.get_value(true)) {
            final double x_diff = Math.abs(this.centerBlock.x - InstantBurrow.mc.player.posX);
            final double z_diff = Math.abs(this.centerBlock.z - InstantBurrow.mc.player.posZ);
            if (x_diff <= 0.1 && z_diff <= 0.1) {
                this.centerBlock = Vec3d.ZERO;
            }
            else {
                final double motion_x = this.centerBlock.x - InstantBurrow.mc.player.posX;
                final double motion_z = this.centerBlock.z - InstantBurrow.mc.player.posZ;
                InstantBurrow.mc.player.motionX = motion_x / 2.0;
                InstantBurrow.mc.player.motionZ = motion_z / 2.0;
            }
        }
        this.oldPos = PlayerUtil.getPlayerPos();
        if (this.block.in("EC&Oby")) {
            this.swapBlock = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        }
        this.swapBlock = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (this.block.in("Obby")) {
            this.swapBlock = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        }
        if (this.block.in("EChest")) {
            this.swapBlock = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        }
        if (this.block.in("Bypass")) {
            if (this.bypass.get_value(true)) {
                final String var9 = this.bypass_blocks.get_current_value();
                byte var10 = -1;
                switch (var9.hashCode()) {
                    case 63422636: {
                        if (var9.equals("Anvil")) {
                            var10 = 2;
                            break;
                        }
                        break;
                    }
                    case 65074913: {
                        if (var9.equals("Chest")) {
                            var10 = 1;
                            break;
                        }
                        break;
                    }
                    case 371274151: {
                        if (var9.equals("PistonItems")) {
                            var10 = 0;
                            break;
                        }
                        break;
                    }
                    case 1804950839: {
                        if (var9.equals("SoulSand")) {
                            var10 = 3;
                            break;
                        }
                        break;
                    }
                }
                switch (var10) {
                    case 0: {
                        this.swapBlock = InventoryUtil.findHotbarBlock(BlockPistonBase.class);
                        break;
                    }
                    case 1: {
                        this.swapBlock = InventoryUtil.findHotbarBlock(BlockChest.class);
                        break;
                    }
                    case 2: {
                        this.swapBlock = InventoryUtil.findHotbarBlock(BlockAnvil.class);
                        break;
                    }
                    case 3: {
                        this.swapBlock = InventoryUtil.findHotbarBlock(BlockSoulSand.class);
                        this.swapBlock = InventoryUtil.findHotbarBlock(BlockSand.class);
                        break;
                    }
                }
            }
            else {
                WurstplusMessageUtil.send_client_message("Bypass is off, changing to EC mode");
                this.block.set_value("EC&Oby");
            }
        }
        if (this.swapBlock == -1) {
            this.disable();
        }
        else {
            if (this.instant.get_value(true)) {
                this.setTimer(50.0f);
            }
            if (this.type.in("Normal")) {
                InstantBurrow.mc.player.jump();
            }
        }
    }
    
    @Override
    public void update() {
        if (this.type.in("Normal") && InstantBurrow.mc.player.posY > this.oldPos.getY() + 1.04) {
            final int old = InstantBurrow.mc.player.inventory.currentItem;
            this.switchToSlot(this.swapBlock);
            BlockUtil.placeBlock(this.oldPos, EnumHand.MAIN_HAND, this.rotate.get_value(true), true, false);
            this.switchToSlot(old);
            InstantBurrow.mc.player.motionY = this.force.get_value(1);
            this.toggle();
        }
        if (this.type.in("Packet")) {
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.41999998688698, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.7531999805211997, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.00133597911214, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.16610926093821, InstantBurrow.mc.player.posZ, true));
            final int old = InstantBurrow.mc.player.inventory.currentItem;
            this.switchToSlot(this.swapBlock);
            BlockUtil.placeBlock(this.oldPos, EnumHand.MAIN_HAND, this.rotate.get_value(true), true, false);
            this.switchToSlot(old);
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + this.force.get_value(1), InstantBurrow.mc.player.posZ, false));
            if (this.bypass.get_value(true) && !InstantBurrow.mc.player.isSneaking()) {
                InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InstantBurrow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                InstantBurrow.mc.player.setSneaking(true);
                InstantBurrow.mc.playerController.updateController();
                InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InstantBurrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                InstantBurrow.mc.player.setSneaking(false);
                InstantBurrow.mc.playerController.updateController();
            }
            this.toggle();
        }
    }
    
    @Override
    protected void disable() {
        if (this.instant.get_value(true)) {
            this.setTimer(1.0f);
        }
    }
    
    private void switchToSlot(final int slot) {
        InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        InstantBurrow.mc.player.inventory.currentItem = slot;
        InstantBurrow.mc.playerController.updateController();
    }
    
    private void setTimer(final float value) {
        try {
            final Field timer = Minecraft.class.getDeclaredField(MappingUtil.timer);
            timer.setAccessible(true);
            final Field tickLength = Timer.class.getDeclaredField(MappingUtil.tickLength);
            tickLength.setAccessible(true);
            tickLength.setFloat(timer.get(InstantBurrow.mc), 50.0f / value);
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    private Vec3d getCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public void setBlock(final Block b) {
        this.blockW = b;
    }
    
    public Block getBlock() {
        return this.blockW;
    }
}
