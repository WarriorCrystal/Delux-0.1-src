//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusOnlineFriends;
import me.travis.wurstplus.Wurstplus;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusFriendList extends WurstplusPinnable
{
    int passes;
    public static ChatFormatting bold;
    
    public WurstplusFriendList() {
        super("Friends", "Friends", 1.0f, 0, 0);
    }
    
    @Override
    public void render() {
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final String line1 = WurstplusFriendList.bold + "the_fellas: ";
        this.passes = 0;
        this.create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);
        if (!WurstplusOnlineFriends.getFriends().isEmpty()) {
            for (final Entity e : WurstplusOnlineFriends.getFriends()) {
                ++this.passes;
                this.create_line(e.getName(), this.docking(1, e.getName()), this.get(line1, "height") * this.passes, nl_r, nl_g, nl_b, nl_a);
            }
        }
        this.set_width(this.get(line1, "width") + 2);
        this.set_height(this.get(line1, "height") + 2);
    }
    
    static {
        WurstplusFriendList.bold = ChatFormatting.BOLD;
    }
}
