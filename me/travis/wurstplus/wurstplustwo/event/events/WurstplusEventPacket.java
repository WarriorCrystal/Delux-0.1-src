// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.event.events;

import net.minecraft.network.Packet;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventPacket extends WurstplusEventCancellable
{
    private final Packet packet;
    
    public WurstplusEventPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet get_packet() {
        return this.packet;
    }
    
    public static class ReceivePacket extends WurstplusEventPacket
    {
        public ReceivePacket(final Packet packet) {
            super(packet);
        }
    }
    
    public static class SendPacket extends WurstplusEventPacket
    {
        public SendPacket(final Packet packet) {
            super(packet);
        }
    }
}
