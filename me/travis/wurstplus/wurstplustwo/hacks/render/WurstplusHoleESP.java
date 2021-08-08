//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import java.awt.Color;
import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusHoleESP extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting off_set;
    WurstplusSetting range;
    WurstplusSetting hide_own;
    WurstplusSetting bedrock_enable;
    WurstplusSetting rb;
    WurstplusSetting gb;
    WurstplusSetting bb;
    WurstplusSetting ab;
    WurstplusSetting rainbow_b;
    WurstplusSetting obsidian_enable;
    WurstplusSetting ro;
    WurstplusSetting go;
    WurstplusSetting bo;
    WurstplusSetting ao;
    WurstplusSetting rainbow_o;
    WurstplusSetting dual_enable;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    WurstplusSetting line_a;
    ArrayList<WurstplusPair<BlockPos, Boolean>> holes;
    boolean outline;
    boolean solid;
    boolean docking;
    int color_r_o;
    int color_g_o;
    int color_b_o;
    int color_r_b;
    int color_g_b;
    int color_b_b;
    int color_r;
    int color_g;
    int color_b;
    int color_a;
    int safe_sides;
    
    public WurstplusHoleESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "HoleESPMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
        this.off_set = this.create("Height", "HoleESPOffSetSide", 0.2, 0.0, 1.0);
        this.range = this.create("Range", "HoleESPRange", 6, 1, 12);
        this.hide_own = this.create("Hide Own", "HoleESPHideOwn", false);
        this.bedrock_enable = this.create("Bedrock Holes", "HoleESPBedrockHoles", true);
        this.rb = this.create("R", "HoleESPRb", 0, 0, 255);
        this.gb = this.create("G", "HoleESPGb", 255, 0, 255);
        this.bb = this.create("B", "HoleESPBb", 0, 0, 255);
        this.ab = this.create("A", "HoleESPAb", 50, 0, 255);
        this.rainbow_b = this.create("RainbowBedrock", "RainbowModeBedrock", false);
        this.obsidian_enable = this.create("Obsidian Holes", "HoleESPObsidianHoles", true);
        this.ro = this.create("R", "HoleESPRo", 255, 0, 255);
        this.go = this.create("G", "HoleESPGo", 0, 0, 255);
        this.bo = this.create("B", "HoleESPBo", 0, 0, 255);
        this.ao = this.create("A", "HoleESPAo", 50, 0, 255);
        this.rainbow_o = this.create("RainbowObi", "RainbowModeObi", false);
        this.dual_enable = this.create("Dual Holes", "HoleESPTwoHoles", true);
        this.sat = this.create("Saturation", "NametagSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "NametagBrightness", 0.8, 0.0, 1.0);
        this.line_a = this.create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);
        this.holes = new ArrayList<WurstplusPair<BlockPos, Boolean>>();
        this.outline = false;
        this.solid = false;
        this.docking = false;
        this.name = "Hole ESP";
        this.tag = "HoleESP";
        this.description = "omg a hole";
    }
    
    @Override
    public void update() {
        this.color_r_b = this.rb.get_value(1);
        this.color_g_b = this.gb.get_value(1);
        this.color_b_b = this.bb.get_value(1);
        this.color_r_o = this.ro.get_value(1);
        this.color_g_o = this.go.get_value(1);
        this.color_b_o = this.bo.get_value(1);
        if (this.rainbow_b.get_value(true)) {
            this.cycle_rainbow1();
        }
        if (this.rainbow_o.get_value(true)) {
            this.cycle_rainbow();
        }
        this.holes.clear();
        if (WurstplusHoleESP.mc.player != null || WurstplusHoleESP.mc.world != null) {
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
            final int colapso_range = (int)Math.ceil(this.range.get_value(1));
            final List<BlockPos> spheres = this.sphere(this.player_as_blockpos(), (float)colapso_range, colapso_range);
            for (final BlockPos pos : spheres) {
                if (WurstplusHoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && WurstplusHoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && WurstplusHoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    boolean possible = true;
                    this.safe_sides = 0;
                    int air_orient = -1;
                    int counter = 0;
                    for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                        final Block block = WurstplusHoleESP.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
                        if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                            possible = false;
                            if (counter == 0) {
                                break;
                            }
                            if (air_orient != -1) {
                                air_orient = -1;
                                break;
                            }
                            if (!block.equals(Blocks.AIR)) {
                                break;
                            }
                            air_orient = counter;
                        }
                        if (block == Blocks.BEDROCK) {
                            ++this.safe_sides;
                        }
                        ++counter;
                    }
                    if (possible) {
                        if (this.safe_sides == 5) {
                            if (!this.bedrock_enable.get_value(true)) {
                                continue;
                            }
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
                        }
                        else {
                            if (!this.obsidian_enable.get_value(true)) {
                                continue;
                            }
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
                        }
                    }
                    else {
                        if (!this.dual_enable.get_value(true) || air_orient < 0) {
                            continue;
                        }
                        final BlockPos second_pos = pos.add((Vec3i)orientConv(air_orient));
                        if (!this.checkDual(second_pos, air_orient)) {
                            continue;
                        }
                        final boolean low_ceiling_hole = WurstplusHoleESP.mc.world.getBlockState(second_pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHoleESP.mc.world.getBlockState(second_pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR);
                        if (this.safe_sides == 8) {
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
                            if (!low_ceiling_hole) {
                                continue;
                            }
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, true));
                        }
                        else {
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
                            if (!low_ceiling_hole) {
                                continue;
                            }
                            this.holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, false));
                        }
                    }
                }
            }
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.ro.set_value(color_rgb_o >> 16 & 0xFF);
        this.go.set_value(color_rgb_o >> 8 & 0xFF);
        this.bo.set_value(color_rgb_o & 0xFF);
    }
    
    public void cycle_rainbow1() {
        final float[] tick_color1 = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_b = Color.HSBtoRGB(tick_color1[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.rb.set_value(color_rgb_b >> 16 & 0xFF);
        this.gb.set_value(color_rgb_b >> 8 & 0xFF);
        this.bb.set_value(color_rgb_b & 0xFF);
    }
    
    private static BlockPos orientConv(final int orient_count) {
        BlockPos converted = null;
        switch (orient_count) {
            case 0: {
                converted = new BlockPos(0, -1, 0);
                break;
            }
            case 1: {
                converted = new BlockPos(0, 0, -1);
                break;
            }
            case 2: {
                converted = new BlockPos(1, 0, 0);
                break;
            }
            case 3: {
                converted = new BlockPos(0, 0, 1);
                break;
            }
            case 4: {
                converted = new BlockPos(-1, 0, 0);
                break;
            }
            case 5: {
                converted = new BlockPos(0, 1, 0);
                break;
            }
        }
        return converted;
    }
    
    private static int oppositeIntOrient(final int orient_count) {
        int opposite = 0;
        switch (orient_count) {
            case 0: {
                opposite = 5;
                break;
            }
            case 1: {
                opposite = 3;
                break;
            }
            case 2: {
                opposite = 4;
                break;
            }
            case 3: {
                opposite = 1;
                break;
            }
            case 4: {
                opposite = 2;
                break;
            }
        }
        return opposite;
    }
    
    private boolean checkDual(final BlockPos second_block, final int counter) {
        int i = -1;
        for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
            ++i;
            if (counter != oppositeIntOrient(i)) {
                final Block block = WurstplusHoleESP.mc.world.getBlockState(second_block.add((Vec3i)seems_blocks)).getBlock();
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    return false;
                }
                if (block == Blocks.BEDROCK) {
                    ++this.safe_sides;
                }
            }
        }
        return true;
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        float off_set_h = 0.0f;
        if (!this.holes.isEmpty()) {
            off_set_h = (float)this.off_set.get_value(1.0);
            for (final WurstplusPair hole : this.holes) {
                if (hole.getValue()) {
                    this.color_r = this.color_r_b;
                    this.color_g = this.color_g_b;
                    this.color_b = this.color_b_b;
                    this.color_a = this.ab.get_value(1);
                }
                else {
                    if (hole.getValue()) {
                        continue;
                    }
                    this.color_r = this.color_r_o;
                    this.color_g = this.color_g_o;
                    this.color_b = this.color_b_o;
                    this.color_a = this.ao.get_value(1);
                }
                if (!this.hide_own.get_value(true) || !hole.getKey().equals((Object)new BlockPos(WurstplusHoleESP.mc.player.posX, WurstplusHoleESP.mc.player.posY, WurstplusHoleESP.mc.player.posZ))) {
                    if (this.solid) {
                        RenderHelp.prepare("quads");
                        RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)hole.getKey().getX(), (float)hole.getKey().getY(), (float)hole.getKey().getZ(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.color_a, "all");
                        RenderHelp.release();
                    }
                    if (!this.outline) {
                        continue;
                    }
                    RenderHelp.prepare("lines");
                    RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)hole.getKey().getX(), (float)hole.getKey().getY(), (float)hole.getKey().getZ(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.line_a.get_value(1), "all");
                    RenderHelp.release();
                }
            }
        }
    }
    
    public List<BlockPos> sphere(final BlockPos pos, final float r, final int h) {
        final boolean hollow = false;
        final boolean sphere = true;
        final int plus_y = 0;
        final List<BlockPos> sphere_block = new ArrayList<BlockPos>();
        final int cx = pos.getX();
        final int cy = pos.getY();
        final int cz = pos.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos spheres = new BlockPos(x, y + plus_y, z);
                        sphere_block.add(spheres);
                    }
                }
            }
        }
        return sphere_block;
    }
    
    public BlockPos player_as_blockpos() {
        return new BlockPos(Math.floor(WurstplusHoleESP.mc.player.posX), Math.floor(WurstplusHoleESP.mc.player.posY), Math.floor(WurstplusHoleESP.mc.player.posZ));
    }
}
