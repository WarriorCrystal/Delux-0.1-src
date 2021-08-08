//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font;

import me.travis.wurstplus.Wurstplus;
import net.minecraft.client.Minecraft;

public class FontUtil
{
    protected static Minecraft mc;
    
    public static void drawString(final String text, final float x, final float y, final int colour) {
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
            Wurstplus.latoFont.drawString(text, x, y, colour);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
            Wurstplus.verdanaFont.drawString(text, x, y, colour);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
            Wurstplus.arialFont.drawString(text, x, y, colour);
        }
        else {
            FontUtil.mc.fontRenderer.drawString(text, (int)x, (int)y, colour);
        }
    }
    
    public static void drawStringWithShadow(final String text, final float x, final float y, final int colour) {
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
            Wurstplus.latoFont.drawStringWithShadow(text, x, y, colour);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
            Wurstplus.verdanaFont.drawStringWithShadow(text, x, y, colour);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
            Wurstplus.arialFont.drawStringWithShadow(text, x, y, colour);
        }
        else {
            FontUtil.mc.fontRenderer.drawStringWithShadow(text, (float)(int)x, (float)(int)y, colour);
        }
    }
    
    public static void drawText(final String text, final float x, final float y, final int colour) {
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFontShadow").get_value(true)) {
            drawStringWithShadow(text, x, y, colour);
        }
        else {
            drawString(text, x, y, colour);
        }
    }
    
    public static int getFontHeight() {
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
            return Wurstplus.latoFont.getHeight();
        }
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
            return Wurstplus.verdanaFont.getHeight();
        }
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
            return Wurstplus.arialFont.getHeight();
        }
        return FontUtil.mc.fontRenderer.FONT_HEIGHT;
    }
    
    static {
        FontUtil.mc = Minecraft.getMinecraft();
    }
}
