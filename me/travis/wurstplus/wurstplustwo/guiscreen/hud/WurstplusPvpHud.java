//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusPvpHud extends WurstplusPinnable
{
    public WurstplusPvpHud() {
        super("PVP Hud", "pvphud", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final String totem = "Totems: " + this.get_totems();
        final String trap = "Trap: " + this.trap_enabled();
        final String aura = "Aura: " + this.aura_enabled();
        final String surround = "Surround: " + this.surround_enabled();
        final String holefill = "Holefill: " + this.holefill_enabled();
        final String socks = "Socks: " + this.socks_enabled();
        final String selftrap = "Self Trap: " + this.selftrap_enabled();
        this.create_line(totem, this.docking(1, totem), 2, nl_r, nl_g, nl_b, nl_a);
        this.create_line(aura, this.docking(1, aura), 13, nl_r, nl_g, nl_b, nl_a);
        this.create_line(trap, this.docking(1, trap), 24, nl_r, nl_g, nl_b, nl_a);
        this.create_line(surround, this.docking(1, surround), 34, nl_r, nl_g, nl_b, nl_a);
        this.create_line(holefill, this.docking(1, holefill), 45, nl_r, nl_g, nl_b, nl_a);
        this.create_line(socks, this.docking(1, socks), 56, nl_r, nl_g, nl_b, nl_a);
        this.create_line(selftrap, this.docking(1, selftrap), 67, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(surround, "width") + 2);
        this.set_height(this.get(surround, "height") * 5);
    }
    
    public String selftrap_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("SelfTrap").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String trap_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("Trap").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String aura_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String socks_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("Socks").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String surround_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("Surround").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String holefill_enabled() {
        try {
            if (Wurstplus.get_hack_manager().get_module_with_tag("HoleFill").is_active()) {
                return "§a 1";
            }
            return "§4 0";
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public String get_totems() {
        try {
            final int totems = this.offhand() + this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
            if (totems > 1) {
                return "§a " + totems;
            }
            return "§4 " + totems;
        }
        catch (Exception e) {
            return "0";
        }
    }
    
    public int offhand() {
        if (this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return 1;
        }
        return 0;
    }
}
