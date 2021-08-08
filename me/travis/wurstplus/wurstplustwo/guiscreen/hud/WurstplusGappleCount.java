//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusGappleCount extends WurstplusPinnable
{
    int gapples;
    
    public WurstplusGappleCount() {
        super("Gapple Count", "GappleCount", 1.0f, 0, 0);
        this.gapples = 0;
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        if (this.mc.player != null) {
            if (this.is_on_gui()) {
                this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
            }
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            this.gapples = this.mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
            int off = 0;
            for (int i = 0; i < 45; ++i) {
                final ItemStack stack2 = this.mc.player.inventory.getStackInSlot(i);
                final ItemStack off_h = this.mc.player.getHeldItemOffhand();
                if (off_h.getItem() == Items.GOLDEN_APPLE) {
                    off = off_h.getMaxStackSize();
                }
                else {
                    off = 0;
                }
                if (stack2.getItem() == Items.GOLDEN_APPLE) {
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack2, this.get_x(), this.get_y());
                    this.create_line(Integer.toString(this.gapples + off), 18, 16 - this.get(Integer.toString(this.gapples + off), "height"), nl_r, nl_g, nl_b, nl_a);
                }
            }
            this.mc.getRenderItem().zLevel = 0.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
            this.set_width(16 + this.get(Integer.toString(this.gapples) + off, "width") + 2);
            this.set_height(16);
        }
    }
}
