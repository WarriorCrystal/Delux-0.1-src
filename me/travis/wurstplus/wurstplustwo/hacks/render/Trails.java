//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import org.lwjgl.opengl.GL11;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import me.travis.wurstplus.wurstplustwo.event.events.UpdateWalkingPlayerEvent;
import java.util.HashMap;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.minecraft.entity.Entity;
import java.util.Map;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Trails extends WurstplusHack
{
    private final WurstplusSetting lineWidth;
    private final WurstplusSetting red;
    private final WurstplusSetting green;
    private final WurstplusSetting blue;
    private final WurstplusSetting alpha;
    private final Map<Entity, List<Vec3d>> renderMap;
    
    public Trails() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.lineWidth = this.create("LineWidth", "LineWidth", 1.5, 0.10000000149011612, 5.0);
        this.red = this.create("Red", "Red", 0, 0, 255);
        this.green = this.create("Green", "Green", 255, 0, 255);
        this.blue = this.create("Blue", "Blue", 0, 0, 255);
        this.alpha = this.create("Alpha", "Alpha", 255, 0, 255);
        this.renderMap = new HashMap<Entity, List<Vec3d>>();
        this.name = "Trails";
        this.tag = "Trails";
        this.description = "Draws trails on projectiles";
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        for (final Entity entity : Trails.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityThrowable) && !(entity instanceof EntityArrow)) {
                continue;
            }
            final List<Vec3d> vectors = (this.renderMap.get(entity) != null) ? this.renderMap.get(entity) : new ArrayList<Vec3d>();
            vectors.add(new Vec3d(entity.posX, entity.posY, entity.posZ));
            this.renderMap.put(entity, vectors);
        }
    }
    
    @Override
    public void render() {
        for (final Entity entity : Trails.mc.world.loadedEntityList) {
            if (!this.renderMap.containsKey(entity)) {
                continue;
            }
            GlStateManager.pushMatrix();
            WurstplusRenderUtil.GLPrePhobos((float)this.lineWidth.get_value(1.5));
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL11.glColor4f(this.red.get_value(0) / 255.0f, this.green.get_value(255) / 255.0f, this.blue.get_value(0) / 255.0f, this.alpha.get_value(255) / 255.0f);
            GL11.glLineWidth((float)this.lineWidth.get_value(1.5));
            GL11.glBegin(1);
            for (int i = 0; i < this.renderMap.get(entity).size() - 1; ++i) {
                GL11.glVertex3d(this.renderMap.get(entity).get(i).x, this.renderMap.get(entity).get(i).y, this.renderMap.get(entity).get(i).z);
                GL11.glVertex3d(this.renderMap.get(entity).get(i + 1).x, this.renderMap.get(entity).get(i + 1).y, this.renderMap.get(entity).get(i + 1).z);
            }
            GL11.glEnd();
            GlStateManager.resetColor();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            WurstplusRenderUtil.GlPost();
            GlStateManager.popMatrix();
        }
    }
}
