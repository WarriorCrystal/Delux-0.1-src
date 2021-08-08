//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.dev;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusFakePlayer extends WurstplusHack
{
    private EntityOtherPlayerMP fake_player;
    
    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Fake Player";
        this.tag = "FakePlayer";
        this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }
    
    @Override
    protected void enable() {
        (this.fake_player = new EntityOtherPlayerMP((World)WurstplusFakePlayer.mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "FitMC"))).copyLocationAndAnglesFrom((Entity)WurstplusFakePlayer.mc.player);
        this.fake_player.rotationYawHead = WurstplusFakePlayer.mc.player.rotationYawHead;
        WurstplusFakePlayer.mc.world.addEntityToWorld(-100, (Entity)this.fake_player);
    }
    
    @Override
    protected void disable() {
        try {
            WurstplusFakePlayer.mc.world.removeEntity((Entity)this.fake_player);
        }
        catch (Exception ex) {}
    }
}
