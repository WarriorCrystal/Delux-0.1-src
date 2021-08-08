//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.travis.wurstplus.Wurstplus;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketUseBed;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.Vec3d;
import java.util.Map;
import java.util.Queue;
import java.text.DecimalFormat;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAnnouncer extends WurstplusHack
{
    WurstplusSetting min_distance;
    WurstplusSetting max_distance;
    WurstplusSetting delay;
    WurstplusSetting queue_size;
    WurstplusSetting units;
    WurstplusSetting movement_string;
    WurstplusSetting suffix;
    WurstplusSetting smol;
    private static DecimalFormat df;
    private static final Queue<String> message_q;
    private static final Map<String, Integer> mined_blocks;
    private static final Map<String, Integer> placed_blocks;
    private static final Map<String, Integer> dropped_items;
    private static final Map<String, Integer> consumed_items;
    private boolean first_run;
    private static Vec3d thisTickPos;
    private static Vec3d lastTickPos;
    private static int delay_count;
    private static double distanceTraveled;
    private static float thisTickHealth;
    private static float lastTickHealth;
    private static float gainedHealth;
    private static float lostHealth;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_listener;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    public WurstplusAnnouncer() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.min_distance = this.create("Min Distance", "AnnouncerMinDist", 12, 1, 100);
        this.max_distance = this.create("Max Distance", "AnnouncerMaxDist", 144, 12, 1200);
        this.delay = this.create("Delay Seconds", "AnnouncerDelay", 4, 0, 20);
        this.queue_size = this.create("Queue Size", "AnnouncerQueueSize", 5, 1, 20);
        this.units = this.create("Units", "AnnouncerUnits", "Meters", this.combobox("Meters", "Feet", "Yards", "Inches"));
        this.movement_string = this.create("Movement", "AnnouncerMovement", "Aha x", this.combobox("Aha x", "Leyta", "FUCK"));
        this.suffix = this.create("Suffix", "AnnouncerSuffix", true);
        this.smol = this.create("Small Text", "AnnouncerSmallText", false);
        this.receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (WurstplusAnnouncer.mc.world == null) {
                return;
            }
            else {
                if (event.get_packet() instanceof SPacketUseBed) {
                    this.queue_message("I am going to bed now, goodnight");
                }
                return;
            }
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        CPacketPlayerDigging packet;
        String name;
        String name2;
        ItemStack stack;
        String name3;
        String name4;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (WurstplusAnnouncer.mc.world == null) {
                return;
            }
            else {
                if (event.get_packet() instanceof CPacketPlayerDigging) {
                    packet = (CPacketPlayerDigging)event.get_packet();
                    if (WurstplusAnnouncer.mc.player.getHeldItemMainhand().getItem() != Items.AIR && (packet.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ITEM) || packet.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ALL_ITEMS))) {
                        name = WurstplusAnnouncer.mc.player.inventory.getCurrentItem().getDisplayName();
                        if (WurstplusAnnouncer.dropped_items.containsKey(name)) {
                            WurstplusAnnouncer.dropped_items.put(name, WurstplusAnnouncer.dropped_items.get(name) + 1);
                        }
                        else {
                            WurstplusAnnouncer.dropped_items.put(name, 1);
                        }
                    }
                    if (packet.getAction().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                        name2 = WurstplusAnnouncer.mc.world.getBlockState(packet.getPosition()).getBlock().getLocalizedName();
                        if (WurstplusAnnouncer.mined_blocks.containsKey(name2)) {
                            WurstplusAnnouncer.mined_blocks.put(name2, WurstplusAnnouncer.mined_blocks.get(name2) + 1);
                        }
                        else {
                            WurstplusAnnouncer.mined_blocks.put(name2, 1);
                        }
                    }
                }
                else {
                    if (event.get_packet() instanceof CPacketUpdateSign) {
                        this.queue_message("I just updated a sign with some epic text");
                    }
                    if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
                        stack = WurstplusAnnouncer.mc.player.inventory.getCurrentItem();
                        if (!stack.isEmpty()) {
                            if (stack.getItem() instanceof ItemBlock) {
                                name3 = WurstplusAnnouncer.mc.player.inventory.getCurrentItem().getDisplayName();
                                if (WurstplusAnnouncer.placed_blocks.containsKey(name3)) {
                                    WurstplusAnnouncer.placed_blocks.put(name3, WurstplusAnnouncer.placed_blocks.get(name3) + 1);
                                }
                                else {
                                    WurstplusAnnouncer.placed_blocks.put(name3, 1);
                                }
                            }
                            else if (stack.getItem() == Items.END_CRYSTAL) {
                                name4 = "Crystals";
                                if (WurstplusAnnouncer.placed_blocks.containsKey(name4)) {
                                    WurstplusAnnouncer.placed_blocks.put(name4, WurstplusAnnouncer.placed_blocks.get(name4) + 1);
                                }
                                else {
                                    WurstplusAnnouncer.placed_blocks.put(name4, 1);
                                }
                            }
                        }
                    }
                }
                return;
            }
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "Announcer";
        this.tag = "Announcer";
        this.description = "how to get muted 101";
    }
    
    @Override
    public void update() {
        if (WurstplusAnnouncer.mc.player == null || WurstplusAnnouncer.mc.world == null) {
            this.set_disable();
            return;
        }
        try {
            this.get_tick_data();
        }
        catch (Exception ignored) {
            this.set_disable();
            return;
        }
        this.send_cycle();
    }
    
    private void get_tick_data() {
        WurstplusAnnouncer.lastTickPos = WurstplusAnnouncer.thisTickPos;
        WurstplusAnnouncer.thisTickPos = WurstplusAnnouncer.mc.player.getPositionVector();
        WurstplusAnnouncer.distanceTraveled += WurstplusAnnouncer.thisTickPos.distanceTo(WurstplusAnnouncer.lastTickPos);
        WurstplusAnnouncer.lastTickHealth = WurstplusAnnouncer.thisTickHealth;
        WurstplusAnnouncer.thisTickHealth = WurstplusAnnouncer.mc.player.getHealth() + WurstplusAnnouncer.mc.player.getAbsorptionAmount();
        final float healthDiff = WurstplusAnnouncer.thisTickHealth - WurstplusAnnouncer.lastTickHealth;
        if (healthDiff < 0.0f) {
            WurstplusAnnouncer.lostHealth += healthDiff * -1.0f;
        }
        else {
            WurstplusAnnouncer.gainedHealth += healthDiff;
        }
    }
    
    public void send_cycle() {
        ++WurstplusAnnouncer.delay_count;
        if (WurstplusAnnouncer.delay_count > this.delay.get_value(1) * 20) {
            WurstplusAnnouncer.delay_count = 0;
            this.composeGameTickData();
            this.composeEventData();
            final Iterator<String> iterator = WurstplusAnnouncer.message_q.iterator();
            if (iterator.hasNext()) {
                final String message = iterator.next();
                this.send_message(message);
                WurstplusAnnouncer.message_q.remove(message);
            }
        }
    }
    
    private void send_message(String s) {
        if (this.suffix.get_value(true)) {
            final String i = " \u2763 ";
            s = s + i + Wurstplus.smoth("sponsored by wurstplus two");
        }
        if (this.smol.get_value(true)) {
            s = Wurstplus.smoth(s.toLowerCase());
        }
        WurstplusAnnouncer.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(s.replaceAll("§", "")));
    }
    
    public void queue_message(final String m) {
        if (WurstplusAnnouncer.message_q.size() > this.queue_size.get_value(1)) {
            return;
        }
        WurstplusAnnouncer.message_q.add(m);
    }
    
    @Override
    protected void enable() {
        this.first_run = true;
        (WurstplusAnnouncer.df = new DecimalFormat("#.#")).setRoundingMode(RoundingMode.CEILING);
        final Vec3d pos = WurstplusAnnouncer.thisTickPos = (WurstplusAnnouncer.lastTickPos = WurstplusAnnouncer.mc.player.getPositionVector());
        WurstplusAnnouncer.distanceTraveled = 0.0;
        final float health = WurstplusAnnouncer.thisTickHealth = (WurstplusAnnouncer.lastTickHealth = WurstplusAnnouncer.mc.player.getHealth() + WurstplusAnnouncer.mc.player.getAbsorptionAmount());
        WurstplusAnnouncer.lostHealth = 0.0f;
        WurstplusAnnouncer.gainedHealth = 0.0f;
        WurstplusAnnouncer.delay_count = 0;
    }
    
    public static double round(final double unrounded, final int precision) {
        final BigDecimal bd = new BigDecimal(unrounded);
        final BigDecimal rounded = bd.setScale(precision, 4);
        return rounded.doubleValue();
    }
    
    private void composeGameTickData() {
        if (this.first_run) {
            this.first_run = false;
            return;
        }
        if (WurstplusAnnouncer.distanceTraveled >= 1.0) {
            if (WurstplusAnnouncer.distanceTraveled < this.delay.get_value(1) * this.min_distance.get_value(1)) {
                return;
            }
            if (WurstplusAnnouncer.distanceTraveled > this.delay.get_value(1) * this.max_distance.get_value(1)) {
                WurstplusAnnouncer.distanceTraveled = 0.0;
                return;
            }
            final CharSequence sb = new StringBuilder();
            if (this.movement_string.in("Aha x")) {
                ((StringBuilder)sb).append("aha x, I just traveled ");
            }
            if (this.movement_string.in("FUCK")) {
                ((StringBuilder)sb).append("FUCK, I just fucking traveled ");
            }
            if (this.movement_string.in("Leyta")) {
                ((StringBuilder)sb).append("leyta bitch, I just traveled ");
            }
            if (this.units.in("Feet")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 3.2808, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Foot");
                }
                else {
                    ((StringBuilder)sb).append(" Feet");
                }
            }
            if (this.units.in("Yards")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 1.0936, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Yard");
                }
                else {
                    ((StringBuilder)sb).append(" Yards");
                }
            }
            if (this.units.in("Inches")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 39.37, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Inch");
                }
                else {
                    ((StringBuilder)sb).append(" Inches");
                }
            }
            if (this.units.in("Meters")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Meter");
                }
                else {
                    ((StringBuilder)sb).append(" Meters");
                }
            }
            this.queue_message(sb.toString());
            WurstplusAnnouncer.distanceTraveled = 0.0;
        }
        if (WurstplusAnnouncer.lostHealth != 0.0f) {
            final CharSequence sb = "HECK! I just lost " + WurstplusAnnouncer.df.format(WurstplusAnnouncer.lostHealth) + " health D:";
            this.queue_message((String)sb);
            WurstplusAnnouncer.lostHealth = 0.0f;
        }
        if (WurstplusAnnouncer.gainedHealth != 0.0f) {
            final CharSequence sb = "#ezmode I now have " + WurstplusAnnouncer.df.format(WurstplusAnnouncer.gainedHealth) + " more health";
            this.queue_message((String)sb);
            WurstplusAnnouncer.gainedHealth = 0.0f;
        }
    }
    
    private void composeEventData() {
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.mined_blocks.entrySet()) {
            this.queue_message("We be mining " + kv.getValue() + " " + kv.getKey() + " out here");
            WurstplusAnnouncer.mined_blocks.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.placed_blocks.entrySet()) {
            this.queue_message("We be placing " + kv.getValue() + " " + kv.getKey() + " out here");
            WurstplusAnnouncer.placed_blocks.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.dropped_items.entrySet()) {
            this.queue_message("I just dropped " + kv.getValue() + " " + kv.getKey() + ", whoops!");
            WurstplusAnnouncer.dropped_items.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.consumed_items.entrySet()) {
            this.queue_message("NOM NOM, I just ate " + kv.getValue() + " " + kv.getKey() + ", yummy");
            WurstplusAnnouncer.consumed_items.remove(kv.getKey());
        }
    }
    
    static {
        WurstplusAnnouncer.df = new DecimalFormat();
        message_q = new ConcurrentLinkedQueue<String>();
        mined_blocks = new ConcurrentHashMap<String, Integer>();
        placed_blocks = new ConcurrentHashMap<String, Integer>();
        dropped_items = new ConcurrentHashMap<String, Integer>();
        consumed_items = new ConcurrentHashMap<String, Integer>();
    }
}
