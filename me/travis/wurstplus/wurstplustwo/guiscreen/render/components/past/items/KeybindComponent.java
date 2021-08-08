//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.items;

import org.lwjgl.input.Keyboard;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.Component;

public class KeybindComponent extends Component
{
    private boolean isBinding;
    private ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private String points;
    private float tick;
    
    public KeybindComponent(final ModuleButton parent, final int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.points = ".";
        this.tick = 0.0f;
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
        if (this.isBinding) {
            this.tick += 0.5f;
            FontUtil.drawText("Press a Key" + ChatFormatting.GRAY + " " + this.points, (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
        }
        else {
            FontUtil.drawText("Bind" + ChatFormatting.GRAY + " " + this.parent.mod.get_bind("string"), (float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), -1);
        }
        if (this.isBinding) {
            if (this.tick >= 15.0f) {
                this.points = "..";
            }
            if (this.tick >= 30.0f) {
                this.points = "...";
            }
            if (this.tick >= 45.0f) {
                this.points = ".";
                this.tick = 0.0f;
            }
        }
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.isBinding = !this.isBinding;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.isBinding) {
            if (Keyboard.isKeyDown(211)) {
                this.parent.mod.set_bind(0);
                this.isBinding = false;
            }
            else if (Keyboard.isKeyDown(14)) {
                this.parent.mod.set_bind(0);
                this.isBinding = false;
            }
            else {
                this.parent.mod.set_bind(key);
                this.isBinding = false;
            }
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
