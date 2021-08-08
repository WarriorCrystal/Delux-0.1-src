//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import me.travis.wurstplus.Wurstplus;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class PacketAutoCity extends WurstplusHack
{
    private boolean firstRun;
    private BlockPos mineTarget;
    private EntityPlayer closestTarget;
    WurstplusSetting range;
    WurstplusSetting announceUsage;
    
    public PacketAutoCity() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mineTarget = null;
        this.name = "PacketAutoCity";
        this.tag = "PacketAutoCity";
        this.description = "PacketAutoCity";
        this.range = this.create("Range", "Range", 7.0, 0.0, 9.0);
        this.announceUsage = this.create("Announce Usage", "Announce Usage", true);
    }
    
    @Override
    protected void enable() {
        if (PacketAutoCity.mc.player == null) {
            this.toggle();
        }
        else {
            MinecraftForge.EVENT_BUS.register((Object)this);
            this.firstRun = true;
        }
    }
    
    @Override
    protected void disable() {
        if (PacketAutoCity.mc.player != null) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            WurstplusMessageUtil.send_client_message(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "AutoCity" + TextFormatting.BLUE + "]" + ChatFormatting.RED.toString() + " Disabled");
        }
    }
    
    @Override
    public void update() {
        if (PacketAutoCity.mc.player != null) {
            this.findClosestTarget();
            if (this.closestTarget == null) {
                if (this.firstRun) {
                    this.firstRun = false;
                    if (this.announceUsage.get_value(true)) {
                        WurstplusMessageUtil.send_client_message(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "AutoCity" + TextFormatting.BLUE + "]" + ChatFormatting.WHITE.toString() + "Enabled" + ChatFormatting.RESET.toString() + ", no one to city!");
                    }
                }
                this.toggle();
            }
            else {
                if (this.firstRun && this.mineTarget != null) {
                    this.firstRun = false;
                    if (this.announceUsage.get_value(true)) {
                        WurstplusMessageUtil.send_client_message(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "AutoCity" + TextFormatting.BLUE + "]" + TextFormatting.WHITE + " Attempting to mine: " + ChatFormatting.BLUE.toString() + this.closestTarget.getName());
                    }
                }
                this.findCityBlock();
                if (this.mineTarget != null) {
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = PacketAutoCity.mc.player.inventory.getStackInSlot(i);
                        if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemPickaxe) {
                            newSlot = i;
                            break;
                        }
                    }
                    if (newSlot != -1) {
                        PacketAutoCity.mc.player.inventory.currentItem = newSlot;
                    }
                    final boolean wasEnabled = Wurstplus.get_hack_manager().get_module_with_tag("PacketMine").is_active();
                    if (!wasEnabled) {
                        Wurstplus.get_hack_manager().get_module_with_tag("PacketMine").is_active();
                    }
                    PacketAutoCity.mc.player.swingArm(EnumHand.MAIN_HAND);
                    PacketAutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.mineTarget, EnumFacing.DOWN));
                    PacketAutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.mineTarget, EnumFacing.DOWN));
                    if (!wasEnabled) {
                        Wurstplus.get_hack_manager().get_module_with_tag("PacketMine").is_active();
                    }
                    this.toggle();
                }
                else {
                    WurstplusMessageUtil.send_client_message(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "AutoCity" + TextFormatting.BLUE + "] No city blocks to mine!");
                    this.toggle();
                }
            }
        }
    }
    
    public BlockPos findCityBlock() {
        final Double dist = this.range.get_value(7.0);
        final Vec3d vec = this.closestTarget.getPositionVector();
        if (PacketAutoCity.mc.player.getPositionVector().distanceTo(vec) <= dist) {
            final BlockPos targetX = new BlockPos(vec.add(1.0, 0.0, 0.0));
            final BlockPos targetXMinus = new BlockPos(vec.add(-1.0, 0.0, 0.0));
            final BlockPos targetZ = new BlockPos(vec.add(0.0, 0.0, 1.0));
            final BlockPos targetZMinus = new BlockPos(vec.add(0.0, 0.0, -1.0));
            if (this.canBreak(targetX)) {
                this.mineTarget = targetX;
            }
            if (!this.canBreak(targetX) && this.canBreak(targetXMinus)) {
                this.mineTarget = targetXMinus;
            }
            if (!this.canBreak(targetX) && !this.canBreak(targetXMinus) && this.canBreak(targetZ)) {
                this.mineTarget = targetZ;
            }
            if (!this.canBreak(targetX) && !this.canBreak(targetXMinus) && !this.canBreak(targetZ) && this.canBreak(targetZMinus)) {
                this.mineTarget = targetZMinus;
            }
            if ((!this.canBreak(targetX) && !this.canBreak(targetXMinus) && !this.canBreak(targetZ) && !this.canBreak(targetZMinus)) || PacketAutoCity.mc.player.getPositionVector().distanceTo(vec) > dist) {
                this.mineTarget = null;
            }
        }
        return this.mineTarget;
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = PacketAutoCity.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)PacketAutoCity.mc.world, pos) != -1.0f;
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PacketAutoCity.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != PacketAutoCity.mc.player && !WurstplusFriendUtil.isFriend(target.getName()) && isLiving((Entity)target) && target.getHealth() > 0.0f) {
                if (this.closestTarget == null) {
                    this.closestTarget = target;
                }
                else {
                    if (PacketAutoCity.mc.player.getDistance((Entity)target) >= PacketAutoCity.mc.player.getDistance((Entity)this.closestTarget)) {
                        continue;
                    }
                    this.closestTarget = target;
                }
            }
        }
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
}
