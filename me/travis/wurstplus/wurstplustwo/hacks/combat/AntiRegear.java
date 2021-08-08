//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockShulkerBox;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Predicate;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AntiRegear extends WurstplusHack
{
    private final WurstplusSetting reach;
    private final WurstplusSetting retry;
    private final List<BlockPos> retries;
    private final List<BlockPos> selfPlaced;
    private int ticks;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    public AntiRegear() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.reach = this.create("Reach", "Reach", 5.0, 1.0, 6.0);
        this.retry = this.create("Retry Delay", "Retry Delay", 10, 0, 20);
        this.retries = new ArrayList<BlockPos>();
        this.selfPlaced = new ArrayList<BlockPos>();
        EntityPlayerSP player;
        CPacketPlayerTryUseItemOnBlock packet;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
                player = AntiRegear.mc.player;
                packet = (CPacketPlayerTryUseItemOnBlock)event.get_packet();
                if (player.getHeldItem(cPacketPlayerTryUseItemOnBlock.getHand()).getItem() instanceof ItemShulkerBox) {
                    this.selfPlaced.add(packet.getPos().offset(packet.getDirection()));
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.name = "AntiRegear";
        this.tag = "AntiRegear";
        this.description = "AntiRegear";
    }
    
    @Override
    public void update() {
        if (this.ticks++ < this.retry.get_value(10)) {
            this.ticks = 0;
            this.retries.clear();
        }
        final List<BlockPos> sphere = WurstplusBlockUtil.getSphere((float)this.reach.get_value(5.0));
        for (int size = sphere.size(), i = 0; i < size; ++i) {
            final BlockPos pos = sphere.get(i);
            if (!this.retries.contains(pos) && !this.selfPlaced.contains(pos)) {
                if (AntiRegear.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox) {
                    AntiRegear.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                    AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
                    this.retries.add(pos);
                }
            }
        }
    }
}
