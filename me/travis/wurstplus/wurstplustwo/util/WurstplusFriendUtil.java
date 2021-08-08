//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.UUID;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonElement;
import com.mojang.util.UUIDTypeAdapter;
import com.google.gson.JsonParser;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;

public class WurstplusFriendUtil
{
    public static ArrayList<Friend> friends;
    
    public static boolean isFriend(final String name) {
        return WurstplusFriendUtil.friends.stream().anyMatch(friend -> friend.username.equalsIgnoreCase(name));
    }
    
    public static Friend get_friend_object(final String name) {
        final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        final NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if (profile == null) {
            final String s = request_ids("[\"" + name + "\"]");
            if (s != null) {
                if (!s.isEmpty()) {
                    final JsonElement element = new JsonParser().parse(s);
                    if (element.getAsJsonArray().size() != 0) {
                        try {
                            final String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                            final String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                            return new Friend(username, UUIDTypeAdapter.fromString(id));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }
        return new Friend(profile.getGameProfile().getName(), profile.getGameProfile().getId());
    }
    
    private static String request_ids(final String data) {
        try {
            final String query = "https://api.mojang.com/profiles/minecraft";
            final URL url = new URL(query);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            final OutputStream os = conn.getOutputStream();
            os.write(data.getBytes("UTF-8"));
            os.close();
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        final String r = s.hasNext() ? s.next() : "/";
        return r;
    }
    
    static {
        WurstplusFriendUtil.friends = new ArrayList<Friend>();
    }
    
    public static class Friend
    {
        String username;
        UUID uuid;
        
        public Friend(final String username, final UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }
        
        public String getUsername() {
            return this.username;
        }
        
        public UUID getUUID() {
            return this.uuid;
        }
    }
}
