//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past;

import org.lwjgl.input.Mouse;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font.FontUtil;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.items.ModuleButton;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class Panel
{
    protected Minecraft mc;
    public ArrayList<Component> components;
    public String title;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean isSettingOpen;
    private boolean isDragging;
    private boolean open;
    public int dragX;
    public int dragY;
    public WurstplusCategory cat;
    public int tY;
    
    public Panel(final String title, final int x, final int y, final int width, final int height, final WurstplusCategory cat) {
        this.mc = Minecraft.getMinecraft();
        this.components = new ArrayList<Component>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;
        this.cat = cat;
        this.tY = this.height;
        for (final WurstplusHack modules : Wurstplus.get_hack_manager().get_modules_with_category(cat)) {
            if (modules.get_category() != cat) {
                continue;
            }
            final ModuleButton modButton = new ModuleButton(modules, this, this.tY);
            this.components.add(modButton);
            this.tY += 15;
        }
        this.refresh();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -14606047);
        WurstplusDraw.draw_rect(this.x - 2, this.y - 0, this.x + this.width + 2, this.y + this.height + 0, Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIR").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIG").get_value(1), Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIB").get_value(1), 255);
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Lato")) {
            FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Verdana")) {
            FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
        }
        else if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIFont").in("Arial")) {
            FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
        }
        else {
            FontUtil.drawText(this.title, (float)(this.x + 27), (float)(this.y + this.height / 2 - FontUtil.getFontHeight() / 2), -1);
        }
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.renderComponent();
            }
        }
    }
    
    public void refresh() {
        int off = this.height;
        for (final Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
        this.scroll();
    }
    
    public void scroll() {
        final int scrollWheel = Mouse.getDWheel();
        for (final Panel panels : PastGUI.panels) {
            if (scrollWheel < 0) {
                panels.setY(panels.getY() - Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIScrollSpeed").get_value(1));
            }
            else {
                if (scrollWheel <= 0) {
                    continue;
                }
                panels.setY(panels.getY() + Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIScrollSpeed").get_value(1));
            }
        }
    }
    
    public void closeAllSetting() {
        for (final Component component : this.components) {
            component.closeAllSub();
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setDragging(final boolean drag) {
        this.isDragging = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public WurstplusCategory getCategory() {
        return this.cat;
    }
}
