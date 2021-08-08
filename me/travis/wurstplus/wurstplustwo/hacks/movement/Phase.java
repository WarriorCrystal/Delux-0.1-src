//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraft.util.math.BlockPos;
import me.zero.alpine.fork.listener.Listenable;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import net.minecraft.client.settings.KeyBinding;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Phase extends WurstplusHack
{
    WurstplusSetting debug;
    WurstplusSetting twodelay;
    WurstplusSetting advd;
    WurstplusSetting EnhancedRots;
    WurstplusSetting invert;
    WurstplusSetting SendRotPackets;
    WurstplusSetting twobeepvp;
    WurstplusSetting PUP;
    WurstplusSetting tickDelay;
    WurstplusSetting EnhancedRotsAmount;
    WurstplusSetting speed;
    WurstplusSetting cmode;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receiveListener;
    KeyBinding left;
    KeyBinding right;
    KeyBinding down;
    KeyBinding up;
    long last;
    
    public Phase() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.debug = this.create("Debug", "Debug", false);
        this.twodelay = this.create("2Delay", "2Delay", true);
        this.advd = this.create("AVD", "AVD", false);
        this.EnhancedRots = this.create("EnchancedControl", "EnchancedControl", false);
        this.invert = this.create("InvertedYaw", "InvertedYaw", false);
        this.SendRotPackets = this.create("SendRotPackets", "SendRotPackets", true);
        this.twobeepvp = this.create("2b2tpvp", "2b2tpvp", true);
        this.PUP = this.create("PUP", "PUP", true);
        this.tickDelay = this.create("TickDelay", "TickDelay", 2, 0, 40);
        this.EnhancedRotsAmount = this.create("EnhancedCtrlSpeed", "EnhancedCtrlSpeed", 2, 0, 20);
        this.speed = this.create("Speed", "Speed", 6.25, 0.0, 6.25);
        this.cmode = this.create("ControlMode", "ControlMode", "Rel", this.combobox("Rel", "Abs"));
        SPacketPlayerPosLook pak;
        SPacketPlayerPosLook pak2;
        double dx;
        double dy;
        double dz;
        EntityPlayerSP player;
        final TextComponentString textComponentString;
        this.receiveListener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketPlayerPosLook) {
                pak = (SPacketPlayerPosLook)event.get_packet();
                pak.yaw = Phase.mc.player.rotationYaw;
                pak.pitch = Phase.mc.player.rotationPitch;
            }
            if (event.get_packet() instanceof SPacketPlayerPosLook) {
                pak2 = (SPacketPlayerPosLook)event.get_packet();
                dx = Math.abs(pak2.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X) ? pak2.getX() : (Phase.mc.player.posX - pak2.getX()));
                dy = Math.abs(pak2.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y) ? pak2.getY() : (Phase.mc.player.posY - pak2.getY()));
                dz = Math.abs(pak2.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z) ? pak2.getZ() : (Phase.mc.player.posZ - pak2.getZ()));
                if (dx < 0.001) {
                    dx = 0.0;
                }
                if (dz < 0.001) {
                    dz = 0.0;
                }
                if ((dx != 0.0 || dy != 0.0 || dz != 0.0) && this.debug.get_value(false)) {
                    player = Phase.mc.player;
                    new TextComponentString("position pak, dx=" + dx + " dy=" + dy + " dz=" + dz);
                    player.sendMessage((ITextComponent)textComponentString);
                }
                if (pak2.yaw != Phase.mc.player.rotationYaw || pak2.pitch != Phase.mc.player.rotationPitch) {
                    if (this.SendRotPackets.get_value(true)) {
                        Phase.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Rotation(Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch, Phase.mc.player.onGround));
                    }
                    pak2.yaw = Phase.mc.player.rotationYaw;
                    pak2.pitch = Phase.mc.player.rotationPitch;
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.last = 0L;
        this.name = "Phase";
        this.tag = "Phase";
        this.description = "Phase";
    }
    
    @Override
    public void update() {
        try {
            Phase.mc.player.noClip = true;
            if (this.tickDelay.get_value(2) > 0 && Phase.mc.player.ticksExisted % this.tickDelay.get_value(2) != 0 && this.twodelay.get_value(true)) {
                return;
            }
            final int eca = this.EnhancedRotsAmount.get_value(2);
            if (this.EnhancedRots.get_value(false) && this.up.isKeyDown()) {
                final EntityPlayerSP player = Phase.mc.player;
                player.rotationPitch -= eca;
            }
            if (this.EnhancedRots.get_value(false) && this.down.isKeyDown()) {
                final EntityPlayerSP player2 = Phase.mc.player;
                player2.rotationPitch += eca;
            }
            if (this.EnhancedRots.get_value(false) && this.left.isKeyDown()) {
                final EntityPlayerSP player3 = Phase.mc.player;
                player3.rotationYaw -= eca;
            }
            if (this.EnhancedRots.get_value(false) && this.right.isKeyDown()) {
                final EntityPlayerSP player4 = Phase.mc.player;
                player4.rotationYaw += eca;
            }
            double yaw = (Phase.mc.player.rotationYaw + 90.0f) * (this.invert.get_value(false) ? -1 : 1);
            double xDir;
            double zDir;
            if (this.cmode.in("Rel")) {
                double dO_numer = 0.0;
                double dO_denom = 0.0;
                if (Phase.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    dO_numer -= 90.0;
                    ++dO_denom;
                }
                if (Phase.mc.gameSettings.keyBindRight.isKeyDown()) {
                    dO_numer += 90.0;
                    ++dO_denom;
                }
                if (Phase.mc.gameSettings.keyBindBack.isKeyDown()) {
                    dO_numer += 180.0;
                    ++dO_denom;
                }
                if (Phase.mc.gameSettings.keyBindForward.isKeyDown()) {
                    ++dO_denom;
                }
                if (dO_denom > 0.0) {
                    yaw += dO_numer / dO_denom % 361.0;
                }
                if (yaw < 0.0) {
                    yaw = 360.0 - yaw;
                }
                if (yaw > 360.0) {
                    yaw %= 361.0;
                }
                xDir = Math.cos(Math.toRadians(yaw));
                zDir = Math.sin(Math.toRadians(yaw));
            }
            else {
                xDir = 0.0;
                zDir = 0.0;
                xDir += (Phase.mc.gameSettings.keyBindForward.isKeyDown() ? 1.0 : 0.0);
                xDir -= (Phase.mc.gameSettings.keyBindBack.isKeyDown() ? 1.0 : 0.0);
                zDir += (Phase.mc.gameSettings.keyBindLeft.isKeyDown() ? 1.0 : 0.0);
                zDir -= (Phase.mc.gameSettings.keyBindRight.isKeyDown() ? 1.0 : 0.0);
            }
            if (Phase.mc.gameSettings.keyBindForward.isKeyDown() || Phase.mc.gameSettings.keyBindLeft.isKeyDown() || Phase.mc.gameSettings.keyBindRight.isKeyDown() || Phase.mc.gameSettings.keyBindBack.isKeyDown()) {
                Phase.mc.player.motionX = xDir * (this.speed.get_value(6.25) / 100.0);
                Phase.mc.player.motionZ = zDir * (this.speed.get_value(6.25) / 100.0);
            }
            Phase.mc.player.motionY = 0.0;
            boolean yes = false;
            if (this.advd.get_value(false)) {
                if (this.last + 50L >= System.currentTimeMillis()) {
                    yes = false;
                }
                else {
                    this.last = System.currentTimeMillis();
                    yes = true;
                }
            }
            Phase.mc.player.noClip = true;
            if (yes) {
                Phase.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.player.posX + Phase.mc.player.motionX, Phase.mc.player.posY + ((Phase.mc.player.posY < (this.twobeepvp.get_value(true) ? 1.1 : -0.98)) ? (this.speed.get_value(6.25) / 100.0) : 0.0) + (Phase.mc.gameSettings.keyBindJump.isKeyDown() ? (this.speed.get_value(6.25) / 100.0) : 0.0) - (Phase.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.speed.get_value(6.25) / 100.0) : 0.0), Phase.mc.player.posZ + Phase.mc.player.motionZ, false));
            }
            if (this.PUP.get_value(true)) {
                Phase.mc.player.noClip = true;
                Phase.mc.player.setLocationAndAngles(Phase.mc.player.posX + Phase.mc.player.motionX, Phase.mc.player.posY, Phase.mc.player.posZ + Phase.mc.player.motionZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch);
            }
            Phase.mc.player.noClip = true;
            if (yes) {
                Phase.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.player.posX + Phase.mc.player.motionX, Phase.mc.player.posY - 42069.0, Phase.mc.player.posZ + Phase.mc.player.motionZ, true));
            }
            final double dx = 0.0;
            double dy = 0.0;
            final double dz = 0.0;
            if (Phase.mc.gameSettings.keyBindSneak.isKeyDown()) {
                dy = -0.0625;
            }
            if (Phase.mc.gameSettings.keyBindJump.isKeyDown()) {
                dy = 0.0625;
            }
            Phase.mc.player.setLocationAndAngles(Phase.mc.player.posX, Phase.mc.player.posY, Phase.mc.player.posZ, Phase.mc.player.rotationYaw, Phase.mc.player.rotationPitch);
            Phase.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.player.posX, Phase.mc.player.posY, Phase.mc.player.posZ, false));
        }
        catch (Exception e) {
            this.disable();
        }
    }
    
    @Override
    protected void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }
    
    @Override
    protected void disable() {
        if (Phase.mc.player != null) {
            Phase.mc.player.noClip = false;
        }
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Phase.mc.player.posX), Math.floor(Phase.mc.player.posY), Math.floor(Phase.mc.player.posZ));
    }
}
