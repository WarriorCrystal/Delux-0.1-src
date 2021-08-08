//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.movement;

import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiChat;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.settings.KeyBinding;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class InventoryMove extends WurstplusHack
{
    private static KeyBinding[] KEYS;
    int JUMP;
    @EventHandler
    public Listener listener;
    
    public InventoryMove() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Inventory Walk";
        this.tag = "InvWalk";
        this.description = "walk while is in any gui.";
        this.JUMP = InventoryMove.mc.gameSettings.keyBindJump.getKeyCode();
        this.listener = new Listener(event -> {
            if (!(InventoryMove.mc.currentScreen instanceof GuiChat) && InventoryMove.mc.currentScreen != null) {
                this.walk();
            }
        }, (Predicate<T>[])new Predicate[0]);
    }
    
    @Override
    public void update() {
        if (!(InventoryMove.mc.currentScreen instanceof GuiChat) && InventoryMove.mc.currentScreen != null) {
            EntityPlayerSP var10000;
            final EntityPlayerSP entityPlayerSP;
            final EntityPlayerSP player = entityPlayerSP = (var10000 = InventoryMove.mc.player);
            entityPlayerSP.rotationYaw += (Keyboard.isKeyDown(205) ? 4.0f : (Keyboard.isKeyDown(203) ? -4.0f : 0.0f));
            final EntityPlayerSP player = InventoryMove.mc.player;
            player.rotationPitch += (float)((Keyboard.isKeyDown(208) ? 4 : (Keyboard.isKeyDown(200) ? -4 : 0)) * 0.75);
            InventoryMove.mc.player.rotationPitch = MathHelper.clamp(InventoryMove.mc.player.rotationPitch, -90.0f, 90.0f);
            if (Keyboard.isKeyDown(this.JUMP)) {
                if (!InventoryMove.mc.player.isInLava() && !InventoryMove.mc.player.isInWater()) {
                    if (InventoryMove.mc.player.onGround) {
                        InventoryMove.mc.player.jump();
                    }
                }
                else {
                    final EntityPlayerSP entityPlayerSP2;
                    final EntityPlayerSP player2 = entityPlayerSP2 = (var10000 = InventoryMove.mc.player);
                    entityPlayerSP2.motionY += 0.3799999952316284;
                }
            }
            this.walk();
        }
    }
    
    public void walk() {
        for (final KeyBinding key_binding : InventoryMove.KEYS) {
            if (Keyboard.isKeyDown(key_binding.getKeyCode())) {
                if (key_binding.getKeyConflictContext() != KeyConflictContext.UNIVERSAL) {
                    key_binding.setKeyConflictContext((IKeyConflictContext)KeyConflictContext.UNIVERSAL);
                }
                KeyBinding.setKeyBindState(key_binding.getKeyCode(), true);
            }
            else {
                KeyBinding.setKeyBindState(key_binding.getKeyCode(), false);
            }
        }
    }
    
    static {
        InventoryMove.KEYS = new KeyBinding[] { InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindJump, InventoryMove.mc.gameSettings.keyBindSprint };
    }
}
