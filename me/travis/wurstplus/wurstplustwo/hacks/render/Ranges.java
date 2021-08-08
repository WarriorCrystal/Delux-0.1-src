//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.Iterator;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.entity.RenderManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import me.travis.other.Phobos.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Ranges extends WurstplusHack
{
    private final WurstplusSetting hitSpheres;
    private final WurstplusSetting circle;
    private final WurstplusSetting ownSphere;
    private final WurstplusSetting raytrace;
    private final WurstplusSetting lineWidth;
    private final WurstplusSetting radius;
    
    public Ranges() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.hitSpheres = this.create("HitSpheres", "HitSpheres", false);
        this.circle = this.create("Circle", "Circle", true);
        this.ownSphere = this.create("OwnSphere", "OwnSphere", false);
        this.raytrace = this.create("RayTrace", "RayTrace", false);
        this.lineWidth = this.create("LineWidth", "LineWidth", 1.5, 0.10000000149011612, 5.0);
        this.radius = this.create("Radius", "Radius", 4.5, 0.1, 8.0);
        this.name = "Ranges";
        this.tag = "Ranges";
        this.description = "Draw a circle around the player";
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        if (this.circle.get_value(true)) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            final RenderManager renderManager = Ranges.mc.getRenderManager();
            float hue = System.currentTimeMillis() % 7200L / 7200.0f;
            Color color = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
            final ArrayList<Vec3d> hVectors = new ArrayList<Vec3d>();
            final double x = Ranges.mc.player.lastTickPosX + (Ranges.mc.player.posX - Ranges.mc.player.lastTickPosX) * event.get_partial_ticks() - renderManager.renderPosX;
            final double y = Ranges.mc.player.lastTickPosY + (Ranges.mc.player.posY - Ranges.mc.player.lastTickPosY) * event.get_partial_ticks() - renderManager.renderPosY;
            final double z = Ranges.mc.player.lastTickPosZ + (Ranges.mc.player.posZ - Ranges.mc.player.lastTickPosZ) * event.get_partial_ticks() - renderManager.renderPosZ;
            GL11.glLineWidth((float)this.lineWidth.get_value(1));
            GL11.glBegin(1);
            for (int i = 0; i <= 360; ++i) {
                final Vec3d vec = new Vec3d(x + Math.sin(i * 3.141592653589793 / 180.0) * this.radius.get_value(4.5), y + 0.1, z + Math.cos(i * 3.141592653589793 / 180.0) * this.radius.get_value(4.5));
                final RayTraceResult result = Ranges.mc.world.rayTraceBlocks(new Vec3d(Ranges.mc.player.posX, Ranges.mc.player.posY + Ranges.mc.player.getEyeHeight(), Ranges.mc.player.posZ), vec, false, false, true);
                if (result != null && this.raytrace.get_value(false)) {
                    WurstplusMessageUtil.send_client_message_simple("raytrace was not null");
                    hVectors.add(result.hitVec);
                }
                else {
                    hVectors.add(vec);
                }
            }
            for (int j = 0; j < hVectors.size() - 1; ++j) {
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                GL11.glVertex3d(hVectors.get(j).x, hVectors.get(j).y, hVectors.get(j).z);
                GL11.glVertex3d(hVectors.get(j + 1).x, hVectors.get(j + 1).y, hVectors.get(j + 1).z);
                color = new Color(Color.HSBtoRGB(hue += 0.0027777778f, 1.0f, 1.0f));
            }
            GL11.glEnd();
            GlStateManager.resetColor();
            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
        if (this.hitSpheres.get_value(false)) {
            for (final EntityPlayer player : Ranges.mc.world.playerEntities) {
                if (player != null) {
                    if (player.equals((Object)Ranges.mc.player) && !this.ownSphere.get_value(false)) {
                        continue;
                    }
                    final Vec3d interpolated = EntityUtil.interpolateEntity((Entity)player, event.get_partial_ticks());
                    if (WurstplusFriendUtil.isFriend(player.getName())) {
                        GL11.glColor4f(0.15f, 0.15f, 1.0f, 1.0f);
                    }
                    else if (Ranges.mc.player.getDistance((Entity)player) >= 64.0f) {
                        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                    }
                    else {
                        GL11.glColor4f(1.0f, Ranges.mc.player.getDistance((Entity)player) / 150.0f, 0.0f, 1.0f);
                    }
                    WurstplusRenderUtil.drawSphere(interpolated.x, interpolated.y, interpolated.z, (float)this.radius.get_value(4), 20, 15);
                }
            }
        }
    }
}
