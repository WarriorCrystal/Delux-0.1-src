//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiInventory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoGapple extends WurstplusHack
{
    WurstplusSetting delay;
    private boolean switching;
    private int last_slot;
    
    public WurstplusAutoGapple() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "GappleDelay", false);
        this.switching = false;
        this.name = "Auto Gapple";
        this.tag = "AutoGapple";
        this.description = "put gapple in offhand";
    }
    
    @Override
    public void update() {
        if (WurstplusAutoGapple.mc.currentScreen == null || WurstplusAutoGapple.mc.currentScreen instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            this.swap_items(this.get_item_slot(), this.delay.get_value(true) ? 1 : 0);
        }
    }
    
    private int get_item_slot() {
        if (Items.GOLDEN_APPLE == WurstplusAutoGapple.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        int i = 36;
        while (i >= 0) {
            final Item item = WurstplusAutoGapple.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.GOLDEN_APPLE) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
            else {
                --i;
            }
        }
        return -1;
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusAutoGapple.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
            WurstplusAutoGapple.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
            WurstplusAutoGapple.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
        }
        if (step == 1) {
            WurstplusAutoGapple.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusAutoGapple.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
            WurstplusAutoGapple.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.player);
            this.switching = false;
        }
        WurstplusAutoGapple.mc.playerController.updateController();
    }
}
