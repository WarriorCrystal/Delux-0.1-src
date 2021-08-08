//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAir;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.GuiHopper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import java.util.Objects;
import net.minecraft.util.math.RayTraceResult;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAuto32k extends WurstplusHack
{
    private BlockPos pos;
    private int hopper_slot;
    private int redstone_slot;
    private int shulker_slot;
    private int ticks_past;
    private int[] rot;
    private boolean setup;
    private boolean place_redstone;
    private boolean dispenser_done;
    WurstplusSetting place_mode;
    WurstplusSetting swing;
    WurstplusSetting delay;
    WurstplusSetting rotate;
    WurstplusSetting debug;
    
    public WurstplusAuto32k() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.place_mode = this.create("Place Mode", "AutotkPlaceMode", "Auto", this.combobox("Auto", "Looking", "Hopper"));
        this.swing = this.create("Swing", "AutotkSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.delay = this.create("Delay", "AutotkDelay", 4, 0, 10);
        this.rotate = this.create("Rotate", "Autotkrotate", false);
        this.debug = this.create("Debug", "AutotkDebug", false);
        this.name = "Auto 32k";
        this.tag = "Auto32k";
        this.description = "fastest in the west";
    }
    
    @Override
    protected void enable() {
        this.ticks_past = 0;
        this.setup = false;
        this.dispenser_done = false;
        this.place_redstone = false;
        this.hopper_slot = -1;
        int dispenser_slot = -1;
        this.redstone_slot = -1;
        this.shulker_slot = -1;
        int block_slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item item = WurstplusAuto32k.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Item.getItemFromBlock((Block)Blocks.HOPPER)) {
                this.hopper_slot = i;
            }
            else if (item == Item.getItemFromBlock(Blocks.DISPENSER)) {
                dispenser_slot = i;
            }
            else if (item == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) {
                this.redstone_slot = i;
            }
            else if (item instanceof ItemShulkerBox) {
                this.shulker_slot = i;
            }
            else if (item instanceof ItemBlock) {
                block_slot = i;
            }
        }
        if ((this.hopper_slot == -1 || dispenser_slot == -1 || this.redstone_slot == -1 || this.shulker_slot == -1 || block_slot == -1) && !this.place_mode.in("Hopper")) {
            WurstplusMessageUtil.send_client_message("missing item");
            this.set_disable();
            return;
        }
        if (this.hopper_slot == -1 || this.shulker_slot == -1) {
            WurstplusMessageUtil.send_client_message("missing item");
            this.set_disable();
            return;
        }
        if (this.place_mode.in("Looking")) {
            final RayTraceResult r = WurstplusAuto32k.mc.player.rayTrace(5.0, WurstplusAuto32k.mc.getRenderPartialTicks());
            this.pos = Objects.requireNonNull(r).getBlockPos().up();
            final double pos_x = this.pos.getX() - WurstplusAuto32k.mc.player.posX;
            final double pos_z = this.pos.getZ() - WurstplusAuto32k.mc.player.posZ;
            this.rot = ((Math.abs(pos_x) > Math.abs(pos_z)) ? ((pos_x > 0.0) ? new int[] { -1, 0 } : new int[] { 1, 0 }) : ((pos_z > 0.0) ? new int[] { 0, -1 } : new int[] { 0, 1 }));
            if (WurstplusBlockUtil.canPlaceBlock(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 1, this.rot[1]))) {
                WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
                WurstplusBlockUtil.rotatePacket(this.pos.add(-this.rot[0], 1, -this.rot[1]).getX() + 0.5, this.pos.getY() + 1, this.pos.add(-this.rot[0], 1, -this.rot[1]).getZ() + 0.5);
                WurstplusBlockUtil.placeBlock(this.pos.up(), dispenser_slot, false, false, this.swing);
                WurstplusBlockUtil.openBlock(this.pos.up());
                this.setup = true;
            }
            else {
                WurstplusMessageUtil.send_client_message("unable to place");
                this.set_disable();
            }
        }
        else if (this.place_mode.in("Auto")) {
            for (int x = -2; x <= 2; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -2; z <= 2; ++z) {
                        this.rot = ((Math.abs(x) > Math.abs(z)) ? ((x > 0) ? new int[] { -1, 0 } : new int[] { 1, 0 }) : ((z > 0) ? new int[] { 0, -1 } : new int[] { 0, 1 }));
                        this.pos = WurstplusAuto32k.mc.player.getPosition().add(x, y, z);
                        if (WurstplusAuto32k.mc.player.getPositionEyes(WurstplusAuto32k.mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add((double)(x - this.rot[0] / 2.0f), y + 0.5, (double)(z + this.rot[1] / 2))) <= 4.5 && WurstplusAuto32k.mc.player.getPositionEyes(WurstplusAuto32k.mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add(x + 0.5, y + 2.5, z + 0.5)) <= 4.5 && WurstplusBlockUtil.canPlaceBlock(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 1, this.rot[1]))) {
                            WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
                            WurstplusBlockUtil.rotatePacket(this.pos.add(-this.rot[0], 1, -this.rot[1]).getX() + 0.5, this.pos.getY() + 1, this.pos.add(-this.rot[0], 1, -this.rot[1]).getZ() + 0.5);
                            WurstplusBlockUtil.placeBlock(this.pos.up(), dispenser_slot, false, false, this.swing);
                            WurstplusBlockUtil.openBlock(this.pos.up());
                            this.setup = true;
                            return;
                        }
                    }
                }
            }
            WurstplusMessageUtil.send_client_message("unable to place");
            this.set_disable();
        }
        else {
            for (int z2 = -2; z2 <= 2; ++z2) {
                for (int y = -1; y <= 2; ++y) {
                    for (int x2 = -2; x2 <= 2; ++x2) {
                        if ((z2 != 0 || y != 0 || x2 != 0) && (z2 != 0 || y != 1 || x2 != 0) && WurstplusBlockUtil.isBlockEmpty(WurstplusAuto32k.mc.player.getPosition().add(z2, y, x2)) && WurstplusAuto32k.mc.player.getPositionEyes(WurstplusAuto32k.mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add(z2 + 0.5, y + 0.5, x2 + 0.5)) < 4.5 && WurstplusBlockUtil.isBlockEmpty(WurstplusAuto32k.mc.player.getPosition().add(z2, y + 1, x2)) && WurstplusAuto32k.mc.player.getPositionEyes(WurstplusAuto32k.mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add(z2 + 0.5, y + 1.5, x2 + 0.5)) < 4.5) {
                            WurstplusBlockUtil.placeBlock(WurstplusAuto32k.mc.player.getPosition().add(z2, y, x2), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
                            WurstplusBlockUtil.placeBlock(WurstplusAuto32k.mc.player.getPosition().add(z2, y + 1, x2), this.shulker_slot, this.rotate.get_value(true), false, this.swing);
                            WurstplusBlockUtil.openBlock(WurstplusAuto32k.mc.player.getPosition().add(z2, y, x2));
                            this.pos = WurstplusAuto32k.mc.player.getPosition().add(z2, y, x2);
                            this.dispenser_done = true;
                            this.place_redstone = true;
                            this.setup = true;
                            return;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void update() {
        if (this.ticks_past > 50 && !(WurstplusAuto32k.mc.currentScreen instanceof GuiHopper)) {
            WurstplusMessageUtil.send_client_message("inactive too long, disabling");
            this.set_disable();
            return;
        }
        if (this.setup && this.ticks_past > this.delay.get_value(1)) {
            if (!this.dispenser_done) {
                try {
                    WurstplusAuto32k.mc.playerController.windowClick(WurstplusAuto32k.mc.player.openContainer.windowId, 36 + this.shulker_slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAuto32k.mc.player);
                    this.dispenser_done = true;
                    if (this.debug.get_value(true)) {
                        WurstplusMessageUtil.send_client_message("sent item");
                    }
                }
                catch (Exception ex) {}
            }
            if (!this.place_redstone) {
                WurstplusBlockUtil.placeBlock(this.pos.add(0, 2, 0), this.redstone_slot, this.rotate.get_value(true), false, this.swing);
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("placed redstone");
                }
                this.place_redstone = true;
                return;
            }
            if (!this.place_mode.in("Hopper") && WurstplusAuto32k.mc.world.getBlockState(this.pos.add(this.rot[0], 1, this.rot[1])).getBlock() instanceof BlockShulkerBox && WurstplusAuto32k.mc.world.getBlockState(this.pos.add(this.rot[0], 0, this.rot[1])).getBlock() != Blocks.HOPPER && this.place_redstone && this.dispenser_done && !(WurstplusAuto32k.mc.currentScreen instanceof GuiInventory)) {
                WurstplusBlockUtil.placeBlock(this.pos.add(this.rot[0], 0, this.rot[1]), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
                WurstplusBlockUtil.openBlock(this.pos.add(this.rot[0], 0, this.rot[1]));
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("in the hopper");
                }
            }
            if (WurstplusAuto32k.mc.currentScreen instanceof GuiHopper) {
                final GuiHopper gui = (GuiHopper)WurstplusAuto32k.mc.currentScreen;
                for (int slot = 32; slot <= 40; ++slot) {
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, gui.inventorySlots.getSlot(slot).getStack()) > 5) {
                        WurstplusAuto32k.mc.player.inventory.currentItem = slot - 32;
                        break;
                    }
                }
                if (!(gui.inventorySlots.inventorySlots.get(0).getStack().getItem() instanceof ItemAir)) {
                    boolean swapReady = true;
                    if (((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty) {
                        swapReady = false;
                    }
                    if (!((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.getSlot(this.shulker_slot + 32).getStack().isEmpty) {
                        swapReady = false;
                    }
                    if (swapReady) {
                        WurstplusAuto32k.mc.playerController.windowClick(((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.windowId, 0, this.shulker_slot, ClickType.SWAP, (EntityPlayer)WurstplusAuto32k.mc.player);
                        this.disable();
                    }
                }
            }
        }
        ++this.ticks_past;
    }
}
