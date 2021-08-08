//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Listed;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.item.Item;

public class InventoryUtil
{
    public static int findFirst(final Class<? extends Item> clazz) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (WurstplusEntityUtil.mc.player.inventory.getStackInSlot(a).getItem().getClass().equals(clazz)) {
                b = a;
                break;
            }
        }
        return b;
    }
    
    public static int find(final Class<? extends Item> clazz) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (WurstplusEntityUtil.mc.player.inventory.getStackInSlot(a).getItem().getClass().equals(clazz)) {
                b = a;
            }
        }
        return b;
    }
    
    public static int findFirst(final Item item) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (WurstplusEntityUtil.mc.player.inventory.getStackInSlot(a).getItem() == item) {
                b = a;
                break;
            }
        }
        return b;
    }
    
    public static int find(final Item item) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (WurstplusEntityUtil.mc.player.inventory.getStackInSlot(a).getItem() == item) {
                b = a;
            }
        }
        return b;
    }
    
    public static boolean switchTo(final Item item) {
        final int a = find(item);
        if (a == -1) {
            return false;
        }
        WurstplusEntityUtil.mc.player.inventory.currentItem = a;
        WurstplusEntityUtil.mc.playerController.updateController();
        return true;
    }
    
    public static int getBlockInHotbar(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final Item item = WurstplusEntityUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(block)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final Block block) {
        if (getBlockInHotbar(block) != -1 && WurstplusEntityUtil.mc.player.inventory.currentItem != getBlockInHotbar(block)) {
            WurstplusEntityUtil.mc.player.inventory.currentItem = getBlockInHotbar(block);
            WurstplusEntityUtil.mc.playerController.updateController();
        }
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusEntityUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        if (slot != -1 && WurstplusEntityUtil.mc.player.inventory.currentItem != slot) {
            WurstplusEntityUtil.mc.player.inventory.currentItem = slot;
            WurstplusEntityUtil.mc.playerController.updateController();
        }
    }
}
