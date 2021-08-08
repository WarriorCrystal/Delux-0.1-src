//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.init.Blocks;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstPlusAnchor extends WurstplusHack
{
    WurstplusSetting Pitch;
    WurstplusSetting Pull;
    private final ArrayList<BlockPos> holes;
    int holeblocks;
    public static boolean AnchorING;
    private Vec3d Center;
    @EventHandler
    private Listener<WurstplusEventMotionUpdate> OnClientTick;
    
    public WurstPlusAnchor() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.Pitch = this.create("Pitch", "AnchorPitch", 60, 0, 90);
        this.Pull = this.create("Pull", "AnchorPull", true);
        this.holes = new ArrayList<BlockPos>();
        this.Center = Vec3d.ZERO;
        double XDiff;
        double ZDiff;
        double MotionX;
        double MotionZ;
        this.OnClientTick = new Listener<WurstplusEventMotionUpdate>(event -> {
            if (WurstPlusAnchor.mc.player.rotationPitch >= this.Pitch.get_value(60)) {
                if (this.isBlockHole(this.getPlayerPos().down(1)) || this.isBlockHole(this.getPlayerPos().down(2)) || this.isBlockHole(this.getPlayerPos().down(3)) || this.isBlockHole(this.getPlayerPos().down(4))) {
                    WurstPlusAnchor.AnchorING = true;
                    if (!this.Pull.get_value(true)) {
                        WurstPlusAnchor.mc.player.motionX = 0.0;
                        WurstPlusAnchor.mc.player.motionZ = 0.0;
                    }
                    else {
                        this.Center = this.GetCenter(WurstPlusAnchor.mc.player.posX, WurstPlusAnchor.mc.player.posY, WurstPlusAnchor.mc.player.posZ);
                        XDiff = Math.abs(this.Center.x - WurstPlusAnchor.mc.player.posX);
                        ZDiff = Math.abs(this.Center.z - WurstPlusAnchor.mc.player.posZ);
                        if (XDiff <= 0.1 && ZDiff <= 0.1) {
                            this.Center = Vec3d.ZERO;
                        }
                        else {
                            MotionX = this.Center.x - WurstPlusAnchor.mc.player.posX;
                            MotionZ = this.Center.z - WurstPlusAnchor.mc.player.posZ;
                            WurstPlusAnchor.mc.player.motionX = MotionX / 2.0;
                            WurstPlusAnchor.mc.player.motionZ = MotionZ / 2.0;
                        }
                    }
                }
                else {
                    WurstPlusAnchor.AnchorING = false;
                }
            }
            return;
        }, (Predicate<WurstplusEventMotionUpdate>[])new Predicate[0]);
        this.name = "Anchor";
        this.tag = "WurstPlusAnchor";
        this.description = "Stops all movement if player is above a hole";
    }
    
    public boolean isBlockHole(final BlockPos blockpos) {
        this.holeblocks = 0;
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 0, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || WurstPlusAnchor.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || WurstPlusAnchor.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || WurstPlusAnchor.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        return this.holeblocks >= 9;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public void onDisable() {
        WurstPlusAnchor.AnchorING = false;
        this.holeblocks = 0;
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(WurstPlusAnchor.mc.player.posX), Math.floor(WurstPlusAnchor.mc.player.posY), Math.floor(WurstPlusAnchor.mc.player.posZ));
    }
}
