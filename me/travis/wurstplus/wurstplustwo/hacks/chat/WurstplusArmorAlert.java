//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import net.minecraft.item.ItemStack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusArmorAlert extends WurstplusHack
{
    int diedTime;
    
    public WurstplusArmorAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.diedTime = 0;
        this.name = "Armor Alert";
        this.tag = "ArmorAlert";
        this.description = "Notifies you when your armor is low";
    }
    
    @Override
    public void update() {
        final boolean ArmorDurability = this.getArmorDurability();
        if (ArmorDurability) {
            WurstplusMessageUtil.send_client_message("Your armor is below 50%");
        }
    }
    
    private boolean getArmorDurability() {
        final boolean TotalDurability = false;
        for (final ItemStack itemStack : WurstplusArmorAlert.mc.player.inventory.armorInventory) {
            if (itemStack.getMaxDamage() / 2 < itemStack.getItemDamage()) {
                return true;
            }
        }
        return TotalDurability;
    }
}
