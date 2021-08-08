//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import java.util.concurrent.TimeUnit;
import me.travis.other.Phobos.MathUtil;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import me.travis.other.Phobos.BlockUtil;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class GodModule extends WurstplusHack
{
    private float yaw;
    private float pitch;
    private boolean rotating;
    private int rotationPacketsSpoofed;
    private int highestID;
    public WurstplusSetting rotations;
    public WurstplusSetting rotate;
    public WurstplusSetting render;
    public WurstplusSetting antiIllegal;
    public WurstplusSetting checkPos;
    public WurstplusSetting oneDot15;
    public WurstplusSetting entitycheck;
    public WurstplusSetting attacks;
    public WurstplusSetting offset;
    public WurstplusSetting delay;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener;
    
    public GodModule() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.highestID = -100000;
        this.rotations = this.create("Spoofs", "Spoofs", 1, 1, 20);
        this.rotate = this.create("Rotate", "Rotate", false);
        this.render = this.create("Render", "Render", false);
        this.antiIllegal = this.create("AntiIllegal", "AntiIllegal", true);
        this.checkPos = this.create("CheckPos", "CheckPos", false);
        this.oneDot15 = this.create("1.15", "1.15", false);
        this.entitycheck = this.create("EntityCheck", "EntityCheck", false);
        this.attacks = this.create("Attacks", "Attacks", 1, 1, 10);
        this.offset = this.create("Offset", "Offset", 0, 0, 2);
        this.delay = this.create("Delay", "Delay", 0, 0, 250);
        CPacketPlayerTryUseItemOnBlock packet;
        int i;
        CPacketPlayer packet2;
        this.send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
            if (event.get_partial_ticks() == 0.0f && event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
                packet = (CPacketPlayerTryUseItemOnBlock)event.get_packet();
                if (GodModule.mc.player.getHeldItem(packet.hand).getItem() instanceof ItemEndCrystal) {
                    if ((this.checkPos.get_value(false) && !BlockUtil.canPlaceCrystal(packet.position, this.entitycheck.get_value(false), this.oneDot15.get_value(false))) || this.checkPlayers()) {
                        return;
                    }
                    else {
                        this.updateEntityID();
                        for (i = 1 - this.offset.get_value(0); i <= this.attacks.get_value(1); ++i) {
                            this.attackID(packet.position, this.highestID + i);
                        }
                    }
                }
            }
            if (event.get_partial_ticks() == 0.0f && this.rotating && this.rotate.get_value(false) && event.get_packet() instanceof CPacketPlayer) {
                packet2 = (CPacketPlayer)event.get_packet();
                packet2.yaw = this.yaw;
                packet2.pitch = this.pitch;
                ++this.rotationPacketsSpoofed;
                if (this.rotationPacketsSpoofed >= this.rotations.get_value(1)) {
                    this.rotating = false;
                    this.rotationPacketsSpoofed = 0;
                }
            }
            return;
        }, (Predicate<WurstplusEventPacket.SendPacket>[])new Predicate[0]);
        this.receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
            if (event.get_packet() instanceof SPacketSpawnObject) {
                this.checkID(((SPacketSpawnObject)event.get_packet()).getEntityID());
            }
            else if (event.get_packet() instanceof SPacketSpawnExperienceOrb) {
                this.checkID(((SPacketSpawnExperienceOrb)event.get_packet()).getEntityID());
            }
            else if (event.get_packet() instanceof SPacketSpawnPlayer) {
                this.checkID(((SPacketSpawnPlayer)event.get_packet()).getEntityID());
            }
            else if (event.get_packet() instanceof SPacketSpawnGlobalEntity) {
                this.checkID(((SPacketSpawnGlobalEntity)event.get_packet()).getEntityId());
            }
            else if (event.get_packet() instanceof SPacketSpawnPainting) {
                this.checkID(((SPacketSpawnPainting)event.get_packet()).getEntityID());
            }
            else if (event.get_packet() instanceof SPacketSpawnMob) {
                this.checkID(((SPacketSpawnMob)event.get_packet()).getEntityID());
            }
            return;
        }, (Predicate<WurstplusEventPacket.ReceivePacket>[])new Predicate[0]);
        this.name = "GodModule";
        this.tag = "GodModule";
        this.description = "Bypass crystals";
    }
    
    @Override
    protected void enable() {
        this.resetFields();
        if (GodModule.mc.world != null) {
            this.updateEntityID();
        }
    }
    
    @Override
    public void update() {
        if (this.render.get_value(false)) {
            for (final Entity entity : GodModule.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    continue;
                }
                entity.setCustomNameTag(String.valueOf(entity.getEntityId()));
                entity.setAlwaysRenderNameTag(true);
            }
        }
    }
    
    private void attackID(final BlockPos pos, final int id) {
        final Entity entity = GodModule.mc.world.getEntityByID(id);
        if (entity == null || entity instanceof EntityEnderCrystal) {
            final AttackThread attackThread = new AttackThread(id, pos, this.delay.get_value(0), this);
            if (this.delay.get_value(0) == 0) {
                attackThread.run();
            }
            else {
                attackThread.start();
            }
        }
    }
    
    private void checkID(final int id) {
        if (id > this.highestID) {
            this.highestID = id;
        }
    }
    
    public void updateEntityID() {
        for (final Entity entity : GodModule.mc.world.loadedEntityList) {
            if (entity.getEntityId() <= this.highestID) {
                continue;
            }
            this.highestID = entity.getEntityId();
        }
    }
    
    private boolean checkPlayers() {
        if (this.antiIllegal.get_value(true)) {
            for (final EntityPlayer player : GodModule.mc.world.playerEntities) {
                if (!this.checkItem(player.getHeldItemMainhand()) && !this.checkItem(player.getHeldItemOffhand())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean checkItem(final ItemStack stack) {
        return stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemExpBottle || stack.getItem() == Items.STRING;
    }
    
    public void rotateTo(final BlockPos pos) {
        final float[] angle = MathUtil.calcAngle(GodModule.mc.player.getPositionEyes(GodModule.mc.getRenderPartialTicks()), new Vec3d((Vec3i)pos));
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.rotating = true;
    }
    
    private void resetFields() {
        this.rotating = false;
        this.highestID = -1000000;
    }
    
    public static class AttackThread extends Thread
    {
        private final BlockPos pos;
        private final int id;
        private final int delay;
        private final GodModule godModule;
        
        public AttackThread(final int idIn, final BlockPos posIn, final int delayIn, final GodModule godModuleIn) {
            this.id = idIn;
            this.pos = posIn;
            this.delay = delayIn;
            this.godModule = godModuleIn;
        }
        
        @Override
        public void run() {
            try {
                if (this.delay != 0) {
                    TimeUnit.MILLISECONDS.sleep(this.delay);
                }
                CPacketUseEntity attack;
                WurstplusHack.mc.addScheduledTask(() -> {
                    if (!WurstplusHack.nullCheck()) {
                        attack = new CPacketUseEntity();
                        attack.entityId = this.id;
                        attack.action = CPacketUseEntity.Action.ATTACK;
                        this.godModule.rotateTo(this.pos.up());
                        WurstplusHack.mc.player.connection.sendPacket((Packet)attack);
                        WurstplusHack.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
