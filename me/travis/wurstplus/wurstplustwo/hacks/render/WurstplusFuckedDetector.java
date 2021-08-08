//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashSet;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import java.util.Set;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusFuckedDetector extends WurstplusHack
{
    WurstplusSetting draw_own;
    WurstplusSetting draw_friends;
    WurstplusSetting render_mode;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    private boolean solid;
    private boolean outline;
    public Set<BlockPos> fucked_players;
    
    public WurstplusFuckedDetector() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.draw_own = this.create("Draw Own", "FuckedDrawOwn", false);
        this.draw_friends = this.create("Draw Friends", "FuckedDrawFriends", false);
        this.render_mode = this.create("Render Mode", "FuckedRenderMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
        this.r = this.create("R", "FuckedR", 255, 0, 255);
        this.g = this.create("G", "FuckedG", 255, 0, 255);
        this.b = this.create("B", "FuckedB", 255, 0, 255);
        this.a = this.create("A", "FuckedA", 100, 0, 255);
        this.fucked_players = new HashSet<BlockPos>();
        this.name = "Fucked Detector";
        this.tag = "FuckedDetector";
        this.description = "see if people are hecked";
    }
    
    @Override
    protected void enable() {
        this.fucked_players.clear();
    }
    
    @Override
    public void update() {
        if (WurstplusFuckedDetector.mc.world == null) {
            return;
        }
        this.set_fucked_players();
    }
    
    public void set_fucked_players() {
        this.fucked_players.clear();
        for (final EntityPlayer player : WurstplusFuckedDetector.mc.world.playerEntities) {
            if (WurstplusEntityUtil.isLiving((Entity)player)) {
                if (player.getHealth() <= 0.0f) {
                    continue;
                }
                if (!this.is_fucked(player)) {
                    continue;
                }
                if (WurstplusFriendUtil.isFriend(player.getName()) && !this.draw_friends.get_value(true)) {
                    continue;
                }
                if (player == WurstplusFuckedDetector.mc.player && !this.draw_own.get_value(true)) {
                    continue;
                }
                this.fucked_players.add(new BlockPos(player.posX, player.posY, player.posZ));
            }
        }
    }
    
    public boolean is_fucked(final EntityPlayer player) {
        final BlockPos pos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
        return WurstplusCrystalUtil.canPlaceCrystal(pos.south()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.south().south()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) || (WurstplusCrystalUtil.canPlaceCrystal(pos.east()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.east().east()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR)) || (WurstplusCrystalUtil.canPlaceCrystal(pos.west()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.west().west()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR)) || (WurstplusCrystalUtil.canPlaceCrystal(pos.north()) || (WurstplusCrystalUtil.canPlaceCrystal(pos.north().north()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR));
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        if (this.render_mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
        }
        if (this.render_mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
        }
        if (this.render_mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
        }
        for (final BlockPos render_block : this.fucked_players) {
            if (render_block == null) {
                return;
            }
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)render_block.getX(), (float)render_block.getY(), (float)render_block.getZ(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (!this.outline) {
                continue;
            }
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)render_block.getX(), (float)render_block.getY(), (float)render_block.getZ(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
        }
    }
}
