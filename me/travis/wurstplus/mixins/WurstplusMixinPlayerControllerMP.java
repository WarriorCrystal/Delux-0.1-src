//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventBlock;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventDamageBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PlayerControllerMP.class })
public class WurstplusMixinPlayerControllerMP
{
    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    private float onPlayerDamageBlockSpeed(final IBlockState state, final EntityPlayer player, final World world, final BlockPos pos) {
        return state.getPlayerRelativeBlockHardness(player, world, pos) * (Wurstplus.get_event_handler().get_tick_rate() / 20.0f);
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> info) {
        final WurstplusEventDamageBlock event_packet = new WurstplusEventDamageBlock(posBlock, directionFacing);
        WurstplusEventBus.EVENT_BUS.post(event_packet);
        if (event_packet.isCancelled()) {
            info.setReturnValue(false);
            info.cancel();
        }
        final WurstplusEventBlock event = new WurstplusEventBlock(4, posBlock, directionFacing);
        WurstplusEventBus.EVENT_BUS.post(event);
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final WurstplusEventBlock event = new WurstplusEventBlock(3, pos, face);
        WurstplusEventBus.EVENT_BUS.post(event);
    }
    
    @Inject(method = { "getBlockReachDistance" }, at = { @At("RETURN") }, cancellable = true)
    private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("Reach").is_active()) {
            distance.setReturnValue(Float.valueOf(Wurstplus.get_setting_manager().get_setting_with_tag("Reach", "Reach").get_value(0)));
        }
    }
}
