//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoArmour extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting smart_mode;
    WurstplusSetting put_back;
    WurstplusSetting player_range;
    WurstplusSetting crystal_range;
    WurstplusSetting boot_percent;
    WurstplusSetting chest_percent;
    private int delay_count;
    
    public WurstplusAutoArmour() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "AADelay", 2, 0, 5);
        this.smart_mode = this.create("Smart Mode", "AASmartMode", true);
        this.put_back = this.create("Equip Armour", "AAEquipArmour", true);
        this.player_range = this.create("Player Range", "AAPlayerRange", 8, 0, 20);
        this.crystal_range = this.create("Crystal Range", "AACrystalRange", 13, 0, 20);
        this.boot_percent = this.create("Boot Percent", "AATBootPercent", 80, 0, 100);
        this.chest_percent = this.create("Chest Percent", "AATChestPercent", 80, 0, 100);
        this.name = "Auto Armour";
        this.tag = "AutoArmour";
        this.description = "WATCH UR BOOTS";
    }
    
    @Override
    protected void enable() {
        this.delay_count = 0;
    }
    
    @Override
    public void update() {
        if (WurstplusAutoArmour.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        boolean flag = false;
        if (this.delay_count < this.delay.get_value(0)) {
            ++this.delay_count;
            return;
        }
        this.delay_count = 0;
        if (this.smart_mode.get_value(true) && !this.is_crystal_in_range(this.crystal_range.get_value(1)) && !this.is_player_in_range(this.player_range.get_value(1))) {
            flag = true;
        }
        if (flag) {
            if (WurstplusAutoArmour.mc.gameSettings.keyBindUseItem.isKeyDown() && WurstplusAutoArmour.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                this.take_off();
            }
            return;
        }
        if (!this.put_back.get_value(true)) {
            return;
        }
        if (WurstplusAutoArmour.mc.currentScreen instanceof GuiContainer && !(WurstplusAutoArmour.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int armorType = 0; armorType < 4; ++armorType) {
            final ItemStack oldArmor = WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = WurstplusAutoArmour.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() <= 1) {
                if (stack.getItem() instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    final int armorType2 = armor.armorType.ordinal() - 2;
                    if (armorType2 != 2 || !WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) {
                        final int armorValue = armor.damageReduceAmount;
                        if (armorValue > bestArmorValues[armorType2]) {
                            bestArmorSlots[armorType2] = slot;
                            bestArmorValues[armorType2] = armorValue;
                        }
                    }
                }
            }
        }
        for (int armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 != -1) {
                final ItemStack oldArmor2 = WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType);
                if (oldArmor2 != ItemStack.EMPTY || WurstplusAutoArmour.mc.player.inventory.getFirstEmptyStack() != -1) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    WurstplusAutoArmour.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
                    WurstplusAutoArmour.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
                    break;
                }
            }
        }
    }
    
    public boolean is_player_in_range(final int range) {
        for (final Entity player : (List)WurstplusAutoArmour.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player == WurstplusAutoArmour.mc.player) {
                continue;
            }
            if (WurstplusAutoArmour.mc.player.getDistance(player) < range) {
                return true;
            }
        }
        return false;
    }
    
    public boolean is_crystal_in_range(final int range) {
        for (final Entity c : (List)WurstplusAutoArmour.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList())) {
            if (WurstplusAutoArmour.mc.player.getDistance(c) < range) {
                return true;
            }
        }
        return false;
    }
    
    public void take_off() {
        if (!this.is_space()) {
            return;
        }
        for (final Map.Entry<Integer, ItemStack> armorSlot : get_armour().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (this.is_healed(stack)) {
                WurstplusAutoArmour.mc.playerController.windowClick(0, (int)armorSlot.getKey(), 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
            }
        }
    }
    
    public boolean is_space() {
        for (final Map.Entry<Integer, ItemStack> invSlot : get_inv().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.getItem() == Items.AIR) {
                return true;
            }
        }
        return false;
    }
    
    private static Map<Integer, ItemStack> get_inv() {
        return get_inv_slots(9, 44);
    }
    
    private static Map<Integer, ItemStack> get_armour() {
        return get_inv_slots(5, 8);
    }
    
    private static Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoArmour.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    public boolean is_healed(final ItemStack item) {
        if (item.getItem() == Items.DIAMOND_BOOTS || item.getItem() == Items.DIAMOND_HELMET) {
            final double max_dam = item.getMaxDamage();
            final double dam_left = item.getMaxDamage() - item.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            return percent >= this.boot_percent.get_value(1);
        }
        final double max_dam = item.getMaxDamage();
        final double dam_left = item.getMaxDamage() - item.getItemDamage();
        final double percent = dam_left / max_dam * 100.0;
        return percent >= this.chest_percent.get_value(1);
    }
}
