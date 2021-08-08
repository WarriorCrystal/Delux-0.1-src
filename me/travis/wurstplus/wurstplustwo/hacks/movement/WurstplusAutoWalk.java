//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.client.settings.KeyBinding;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAutoWalk extends WurstplusHack
{
    public WurstplusAutoWalk() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Auto Walk";
        this.tag = "AutoWalk";
        this.description = "automatically walks";
    }
    
    @Override
    public void update() {
        if (!WurstplusHack.nullCheck()) {
            KeyBinding.setKeyBindState(WurstplusAutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }
}
