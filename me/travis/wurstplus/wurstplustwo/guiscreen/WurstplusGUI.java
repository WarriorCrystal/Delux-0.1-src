//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen;

import java.io.IOException;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.client.Minecraft;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusGUI extends GuiScreen
{
    private ArrayList<WurstplusFrame> frame;
    private int frame_x;
    private WurstplusFrame current;
    private boolean event_start;
    private boolean event_finish;
    public int theme_frame_name_r;
    public int theme_frame_name_g;
    public int theme_frame_name_b;
    public int theme_frame_name_a;
    public int theme_frame_background_r;
    public int theme_frame_background_g;
    public int theme_frame_background_b;
    public int theme_frame_background_a;
    public int theme_frame_border_r;
    public int theme_frame_border_g;
    public int theme_frame_border_b;
    public int theme_widget_name_r;
    public int theme_widget_name_g;
    public int theme_widget_name_b;
    public int theme_widget_name_a;
    public int theme_widget_background_r;
    public int theme_widget_background_g;
    public int theme_widget_background_b;
    public int theme_widget_background_a;
    public int theme_widget_border_r;
    public int theme_widget_border_g;
    public int theme_widget_border_b;
    private final Minecraft mc;
    
    public WurstplusGUI() {
        this.theme_frame_name_r = 0;
        this.theme_frame_name_g = 0;
        this.theme_frame_name_b = 0;
        this.theme_frame_name_a = 0;
        this.theme_frame_background_r = 0;
        this.theme_frame_background_g = 0;
        this.theme_frame_background_b = 0;
        this.theme_frame_background_a = 0;
        this.theme_frame_border_r = 0;
        this.theme_frame_border_g = 0;
        this.theme_frame_border_b = 0;
        this.theme_widget_name_r = 0;
        this.theme_widget_name_g = 0;
        this.theme_widget_name_b = 0;
        this.theme_widget_name_a = 0;
        this.theme_widget_background_r = 0;
        this.theme_widget_background_g = 0;
        this.theme_widget_background_b = 0;
        this.theme_widget_background_a = 0;
        this.theme_widget_border_r = 0;
        this.theme_widget_border_g = 0;
        this.theme_widget_border_b = 0;
        this.mc = Minecraft.getMinecraft();
        this.frame = new ArrayList<WurstplusFrame>();
        this.frame_x = 10;
        this.event_start = true;
        this.event_finish = false;
        for (final WurstplusCategory categorys : WurstplusCategory.values()) {
            if (!categorys.is_hidden()) {
                final WurstplusFrame frames = new WurstplusFrame(categorys);
                frames.set_x(this.frame_x);
                this.frame.add(frames);
                this.frame_x += frames.get_width() + 5;
                this.current = frames;
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        Wurstplus.get_hack_manager().get_module_with_tag("ClickGUI").set_active(false);
        Wurstplus.get_config_manager().save_settings();
    }
    
    protected void keyTyped(final char char_, final int key) {
        for (final WurstplusFrame frame : this.frame) {
            frame.bind(char_, key);
            if (key == 1 && !frame.is_binding()) {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            if (key == 208 || key == 200) {
                frame.set_y(frame.get_y() - 1);
            }
            if (key == 200 || key == 208) {
                frame.set_y(frame.get_y() + 1);
            }
        }
    }
    
    protected void mouseClicked(final int mx, final int my, final int mouse) {
        for (final WurstplusFrame frames : this.frame) {
            frames.mouse(mx, my, mouse);
            if (mouse == 0 && frames.motion(mx, my) && frames.can()) {
                frames.does_button_for_do_widgets_can(false);
                (this.current = frames).set_move(true);
                this.current.set_move_x(mx - this.current.get_x());
                this.current.set_move_y(my - this.current.get_y());
            }
        }
    }
    
    protected void mouseReleased(final int mx, final int my, final int state) {
        for (final WurstplusFrame frames : this.frame) {
            frames.does_button_for_do_widgets_can(true);
            frames.mouse_release(mx, my, state);
            frames.set_move(false);
        }
        this.set_current(this.current);
    }
    
    public void drawScreen(final int mx, final int my, final float tick) {
        this.drawDefaultBackground();
        for (final WurstplusFrame frames : this.frame) {
            frames.render(mx, my);
        }
    }
    
    public void set_current(final WurstplusFrame current) {
        this.frame.remove(current);
        this.frame.add(current);
    }
    
    public WurstplusFrame get_current() {
        return this.current;
    }
    
    public ArrayList<WurstplusFrame> get_array_frames() {
        return this.frame;
    }
    
    public WurstplusFrame get_frame_with_tag(final String tag) {
        WurstplusFrame frame_requested = null;
        for (final WurstplusFrame frames : this.get_array_frames()) {
            if (frames.get_tag().equals(tag)) {
                frame_requested = frames;
            }
        }
        return frame_requested;
    }
    
    public void handleMouseInput() throws IOException {
        if (Mouse.getEventDWheel() > 0) {
            for (final WurstplusFrame frames : this.get_array_frames()) {
                frames.set_y(frames.get_y() + 10);
            }
        }
        if (Mouse.getEventDWheel() < 0) {
            for (final WurstplusFrame frames : this.get_array_frames()) {
                frames.set_y(frames.get_y() - 10);
            }
        }
        super.handleMouseInput();
    }
}
