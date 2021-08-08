//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.MobEffects;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusWeaknessAlert extends WurstplusHack
{
    public WurstplusSetting sound;
    private boolean hasAnnounced;
    
    public WurstplusWeaknessAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.sound = this.create("Sound", "WeaknessAlertSound", true);
        this.hasAnnounced = false;
        this.name = "WeaknessAlert";
        this.tag = "WeaknessAlert";
        this.description = "Notifies you if you get weakness";
    }
    
    @Override
    public void update() {
        if (nullCheck()) {
            return;
        }
        if (WurstplusWeaknessAlert.mc.player.isPotionActive(MobEffects.WEAKNESS) && !this.hasAnnounced) {
            this.hasAnnounced = true;
            WurstplusMessageUtil.send_client_message(ChatFormatting.AQUA + WurstplusWeaknessAlert.mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You now have " + ChatFormatting.RED + "weakness" + ChatFormatting.GRAY + "!");
            if (this.sound.get_value(true)) {
                WurstplusWeaknessAlert.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
            }
        }
        else if (!WurstplusWeaknessAlert.mc.player.isPotionActive(MobEffects.WEAKNESS) && this.hasAnnounced) {
            this.hasAnnounced = false;
            WurstplusMessageUtil.send_client_message(ChatFormatting.AQUA + WurstplusWeaknessAlert.mc.getSession().getUsername() + ChatFormatting.GRAY + " - " + ChatFormatting.WHITE + "You no longer have " + ChatFormatting.RED + "weakness" + ChatFormatting.GRAY + "!");
            if (this.sound.get_value(true)) {
                WurstplusWeaknessAlert.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
            }
        }
    }
}
