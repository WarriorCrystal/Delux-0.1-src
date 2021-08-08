//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.misc;

import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusMiddleClickFriends extends WurstplusHack
{
    private boolean clicked;
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    
    public WurstplusMiddleClickFriends() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.clicked = false;
        this.name = "Middleclick Friends";
        this.tag = "MiddleclickFriends";
        this.description = "you press button and the world becomes a better place :D";
    }
    
    @Override
    public void update() {
        if (WurstplusMiddleClickFriends.mc.currentScreen != null) {
            return;
        }
        if (!Mouse.isButtonDown(2)) {
            this.clicked = false;
            return;
        }
        if (!this.clicked) {
            this.clicked = true;
            final RayTraceResult result = WurstplusMiddleClickFriends.mc.objectMouseOver;
            if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY) {
                return;
            }
            if (!(result.entityHit instanceof EntityPlayer)) {
                return;
            }
            final Entity player = result.entityHit;
            if (WurstplusFriendUtil.isFriend(player.getName())) {
                final WurstplusFriendUtil.Friend f = WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(player.getName())).findFirst().get();
                WurstplusFriendUtil.friends.remove(f);
                WurstplusMessageUtil.send_client_message("Player " + WurstplusMiddleClickFriends.red + WurstplusMiddleClickFriends.bold + player.getName() + WurstplusMiddleClickFriends.reset + " is now not your friend :(");
            }
            else {
                final WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object(player.getName());
                WurstplusFriendUtil.friends.add(f);
                WurstplusMessageUtil.send_client_message("Player " + WurstplusMiddleClickFriends.green + WurstplusMiddleClickFriends.bold + player.getName() + WurstplusMiddleClickFriends.reset + " is now your friend :D");
            }
        }
    }
    
    static {
        WurstplusMiddleClickFriends.red = ChatFormatting.RED;
        WurstplusMiddleClickFriends.green = ChatFormatting.GREEN;
        WurstplusMiddleClickFriends.bold = ChatFormatting.BOLD;
        WurstplusMiddleClickFriends.reset = ChatFormatting.RESET;
    }
}
