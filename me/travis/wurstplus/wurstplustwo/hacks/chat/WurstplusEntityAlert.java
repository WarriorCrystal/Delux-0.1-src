//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Iterator;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusEntityAlert extends WurstplusHack
{
    WurstplusSetting donkey;
    WurstplusSetting mule;
    WurstplusSetting llama;
    WurstplusSetting sound;
    
    public WurstplusEntityAlert() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.donkey = this.create("Donkey", "Donkey", true);
        this.mule = this.create("Mule", "Mule", true);
        this.llama = this.create("Llama", "Llama", true);
        this.sound = this.create("Sound", "Sound", true);
        this.name = "Entity Alert";
        this.tag = "EntityAlert";
        this.description = "Notifys entities";
    }
    
    @Override
    public void update() {
        if (!WurstplusHack.nullCheck()) {
            for (final Entity entity : WurstplusEntityAlert.mc.world.getLoadedEntityList()) {
                if (entity instanceof EntityDonkey && this.donkey.get_value(true)) {
                    WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "donkey " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (this.sound.get_value(true)) {
                        WurstplusEntityAlert.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f));
                        this.toggle();
                    }
                }
                if (entity instanceof EntityMule && this.mule.get_value(true)) {
                    WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "llama " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (!this.sound.get_value(true)) {
                        continue;
                    }
                    WurstplusEntityAlert.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f));
                    this.toggle();
                }
                else {
                    if (!(entity instanceof EntityLlama) || !this.llama.get_value(true)) {
                        continue;
                    }
                    WurstplusMessageUtil.send_client_message(ChatFormatting.WHITE + "There is a " + ChatFormatting.BLUE + "mule " + ChatFormatting.WHITE + "at " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Math.round(entity.lastTickPosX) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosY) + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + Math.round(entity.lastTickPosZ) + ChatFormatting.GRAY + "]");
                    if (!this.sound.get_value(true)) {
                        continue;
                    }
                    WurstplusEntityAlert.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f));
                    this.toggle();
                }
            }
        }
    }
}
