//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.other.Phobos.MathUtil;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketKeepAlive;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import net.minecraft.network.Packet;
import java.util.Queue;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class PingSpoof extends WurstplusHack
{
    private final WurstplusSetting seconds;
    private final WurstplusSetting delay;
    private final WurstplusSetting secondDelay;
    private final WurstplusSetting offOnLogout;
    private final Queue<Packet<?>> packets;
    private final WurstplusTimer timer;
    private boolean receive;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    public PingSpoof() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.seconds = this.create("Seconds", "Seconds", false);
        this.delay = this.create("DelayMS", "DelayMS", 20, 0, 1000);
        this.secondDelay = this.create("DelayS", "DelayS", 5, 0, 30);
        this.offOnLogout = this.create("Logout", "Logout", false);
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
        this.timer = new WurstplusTimer();
        this.receive = true;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (this.receive && PingSpoof.mc.player != null && !PingSpoof.mc.isSingleplayer() && PingSpoof.mc.player.isEntityAlive() && event.get_partial_ticks() == 0.0f && event.get_packet() instanceof CPacketKeepAlive) {
                this.packets.add((Packet<?>)event.get_packet());
                event.isCancelled();
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "PingSpoof";
        this.tag = "PingSpoof";
        this.description = "30k ms go brrrn\u00c2´t";
    }
    
    @Override
    public void onLoad() {
        if (this.offOnLogout.get_value(false)) {
            this.disable();
        }
    }
    
    @Override
    public void onLogout() {
        if (this.offOnLogout.get_value(false)) {
            this.disable();
        }
    }
    
    @Override
    public void update() {
        this.clearQueue();
    }
    
    @Override
    protected void disable() {
        this.clearQueue();
    }
    
    public void clearQueue() {
        if (PingSpoof.mc.player != null && !PingSpoof.mc.isSingleplayer() && PingSpoof.mc.player.isEntityAlive() && ((!this.seconds.get_value(false) && this.timer.passedMs(this.delay.get_value(0))) || (this.seconds.get_value(false) && this.timer.passedS(this.secondDelay.get_value(5))))) {
            final double limit = MathUtil.getIncremental(Math.random() * 10.0, 1.0);
            this.receive = false;
            for (int i = 0; i < limit; ++i) {
                final Packet<?> packet = this.packets.poll();
                if (packet != null) {
                    PingSpoof.mc.player.connection.sendPacket((Packet)packet);
                }
            }
            this.timer.reset();
            this.receive = true;
        }
    }
}
