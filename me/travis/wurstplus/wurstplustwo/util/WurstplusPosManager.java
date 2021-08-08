//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;

public class WurstplusPosManager
{
    private static double x;
    private static double y;
    private static double z;
    private static boolean onground;
    private static final Minecraft mc;
    
    public static void updatePosition() {
        WurstplusPosManager.x = WurstplusPosManager.mc.player.posX;
        WurstplusPosManager.y = WurstplusPosManager.mc.player.posY;
        WurstplusPosManager.z = WurstplusPosManager.mc.player.posZ;
        WurstplusPosManager.onground = WurstplusPosManager.mc.player.onGround;
    }
    
    public static void restorePosition() {
        WurstplusPosManager.mc.player.posX = WurstplusPosManager.x;
        WurstplusPosManager.mc.player.posY = WurstplusPosManager.y;
        WurstplusPosManager.mc.player.posZ = WurstplusPosManager.z;
        WurstplusPosManager.mc.player.onGround = WurstplusPosManager.onground;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
