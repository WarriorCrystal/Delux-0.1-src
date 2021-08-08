//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import net.minecraft.util.math.MathHelper;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import com.google.common.collect.Lists;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import java.util.List;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusArrayList extends WurstplusPinnable
{
    boolean flag;
    private int scaled_width;
    private int scaled_height;
    private int scale_factor;
    
    public WurstplusArrayList() {
        super("Array List", "ArrayList", 1.0f, 0, 0);
        this.flag = true;
    }
    
    @Override
    public void render() {
        this.updateResolution();
        int position_update_y = 2;
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        String string;
        List<WurstplusHack> pretty_modules = Wurstplus.get_hack_manager().get_array_active_hacks().stream().sorted(Comparator.comparing(modules -> {
            if (modules.array_detail() == null) {
                string = modules.get_tag();
            }
            else {
                string = modules.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules.array_detail() + Wurstplus.g + "]" + Wurstplus.r;
            }
            return Integer.valueOf(this.get(string, "width"));
        })).collect((Collector<? super Object, ?, List<WurstplusHack>>)Collectors.toList());
        int count = 0;
        if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R") || Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
            pretty_modules = (List<WurstplusHack>)Lists.reverse((List)pretty_modules);
        }
        for (final WurstplusHack modules2 : pretty_modules) {
            this.flag = true;
            if (modules2.get_category().get_tag().equals("WurstplusGUI")) {
                continue;
            }
            for (final String s : WurstplusDrawnUtil.hidden_tags) {
                if (modules2.get_tag().equalsIgnoreCase(s)) {
                    this.flag = false;
                    break;
                }
                if (!this.flag) {
                    break;
                }
            }
            if (!this.flag) {
                continue;
            }
            final String module_name = (modules2.array_detail() == null) ? modules2.get_tag() : (modules2.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules2.array_detail() + Wurstplus.g + "]" + Wurstplus.r);
            if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Free")) {
                this.create_line(module_name, this.docking(2, module_name), position_update_y, nl_r, nl_g, nl_b, nl_a);
                position_update_y += this.get(module_name, "height") + 2;
                if (this.get(module_name, "width") > this.get_width()) {
                    this.set_width(this.get(module_name, "width") + 2);
                }
                this.set_height(position_update_y);
            }
            else {
                if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R")) {
                    this.mc.fontRenderer.drawStringWithShadow(module_name, (float)(this.scaled_width - 2 - this.mc.fontRenderer.getStringWidth(module_name)), (float)(3 + count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                    ++count;
                }
                if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
                    this.mc.fontRenderer.drawStringWithShadow(module_name, 2.0f, (float)(3 + count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                    ++count;
                }
                if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom R")) {
                    this.mc.fontRenderer.drawStringWithShadow(module_name, (float)(this.scaled_width - 2 - this.mc.fontRenderer.getStringWidth(module_name)), (float)(this.scaled_height - count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                    ++count;
                }
                if (!Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom L")) {
                    continue;
                }
                this.mc.fontRenderer.drawStringWithShadow(module_name, 2.0f, (float)(this.scaled_height - count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                ++count;
            }
        }
    }
    
    public void updateResolution() {
        this.scaled_width = this.mc.displayWidth;
        this.scaled_height = this.mc.displayHeight;
        this.scale_factor = 1;
        final boolean flag = this.mc.isUnicode();
        int i = this.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scale_factor < i && this.scaled_width / (this.scale_factor + 1) >= 320 && this.scaled_height / (this.scale_factor + 1) >= 240) {
            ++this.scale_factor;
        }
        if (flag && this.scale_factor % 2 != 0 && this.scale_factor != 1) {
            --this.scale_factor;
        }
        final double scaledWidthD = this.scaled_width / (double)this.scale_factor;
        final double scaledHeightD = this.scaled_height / (double)this.scale_factor;
        this.scaled_width = MathHelper.ceil(scaledWidthD);
        this.scaled_height = MathHelper.ceil(scaledHeightD);
    }
}
