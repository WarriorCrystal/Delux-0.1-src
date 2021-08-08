//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import java.awt.Color;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class BurrowESP extends WurstplusHack
{
    public WurstplusSetting burrowEspRange;
    public WurstplusSetting renderOwnBurrow;
    
    public BurrowESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "BurrowESP";
        this.tag = "BurrowESP";
        this.description = "Shows when someone is burrowed";
        this.burrowEspRange = this.create("Range", "Range", 10, 0, 20);
        this.renderOwnBurrow = this.create("RenderOwn", "RenderOwn", false);
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        for (final EntityPlayer player : BurrowESP.mc.world.playerEntities) {
            if (player.getDistance((Entity)BurrowESP.mc.player) < this.burrowEspRange.get_value(10) && BurrowESP.mc.world != null && BurrowESP.mc.player != null && !player.isSneaking()) {
                if (BurrowESP.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ)).getBlock() != Blocks.OBSIDIAN && BurrowESP.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ)).getBlock() != Blocks.BEDROCK) {
                    continue;
                }
                if (player == BurrowESP.mc.player && !this.renderOwnBurrow.get_value(false)) {
                    continue;
                }
                final AxisAlignedBB bb = new AxisAlignedBB(player.posX - BurrowESP.mc.getRenderManager().viewerPosX, player.posY - BurrowESP.mc.getRenderManager().viewerPosY, player.posZ - BurrowESP.mc.getRenderManager().viewerPosZ, player.posX + 0.5 - BurrowESP.mc.getRenderManager().viewerPosX, player.posY + 0.5 - BurrowESP.mc.getRenderManager().viewerPosY, player.posZ + 0.5 - BurrowESP.mc.getRenderManager().viewerPosZ);
                WurstplusRenderUtil.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, new Color(0, 0, 255, 150).getRGB());
                WurstplusRenderUtil.drawBox(bb, new Color(25, 198, 217, 75).getRGB());
            }
        }
    }
}
