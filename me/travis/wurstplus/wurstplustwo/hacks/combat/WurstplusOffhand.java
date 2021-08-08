//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiInventory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusOffhand extends WurstplusHack
{
    WurstplusSetting switch_mode;
    WurstplusSetting totem_switch;
    WurstplusSetting gapple_in_hole;
    WurstplusSetting gapple_hole_hp;
    WurstplusSetting delay;
    private boolean switching;
    private int last_slot;
    
    public WurstplusOffhand() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.switch_mode = this.create("Offhand", "OffhandOffhand", "Totem", this.combobox("Totem", "Crystal", "Gapple"));
        this.totem_switch = this.create("Totem HP", "OffhandTotemHP", 16, 0, 36);
        this.gapple_in_hole = this.create("Gapple In Hole", "OffhandGapple", false);
        this.gapple_hole_hp = this.create("Gapple Hole HP", "OffhandGappleHP", 8, 0, 36);
        this.delay = this.create("Delay", "OffhandDelay", false);
        this.switching = false;
        this.name = "Offhand";
        this.tag = "Offhand";
        this.description = "Switches shit to ur offhand";
    }
    
    @Override
    public void update() {
        if (WurstplusOffhand.mc.currentScreen == null || WurstplusOffhand.mc.currentScreen instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            final float hp = WurstplusOffhand.mc.player.getHealth() + WurstplusOffhand.mc.player.getAbsorptionAmount();
            if (hp <= this.totem_switch.get_value(1)) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Crystal") && Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
                this.swap_items(this.get_item_slot(Items.END_CRYSTAL), 0);
                return;
            }
            if (this.gapple_in_hole.get_value(true) && hp > this.gapple_hole_hp.get_value(1) && this.is_in_hole()) {
                this.swap_items(this.get_item_slot(Items.GOLDEN_APPLE), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Totem")) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Gapple")) {
                this.swap_items(this.get_item_slot(Items.GOLDEN_APPLE), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Crystal") && !Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), 0);
                return;
            }
            if (WurstplusOffhand.mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                this.swap_items(this.get_item_slot(Items.TOTEM_OF_UNDYING), this.delay.get_value(true) ? 1 : 0);
            }
        }
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
            WurstplusOffhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
            WurstplusOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
        }
        if (step == 1) {
            WurstplusOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusOffhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
            WurstplusOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.player);
            this.switching = false;
        }
        WurstplusOffhand.mc.playerController.updateController();
    }
    
    private boolean is_in_hole() {
        final BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return WurstplusOffhand.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && WurstplusOffhand.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && WurstplusOffhand.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && WurstplusOffhand.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR;
    }
    
    private int get_item_slot(final Item input) {
        if (input == WurstplusOffhand.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = WurstplusOffhand.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.GOLDEN_APPLE) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
