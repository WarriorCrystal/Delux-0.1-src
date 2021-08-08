//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.combat;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import java.util.Comparator;
import me.travis.other.Phobos.BlockUtil;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import java.util.ArrayList;
import me.travis.other.Phobos.InventoryUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Crasher extends WurstplusHack
{
    private final WurstplusSetting oneDot15;
    private final WurstplusSetting placeRange;
    private final WurstplusSetting crystals;
    private final WurstplusSetting coolDown;
    private final WurstplusSetting switchMode;
    private final WurstplusTimer timer;
    private final List<Integer> entityIDs;
    public WurstplusSetting sort;
    private boolean offhand;
    private boolean mainhand;
    private int lastHotbarSlot;
    private boolean switchedItem;
    private boolean chinese;
    private int currentID;
    
    public Crasher() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.oneDot15 = this.create("1.15", "1.15", false);
        this.placeRange = this.create("PlaceRange", "PlaceRange", 6.0, 0.0, 10.0);
        this.crystals = this.create("Packets", "Packets", 25, 0, 100);
        this.coolDown = this.create("CoolDown", "CoolDown", 400, 0, 1000);
        this.switchMode = this.create("Switch", "Switch", String.valueOf(InventoryUtil.Switch.NORMAL));
        this.timer = new WurstplusTimer();
        this.entityIDs = new ArrayList<Integer>();
        this.sort = this.create("Sort", "Sort", 0, 0, 2);
        this.offhand = false;
        this.mainhand = false;
        this.lastHotbarSlot = -1;
        this.switchedItem = false;
        this.chinese = false;
        this.currentID = -1000;
        this.name = "Crasher";
        this.tag = "Crasher";
        this.description = "Attempts to crash chinese AutoCrystals";
    }
    
    @Override
    protected void enable() {
        this.chinese = false;
        if (WurstplusHack.fullNullCheck() || !this.timer.passedMs(this.coolDown.get_value(400))) {
            this.disable();
            return;
        }
        this.lastHotbarSlot = Crasher.mc.player.inventory.currentItem;
        this.placeCrystals();
        this.disable();
    }
    
    @Override
    protected void disable() {
        if (!WurstplusHack.fullNullCheck()) {
            for (final int i : this.entityIDs) {
                Crasher.mc.world.removeEntityFromWorld(i);
            }
        }
        this.entityIDs.clear();
        this.currentID = -1000;
        this.timer.reset();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (WurstplusHack.fullNullCheck() || event.phase == TickEvent.Phase.START || this.timer.passedMs(10L)) {
            return;
        }
        this.switchItem(true);
    }
    
    private void placeCrystals() {
        this.offhand = (Crasher.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.mainhand = (Crasher.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL);
        int crystalcount = 0;
        final List<BlockPos> blocks = BlockUtil.possiblePlacePositions((float)this.placeRange.get_value(6.0), false, this.oneDot15.get_value(false));
        if (this.sort.get_value(0) == 1) {
            blocks.sort(Comparator.comparingDouble(hole -> Crasher.mc.player.getDistanceSq(hole)));
        }
        else if (this.sort.get_value(0) == 2) {
            blocks.sort(Comparator.comparingDouble(hole -> -Crasher.mc.player.getDistanceSq(hole)));
        }
        for (final BlockPos pos : blocks) {
            if (crystalcount >= this.crystals.get_value(25)) {
                break;
            }
            if (!BlockUtil.canPlaceCrystal(pos, false, this.oneDot15.get_value(false))) {
                continue;
            }
            this.placeCrystal(pos);
            ++crystalcount;
        }
    }
    
    private void placeCrystal(final BlockPos pos) {
        if (!this.chinese && !this.mainhand && !this.offhand && !this.switchItem(false)) {
            this.disable();
            return;
        }
        final RayTraceResult result = Crasher.mc.world.rayTraceBlocks(new Vec3d(Crasher.mc.player.posX, Crasher.mc.player.posY + Crasher.mc.player.getEyeHeight(), Crasher.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        Crasher.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        Crasher.mc.player.swingArm(EnumHand.MAIN_HAND);
        final EntityEnderCrystal fakeCrystal = new EntityEnderCrystal((World)Crasher.mc.world, (double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
        final int newID = this.currentID--;
        this.entityIDs.add(newID);
        Crasher.mc.world.addEntityToWorld(newID, (Entity)fakeCrystal);
    }
    
    private boolean switchItem(final boolean back) {
        this.chinese = true;
        if (this.offhand) {
            return true;
        }
        final boolean[] value = InventoryUtil.switchItemToItem(back, this.lastHotbarSlot, this.switchedItem, InventoryUtil.Switch.valueOf(this.switchMode.get_value(String.valueOf(InventoryUtil.Switch.NORMAL))), Items.END_CRYSTAL);
        this.switchedItem = value[0];
        return value[1];
    }
}
