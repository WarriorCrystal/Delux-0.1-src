// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.Wurstplus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiBossOverlay.class })
public class PASTMixinGuiBossOverlay
{
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    private void renderBossHealth(final CallbackInfo callbackInfo) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("NoRender").is_active() && Wurstplus.get_setting_manager().get_setting_with_tag("NoRender", "NoBossOverlay").get_value(true)) {
            callbackInfo.cancel();
        }
    }
}
