//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.items;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font.FontUtil;
import net.minecraft.client.gui.Gui;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.Component;

public class BooleanComponent extends Component
{
    private WurstplusSetting op;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    
    public BooleanComponent(final WurstplusSetting op, final ModuleButton parent, final int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void renderComponent() {
        WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX(), this.parent.parent.getY() + 15 + this.offset, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + 15 + this.offset, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        WurstplusDraw.draw_rect(this.parent.parent.getX() - 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() + 1, this.parent.parent.getY() + this.offset + 16, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        if (this.op.get_value(true)) {
            WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
            Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -13684945);
        }
        else {
            Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -14606047);
        }
        WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        FontUtil.drawText(this.op.get_name(), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.op.set_value(!this.op.get_value(true));
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
