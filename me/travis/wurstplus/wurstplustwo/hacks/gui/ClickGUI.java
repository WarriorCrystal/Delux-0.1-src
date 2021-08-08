//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.gui;

import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class ClickGUI extends WurstplusHack
{
    public WurstplusSetting red;
    public WurstplusSetting green;
    public WurstplusSetting blue;
    public WurstplusSetting rainbow;
    public WurstplusSetting font;
    public WurstplusSetting scroll_speed;
    public WurstplusSetting button_sound;
    public WurstplusSetting font_shadow;
    public WurstplusSetting hover_change;
    public WurstplusSetting pause_game;
    WurstplusTimer timer;
    
    public ClickGUI() {
        super(WurstplusCategory.WURSTPLUS_GUI);
        this.red = this.create("Red", "PastGUIR", 255, 0, 255);
        this.green = this.create("Green", "PastGUIG", 255, 0, 255);
        this.blue = this.create("Blue", "PastGUIB", 255, 0, 255);
        this.rainbow = this.create("Rainbow", "Rainbow", true);
        this.font = this.create("Font", "PastGUIFont", "Lato", this.combobox("Lato", "Verdana", "Arial", "None"));
        this.scroll_speed = this.create("Scroll Speed", "PastGUIScrollSpeed", 10, 0, 20);
        this.button_sound = this.create("Button Sound", "PastGUISound", true);
        this.font_shadow = this.create("Font Shadow", "PastGUIFontShadow", false);
        this.hover_change = this.create("Hover Change", "PastGUIHoverChange", true);
        this.pause_game = this.create("Pause Game", "PastGUIPauseGame", false);
        this.timer = new WurstplusTimer();
        this.name = "ClickGUI";
        this.tag = "ClickGUI";
        this.description = "ClickGUI";
        this.set_bind(54);
    }
    
    @Override
    protected void enable() {
        if (!WurstplusHack.nullCheck()) {
            ClickGUI.mc.displayGuiScreen((GuiScreen)Wurstplus.past_gui);
        }
    }
    
    @Override
    protected void disable() {
        if (!WurstplusHack.nullCheck()) {
            ClickGUI.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    @Override
    public void update() {
        if (this.rainbow.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);
        this.red.set_value(color_rgb_o >> 16 & 0xFF);
        this.green.set_value(color_rgb_o >> 8 & 0xFF);
        this.blue.set_value(color_rgb_o & 0xFF);
    }
}
