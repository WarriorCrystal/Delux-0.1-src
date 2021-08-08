// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventHandler;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusTPS extends WurstplusPinnable
{
    public WurstplusTPS() {
        super("TPS", "TPS", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final String line = "TPS: " + this.getTPS();
        this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }
    
    public String getTPS() {
        try {
            final int tps = Math.round(WurstplusEventHandler.INSTANCE.get_tick_rate());
            if (tps >= 16) {
                return "§a" + Integer.toString(tps);
            }
            if (tps >= 10) {
                return "§3" + Integer.toString(tps);
            }
            return "§4" + Integer.toString(tps);
        }
        catch (Exception e) {
            return "oh no " + e;
        }
    }
}
