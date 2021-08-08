//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Items;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusQuiver extends WurstplusHack
{
    WurstplusSetting disable;
    private int random_variation;
    
    public WurstplusQuiver() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.disable = this.create("Toggle", "Toggle", true);
        this.name = "Quiver";
        this.tag = "Quiver";
        this.description = "Shoots good arrows at you";
    }
    
    @Override
    public void update() {
        if (WurstplusQuiver.mc.player.inventory.getCurrentItem().getItem() == Items.BOW) {
            WurstplusQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, -90.0f, true));
            if (WurstplusQuiver.mc.player.getItemInUseMaxCount() >= this.getBowCharge()) {
                WurstplusQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, WurstplusQuiver.mc.player.getHorizontalFacing()));
                WurstplusQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
        }
        if (this.disable.get_value(true)) {
            this.set_disable();
        }
    }
    
    private int getBowCharge() {
        if (this.random_variation == 0) {
            this.random_variation = 2;
        }
        return 3 + this.random_variation;
    }
}
