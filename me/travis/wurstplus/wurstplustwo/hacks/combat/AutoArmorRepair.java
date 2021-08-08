//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.HashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import java.util.Map;
import net.minecraft.inventory.ClickType;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import me.travis.wurstplus.wurstplustwo.util.WorldUtils;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoArmorRepair extends WurstplusHack
{
    private WurstplusSetting delay;
    private WurstplusSetting damage;
    private int mostDamagedSlot;
    private int mostDamage;
    private int lastSlot;
    private int counter;
    private int armorCount;
    private int wait;
    private int[] slots;
    private boolean shouldThrow;
    private boolean shouldArmor;
    private boolean falg;
    
    public AutoArmorRepair() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "Delay", 12, 16, 24);
        this.damage = this.create("Heal Damage", "Heal Damage", 10, 60, 90);
        this.name = "AutoArmorRepair";
        this.tag = "AutoArmorRepair";
        this.description = "AutoArmorRepair";
    }
    
    @Override
    protected void enable() {
        this.falg = false;
        this.mostDamage = -1;
        this.mostDamagedSlot = -1;
        this.shouldArmor = false;
        this.armorCount = 0;
        this.slots = new int[3];
        this.wait = 0;
        this.takeOffArmor();
        if (Wurstplus.get_hack_manager().get_module_with_tag("FastUtil").is_active()) {
            this.falg = true;
            Wurstplus.get_hack_manager().get_module_with_tag("FastUtil").set_disable();
        }
    }
    
    @Override
    protected void disable() {
        if (this.falg) {
            Wurstplus.get_hack_manager().get_module_with_tag("FastUtil").set_enable();
        }
    }
    
    @Override
    public void update() {
        if (AutoArmorRepair.mc.player == null || this.is_active() || AutoArmorRepair.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.shouldThrow) {
            WorldUtils.lookAtBlock(new BlockPos((Vec3i)AutoArmorRepair.mc.player.getPosition().add(0, -1, 0)));
            AutoArmorRepair.mc.player.inventory.currentItem = this.findXP();
            AutoArmorRepair.mc.playerController.processRightClick((EntityPlayer)AutoArmorRepair.mc.player, (World)AutoArmorRepair.mc.world, EnumHand.MAIN_HAND);
            if (this.isRepaired() || this.counter > 40) {
                this.shouldThrow = false;
                this.shouldArmor = true;
                AutoArmorRepair.mc.player.inventory.currentItem = this.lastSlot;
                WurstplusMessageUtil.send_client_message("done");
            }
            ++this.counter;
        }
        if (this.shouldArmor) {
            if (this.wait >= this.delay.get_value(12)) {
                this.wait = 0;
                AutoArmorRepair.mc.playerController.windowClick(0, this.slots[this.armorCount], 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmorRepair.mc.player);
                AutoArmorRepair.mc.playerController.updateController();
                ++this.armorCount;
                if (this.armorCount > 2) {
                    this.armorCount = 0;
                    this.shouldArmor = false;
                    this.disable();
                    return;
                }
            }
            ++this.wait;
        }
    }
    
    public int getMostDamagedSlot() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (stack.getItemDamage() > this.mostDamage) {
                this.mostDamage = stack.getItemDamage();
                this.mostDamagedSlot = armorSlot.getKey();
            }
        }
        return this.mostDamagedSlot;
    }
    
    public boolean isRepaired() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (armorSlot.getKey() == this.mostDamagedSlot) {
                final float percent = this.damage.get_value(10) / 100.0f;
                final int dam = Math.round(stack.getMaxDamage() * percent);
                final int goods = stack.getMaxDamage() - stack.getItemDamage();
                return dam <= goods;
            }
        }
        return false;
    }
    
    public int findXP() {
        this.lastSlot = AutoArmorRepair.mc.player.inventory.currentItem;
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoArmorRepair.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemExpBottle) {
                slot = i;
                break;
            }
        }
        if (slot == -1) {
            WurstplusMessageUtil.send_client_message("no xp");
            this.disable();
            return 1;
        }
        return slot;
    }
    
    public boolean isSpace() {
        int spareSlots = 0;
        for (final Map.Entry<Integer, ItemStack> invSlot : getInventory().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.getItem() == Items.AIR) {
                this.slots[spareSlots] = invSlot.getKey();
                if (++spareSlots > 2) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public void takeOffArmor() {
        if (this.isSpace()) {
            this.getMostDamagedSlot();
            if (this.mostDamagedSlot != -1) {
                for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
                    if (armorSlot.getKey() != this.mostDamagedSlot) {
                        AutoArmorRepair.mc.playerController.windowClick(0, (int)armorSlot.getKey(), 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmorRepair.mc.player);
                    }
                }
                this.counter = 0;
                this.shouldThrow = true;
                return;
            }
        }
        WurstplusMessageUtil.send_client_message("Please ensure there is atleast 3 inv slots open!");
        this.disable();
    }
    
    private static Map<Integer, ItemStack> getInventory() {
        return getInventorySlots(9, 44);
    }
    
    private static Map<Integer, ItemStack> getArmor() {
        return getInventorySlots(5, 8);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoArmorRepair.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
}
