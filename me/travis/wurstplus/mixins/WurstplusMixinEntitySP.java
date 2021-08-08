//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import me.travis.wurstplus.wurstplustwo.event.events.EventPlayerPushOutOfBlocks;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSwing;
import net.minecraft.util.EnumHand;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.MoverType;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPlayerSP.class })
public class WurstplusMixinEntitySP extends WurstplusMixinEntity
{
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    private void move(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        final WurstplusEventMove event = new WurstplusEventMove(type, x, y, z);
        WurstplusEventBus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            super.move(type, event.get_x(), event.get_y(), event.get_z());
            info.cancel();
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void OnPreUpdateWalkingPlayer(final CallbackInfo p_Info) {
        final WurstplusEventMotionUpdate l_Event = new WurstplusEventMotionUpdate(0);
        WurstplusEventBus.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") }, cancellable = true)
    public void OnPostUpdateWalkingPlayer(final CallbackInfo p_Info) {
        final WurstplusEventMotionUpdate l_Event = new WurstplusEventMotionUpdate(1);
        WurstplusEventBus.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "swingArm" }, at = { @At("RETURN") }, cancellable = true)
    public void swingArm(final EnumHand p_Hand, final CallbackInfo p_Info) {
        final WurstplusEventSwing l_Event = new WurstplusEventSwing(p_Hand);
        WurstplusEventBus.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "pushOutOfBlocks(DDD)Z" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerPushOutOfBlocks l_Event = new EventPlayerPushOutOfBlocks(x, y, z);
        WurstplusEventBus.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfo.setReturnValue(false);
        }
    }
}
