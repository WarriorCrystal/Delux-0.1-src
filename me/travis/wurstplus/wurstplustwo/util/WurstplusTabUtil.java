//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.client.network.NetworkPlayerInfo;

public class WurstplusTabUtil
{
    public static String get_player_name(final NetworkPlayerInfo info) {
        final String name = (info.getDisplayName() != null) ? info.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)info.getPlayerTeam(), info.getGameProfile().getName());
        if (WurstplusFriendUtil.isFriend(name)) {
            return section_sign() + "6" + name;
        }
        if (WurstplusEnemyUtil.isEnemy(name)) {
            return section_sign() + "c" + name;
        }
        return name;
    }
    
    public static String section_sign() {
        return "§";
    }
}
