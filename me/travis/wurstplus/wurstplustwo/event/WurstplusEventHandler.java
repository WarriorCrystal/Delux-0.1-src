//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event;

import java.util.Arrays;
import net.minecraft.util.math.MathHelper;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.zero.alpine.fork.listener.Listenable;

public class WurstplusEventHandler implements Listenable
{
    public static WurstplusEventHandler INSTANCE;
    static final float[] ticks;
    private long last_update_tick;
    private int next_index;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_event_packet;
    
    public WurstplusEventHandler() {
        this.next_index = 0;
        this.receive_event_packet = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketTimeUpdate) {
                WurstplusEventHandler.INSTANCE.update_time();
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        WurstplusEventBus.EVENT_BUS.subscribe(this);
        this.reset_tick();
    }
    
    public float get_tick_rate() {
        float num_ticks = 0.0f;
        float sum_ticks = 0.0f;
        for (final float tick : WurstplusEventHandler.ticks) {
            if (tick > 0.0f) {
                sum_ticks += tick;
                ++num_ticks;
            }
        }
        return MathHelper.clamp(sum_ticks / num_ticks, 0.0f, 20.0f);
    }
    
    public void reset_tick() {
        this.next_index = 0;
        this.last_update_tick = -1L;
        Arrays.fill(WurstplusEventHandler.ticks, 0.0f);
    }
    
    public void update_time() {
        if (this.last_update_tick != -1L) {
            final float time = (System.currentTimeMillis() - this.last_update_tick) / 1000.0f;
            WurstplusEventHandler.ticks[this.next_index % WurstplusEventHandler.ticks.length] = MathHelper.clamp(20.0f / time, 0.0f, 20.0f);
            ++this.next_index;
        }
        this.last_update_tick = System.currentTimeMillis();
    }
    
    static {
        ticks = new float[20];
    }
}
