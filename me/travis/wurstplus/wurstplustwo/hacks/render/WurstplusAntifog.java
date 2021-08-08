//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import net.minecraft.client.renderer.GlStateManager;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSetupFog;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAntifog extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventSetupFog> setup_fog;
    
    public WurstplusAntifog() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.setup_fog = new Listener<WurstplusEventSetupFog>(event -> {
            event.cancel();
            WurstplusAntifog.mc.entityRenderer.setupFogColor(false);
            GlStateManager.glNormal3f(0.0f, -1.0f, 0.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.colorMaterial(1028, 4608);
            return;
        }, (Predicate<WurstplusEventSetupFog>[])new Predicate[0]);
        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }
}
