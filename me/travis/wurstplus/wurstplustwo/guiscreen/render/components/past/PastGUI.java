//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past;

import me.travis.wurstplus.Wurstplus;
import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class PastGUI extends GuiScreen
{
    public static ArrayList<Panel> panels;
    
    public PastGUI() {
        PastGUI.panels = new ArrayList<Panel>();
        int panelX = 5;
        final int panelY = 5;
        final int panelWidth = 100;
        final int panelHeight = 15;
        for (final WurstplusCategory c : WurstplusCategory.values()) {
            if (!c.is_hidden()) {
                PastGUI.panels.add(new Panel(c.get_name(), panelX, panelY, panelWidth, panelHeight, c));
                panelX += 105;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        for (final Panel p : PastGUI.panels) {
            p.updatePosition(mouseX, mouseY);
            p.drawScreen(mouseX, mouseY, partialTicks);
            for (final Component comp : p.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Panel p : PastGUI.panels) {
            if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                p.setDragging(true);
                p.dragX = mouseX - p.getX();
                p.dragY = mouseY - p.getY();
            }
            else if (p.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                p.setOpen(!p.isOpen());
            }
            else {
                if (!p.isOpen()) {
                    continue;
                }
                if (p.getComponents().isEmpty()) {
                    continue;
                }
                for (final Component component : p.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Panel panel : PastGUI.panels) {
            if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                if (keyCode == 1) {
                    continue;
                }
                for (final Component component : panel.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void onGuiClosed() {
        Wurstplus.get_hack_manager().get_module_with_tag("ClickGUI").set_active(false);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Panel p : PastGUI.panels) {
            p.setDragging(false);
            if (p.isOpen()) {
                if (p.getComponents().isEmpty()) {
                    continue;
                }
                for (final Component component : p.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public static ArrayList<Panel> getPanels() {
        return PastGUI.panels;
    }
    
    public static Panel getPanelByName(final String name) {
        Panel panel = null;
        for (final Panel p : getPanels()) {
            if (!p.title.equalsIgnoreCase(name)) {
                continue;
            }
            panel = p;
        }
        return panel;
    }
    
    public boolean doesGuiPauseGame() {
        return Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIPauseGame").get_value(true);
    }
}
