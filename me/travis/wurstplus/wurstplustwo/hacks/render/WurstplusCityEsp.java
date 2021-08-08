//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusCityEsp extends WurstplusHack
{
    WurstplusSetting endcrystal_mode;
    WurstplusSetting mode;
    WurstplusSetting off_set;
    WurstplusSetting range;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    List<BlockPos> blocks;
    boolean outline;
    boolean solid;
    
    public WurstplusCityEsp() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.endcrystal_mode = this.create("EndCrystal", "CityEndCrystal", false);
        this.mode = this.create("Mode", "CityMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
        this.off_set = this.create("Height", "CityOffSetSide", 0.2, 0.0, 1.0);
        this.range = this.create("Range", "CityRange", 6, 1, 12);
        this.r = this.create("R", "CityR", 0, 0, 255);
        this.g = this.create("G", "CityG", 255, 0, 255);
        this.b = this.create("B", "CityB", 0, 0, 255);
        this.a = this.create("A", "CityA", 50, 0, 255);
        this.blocks = new ArrayList<BlockPos>();
        this.outline = false;
        this.solid = false;
        this.name = "CityESP";
        this.tag = "City ESP";
        this.description = "jumpy isnt gonna be happy about this";
    }
    
    @Override
    public void update() {
        this.blocks.clear();
        for (final EntityPlayer player : WurstplusCityEsp.mc.world.playerEntities) {
            if (WurstplusCityEsp.mc.player.getDistance((Entity)player) <= this.range.get_value(1)) {
                if (WurstplusCityEsp.mc.player == player) {
                    continue;
                }
                final BlockPos p = WurstplusEntityUtil.is_cityable(player, this.endcrystal_mode.get_value(true));
                if (p == null) {
                    continue;
                }
                this.blocks.add(p);
            }
        }
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        final float off_set_h = (float)this.off_set.get_value(1.0);
        for (final BlockPos pos : this.blocks) {
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
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)pos.getX(), (float)pos.getY(), (float)pos.getZ(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (this.outline) {
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)pos.getX(), (float)pos.getY(), (float)pos.getZ(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
        }
    }
}
