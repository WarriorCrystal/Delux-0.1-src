//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCapeUtil;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ AbstractClientPlayer.class })
public abstract class WurstplusMixinAbstractClientPlayer
{
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("Capes").is_active()) {
            final NetworkPlayerInfo info = this.getPlayerInfo();
            assert info != null;
            if (!WurstplusCapeUtil.is_uuid_valid(info.getGameProfile().getId())) {
                return;
            }
            ResourceLocation r;
            if (Wurstplus.get_setting_manager().get_setting_with_tag("Capes", "CapeCape").in("OG")) {
                r = new ResourceLocation("custom/cape-old.png");
            }
            else {
                r = new ResourceLocation("custom/cape.png");
            }
            callbackInfoReturnable.setReturnValue(r);
        }
    }
}
