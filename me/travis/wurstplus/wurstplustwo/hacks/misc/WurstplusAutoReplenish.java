//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.Map;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoReplenish extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting threshold;
    WurstplusSetting tickdelay;
    private int delay_step;
    
    public WurstplusAutoReplenish() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.mode = this.create("Mode", "AutoReplenishMode", "All", this.combobox("All", "Crystals", "Xp", "Both"));
        this.threshold = this.create("Threshold", "AutoReplenishThreshold", 32, 1, 63);
        this.tickdelay = this.create("Delay", "AutoReplenishDelay", 2, 1, 10);
        this.delay_step = 0;
        this.name = "Hotbar Replenish";
        this.tag = "HotbarReplenish";
        this.description = "chad this doesnt desync you i swear";
    }
    
    @Override
    public void update() {
        if (WurstplusAutoReplenish.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delay_step < this.tickdelay.get_value(1)) {
            ++this.delay_step;
            return;
        }
        this.delay_step = 0;
        final WurstplusPair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = slots.getKey();
        final int hotbarSlot = slots.getValue();
        WurstplusAutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.updateController();
    }
    
    private WurstplusPair<Integer, Integer> findReplenishableHotbarSlot() {
        WurstplusPair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : this.get_hotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.isEmpty) {
                if (stack.getItem() == Items.AIR) {
                    continue;
                }
                if (!stack.isStackable()) {
                    continue;
                }
                if (stack.stackSize >= stack.getMaxStackSize()) {
                    continue;
                }
                if (stack.stackSize > this.threshold.get_value(1)) {
                    continue;
                }
                final int inventorySlot = this.findCompatibleInventorySlot(stack);
                if (inventorySlot == -1) {
                    continue;
                }
                returnPair = new WurstplusPair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : this.get_inventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.isEmpty) {
                if (inventoryStack.getItem() == Items.AIR) {
                    continue;
                }
                if (!this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                    continue;
                }
                final int currentStackSize = ((ItemStack)WurstplusAutoReplenish.mc.player.inventoryContainer.getInventory().get((int)entry.getKey())).stackSize;
                if (smallestStackSize <= currentStackSize) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }
    
    private Map<Integer, ItemStack> get_inventory() {
        return this.get_inv_slots(9, 35);
    }
    
    private Map<Integer, ItemStack> get_hotbar() {
        return this.get_inv_slots(36, 44);
    }
    
    private Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoReplenish.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
}
