//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.item.ItemExpBottle;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusStopEXP extends WurstplusHack
{
    WurstplusSetting helmet_boot_percent;
    WurstplusSetting chest_leggings_percent;
    private boolean should_cancel;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> packet_event;
    
    public WurstplusStopEXP() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.helmet_boot_percent = this.create("Helment Boots %", "StopEXHelmet", 80, 0, 100);
        this.chest_leggings_percent = this.create("Chest Leggins %", "StopEXChest", 100, 0, 100);
        this.should_cancel = false;
        this.packet_event = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_packet() instanceof CPacketPlayerTryUseItem && this.should_cancel) {
                event.cancel();
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "Stop EXP";
        this.tag = "StopEXP";
        this.description = "jumpy has a good idea?? (nvm this is dumb)";
    }
    
    @Override
    public void update() {
        int counter = 0;
        for (final Map.Entry<Integer, ItemStack> armor_slot : this.get_armor().entrySet()) {
            ++counter;
            if (armor_slot.getValue().isEmpty()) {
                continue;
            }
            final ItemStack stack = armor_slot.getValue();
            final double max_dam = stack.getMaxDamage();
            final double dam_left = stack.getMaxDamage() - stack.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            if (counter == 1 || counter == 4) {
                if (percent >= this.helmet_boot_percent.get_value(1)) {
                    if (this.is_holding_exp()) {
                        this.should_cancel = true;
                    }
                    else {
                        this.should_cancel = false;
                    }
                }
                else {
                    this.should_cancel = false;
                }
            }
            if (counter != 2 && counter != 3) {
                continue;
            }
            if (percent >= this.chest_leggings_percent.get_value(1)) {
                if (this.is_holding_exp()) {
                    this.should_cancel = true;
                }
                else {
                    this.should_cancel = false;
                }
            }
            else {
                this.should_cancel = false;
            }
        }
    }
    
    private Map<Integer, ItemStack> get_armor() {
        return this.get_inv_slots(5, 8);
    }
    
    private Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> full_inv_slots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            full_inv_slots.put(current, (ItemStack)WurstplusStopEXP.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return full_inv_slots;
    }
    
    public boolean is_holding_exp() {
        return WurstplusStopEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || WurstplusStopEXP.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle;
    }
}
