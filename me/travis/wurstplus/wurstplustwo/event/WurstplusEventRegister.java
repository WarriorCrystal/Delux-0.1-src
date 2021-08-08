// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event;

import me.travis.wurstplus.wurstplustwo.manager.WurstplusEventManager;
import net.minecraftforge.common.MinecraftForge;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;

public class WurstplusEventRegister
{
    public static void register_command_manager(final WurstplusCommandManager manager) {
        MinecraftForge.EVENT_BUS.register((Object)manager);
    }
    
    public static void register_module_manager(final WurstplusEventManager manager) {
        MinecraftForge.EVENT_BUS.register((Object)manager);
    }
}
