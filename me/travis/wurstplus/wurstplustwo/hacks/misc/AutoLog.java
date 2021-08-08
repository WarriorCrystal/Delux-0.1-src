//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoLog extends WurstplusHack
{
    WurstplusSetting health;
    
    public AutoLog() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.health = this.create("Health", "LogHealth", 7.0, 0.0, 36.0);
        this.name = "Auto Log";
        this.tag = "AutoLog";
        this.description = "automatically logs";
    }
    
    @Override
    public void update() {
        if (nullCheck()) {
            return;
        }
        if (AutoLog.mc.player.getHealth() <= this.health.get_value(1)) {
            AutoLog.mc.world.sendQuittingDisconnectingPacket();
            AutoLog.mc.loadWorld((WorldClient)null);
            AutoLog.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
    }
}
