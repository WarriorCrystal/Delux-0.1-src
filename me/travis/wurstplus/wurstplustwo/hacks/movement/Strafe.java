//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.MobEffects;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import net.minecraft.entity.EntityLivingBase;
import me.travis.other.Gamesense.EntityUtil;
import me.travis.other.WurstPlusThree.PlayerUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Strafe extends WurstplusHack
{
    private static Strafe INSTANCE;
    WurstplusSetting mode;
    WurstplusSetting yPortSpeed;
    WurstplusSetting jumpHeight;
    WurstplusSetting timerVal;
    private boolean slowdown;
    private double playerSpeed;
    private final WurstplusTimer timer;
    
    public Strafe() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.mode = this.create("Mode", "Mode", "Strafe", this.combobox("Strafe", "Fake", "YPort"));
        this.yPortSpeed = this.create("YPort Speed", "YPort Speed", 0.06, 0.01, 0.15);
        this.jumpHeight = this.create("Jump Height", "Jump Height", 0.41, 0.0, 1.0);
        this.timerVal = this.create("Timer Speed", "Timer Speed", 1.15, 1.0, 1.5);
        this.timer = new WurstplusTimer();
        this.name = "Strafe";
        this.tag = "Strafe";
        this.description = "its like running, but faster";
        Strafe.INSTANCE = this;
    }
    
    public static Strafe getInstance() {
        if (Strafe.INSTANCE == null) {
            Strafe.INSTANCE = new Strafe();
        }
        return Strafe.INSTANCE;
    }
    
    @Override
    protected void enable() {
        this.playerSpeed = PlayerUtil.getBaseMoveSpeed();
    }
    
    @Override
    protected void disable() {
        EntityUtil.resetTimer();
        this.timer.reset();
    }
    
    @Override
    public void update() {
        if (nullCheck()) {
            this.disable();
            return;
        }
        if (this.mode.in("YPort")) {
            this.handleYPortSpeed();
        }
    }
    
    private void handleYPortSpeed() {
        if (!PlayerUtil.isMoving((EntityLivingBase)Strafe.mc.player) || (Strafe.mc.player.isInWater() && Strafe.mc.player.isInLava()) || Strafe.mc.player.collidedHorizontally) {
            return;
        }
        if (Strafe.mc.player.onGround) {
            EntityUtil.setTimer(1.15f);
            Strafe.mc.player.jump();
            PlayerUtil.setSpeed((EntityLivingBase)Strafe.mc.player, PlayerUtil.getBaseMoveSpeed() + this.yPortSpeed.get_value(0.06));
        }
        else {
            Strafe.mc.player.motionY = -1.0;
            EntityUtil.resetTimer();
        }
    }
    
    @SubscribeEvent
    public void onMove(final WurstplusEventMove event) {
        if (Strafe.mc.player.isInLava() || Strafe.mc.player.isInWater() || Strafe.mc.player.isOnLadder() || Strafe.mc.player.isInWeb) {
            return;
        }
        if (this.mode.get_current_value().equalsIgnoreCase("Strafe")) {
            double speedY = this.jumpHeight.get_value(0.41);
            if (Strafe.mc.player.onGround && PlayerUtil.isMoving((EntityLivingBase)Strafe.mc.player) && this.timer.passedMs(300L)) {
                EntityUtil.setTimer((float)this.timerVal.get_value(1.15));
                if (Strafe.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    speedY += (Strafe.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                }
                event.set_y(Strafe.mc.player.motionY = speedY);
                this.playerSpeed = PlayerUtil.getBaseMoveSpeed() * ((EntityUtil.isColliding(0.0, -0.5, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.9 : 1.901);
                this.slowdown = true;
                this.timer.reset();
            }
            else {
                EntityUtil.resetTimer();
                if (this.slowdown || Strafe.mc.player.collidedHorizontally) {
                    final double playerSpeed = this.playerSpeed;
                    double n;
                    if (EntityUtil.isColliding(0.0, -0.8, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
                        n = 0.4;
                    }
                    else {
                        final double n2 = 0.7;
                        final double baseMoveSpeed = PlayerUtil.getBaseMoveSpeed();
                        this.playerSpeed = baseMoveSpeed;
                        n = n2 * baseMoveSpeed;
                    }
                    this.playerSpeed = playerSpeed - n;
                    this.slowdown = false;
                }
                else {
                    this.playerSpeed -= this.playerSpeed / 159.0;
                }
            }
            this.playerSpeed = Math.max(this.playerSpeed, PlayerUtil.getBaseMoveSpeed());
            final double[] dir = PlayerUtil.forward(this.playerSpeed);
            event.set_x(dir[0]);
            event.set_z(dir[1]);
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.get_current_value();
    }
}
