//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityXPOrb;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import java.awt.Color;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusChams extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting players;
    WurstplusSetting mobs;
    WurstplusSetting self;
    WurstplusSetting items;
    WurstplusSetting xporbs;
    WurstplusSetting xpbottles;
    WurstplusSetting pearl;
    WurstplusSetting top;
    WurstplusSetting scale;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting box_a;
    WurstplusSetting width;
    WurstplusSetting rainbow_mode;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    
    public WurstplusChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "ChamsMode", "Outline", this.combobox("Outline", "Wireframe"));
        this.players = this.create("Players", "ChamsPlayers", true);
        this.mobs = this.create("Mobs", "ChamsMobs", true);
        this.self = this.create("Self", "ChamsSelf", true);
        this.items = this.create("Items", "ChamsItems", true);
        this.xporbs = this.create("Xp Orbs", "ChamsXPO", true);
        this.xpbottles = this.create("Xp Bottles", "ChamsBottles", true);
        this.pearl = this.create("Pearls", "ChamsPearls", true);
        this.top = this.create("Top", "ChamsTop", true);
        this.scale = this.create("Factor", "ChamsFactor", 0.0, -1.0, 1.0);
        this.r = this.create("R", "ChamsR", 255, 0, 255);
        this.g = this.create("G", "ChamsG", 255, 0, 255);
        this.b = this.create("B", "ChamsB", 255, 0, 255);
        this.a = this.create("A", "ChamsA", 100, 0, 255);
        this.box_a = this.create("Box A", "ChamsABox", 100, 0, 255);
        this.width = this.create("Width", "ChamsWdith", 2.0, 0.5, 5.0);
        this.rainbow_mode = this.create("Rainbow", "ChamsRainbow", false);
        this.sat = this.create("Satiation", "ChamsSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "ChamsBrightness", 0.8, 0.0, 1.0);
        this.name = "Chams";
        this.tag = "Chams";
        this.description = "see even less (now with epic colours)";
    }
    
    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        if (this.items.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (entity instanceof EntityItem && WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xporbs.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (entity instanceof EntityXPOrb && WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.pearl.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl && WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.xpbottles.get_value(true)) {
            int i = 0;
            for (final Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle && WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, WurstplusChams.mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow((double)this.scale.get_value(1)), this.r.get_value(1) / 255.0f, this.g.get_value(1) / 255.0f, this.b.get_value(1) / 255.0f, this.box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                    if (++i >= 50) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public void on_render_model(final WurstplusEventRenderEntityModel event) {
        if (event.stage != 0 || event.entity == null || (!this.self.get_value(true) && event.entity.equals((Object)WurstplusChams.mc.player)) || (!this.players.get_value(true) && event.entity instanceof EntityPlayer) || (!this.mobs.get_value(true) && event.entity instanceof EntityMob)) {
            return;
        }
        final Color color = new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1));
        final boolean fancyGraphics = WurstplusChams.mc.gameSettings.fancyGraphics;
        WurstplusChams.mc.gameSettings.fancyGraphics = false;
        final float gamma = WurstplusChams.mc.gameSettings.gammaSetting;
        WurstplusChams.mc.gameSettings.gammaSetting = 10000.0f;
        if (this.top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (this.mode.in("outline")) {
            WurstplusRenderUtil.renderOne((float)this.width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderFive();
        }
        else {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1028, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!this.top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            WurstplusChams.mc.gameSettings.fancyGraphics = fancyGraphics;
            WurstplusChams.mc.gameSettings.gammaSetting = gamma;
        }
        catch (Exception ex) {}
        event.cancel();
    }
}
