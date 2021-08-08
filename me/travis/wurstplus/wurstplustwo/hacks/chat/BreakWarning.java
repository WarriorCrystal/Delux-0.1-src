//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Predicate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import java.util.Objects;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class BreakWarning extends WurstplusHack
{
    WurstplusSetting breakwarner;
    WurstplusSetting distanceToDetect;
    private int delay;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener;
    
    public BreakWarning() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.breakwarner = this.create("BreakWarner", "BreakWarner", true);
        this.distanceToDetect = this.create("Break Distance", "WarnerDistance", 2, 1, 5);
        final EntityPlayerSP player;
        final WorldClient world;
        this.receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            player = BreakWarning.mc.player;
            world = BreakWarning.mc.world;
            if (!Objects.isNull(player) && !Objects.isNull(world) && event.get_packet() instanceof SPacketBlockBreakAnim && this.pastDistance((EntityPlayer)player, ((SPacketBlockBreakAnim)event.get_packet()).getPosition(), this.distanceToDetect.get_value(1))) {
                this.sendChat();
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.name = "BreakWarning";
        this.tag = "BreakWarning";
        this.description = "Warn you when player breaks your surround";
    }
    
    private boolean pastDistance(final EntityPlayer player, final BlockPos pos, final double dist) {
        return player.getDistanceSqToCenter(pos) <= Math.pow(dist, 2.0);
    }
    
    public void sendChat() {
        if (this.breakwarner.get_value(true)) {
            WurstplusMessageUtil.send_client_message(ChatFormatting.GRAY + ">>>" + ChatFormatting.RESET + ChatFormatting.RED + "BREAK WARNING!!!");
        }
        ++this.delay;
    }
}
