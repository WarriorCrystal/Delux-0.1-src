//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.items;

import java.math.RoundingMode;
import java.math.BigDecimal;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.Component;

public class DoubleComponent extends Component
{
    private WurstplusSetting set;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double sliderWidth;
    
    public DoubleComponent(final WurstplusSetting value, final ModuleButton button, final int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
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
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, -13684945);
        WurstplusDraw.draw_rect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        WurstplusDraw.draw_rect(this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        WurstplusDraw.draw_rect(this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.sliderWidth - 1, this.parent.parent.getY() + this.offset + 15, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 1, this.parent.parent.getY() + this.offset + 15, -14606047);
        FontUtil.drawText(this.set.get_name() + ChatFormatting.GRAY + " " + this.set.get_value(1.0), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 3), -1);
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        final double diff = Math.min(100, Math.max(0, mouseX - this.x));
        final double min = this.set.get_min(1.0);
        final double max = this.set.get_max(1.0);
        this.sliderWidth = 100.0 * (this.set.get_value(1.0) - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.set.set_value(this.set.get_min(1.0));
            }
            else {
                final double newValue = roundToPlace(diff / 100.0 * (max - min) + min, 2);
                this.set.set_value(newValue);
            }
        }
    }
    
    private static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.dragging = false;
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
