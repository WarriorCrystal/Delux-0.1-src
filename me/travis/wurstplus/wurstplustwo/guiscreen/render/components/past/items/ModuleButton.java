//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.items;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.client.Minecraft;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.font.FontUtil;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.Panel;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.past.Component;

public class ModuleButton extends Component
{
    private ArrayList<Component> subcomponents;
    public WurstplusHack mod;
    public Panel parent;
    public int offset;
    private boolean open;
    private boolean hovered;
    
    public ModuleButton(final WurstplusHack mod, final Panel parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        int opY = offset + 15;
        for (final WurstplusSetting settings : Wurstplus.get_setting_manager().get_settings_with_hack(mod)) {
            if (settings.get_type().equals("button")) {
                this.subcomponents.add(new BooleanComponent(settings, this, opY));
                opY += 15;
            }
            else if (settings.get_type().equals("integerslider")) {
                this.subcomponents.add(new IntegerComponent(settings, this, opY));
                opY += 15;
            }
            else if (settings.get_type().equals("doubleslider")) {
                this.subcomponents.add(new DoubleComponent(settings, this, opY));
                opY += 15;
            }
            else {
                if (!settings.get_type().equals("combobox")) {
                    continue;
                }
                this.subcomponents.add(new ModeComponent(settings, this, opY));
                opY += 15;
            }
        }
        this.subcomponents.add(new KeybindComponent(this, opY));
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 15;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 15;
        }
    }
    
    @Override
    public void renderComponent() {
        if (this.mod.is_active()) {
            Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -13684945);
        }
        else {
            Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -14606047);
        }
        if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUIHoverChange").get_value(true) && this.hovered) {
            Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 15 + this.offset, -13684945);
            FontUtil.drawText(this.mod.get_name(), (float)(this.parent.getX() + 4), (float)(this.parent.getY() + this.offset + 4), -1);
        }
        else {
            FontUtil.drawText(this.mod.get_name(), (float)(this.parent.getX() + 4), (float)(this.parent.getY() + this.offset + 4), -1);
        }
        if (!this.isOpen()) {
            FontUtil.drawText("...", (float)(this.parent.getX() + this.parent.getWidth() - 10), (float)(this.parent.getY() + this.offset + 2), -1);
        }
        else if (this.isOpen()) {
            FontUtil.drawText(" ", (float)(this.parent.getX() + this.parent.getWidth() - 10), (float)(this.parent.getY() + this.offset + 2), -1);
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }
    
    @Override
    public void closeAllSub() {
        this.open = false;
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 15 * (this.subcomponents.size() + 1);
        }
        return 15;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
            if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUISound").get_value(true)) {
                Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (Wurstplus.get_setting_manager().get_setting_with_tag("ClickGUI", "PastGUISound").get_value(true)) {
                Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            if (!this.isOpen()) {
                this.parent.closeAllSetting();
                this.setOpen(true);
            }
            else {
                this.setOpen(false);
            }
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + 100 && y > this.parent.getY() + this.offset && y < this.parent.getY() + 15 + this.offset;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
}
