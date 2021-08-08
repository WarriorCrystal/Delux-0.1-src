// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.util.WurstplusTimeUtil;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusTime extends WurstplusPinnable
{
    public WurstplusTime() {
        super("Time", "Time", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        String line = "";
        line += ((WurstplusTimeUtil.get_hour() < 10) ? ("0" + WurstplusTimeUtil.get_hour()) : Integer.valueOf(WurstplusTimeUtil.get_hour()));
        line += ":";
        line += ((WurstplusTimeUtil.get_minuite() < 10) ? ("0" + WurstplusTimeUtil.get_minuite()) : Integer.valueOf(WurstplusTimeUtil.get_minuite()));
        line += ":";
        line += ((WurstplusTimeUtil.get_second() < 10) ? ("0" + WurstplusTimeUtil.get_second()) : Integer.valueOf(WurstplusTimeUtil.get_second()));
        this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }
}
