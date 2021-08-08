//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import java.util.List;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.item.ItemSword;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.EnumHand;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusKillAura extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting player;
    WurstplusSetting hostile;
    WurstplusSetting sword;
    WurstplusSetting sync_tps;
    WurstplusSetting range;
    WurstplusSetting delay;
    boolean start_verify;
    EnumHand actual_hand;
    double tick;
    
    public WurstplusKillAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "KillAuraMode", "A32k", this.combobox("A32k", "Normal"));
        this.player = this.create("Player", "KillAuraPlayer", true);
        this.hostile = this.create("Hostile", "KillAuraHostile", false);
        this.sword = this.create("Sword", "KillAuraSword", true);
        this.sync_tps = this.create("Sync TPS", "KillAuraSyncTps", true);
        this.range = this.create("Range", "KillAuraRange", 5.0, 0.5, 6.0);
        this.delay = this.create("Delay", "KillAuraDelay", 2, 0, 10);
        this.start_verify = true;
        this.actual_hand = EnumHand.MAIN_HAND;
        this.tick = 0.0;
        this.name = "Kill Aura";
        this.tag = "KillAura";
        this.description = "To able hit enemies in a range.";
    }
    
    @Override
    protected void enable() {
        this.tick = 0.0;
    }
    
    @Override
    public void update() {
        if (WurstplusKillAura.mc.player != null && WurstplusKillAura.mc.world != null) {
            ++this.tick;
            if (WurstplusKillAura.mc.player.isDead | WurstplusKillAura.mc.player.getHealth() <= 0.0f) {
                return;
            }
            if (this.mode.in("Normal")) {
                if (!(WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && this.sword.get_value(true)) {
                    this.start_verify = false;
                }
                else if (WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                else if (!this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                final Entity entity = this.find_entity();
                if (entity != null && this.start_verify) {
                    final float tick_to_hit = 20.0f - Wurstplus.get_event_handler().get_tick_rate();
                    final boolean is_possible_attack = WurstplusKillAura.mc.player.getCooledAttackStrength(this.sync_tps.get_value(true) ? (-tick_to_hit) : 0.0f) >= 1.0f;
                    if (is_possible_attack) {
                        this.attack_entity(entity);
                    }
                }
            }
            else {
                if (!(WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                    return;
                }
                if (this.tick < this.delay.get_value(1)) {
                    return;
                }
                this.tick = 0.0;
                final Entity entity = this.find_entity();
                if (entity != null) {
                    this.attack_entity(entity);
                }
            }
        }
    }
    
    public void attack_entity(final Entity entity) {
        if (this.mode.in("A32k")) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = WurstplusKillAura.mc.player.inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY) {
                    if (this.checkSharpness(stack)) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                WurstplusKillAura.mc.player.inventory.currentItem = newSlot;
            }
        }
        final ItemStack off_hand_item = WurstplusKillAura.mc.player.getHeldItemOffhand();
        if (off_hand_item.getItem() == Items.SHIELD) {
            WurstplusKillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, WurstplusKillAura.mc.player.getHorizontalFacing()));
        }
        WurstplusKillAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        WurstplusKillAura.mc.player.swingArm(this.actual_hand);
        WurstplusKillAura.mc.player.resetCooldown();
    }
    
    public Entity find_entity() {
        Entity entity_requested = null;
        for (final Entity player : (List)WurstplusKillAura.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player != null && this.is_compatible(player) && WurstplusKillAura.mc.player.getDistance(player) <= this.range.get_value(1.0)) {
                entity_requested = player;
            }
        }
        return entity_requested;
    }
    
    public boolean is_compatible(final Entity entity) {
        if (this.player.get_value(true) && entity instanceof EntityPlayer && entity != WurstplusKillAura.mc.player && !entity.getName().equals(WurstplusKillAura.mc.player.getName())) {
            return true;
        }
        if (this.hostile.get_value(true) && entity instanceof IMob) {
            return true;
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entity_living_base = (EntityLivingBase)entity;
            if (entity_living_base.getHealth() <= 0.0f) {
                return false;
            }
        }
        return false;
    }
    
    private boolean checkSharpness(final ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl > 5) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
}
