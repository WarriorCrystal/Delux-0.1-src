//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import net.minecraft.util.math.BlockPos;
import me.travis.turok.draw.RenderHelp;
import net.minecraft.util.math.RayTraceResult;
import java.awt.Color;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusHighlight extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting rgb;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting l_a;
    int color_r;
    int color_g;
    int color_b;
    boolean outline;
    boolean solid;
    
    public WurstplusHighlight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "HighlightMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
        this.rgb = this.create("RGB Effect", "HighlightRGBEffect", true);
        this.r = this.create("R", "HighlightR", 255, 0, 255);
        this.g = this.create("G", "HighlightG", 255, 0, 255);
        this.b = this.create("B", "HighlightB", 255, 0, 255);
        this.a = this.create("A", "HighlightA", 100, 0, 255);
        this.l_a = this.create("Outline A", "HighlightLineA", 255, 0, 255);
        this.outline = false;
        this.solid = false;
        this.name = "Block Highlight";
        this.tag = "BlockHighlight";
        this.description = "see what block ur targeting";
    }
    
    public void disable() {
        this.outline = false;
        this.solid = false;
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        if (WurstplusHighlight.mc.player != null && WurstplusHighlight.mc.world != null) {
            final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int color_rgb = Color.HSBtoRGB(tick_color[0], 1.0f, 1.0f);
            if (this.rgb.get_value(true)) {
                this.color_r = (color_rgb >> 16 & 0xFF);
                this.color_g = (color_rgb >> 8 & 0xFF);
                this.color_b = (color_rgb & 0xFF);
                this.r.set_value(this.color_r);
                this.g.set_value(this.color_g);
                this.b.set_value(this.color_b);
            }
            else {
                this.color_r = this.r.get_value(1);
                this.color_g = this.g.get_value(2);
                this.color_b = this.b.get_value(3);
            }
            if (this.mode.in("Pretty")) {
                this.outline = true;
                this.solid = true;
            }
            if (this.mode.in("Solid")) {
                this.outline = false;
                this.solid = true;
            }
            if (this.mode.in("Outline")) {
                this.outline = true;
                this.solid = false;
            }
            final RayTraceResult result = WurstplusHighlight.mc.objectMouseOver;
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                final BlockPos block_pos = result.getBlockPos();
                if (this.solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(block_pos, this.color_r, this.color_g, this.color_b, this.a.get_value(1), "all");
                    RenderHelp.release();
                }
                if (this.outline) {
                    RenderHelp.prepare("lines");
                    RenderHelp.draw_cube_line(block_pos, this.color_r, this.color_g, this.color_b, this.l_a.get_value(1), "all");
                    RenderHelp.release();
                }
            }
        }
    }
}
