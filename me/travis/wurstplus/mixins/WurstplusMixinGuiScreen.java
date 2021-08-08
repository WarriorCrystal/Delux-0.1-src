//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemShulkerBox;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiScreen.class })
public class WurstplusMixinGuiScreen
{
    RenderItem itemRender;
    FontRenderer fontRenderer;
    
    public WurstplusMixinGuiScreen() {
        this.itemRender = Minecraft.getMinecraft().getRenderItem();
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
    
    @Inject(method = { "drawWorldBackground" }, at = { @At("HEAD") }, cancellable = true)
    public void drawWorldBackground(final int tint, final CallbackInfo info) {
        if (Minecraft.getMinecraft().player != null) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTip(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("ShulkerPreview").is_active() && stack.getItem() instanceof ItemShulkerBox) {
            final NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                if (blockEntityTag.hasKey("Items", 9)) {
                    info.cancel();
                    final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(blockEntityTag, (NonNullList)nonnulllist);
                    GlStateManager.enableBlend();
                    GlStateManager.disableRescaleNormal();
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    final int width = Math.max(144, this.fontRenderer.getStringWidth(stack.getDisplayName()) + 3);
                    final int x2 = x + 12;
                    final int y2 = y - 12;
                    final int height = 57;
                    this.itemRender.zLevel = 300.0f;
                    this.drawGradientRectP(x2 - 3, y2 - 4, x2 + width + 3, y2 - 3, -267386864, -267386864);
                    this.drawGradientRectP(x2 - 3, y2 + height + 3, x2 + width + 3, y2 + height + 4, -267386864, -267386864);
                    this.drawGradientRectP(x2 - 3, y2 - 3, x2 + width + 3, y2 + height + 3, -267386864, -267386864);
                    this.drawGradientRectP(x2 - 4, y2 - 3, x2 - 3, y2 + height + 3, -267386864, -267386864);
                    this.drawGradientRectP(x2 + width + 3, y2 - 3, x2 + width + 4, y2 + height + 3, -267386864, -267386864);
                    this.drawGradientRectP(x2 - 3, y2 - 3 + 1, x2 - 3 + 1, y2 + height + 3 - 1, 1347420415, 1344798847);
                    this.drawGradientRectP(x2 + width + 2, y2 - 3 + 1, x2 + width + 3, y2 + height + 3 - 1, 1347420415, 1344798847);
                    this.drawGradientRectP(x2 - 3, y2 - 3, x2 + width + 3, y2 - 3 + 1, 1347420415, 1347420415);
                    this.drawGradientRectP(x2 - 3, y2 + height + 2, x2 + width + 3, y2 + height + 3, 1344798847, 1344798847);
                    this.fontRenderer.drawString(stack.getDisplayName(), x + 12, y - 12, 16777215);
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    RenderHelper.enableGUIStandardItemLighting();
                    for (int i = 0; i < nonnulllist.size(); ++i) {
                        final int iX = x + i % 9 * 16 + 11;
                        final int iY = y + i / 9 * 16 - 11 + 8;
                        final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                        this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStack, iX, iY, (String)null);
                    }
                    RenderHelper.disableStandardItemLighting();
                    this.itemRender.zLevel = 0.0f;
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                }
            }
        }
    }
    
    private void drawGradientRectP(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 300.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, 300.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 300.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 300.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
