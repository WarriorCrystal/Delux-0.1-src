//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import java.math.RoundingMode;
import me.travis.wurstplus.Wurstplus;
import java.text.DecimalFormat;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusPlayerList extends WurstplusPinnable
{
    DecimalFormat df_health;
    
    public WurstplusPlayerList() {
        super("Player List", "PlayerList", 1.0f, 0, 0);
        this.df_health = new DecimalFormat("#.#");
    }
    
    @Override
    public void render() {
        int counter = 12;
        final int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        final int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        final int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        final int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        this.df_health.setRoundingMode(RoundingMode.HALF_UP);
        final List<EntityPlayer> entity_list = (List<EntityPlayer>)this.mc.world.playerEntities;
        Map<String, Integer> players = new HashMap<String, Integer>();
        for (final EntityPlayer player : entity_list) {
            final StringBuilder sb_health = new StringBuilder();
            if (player == this.mc.player) {
                continue;
            }
            final String posString = (player.posY > this.mc.player.posY) ? (ChatFormatting.DARK_GREEN + "+") : ((player.posY == this.mc.player.posY) ? " " : (ChatFormatting.DARK_RED + "-"));
            final float hp_raw = player.getHealth() + player.getAbsorptionAmount();
            final String hp = this.df_health.format(hp_raw);
            sb_health.append('§');
            if (hp_raw >= 20.0f) {
                sb_health.append("a");
            }
            else if (hp_raw >= 10.0f) {
                sb_health.append("e");
            }
            else if (hp_raw >= 5.0f) {
                sb_health.append("6");
            }
            else {
                sb_health.append("c");
            }
            sb_health.append(hp);
            players.put(posString + " " + sb_health.toString() + " " + (WurstplusFriendUtil.isFriend(player.getName()) ? ChatFormatting.GREEN : ChatFormatting.RESET) + player.getName(), (int)this.mc.player.getDistance((Entity)player));
        }
        if (players.isEmpty()) {
            return;
        }
        players = sortByValue(players);
        final int max = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDMaxPlayers").get_value(1);
        int count = 0;
        for (final Map.Entry<String, Integer> player2 : players.entrySet()) {
            if (max < count) {
                return;
            }
            ++count;
            counter += 12;
            final String line = player2.getKey() + " " + player2.getValue();
            this.create_line(line, this.docking(1, line), counter, nl_r, nl_g, nl_b, nl_a);
        }
        this.set_width(24);
        this.set_height(counter + 2);
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        list.sort((Comparator<? super Map.Entry<K, V>>)Map.Entry.comparingByValue());
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
