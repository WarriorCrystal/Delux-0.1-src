// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.client.gui.GuiScreen;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventGUIScreen extends WurstplusEventCancellable
{
    private final GuiScreen guiscreen;
    
    public WurstplusEventGUIScreen(final GuiScreen screen) {
        this.guiscreen = screen;
    }
    
    public GuiScreen get_guiscreen() {
        return this.guiscreen;
    }
}
