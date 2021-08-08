//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntity;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class StorageESP extends WurstplusHack
{
    WurstplusSetting shulkeresp;
    WurstplusSetting shu_r;
    WurstplusSetting shu_g;
    WurstplusSetting shu_b;
    WurstplusSetting echestesp;
    WurstplusSetting ec_r;
    WurstplusSetting ec_g;
    WurstplusSetting ec_b;
    WurstplusSetting chestesp;
    WurstplusSetting ch_r;
    WurstplusSetting ch_g;
    WurstplusSetting ch_b;
    WurstplusSetting otheresp;
    WurstplusSetting ot_r;
    WurstplusSetting ot_g;
    WurstplusSetting ot_b;
    WurstplusSetting ot_a;
    WurstplusSetting a;
    private int color_alpha;
    
    public StorageESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.shulkeresp = this.create("ShulkerESP", "ShulkerESP", "ShulkerColor", this.combobox("ShulkerColor", "RGBMode"));
        this.shu_r = this.create("ShulkerR", "ShulkerR", 230, 0, 255);
        this.shu_g = this.create("ShulkerG", "ShulkerG", 0, 0, 255);
        this.shu_b = this.create("ShulkerB", "ShulkerB", 0, 0, 255);
        this.echestesp = this.create("EChestESP", "EChestESP", true);
        this.ec_r = this.create("EChestR", "ECR", 204, 0, 255);
        this.ec_g = this.create("EChestG", "ECG", 0, 0, 255);
        this.ec_b = this.create("EChestB", "ECB", 255, 0, 255);
        this.chestesp = this.create("ChestESP", "ChestESP", true);
        this.ch_r = this.create("ChestR", "ChestR", 153, 0, 255);
        this.ch_g = this.create("ChestG", "ChestG", 102, 0, 255);
        this.ch_b = this.create("ChestB", "ChestB", 0, 0, 255);
        this.otheresp = this.create("OtherESP", "OtherESP", true);
        this.ot_r = this.create("OtherR", "OtR", 153, 0, 255);
        this.ot_g = this.create("OtherG", "OtG", 102, 0, 255);
        this.ot_b = this.create("OtherB", "OtB", 0, 0, 255);
        this.ot_a = this.create("Outline A", "StorageESPOutlineA", 150, 0, 255);
        this.a = this.create("Solid A", "StorageESPSolidA", 150, 0, 255);
        this.name = "Storage ESP";
        this.tag = "StorageESP";
        this.description = "Is able to see storages in world";
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        this.color_alpha = this.a.get_value(1);
        for (final TileEntity tiles : StorageESP.mc.world.loadedTileEntityList) {
            if (tiles instanceof TileEntityShulkerBox) {
                final TileEntityShulkerBox shulker = (TileEntityShulkerBox)tiles;
                final int hex = 0xFF000000 | (shulker.getColor().getColorValue() & -1);
                if (this.shulkeresp.in("RGBMode")) {
                    this.draw(tiles, this.shu_r.get_value(1), this.shu_g.get_value(1), this.shu_b.get_value(1));
                }
                else {
                    this.draw(tiles, (hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, hex & 0xFF);
                }
            }
            if (tiles instanceof TileEntityEnderChest && this.echestesp.get_value(true)) {
                this.draw(tiles, this.ec_r.get_value(1), this.ec_g.get_value(1), this.ec_b.get_value(1));
            }
            if (tiles instanceof TileEntityChest && this.chestesp.get_value(true)) {
                this.draw(tiles, this.ch_r.get_value(1), this.ch_g.get_value(1), this.ch_b.get_value(1));
            }
            if (tiles instanceof TileEntityDispenser || tiles instanceof TileEntityDropper || tiles instanceof TileEntityHopper || tiles instanceof TileEntityFurnace || tiles instanceof TileEntityMobSpawner || tiles instanceof TileEntityEnchantmentTable || tiles instanceof TileEntityBed || tiles instanceof TileEntityBrewingStand) {
                if (!this.otheresp.get_value(true)) {
                    continue;
                }
                this.draw(tiles, this.ot_r.get_value(1), this.ot_g.get_value(1), this.ot_b.get_value(1));
            }
        }
    }
    
    public void draw(final TileEntity tile_entity, final int r, final int g, final int b) {
        RenderHelp.prepare("quads");
        RenderHelp.draw_cube(tile_entity.getPos(), r, g, b, this.a.get_value(1), "all");
        RenderHelp.release();
        RenderHelp.prepare("lines");
        RenderHelp.draw_cube_line(tile_entity.getPos(), r, g, b, this.ot_a.get_value(1), "all");
        RenderHelp.release();
    }
}
