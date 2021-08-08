//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusBowSpam extends WurstplusHack
{
    public WurstplusBowSpam() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Bow Spam";
        this.tag = "BowSpam";
        this.description = "bow spam go brrrr";
    }
    
    @Override
    public void update() {
        if (WurstplusBowSpam.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && WurstplusBowSpam.mc.player.isHandActive() && WurstplusBowSpam.mc.player.getItemInUseMaxCount() >= 3) {
            WurstplusBowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, WurstplusBowSpam.mc.player.getHorizontalFacing()));
            WurstplusBowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(WurstplusBowSpam.mc.player.getActiveHand()));
            WurstplusBowSpam.mc.player.stopActiveHand();
        }
    }
}
