//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagList;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDamageUtil;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.zero.alpine.fork.listener.EventHandler;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderName;
import me.zero.alpine.fork.listener.Listener;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusNameTags extends WurstplusHack
{
    WurstplusSetting show_armor;
    WurstplusSetting show_health;
    WurstplusSetting show_ping;
    WurstplusSetting show_totems;
    WurstplusSetting show_invis;
    WurstplusSetting reverse;
    WurstplusSetting simplify;
    WurstplusSetting m_scale;
    WurstplusSetting range;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting rainbow_mode;
    WurstplusSetting sat;
    WurstplusSetting brightness;
    @EventHandler
    private final Listener<WurstplusEventRenderName> on_render_name;
    
    public WurstplusNameTags() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.show_armor = this.create("Armor", "NametagArmor", true);
        this.show_health = this.create("Health", "NametagHealth", true);
        this.show_ping = this.create("Ping", "NametagPing", true);
        this.show_totems = this.create("Totem Count", "NametagTotems", true);
        this.show_invis = this.create("Invis", "NametagInvis", true);
        this.reverse = this.create("Armour Reverse", "NametagReverse", true);
        this.simplify = this.create("Simplify", "NametagSimp", false);
        this.m_scale = this.create("Scale", "NametagScale", 4, 1, 15);
        this.range = this.create("Range", "NametagRange", 75, 1, 250);
        this.r = this.create("R", "NametagR", 255, 0, 255);
        this.g = this.create("G", "NametagG", 255, 0, 255);
        this.b = this.create("B", "NametagB", 255, 0, 255);
        this.a = this.create("A", "NametagA", 0.699999988079071, 0.0, 1.0);
        this.rainbow_mode = this.create("Rainbow", "NametagRainbow", false);
        this.sat = this.create("Saturation", "NametagSatiation", 0.8, 0.0, 1.0);
        this.brightness = this.create("Brightness", "NametagBrightness", 0.8, 0.0, 1.0);
        this.on_render_name = new Listener<WurstplusEventRenderName>(event -> event.cancel(), (Predicate<WurstplusEventRenderName>[])new Predicate[0]);
        this.name = "Name Tags";
        this.tag = "NameTags";
        this.description = "MORE DETAILED NAMESSSSS";
    }
    
    @Override
    public void render(final WurstplusEventRender event) {
        for (final EntityPlayer player : WurstplusNameTags.mc.world.playerEntities) {
            if (player != null && !player.equals((Object)WurstplusNameTags.mc.player) && player.isEntityAlive() && (!player.isInvisible() || this.show_invis.get_value(true)) && WurstplusNameTags.mc.player.getDistance((Entity)player) < this.range.get_value(1)) {
                final double x = this.interpolate(player.lastTickPosX, player.posX, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosX;
                final double y = this.interpolate(player.lastTickPosY, player.posY, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosY;
                final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosZ;
                this.renderNameTag(player, x, y, z, event.get_partial_ticks());
            }
        }
    }
    
    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
    
    private void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.isSneaking() ? 0.5 : 0.7);
        final Entity camera = WurstplusNameTags.mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.getDistance(x + WurstplusNameTags.mc.getRenderManager().viewerPosX, y + WurstplusNameTags.mc.getRenderManager().viewerPosY, z + WurstplusNameTags.mc.getRenderManager().viewerPosZ);
        final int width = WurstplusNameTags.mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.m_scale.get_value(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-WurstplusNameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(WurstplusNameTags.mc.getRenderManager().playerViewX, (WurstplusNameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        final boolean is_friend = WurstplusFriendUtil.isFriend(player.getName());
        final boolean is_enemy = WurstplusEnemyUtil.isEnemy(player.getName());
        int red = this.r.get_value(1);
        int green = this.g.get_value(1);
        int blue = this.b.get_value(1);
        if (is_friend) {
            red = 157;
            green = 99;
            blue = 255;
        }
        if (is_enemy) {
            red = 255;
            green = 40;
            blue = 7;
        }
        WurstplusRenderUtil.drawRect(-width - 2 - 1.0f, -(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT + 1) - 1.0f, width + 3.0f, 2.5f, (float)red, (float)green, (float)blue, (float)this.a.get_value(1));
        WurstplusRenderUtil.drawRect((float)(-width - 2), (float)(-(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, 1426063360);
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor)) {
            renderMainHand.stackSize = 1;
        }
        if (!renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
            final String stackName = renderMainHand.getDisplayName();
            final int stackNameWidth = WurstplusNameTags.mc.fontRenderer.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 18.0f), -1);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.show_armor.get_value(true)) {
            GlStateManager.pushMatrix();
            int xOffset = -8;
            for (final ItemStack stack : player.inventory.armorInventory) {
                if (stack != null) {
                    xOffset -= 8;
                }
            }
            xOffset -= 8;
            final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
            if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof ItemTool || renderOffhand.getItem() instanceof ItemArmor)) {
                renderOffhand.stackSize = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (this.reverse.get_value(true)) {
                for (final ItemStack stack2 : player.inventory.armorInventory) {
                    if (stack2 != null) {
                        final ItemStack armourStack = stack2.copy();
                        if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                            armourStack.stackSize = 1;
                        }
                        this.renderItemStack(armourStack, xOffset);
                        xOffset += 16;
                    }
                }
            }
            else {
                for (int i = player.inventory.armorInventory.size(); i > 0; --i) {
                    final ItemStack stack2 = (ItemStack)player.inventory.armorInventory.get(i - 1);
                    final ItemStack armourStack = stack2.copy();
                    if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                        armourStack.stackSize = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            }
            this.renderItemStack(renderMainHand, xOffset);
            GlStateManager.popMatrix();
        }
        WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(displayTag, (float)(-width), (float)(-(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT - 1)), this.getDisplayColour(player));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        WurstplusNameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        WurstplusNameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -29);
        WurstplusNameTags.mc.getRenderItem().renderItemOverlays(WurstplusNameTags.mc.fontRenderer, stack, x, -29);
        WurstplusNameTags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -37;
        final NBTTagList enchants = stack.getEnchantmentTagList();
        if (enchants.tagCount() > 2 && this.simplify.get_value(true)) {
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        }
        else {
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final short level = enchants.getCompoundTagAt(index).getShort("lvl");
                final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc != null) {
                    String encName = enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
                    encName += level;
                    WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(encName, (float)(x * 2), (float)enchantmentY, -1);
                    enchantmentY -= 8;
                }
            }
        }
        if (WurstplusDamageUtil.hasDurability(stack)) {
            final int percent = WurstplusDamageUtil.getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = this.section_sign() + "a";
            }
            else if (percent >= 25) {
                color = this.section_sign() + "e";
            }
            else {
                color = this.section_sign() + "c";
            }
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(color + percent + "%", (float)(x * 2), (enchantmentY < -62) ? ((float)enchantmentY) : -62.0f, -1);
        }
    }
    
    private float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (final ItemStack stack : player.inventory.armorInventory) {
            float encY = 0.0f;
            if (stack != null) {
                final NBTTagList enchants = stack.getEnchantmentTagList();
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc != null) {
                        encY += 8.0f;
                        arm = true;
                    }
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                final short id2 = enchants2.getCompoundTagAt(index2).getShort("id");
                final Enchantment enc2 = Enchantment.getEnchantmentByID((int)id2);
                if (enc2 != null) {
                    encY2 += 8.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand = player.getHeldItemOffhand().copy();
        if (renderOffHand.hasEffect()) {
            float encY = 0.0f;
            final NBTTagList enchants = renderOffHand.getEnchantmentTagList();
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc != null) {
                    encY += 8.0f;
                    arm = true;
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (arm ? 0 : 20) + enchantmentY;
    }
    
    private String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!this.show_health.get_value(true)) {
            return name;
        }
        final float health = player.getHealth() + player.getAbsorptionAmount();
        if (health <= 0.0f) {
            return name + " - DEAD";
        }
        String color;
        if (health > 25.0f) {
            color = this.section_sign() + "5";
        }
        else if (health > 20.0f) {
            color = this.section_sign() + "a";
        }
        else if (health > 15.0f) {
            color = this.section_sign() + "2";
        }
        else if (health > 10.0f) {
            color = this.section_sign() + "6";
        }
        else if (health > 5.0f) {
            color = this.section_sign() + "c";
        }
        else {
            color = this.section_sign() + "4";
        }
        String pingStr = "";
        if (this.show_ping.get_value(true)) {
            try {
                final int responseTime = Objects.requireNonNull(WurstplusNameTags.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                if (responseTime > 150) {
                    pingStr = pingStr + this.section_sign() + "4";
                }
                else if (responseTime > 100) {
                    pingStr = pingStr + this.section_sign() + "6";
                }
                else {
                    pingStr = pingStr + this.section_sign() + "2";
                }
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception ex) {}
        }
        String popStr = " ";
        if (this.show_totems.get_value(true)) {
            try {
                popStr += ((WurstplusTotempop.totem_pop_counter.get(player.getName()) == null) ? (this.section_sign() + "70") : (this.section_sign() + "c -" + WurstplusTotempop.totem_pop_counter.get(player.getName())));
            }
            catch (Exception ex2) {}
        }
        if (Math.floor(health) == health) {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)Math.floor(health)) : "dead");
        }
        else {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)health) : "dead");
        }
        return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
    }
    
    private int getDisplayColour(final EntityPlayer player) {
        final int colour = -5592406;
        if (WurstplusFriendUtil.isFriend(player.getName())) {
            return -11157267;
        }
        if (WurstplusEnemyUtil.isEnemy(player.getName())) {
            return -49632;
        }
        return colour;
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    public String section_sign() {
        return "§";
    }
}
