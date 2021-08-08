//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;

public class WurstplusTextureHelper
{
    static final Minecraft mc;
    
    public static void drawTexture(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height) {
        GL11.glPushMatrix();
        final float size = width / 2.0f;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d((double)(0.0f / size), (double)(0.0f / size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2d((double)(0.0f / size), (double)((0.0f + size) / size));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2d((double)((0.0f + size) / size), (double)((0.0f + size) / size));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2d((double)((0.0f + size) / size), (double)(0.0f / size));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        WurstplusTextureHelper.mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack((Item)Items.DIAMOND_HELMET), 999999, 999999);
    }
    
    public static void bindTexture(final ResourceLocation resourceLocation) {
        try {
            final ITextureObject texture = WurstplusTextureHelper.mc.getTextureManager().getTexture(resourceLocation);
            GL11.glBindTexture(3553, texture.getGlTextureId());
        }
        catch (Exception ex) {}
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
