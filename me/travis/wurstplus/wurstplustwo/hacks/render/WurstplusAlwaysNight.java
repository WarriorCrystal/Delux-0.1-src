//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAlwaysNight extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventRender> on_render;
    
    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.on_render = new Listener<WurstplusEventRender>(event -> {
            if (WurstplusAlwaysNight.mc.world == null) {
                return;
            }
            else {
                WurstplusAlwaysNight.mc.world.setWorldTime(18000L);
                return;
            }
        }, (Predicate<WurstplusEventRender>[])new Predicate[0]);
        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }
    
    @Override
    public void update() {
        if (WurstplusAlwaysNight.mc.world == null) {
            return;
        }
        WurstplusAlwaysNight.mc.world.setWorldTime(18000L);
    }
}
