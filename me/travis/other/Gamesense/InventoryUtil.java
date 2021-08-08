//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Gamesense;

import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;

public class InventoryUtil
{
    private static final Minecraft mc;
    
    public static int findObsidianSlot(final boolean offHandActived, final boolean activeBefore) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int findTotemSlot(final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int findFirstItemSlot(final Class<? extends Item> itemToFind, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (itemToFind.isInstance(stack.getItem())) {
                    if (itemToFind.isInstance(stack.getItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int findFirstBlockSlot(final Class<? extends Block> blockToFind, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static List<Integer> findAllItemSlots(final Class<? extends Item> itemToFind) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (itemToFind.isInstance(stack.getItem())) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }
    
    public static List<Integer> findAllBlockSlots(final Class<? extends Block> blockToFind) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        slots.add(i);
                    }
                }
            }
        }
        return slots;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
