//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.manager;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class InventoryManager
{
    public static final Minecraft mc;
    public Map<String, List<ItemStack>> inventories;
    private int recoverySlot;
    
    public InventoryManager() {
        this.inventories = new HashMap<String, List<ItemStack>>();
        this.recoverySlot = -1;
    }
    
    public void update() {
        if (this.recoverySlot != -1) {
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange((this.recoverySlot == 8) ? 7 : (this.recoverySlot + 1)));
            InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.recoverySlot));
            InventoryManager.mc.player.inventory.currentItem = this.recoverySlot;
            this.recoverySlot = -1;
        }
    }
    
    public void recoverSilent(final int slot) {
        this.recoverySlot = slot;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
