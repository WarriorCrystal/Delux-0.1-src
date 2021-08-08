// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGUIScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public class WurstplusMixinMinecraft
{
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    private void displayGuiScreen(final GuiScreen guiScreenIn, final CallbackInfo info) {
        final WurstplusEventGUIScreen guiscreen = new WurstplusEventGUIScreen(guiScreenIn);
        WurstplusEventBus.EVENT_BUS.post(guiscreen);
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    private void shutdown(final CallbackInfo info) {
        Wurstplus.get_config_manager().save_settings();
    }
}
