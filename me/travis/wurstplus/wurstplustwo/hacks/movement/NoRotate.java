//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoRotate extends WurstplusHack
{
    private final WurstplusSetting waitDelay;
    private final WurstplusTimer timer;
    private boolean cancelPackets;
    private boolean timerReset;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener;
    
    public NoRotate() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        SPacketPlayerPosLook packet;
        this.receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_partial_ticks() == 0.0f && this.cancelPackets && event.get_packet() instanceof SPacketPlayerPosLook) {
                packet = (SPacketPlayerPosLook)event.get_packet();
                packet.yaw = NoRotate.mc.player.rotationYaw;
                packet.pitch = NoRotate.mc.player.rotationPitch;
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.waitDelay = this.create("Delay", "Delay", 2500, 0, 10000);
        this.timer = new WurstplusTimer();
        this.cancelPackets = true;
        this.timerReset = false;
        this.name = "NoRotate";
        this.tag = "NoRotate";
        this.description = "NoRotate";
    }
    
    @Override
    public void onLogout() {
        this.cancelPackets = false;
    }
    
    public void onLogin() {
        this.timer.reset();
        this.timerReset = true;
    }
    
    @Override
    public void update() {
        if (this.timerReset && !this.cancelPackets && this.timer.passedMs(this.waitDelay.get_value(0))) {
            this.cancelPackets = true;
            this.timerReset = false;
        }
    }
    
    public void enable() {
    }
}
