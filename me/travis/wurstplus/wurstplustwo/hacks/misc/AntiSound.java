//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import me.zero.alpine.fork.listener.Listenable;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketSoundEffect;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AntiSound extends WurstplusHack
{
    public final WurstplusSetting wither;
    private final WurstplusSetting witherHurt;
    public final WurstplusSetting witherSpawn;
    private final WurstplusSetting witherDeath;
    private final WurstplusSetting punches;
    private final WurstplusSetting punchW;
    private final WurstplusSetting punchKB;
    private final WurstplusSetting explosion;
    public final WurstplusSetting totem;
    public final WurstplusSetting elytra;
    public final WurstplusSetting portal;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_event_packet;
    
    public AntiSound() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.wither = this.create("Wither Ambient", "Wither Ambient", true);
        this.witherHurt = this.create("Wither Hurt", "Wither Hurt", true);
        this.witherSpawn = this.create("Wither Spawn", "Wither Spawn", false);
        this.witherDeath = this.create("Wither Death", "Wither Death", false);
        this.punches = this.create("Punches", "Punches", true);
        this.punchW = this.create("Weak Punch", "Weak Punch", true);
        this.punchKB = this.create("Knockback Punch", "Knockback Punch", true);
        this.explosion = this.create("Explosion", "Explosion", false);
        this.totem = this.create("Totem Pop", "Totem Pop", false);
        this.elytra = this.create("Elytra Wind", "Elytra Wind", true);
        this.portal = this.create("Nether Portal", "Nether Portal", true);
        final SPacketSoundEffect[] packet = { null };
        final Object o;
        this.receive_event_packet = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketSoundEffect) {
                o[0] = (SPacketSoundEffect)event.get_packet();
                if (this.shouldCancelSound(o[0].getSound())) {
                    event.cancel();
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.name = "AntiSound";
        this.tag = "AntiSound";
        this.description = "Blacklists certain annoying sounds";
    }
    
    @Override
    protected void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }
    
    @Override
    protected void disable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
    
    private boolean shouldCancelSound(final SoundEvent soundEvent) {
        return (soundEvent == SoundEvents.ENTITY_WITHER_AMBIENT && this.wither.get_value(true)) || (soundEvent == SoundEvents.ENTITY_WITHER_SPAWN && this.witherSpawn.get_value(false)) || (soundEvent == SoundEvents.ENTITY_WITHER_HURT && this.witherHurt.get_value(true)) || (soundEvent == SoundEvents.ENTITY_WITHER_DEATH && this.witherDeath.get_value(false)) || (soundEvent == SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE && this.punches.get_value(true)) || (soundEvent == SoundEvents.ENTITY_PLAYER_ATTACK_WEAK && this.punchW.get_value(true)) || (soundEvent == SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK && this.punchKB.get_value(true)) || (soundEvent == SoundEvents.ENTITY_GENERIC_EXPLODE && this.explosion.get_value(false));
    }
}
