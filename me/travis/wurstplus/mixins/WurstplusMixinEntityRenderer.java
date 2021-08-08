// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSetupFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public class WurstplusMixinEntityRenderer
{
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo p_Info) {
        final WurstplusEventSetupFog event = new WurstplusEventSetupFog(startCoords, partialTicks);
        WurstplusEventBus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            return;
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("NoRender").is_active() && Wurstplus.get_setting_manager().get_setting_with_tag("NoRender", "HurtCam").get_value(true)) {
            info.cancel();
        }
    }
}
