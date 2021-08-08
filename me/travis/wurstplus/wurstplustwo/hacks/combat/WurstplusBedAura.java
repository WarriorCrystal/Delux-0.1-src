//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusBedAura extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting range;
    WurstplusSetting hard;
    WurstplusSetting swing;
    private BlockPos render_pos;
    private int counter;
    private spoof_face spoof_looking;
    
    public WurstplusBedAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "BedAuraDelay", 6, 0, 20);
        this.range = this.create("Range", "BedAuraRange", 5, 0, 6);
        this.hard = this.create("Hard Rotate", "BedAuraRotate", false);
        this.swing = this.create("Swing", "BedAuraSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
        this.name = "Bed Aura";
        this.tag = "BedAura";
        this.description = "fucking endcrystal.me";
    }
    
    @Override
    protected void enable() {
        this.render_pos = null;
        this.counter = 0;
    }
    
    @Override
    protected void disable() {
        this.render_pos = null;
    }
    
    @Override
    public void update() {
        if (WurstplusBedAura.mc.player == null) {
            return;
        }
        if (this.counter > this.delay.get_value(1)) {
            this.counter = 0;
            this.place_bed();
            this.break_bed();
            this.refill_bed();
        }
        ++this.counter;
    }
    
    public void refill_bed() {
        if (!(WurstplusBedAura.mc.currentScreen instanceof GuiContainer) && this.is_space()) {
            for (int i = 9; i < 35; ++i) {
                if (WurstplusBedAura.mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                    WurstplusBedAura.mc.playerController.windowClick(WurstplusBedAura.mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusBedAura.mc.player);
                    break;
                }
            }
        }
    }
    
    private boolean is_space() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusBedAura.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                return true;
            }
        }
        return false;
    }
    
    public void place_bed() {
        if (this.find_bed() == -1) {
            return;
        }
        final int bed_slot = this.find_bed();
        BlockPos best_pos = null;
        EntityPlayer best_target = null;
        float best_distance = (float)this.range.get_value(1);
        for (final EntityPlayer player : (List)WurstplusBedAura.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player == WurstplusBedAura.mc.player) {
                continue;
            }
            if (best_distance < WurstplusBedAura.mc.player.getDistance((Entity)player)) {
                continue;
            }
            boolean face_place = true;
            final BlockPos pos = get_pos_floor(player).down();
            final BlockPos pos2 = this.check_side_block(pos);
            if (pos2 != null) {
                best_pos = pos2.up();
                best_target = player;
                best_distance = WurstplusBedAura.mc.player.getDistance((Entity)player);
                face_place = false;
            }
            if (!face_place) {
                continue;
            }
            final BlockPos upos = get_pos_floor(player);
            final BlockPos upos2 = this.check_side_block(upos);
            if (upos2 == null) {
                continue;
            }
            best_pos = upos2.up();
            best_target = player;
            best_distance = WurstplusBedAura.mc.player.getDistance((Entity)player);
        }
        if (best_target == null) {
            return;
        }
        this.render_pos = best_pos;
        if (this.spoof_looking == spoof_face.NORTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 180.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        }
        else if (this.spoof_looking == spoof_face.SOUTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 0.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        }
        else if (this.spoof_looking == spoof_face.WEST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 90.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        }
        else if (this.spoof_looking == spoof_face.EAST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = -90.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        }
        WurstplusBlockUtil.placeBlock(best_pos, bed_slot, false, false, this.swing);
    }
    
    public void break_bed() {
        for (final BlockPos pos : WurstplusBlockInteractHelper.getSphere(get_pos_floor((EntityPlayer)WurstplusBedAura.mc.player), (float)this.range.get_value(1), this.range.get_value(1), false, true, 0).stream().filter((Predicate<? super Object>)WurstplusBedAura::is_bed).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())) {
            if (WurstplusBedAura.mc.player.isSneaking()) {
                WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBedAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            WurstplusBlockUtil.openBlock(pos);
        }
    }
    
    public int find_bed() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusBedAura.mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                return i;
            }
        }
        return -1;
    }
    
    public BlockPos check_side_block(final BlockPos pos) {
        if (WurstplusBedAura.mc.world.getBlockState(pos.east()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.east().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.WEST;
            return pos.east();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.north()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.north().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.SOUTH;
            return pos.north();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.west()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.west().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.EAST;
            return pos.west();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.south()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.south().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.NORTH;
            return pos.south();
        }
        return null;
    }
    
    public static BlockPos get_pos_floor(final EntityPlayer player) {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }
    
    public static boolean is_bed(final BlockPos pos) {
        final Block block = WurstplusBedAura.mc.world.getBlockState(pos).getBlock();
        return block == Blocks.BED;
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        if (this.render_pos == null) {
            return;
        }
        RenderHelp.prepare("lines");
        RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)this.render_pos.getX(), (float)this.render_pos.getY(), (float)this.render_pos.getZ(), 1.0f, 0.2f, 1.0f, 255, 20, 20, 180, "all");
        RenderHelp.release();
    }
    
    enum spoof_face
    {
        EAST, 
        WEST, 
        NORTH, 
        SOUTH;
    }
}
