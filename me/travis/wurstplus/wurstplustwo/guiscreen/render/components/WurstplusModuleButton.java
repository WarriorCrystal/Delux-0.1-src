// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components;

import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets.WurstplusButtonBind;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets.WurstplusSlider;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets.WurstplusLabel;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets.WurstplusCombobox;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets.WurstplusButton;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusModuleButton
{
    private WurstplusHack module;
    private WurstplusFrame master;
    private ArrayList<WurstplusAbstractWidget> widget;
    private String module_name;
    private boolean opened;
    private int x;
    private int y;
    private int width;
    private int height;
    private int opened_height;
    private int save_y;
    private WurstplusDraw font;
    private int border_a;
    private int border_size;
    private int master_height_cache;
    public int settings_height;
    private int count;
    
    public WurstplusModuleButton(final WurstplusHack module, final WurstplusFrame master) {
        this.font = new WurstplusDraw(1.0f);
        this.border_a = 200;
        this.border_size = 1;
        this.module = module;
        this.master = master;
        this.widget = new ArrayList<WurstplusAbstractWidget>();
        this.module_name = module.get_name();
        this.x = 0;
        this.y = 0;
        this.width = this.font.get_string_width(module.get_name()) + 5;
        this.height = this.font.get_string_height();
        this.opened_height = this.height;
        this.save_y = 0;
        this.opened = false;
        this.master_height_cache = master.get_height();
        this.settings_height = this.y + 10;
        this.count = 0;
        for (final WurstplusSetting settings : Wurstplus.get_setting_manager().get_settings_with_hack(module)) {
            if (settings.get_type().equals("button")) {
                this.widget.add(new WurstplusButton(master, this, settings.get_tag(), this.settings_height));
                this.settings_height += 10;
                ++this.count;
            }
            if (settings.get_type().equals("combobox")) {
                this.widget.add(new WurstplusCombobox(master, this, settings.get_tag(), this.settings_height));
                this.settings_height += 10;
                ++this.count;
            }
            if (settings.get_type().equals("label")) {
                this.widget.add(new WurstplusLabel(master, this, settings.get_tag(), this.settings_height));
                this.settings_height += 10;
                ++this.count;
            }
            if (settings.get_type().equals("doubleslider") || settings.get_type().equals("integerslider")) {
                this.widget.add(new WurstplusSlider(master, this, settings.get_tag(), this.settings_height));
                this.settings_height += 10;
                ++this.count;
            }
        }
        final int size = Wurstplus.get_setting_manager().get_settings_with_hack(module).size();
        if (this.count >= size) {
            this.widget.add(new WurstplusButtonBind(master, this, "bind", this.settings_height));
            this.settings_height += 10;
        }
    }
    
    public WurstplusHack get_module() {
        return this.module;
    }
    
    public WurstplusFrame get_master() {
        return this.master;
    }
    
    public void set_pressed(final boolean value) {
        this.module.set_active(value);
    }
    
    public void set_width(final int width) {
        this.width = width;
    }
    
    public void set_height(final int height) {
        this.height = height;
    }
    
    public void set_x(final int x) {
        this.x = x;
    }
    
    public void set_y(final int y) {
        this.y = y;
    }
    
    public void set_open(final boolean value) {
        this.opened = value;
    }
    
    public boolean get_state() {
        return this.module.is_active();
    }
    
    public int get_settings_height() {
        return this.settings_height;
    }
    
    public int get_cache_height() {
        return this.master_height_cache;
    }
    
    public int get_width() {
        return this.width;
    }
    
    public int get_height() {
        return this.height;
    }
    
    public int get_x() {
        return this.x;
    }
    
    public int get_y() {
        return this.y;
    }
    
    public int get_save_y() {
        return this.save_y;
    }
    
    public boolean is_open() {
        return this.opened;
    }
    
    public boolean is_binding() {
        boolean value_requested = false;
        for (final WurstplusAbstractWidget widgets : this.widget) {
            if (widgets.is_binding()) {
                value_requested = true;
            }
        }
        return value_requested;
    }
    
    public boolean motion(final int mx, final int my) {
        return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
    }
    
    public void does_widgets_can(final boolean can) {
        for (final WurstplusAbstractWidget widgets : this.widget) {
            widgets.does_can(can);
        }
    }
    
    public void bind(final char char_, final int key) {
        for (final WurstplusAbstractWidget widgets : this.widget) {
            widgets.bind(char_, key);
        }
    }
    
    public void mouse(final int mx, final int my, final int mouse) {
        for (final WurstplusAbstractWidget widgets : this.widget) {
            widgets.mouse(mx, my, mouse);
        }
        if (mouse == 0 && this.motion(mx, my)) {
            this.master.does_can(false);
            this.set_pressed(!this.get_state());
        }
        if (mouse == 1 && this.motion(mx, my)) {
            this.master.does_can(false);
            this.set_open(!this.is_open());
            this.master.refresh_frame(this, 0);
        }
    }
    
    public void button_release(final int mx, final int my, final int mouse) {
        for (final WurstplusAbstractWidget widgets : this.widget) {
            widgets.release(mx, my, mouse);
        }
        this.master.does_can(true);
    }
    
    public void render(final int mx, final int my, final int separe) {
        this.set_width(this.master.get_width() - separe);
        this.save_y = this.y + this.master.get_y() - 10;
        final int nm_r = Wurstplus.click_gui.theme_widget_name_r;
        final int nm_g = Wurstplus.click_gui.theme_widget_name_g;
        final int nm_b = Wurstplus.click_gui.theme_widget_name_b;
        final int nm_a = Wurstplus.click_gui.theme_widget_name_a;
        final int bg_r = Wurstplus.click_gui.theme_widget_background_r;
        final int bg_g = Wurstplus.click_gui.theme_widget_background_g;
        final int bg_b = Wurstplus.click_gui.theme_widget_background_b;
        final int bg_a = Wurstplus.click_gui.theme_widget_background_a;
        final int bd_r = Wurstplus.click_gui.theme_widget_border_r;
        final int bd_g = Wurstplus.click_gui.theme_widget_border_g;
        final int bd_b = Wurstplus.click_gui.theme_widget_border_b;
        if (this.module.is_active()) {
            WurstplusDraw.draw_rect(this.x, this.save_y, this.x + this.width - separe, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
            final WurstplusDraw font = this.font;
            WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
        }
        else {
            final WurstplusDraw font2 = this.font;
            WurstplusDraw.draw_string(this.module_name, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
        }
        for (final WurstplusAbstractWidget widgets : this.widget) {
            widgets.set_x(this.get_x());
            final boolean is_passing_in_widget = this.opened && widgets.motion_pass(mx, my);
            if (this.motion(mx, my) || is_passing_in_widget) {
                WurstplusDraw.draw_rect(this.master.get_x() - 1, this.save_y, this.master.get_width() + 1, this.opened_height, bd_r, bd_g, bd_b, this.border_a, this.border_size, "right-left");
            }
            if (this.opened) {
                this.opened_height = this.height + this.settings_height - 10;
                widgets.render(this.get_save_y(), separe, mx, my);
            }
            else {
                this.opened_height = this.height;
            }
        }
    }
}
