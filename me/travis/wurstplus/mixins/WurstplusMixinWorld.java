// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import net.minecraft.entity.item.EntityFireworkRocket;
import me.travis.wurstplus.Wurstplus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntityRemoved;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ World.class })
public class WurstplusMixinWorld
{
    @Inject(method = { "onEntityRemoved" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityRemoved(final Entity event_packet, final CallbackInfo p_Info) {
        final WurstplusEventEntityRemoved l_Event = new WurstplusEventEntityRemoved(event_packet);
        WurstplusEventBus.EVENT_BUS.post(l_Event);
    }
    
    @Inject(method = { "spawnEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void spawnEntity(final Entity entityIn, final CallbackInfoReturnable<Boolean> cir) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("NoRender").is_active() && Wurstplus.get_setting_manager().get_setting_with_tag("NoRender", "FireWorks").get_value(true) && entityIn instanceof EntityFireworkRocket) {
            cir.cancel();
        }
    }
    
    @Inject(method = { "updateEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void updateEntity(final Entity ent, final CallbackInfo ci) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("NoRender").is_active() && Wurstplus.get_setting_manager().get_setting_with_tag("NoRender", "FireWorks").get_value(true) && ent instanceof EntityFireworkRocket) {
            ci.cancel();
        }
    }
}
