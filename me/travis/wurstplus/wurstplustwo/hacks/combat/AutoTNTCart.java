//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.other.Badlion.ColorUtils;
import me.travis.other.Badlion.BadlionTessellator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import me.zero.alpine.fork.listener.Listenable;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoTNTCart extends WurstplusHack
{
    private boolean firstSwap;
    private boolean secondSwap;
    private boolean beginPlacing;
    private int lighterSlot;
    private EntityPlayer closestTarget;
    private BlockPos targetPos;
    WurstplusSetting announceUsage;
    WurstplusSetting debug;
    WurstplusSetting carts;
    
    public AutoTNTCart() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.firstSwap = true;
        this.secondSwap = true;
        this.beginPlacing = false;
        this.announceUsage = this.create("Announce Usage", "Announce Usage", true);
        this.debug = this.create("Debug Mode", "Debug Mode", false);
        this.carts = this.create("Place Duration ", "CartsToPlace", 60.0, 1.0, 100.0);
        this.name = "AutoTNTCart";
        this.tag = "AutoTNTCart";
        this.description = "MinecrartTNT = Kaboom";
    }
    
    @Override
    protected void enable() {
        if (AutoTNTCart.mc.player != null && AutoTNTCart.mc.world != null) {
            WurstplusEventBus.EVENT_BUS.subscribe(this);
            MinecraftForge.EVENT_BUS.register((Object)this);
            this.tickDelay = 0;
            try {
                this.findClosestTarget();
            }
            catch (Exception ex) {}
            if (this.closestTarget != null) {
                if (this.announceUsage.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("Attempting to TNT cart " + ChatFormatting.BLUE.toString() + this.closestTarget.getName() + ChatFormatting.WHITE.toString() + " ...");
                }
                this.targetPos = new BlockPos(this.closestTarget.getPositionVector());
            }
            else {
                if (this.announceUsage.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("No target within range to TNT Cart.");
                }
                this.toggle();
            }
        }
        else {
            this.toggle();
        }
    }
    
    @Override
    protected void disable() {
        if (AutoTNTCart.mc.player != null && AutoTNTCart.mc.world != null) {
            if (this.announceUsage.get_value(true)) {
                WurstplusMessageUtil.client_message_simple(TextFormatting.BLUE + "[" + TextFormatting.GOLD + "AutoTNTCart" + TextFormatting.BLUE + "]" + ChatFormatting.RED.toString() + " Disabled!");
            }
            this.firstSwap = true;
            this.secondSwap = true;
            this.beginPlacing = false;
            this.tickDelay = 0;
            WurstplusEventBus.EVENT_BUS.unsubscribe(this);
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
    
    @Override
    public void update() {
        if (AutoTNTCart.mc.player != null && AutoTNTCart.mc.world != null) {
            final int tntSlot = this.findTNTCart();
            final int railSlot = this.findRail();
            if (railSlot > -1 && this.firstSwap) {
                AutoTNTCart.mc.player.inventory.currentItem = railSlot;
                this.firstSwap = false;
                this.placeBlock(this.targetPos, EnumFacing.DOWN);
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("Place Rail");
                }
            }
            if (tntSlot > -1 && this.secondSwap && !this.firstSwap) {
                AutoTNTCart.mc.player.inventory.currentItem = tntSlot;
                this.secondSwap = false;
                this.beginPlacing = true;
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("Swap Tnt & Place");
                }
            }
            if (!this.firstSwap && !this.secondSwap && this.beginPlacing) {
                if (this.tickDelay > 0) {
                    AutoTNTCart.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AutoTNTCart.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
                if (this.tickDelay == this.carts.get_value(60.0)) {
                    final int flint = this.findPick();
                    if (flint > -1) {
                        AutoTNTCart.mc.player.inventory.currentItem = flint;
                    }
                    AutoTNTCart.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AutoTNTCart.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, EnumFacing.DOWN));
                    AutoTNTCart.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetPos, EnumFacing.DOWN));
                    if (this.debug.get_value(true)) {
                        WurstplusMessageUtil.send_client_message("Break Rail");
                    }
                }
                if (this.tickDelay == this.carts.get_value(60.0) + 5.0) {
                    final int flint = this.findFlint();
                    if (flint > -1) {
                        AutoTNTCart.mc.player.inventory.currentItem = flint;
                        this.placeBlock(this.targetPos, EnumFacing.DOWN);
                    }
                    else {
                        this.invGrabFlint();
                        AutoTNTCart.mc.player.inventory.currentItem = 0;
                        this.placeBlock(this.targetPos, EnumFacing.DOWN);
                    }
                    this.toggle();
                }
            }
        }
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)AutoTNTCart.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target != AutoTNTCart.mc.player && !WurstplusFriendUtil.isFriend(target.getName()) && isLiving((Entity)target) && target.getHealth() > 0.0f && AutoTNTCart.mc.player.getDistance((Entity)target) <= 6.0f && this.closestTarget == null) {
                this.closestTarget = target;
            }
        }
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    private int findTNTCart() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTNTCart.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && !(stack.getItem() instanceof ItemBlock)) {
                final Item item = stack.getItem();
                if (item instanceof ItemMinecart) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private int findRail() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTNTCart.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockRailBase) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private int findFlint() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTNTCart.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && !(stack.getItem() instanceof ItemBlock)) {
                final Item item = stack.getItem();
                if (item instanceof ItemFlintAndSteel) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private void invGrabFlint() {
        if ((AutoTNTCart.mc.currentScreen == null || !(AutoTNTCart.mc.currentScreen instanceof GuiContainer)) && AutoTNTCart.mc.player.inventory.getStackInSlot(0).getItem() != Items.FLINT_AND_STEEL) {
            for (int i = 9; i < 36; ++i) {
                if (AutoTNTCart.mc.player.inventory.getStackInSlot(i).getItem() == Items.FLINT_AND_STEEL) {
                    AutoTNTCart.mc.playerController.windowClick(AutoTNTCart.mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, (EntityPlayer)AutoTNTCart.mc.player);
                    this.lighterSlot = i;
                    break;
                }
            }
        }
    }
    
    private int findPick() {
        int pickSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTNTCart.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemPickaxe) {
                pickSlot = i;
                break;
            }
        }
        return pickSlot;
    }
    
    private void placeBlock(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        AutoTNTCart.mc.playerController.processRightClickBlock(AutoTNTCart.mc.player, AutoTNTCart.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoTNTCart.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    @Override
    public void render() {
        if (AutoTNTCart.mc.world != null && this.targetPos != null) {
            try {
                final float posx = (float)this.targetPos.getX();
                final float posy = (float)this.targetPos.getY();
                final float posz = (float)this.targetPos.getZ();
                BadlionTessellator.prepare("lines");
                BadlionTessellator.draw_cube_line_full(posx, posy, posz, ColorUtils.GenRainbow(), "all");
            }
            catch (Exception ex) {}
            BadlionTessellator.release();
        }
    }
}
