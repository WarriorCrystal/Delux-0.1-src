// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public class WurstplusMixinNetworkManager
{
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void receive(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callback) {
        final WurstplusEventPacket event_packet = new WurstplusEventPacket.ReceivePacket(packet);
        WurstplusEventBus.EVENT_BUS.post(event_packet);
        if (event_packet.isCancelled()) {
            callback.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void send(final Packet<?> packet, final CallbackInfo callback) {
        final WurstplusEventPacket event_packet = new WurstplusEventPacket.SendPacket(packet);
        WurstplusEventBus.EVENT_BUS.post(event_packet);
        if (event_packet.isCancelled()) {
            callback.cancel();
        }
    }
    
    @Inject(method = { "exceptionCaught" }, at = { @At("HEAD") }, cancellable = true)
    private void exception(final ChannelHandlerContext exc, final Throwable exc_, final CallbackInfo callback) {
        if (exc_ instanceof Exception) {
            callback.cancel();
        }
    }
}
