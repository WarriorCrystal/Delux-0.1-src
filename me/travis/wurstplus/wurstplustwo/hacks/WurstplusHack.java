//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import me.zero.alpine.fork.listener.Listenable;

public class WurstplusHack implements Listenable
{
    public WurstplusCategory category;
    public String name;
    public String tag;
    public String description;
    public int bind;
    public boolean state_module;
    public boolean toggle_message;
    public boolean widget_usage;
    protected int tickDelay;
    public static final Minecraft mc;
    
    public WurstplusHack(final WurstplusCategory category) {
        this.name = "";
        this.tag = "";
        this.description = "";
        this.bind = -1;
        this.toggle_message = true;
        this.widget_usage = false;
        this.category = category;
    }
    
    public void set_bind(final int key) {
        this.bind = key;
    }
    
    public static boolean nullCheck() {
        return WurstplusHack.mc.player == null;
    }
    
    public static boolean fullNullCheck() {
        return WurstplusHack.mc.player == null || WurstplusHack.mc.world == null;
    }
    
    public void onLoad() {
    }
    
    public void onLogout() {
    }
    
    public void set_if_can_send_message_toggle(final boolean value) {
        this.toggle_message = value;
    }
    
    public boolean is_active() {
        return this.state_module;
    }
    
    public boolean using_widget() {
        return this.widget_usage;
    }
    
    public String get_name() {
        return this.name;
    }
    
    public String get_tag() {
        return this.tag;
    }
    
    public String get_description() {
        return this.description;
    }
    
    public int get_bind(final int type) {
        return this.bind;
    }
    
    public String get_bind(final String type) {
        String converted_bind = "null";
        if (this.get_bind(0) < 0) {
            converted_bind = "NONE";
        }
        if (!converted_bind.equals("NONE")) {
            final String key = Keyboard.getKeyName(this.get_bind(0));
            converted_bind = Character.toUpperCase(key.charAt(0)) + ((key.length() != 1) ? key.substring(1).toLowerCase() : "");
        }
        else {
            converted_bind = "None";
        }
        return converted_bind;
    }
    
    public WurstplusCategory get_category() {
        return this.category;
    }
    
    public boolean can_send_message_when_toggle() {
        return this.toggle_message;
    }
    
    public void set_disable() {
        this.state_module = false;
        this.disable();
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
    
    public void set_enable() {
        this.state_module = true;
        this.enable();
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }
    
    public void set_active(final boolean value) {
        if (this.state_module != value) {
            if (value) {
                this.set_enable();
            }
            else {
                this.set_disable();
            }
        }
        if (!this.tag.equals("ClickGUI") && !this.tag.equals("HUD") && this.toggle_message) {
            WurstplusMessageUtil.toggle_message(this);
        }
    }
    
    public void toggle() {
        this.set_active(!this.is_active());
    }
    
    protected WurstplusSetting create(final String name, final String tag, final int value, final int min, final int max) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }
    
    protected WurstplusSetting create(final String name, final String tag, final double value, final double min, final double max) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value, min, max));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }
    
    protected WurstplusSetting create(final String name, final String tag, final boolean value) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }
    
    protected WurstplusSetting create(final String name, final String tag, final String value) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }
    
    protected WurstplusSetting create(final String name, final String tag, final String value, final List<String> values) {
        Wurstplus.get_setting_manager().register(new WurstplusSetting(this, name, tag, values, value));
        return Wurstplus.get_setting_manager().get_setting_with_tag(this, tag);
    }
    
    protected List<String> combobox(final String... item) {
        return new ArrayList<String>(Arrays.asList(item));
    }
    
    public void render(final WurstplusEventRender event) {
    }
    
    public void render() {
    }
    
    public void update() {
    }
    
    public void event_widget() {
    }
    
    protected void disable() {
    }
    
    protected void enable() {
    }
    
    public void onTick() {
    }
    
    public String array_detail() {
        return null;
    }
    
    public String getDisplayInfo() {
        return null;
    }
    
    public void on_render_model(final WurstplusEventRenderEntityModel event) {
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
