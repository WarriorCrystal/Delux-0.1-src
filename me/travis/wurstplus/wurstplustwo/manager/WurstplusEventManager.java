//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.manager;

import net.minecraftforge.client.event.InputUpdateEvent;
import java.util.Iterator;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommands;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.travis.turok.draw.RenderHelp;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.passive.AbstractHorse;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGameOverlay;
import net.minecraft.client.gui.ScaledResolution;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import me.travis.wurstplus.Wurstplus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.client.Minecraft;

public class WurstplusEventManager
{
    private final Minecraft mc;
    
    public WurstplusEventManager() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) {
            return;
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        Wurstplus.get_hack_manager().update();
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        Wurstplus.get_hack_manager().render(event);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (event.isCanceled()) {
            return;
        }
        WurstplusEventBus.EVENT_BUS.post(new WurstplusEventGameOverlay(event.getPartialTicks(), new ScaledResolution(this.mc)));
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;
        if (!this.mc.player.isCreative() && this.mc.player.getRidingEntity() instanceof AbstractHorse) {
            target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
        }
        if (event.getType() == target) {
            Wurstplus.get_hack_manager().render();
            if (!Wurstplus.get_hack_manager().get_module_with_tag("ClickGUI").is_active()) {
                Wurstplus.get_hud_manager().render();
            }
            GL11.glPushMatrix();
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GlStateManager.enableBlend();
            GL11.glPopMatrix();
            RenderHelp.release_gl();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Wurstplus.get_hack_manager().bind(Keyboard.getEventKey());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onChat(final ClientChatEvent event) {
        final String message = event.getMessage();
        final String[] message_args = WurstplusCommandManager.command_list.get_message(event.getMessage());
        boolean true_command = false;
        if (message_args.length > 0) {
            event.setCanceled(true);
            this.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            for (final WurstplusCommand command : WurstplusCommands.get_pure_command_list()) {
                try {
                    if (!WurstplusCommandManager.command_list.get_message(event.getMessage())[0].equalsIgnoreCase(command.get_name())) {
                        continue;
                    }
                    true_command = command.get_message(WurstplusCommandManager.command_list.get_message(event.getMessage()));
                }
                catch (Exception ex) {}
            }
            if (!true_command && WurstplusCommandManager.command_list.has_prefix(event.getMessage())) {
                WurstplusMessageUtil.send_client_message("Try using " + WurstplusCommandManager.get_prefix() + "help list to see all commands");
                true_command = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        WurstplusEventBus.EVENT_BUS.post(event);
    }
}
