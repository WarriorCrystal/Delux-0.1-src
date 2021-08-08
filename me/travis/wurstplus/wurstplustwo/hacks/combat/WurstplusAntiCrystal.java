//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.entity.item.EntityEnderCrystal;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusAntiCrystal extends WurstplusHack
{
    int index;
    WurstplusSetting switch_mode;
    WurstplusSetting max_self_damage;
    WurstplusSetting required_health;
    WurstplusSetting delay;
    WurstplusSetting range;
    WurstplusSetting rotate;
    
    public WurstplusAntiCrystal() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.switch_mode = this.create("Mode", "Mode", "Normal", this.combobox("Normal", "Ghost", "None"));
        this.max_self_damage = this.create("Max Self Damage", "MaxSelfDamage", 6, 0, 20);
        this.required_health = this.create("Required Health", "RequiredHealth", 12.0, 1.0, 36.0);
        this.delay = this.create("Delay", "Delay", 2, 1, 20);
        this.range = this.create("Range", "Range", 4, 0, 10);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.name = "Anti Crystal";
        this.tag = "AntiCrystal";
        this.description = "Places a pressure plate below crystals to remove crystal damage";
        this.index = 0;
    }
    
    @Override
    public void update() {
        if (this.index > 2000) {
            this.index = 0;
        }
        if (this.find_in_hotbar() == -1) {
            WurstplusMessageUtil.send_client_message("No mats");
            this.set_disable();
        }
        if (WurstplusAntiCrystal.mc.player.getHealth() + WurstplusAntiCrystal.mc.player.getAbsorptionAmount() <= this.required_health.get_value(1)) {
            for (final EntityEnderCrystal cry : this.getExclusions()) {
                if (this.index % this.delay.get_value(1) == 0) {
                    if (this.switch_mode.in("Normal")) {
                        WurstplusAntiCrystal.mc.player.inventory.currentItem = this.find_in_hotbar();
                    }
                    else if (this.switch_mode.in("Ghost")) {
                        WurstplusAntiCrystal.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.find_in_hotbar()));
                    }
                    if (WurstplusAntiCrystal.mc.player.inventory.currentItem == this.find_in_hotbar()) {
                        WurstplusBlockInteractHelper.placeBlock(cry.getPosition(), this.rotate.get_value(true));
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    public ArrayList<EntityEnderCrystal> getCrystals() {
        final ArrayList<EntityEnderCrystal> crystals = new ArrayList<EntityEnderCrystal>();
        for (final Entity ent : WurstplusAntiCrystal.mc.world.getLoadedEntityList()) {
            if (ent instanceof EntityEnderCrystal) {
                crystals.add((EntityEnderCrystal)ent);
            }
        }
        return crystals;
    }
    
    public ArrayList<EntityEnderCrystal> getInRange() {
        final ArrayList<EntityEnderCrystal> inRange = new ArrayList<EntityEnderCrystal>();
        for (final EntityEnderCrystal crystal : this.getCrystals()) {
            if (WurstplusAntiCrystal.mc.player.getDistance((Entity)crystal) <= this.range.get_value(1)) {
                inRange.add(crystal);
            }
        }
        return inRange;
    }
    
    public ArrayList<EntityEnderCrystal> getExclusions() {
        final ArrayList<EntityEnderCrystal> returnOutput = new ArrayList<EntityEnderCrystal>();
        for (final EntityEnderCrystal crystal : this.getInRange()) {
            if (WurstplusAntiCrystal.mc.world.getBlockState(crystal.getPosition()).getBlock() != Blocks.WOODEN_PRESSURE_PLATE && WurstplusAntiCrystal.mc.world.getBlockState(crystal.getPosition()).getBlock() != Blocks.STONE_PRESSURE_PLATE && WurstplusAntiCrystal.mc.world.getBlockState(crystal.getPosition()).getBlock() != Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE && WurstplusAntiCrystal.mc.world.getBlockState(crystal.getPosition()).getBlock() != Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE) {
                final double self_damage = WurstplusCrystalUtil.calculateDamage(crystal, (Entity)WurstplusAntiCrystal.mc.player);
                if (self_damage < this.max_self_damage.get_value(1)) {
                    continue;
                }
                returnOutput.add(crystal);
            }
        }
        return returnOutput;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAntiCrystal.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockPressurePlate) {
                    return i;
                }
            }
        }
        return -1;
    }
}
