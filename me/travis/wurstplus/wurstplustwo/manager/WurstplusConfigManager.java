// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.manager;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.LinkOption;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import java.nio.file.OpenOption;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.File;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import java.io.Reader;
import com.google.common.reflect.TypeToken;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import com.google.gson.GsonBuilder;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import java.util.Iterator;
import java.nio.file.Files;
import java.io.IOException;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.nio.file.Path;

public class WurstplusConfigManager
{
    private final String MAIN_FOLDER = "WURSTPLUSTWO/";
    private final String CONFIGS_FOLDER = "WURSTPLUSTWO/configs/";
    private String ACTIVE_CONFIG_FOLDER;
    private final String CLIENT_FILE = "client.json";
    private final String CONFIG_FILE = "config.txt";
    private final String DRAWN_FILE = "drawn.txt";
    private final String EZ_FILE = "ez.txt";
    private final String ENEMIES_FILE = "enemies.json";
    private final String FRIENDS_FILE = "friends.json";
    private final String HUD_FILE = "hud.json";
    private final String BINDS_FILE = "binds.txt";
    private final String CLIENT_DIR = "WURSTPLUSTWO/client.json";
    private final String CONFIG_DIR = "WURSTPLUSTWO/config.txt";
    private final String DRAWN_DIR = "WURSTPLUSTWO/drawn.txt";
    private final String EZ_DIR = "WURSTPLUSTWO/ez.txt";
    private final String ENEMIES_DIR = "WURSTPLUSTWO/enemies.json";
    private final String FRIENDS_DIR = "WURSTPLUSTWO/friends.json";
    private final String HUD_DIR = "WURSTPLUSTWO/hud.json";
    private String CURRENT_CONFIG_DIR;
    private String BINDS_DIR;
    private final Path MAIN_FOLDER_PATH;
    private final Path CONFIGS_FOLDER_PATH;
    private Path ACTIVE_CONFIG_FOLDER_PATH;
    private final Path CLIENT_PATH;
    private final Path CONFIG_PATH;
    private final Path DRAWN_PATH;
    private final Path EZ_PATH;
    private final Path ENEMIES_PATH;
    private final Path FRIENDS_PATH;
    private final Path HUD_PATH;
    private Path BINDS_PATH;
    private Path CURRENT_CONFIG_PATH;
    
    public WurstplusConfigManager() {
        this.ACTIVE_CONFIG_FOLDER = "WURSTPLUSTWO/configs/default/";
        this.CURRENT_CONFIG_DIR = "WURSTPLUSTWO/WURSTPLUSTWO/configs/" + this.ACTIVE_CONFIG_FOLDER;
        this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
        this.MAIN_FOLDER_PATH = Paths.get("WURSTPLUSTWO/", new String[0]);
        this.CONFIGS_FOLDER_PATH = Paths.get("WURSTPLUSTWO/configs/", new String[0]);
        this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER, new String[0]);
        this.CLIENT_PATH = Paths.get("WURSTPLUSTWO/client.json", new String[0]);
        this.CONFIG_PATH = Paths.get("WURSTPLUSTWO/config.txt", new String[0]);
        this.DRAWN_PATH = Paths.get("WURSTPLUSTWO/drawn.txt", new String[0]);
        this.EZ_PATH = Paths.get("WURSTPLUSTWO/ez.txt", new String[0]);
        this.ENEMIES_PATH = Paths.get("WURSTPLUSTWO/enemies.json", new String[0]);
        this.FRIENDS_PATH = Paths.get("WURSTPLUSTWO/friends.json", new String[0]);
        this.HUD_PATH = Paths.get("WURSTPLUSTWO/hud.json", new String[0]);
        this.BINDS_PATH = Paths.get(this.BINDS_DIR, new String[0]);
        this.CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR, new String[0]);
    }
    
    public boolean set_active_config_folder(final String folder) {
        if (folder.equals(this.ACTIVE_CONFIG_FOLDER)) {
            return false;
        }
        this.ACTIVE_CONFIG_FOLDER = "WURSTPLUSTWO/configs/" + folder;
        this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER, new String[0]);
        this.CURRENT_CONFIG_DIR = "WURSTPLUSTWO/WURSTPLUSTWO/configs/" + this.ACTIVE_CONFIG_FOLDER;
        this.CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR, new String[0]);
        this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
        this.BINDS_PATH = Paths.get(this.BINDS_DIR, new String[0]);
        this.load_settings();
        return true;
    }
    
    private void save_ezmessage() throws IOException {
        final FileWriter writer = new FileWriter("WURSTPLUSTWO/ez.txt");
        try {
            writer.write(WurstplusEzMessageUtil.get_message());
        }
        catch (Exception ignored) {
            writer.write("test message");
        }
        writer.close();
    }
    
    private void load_ezmessage() throws IOException {
        final StringBuilder sb = new StringBuilder();
        for (final String s : Files.readAllLines(this.EZ_PATH)) {
            sb.append(s);
        }
        WurstplusEzMessageUtil.set_message(sb.toString());
    }
    
    private void save_drawn() throws IOException {
        final FileWriter writer = new FileWriter("WURSTPLUSTWO/drawn.txt");
        for (final String s : WurstplusDrawnUtil.hidden_tags) {
            writer.write(s + System.lineSeparator());
        }
        writer.close();
    }
    
    private void load_drawn() throws IOException {
        WurstplusDrawnUtil.hidden_tags = Files.readAllLines(this.DRAWN_PATH);
    }
    
    private void save_friends() throws IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson((Object)WurstplusFriendUtil.friends);
        final OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("WURSTPLUSTWO/friends.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }
    
    private void load_friends() throws IOException {
        final Gson gson = new Gson();
        final Reader reader = Files.newBufferedReader(Paths.get("WURSTPLUSTWO/friends.json", new String[0]));
        WurstplusFriendUtil.friends = (ArrayList<WurstplusFriendUtil.Friend>)gson.fromJson(reader, new TypeToken<ArrayList<WurstplusFriendUtil.Friend>>() {}.getType());
        reader.close();
    }
    
    private void save_enemies() throws IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson((Object)WurstplusEnemyUtil.enemies);
        final OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("WURSTPLUSTWO/enemies.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }
    
    private void load_enemies() throws IOException {
        final Gson gson = new Gson();
        final Reader reader = Files.newBufferedReader(Paths.get("WURSTPLUSTWO/enemies.json", new String[0]));
        WurstplusEnemyUtil.enemies = (ArrayList<WurstplusEnemyUtil.Enemy>)gson.fromJson(reader, new TypeToken<ArrayList<WurstplusEnemyUtil.Enemy>>() {}.getType());
        reader.close();
    }
    
    private void save_hacks() throws IOException {
        for (final WurstplusHack hack : Wurstplus.get_hack_manager().get_array_hacks()) {
            final String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
            final Path file_path = Paths.get(file_name, new String[0]);
            this.delete_file(file_name);
            this.verify_file(file_path);
            final File file = new File(file_name);
            final BufferedWriter br = new BufferedWriter(new FileWriter(file));
            for (final WurstplusSetting setting : Wurstplus.get_setting_manager().get_settings_with_hack(hack)) {
                final String get_type = setting.get_type();
                switch (get_type) {
                    case "button": {
                        br.write(setting.get_tag() + ":" + setting.get_value(true) + "\r\n");
                        continue;
                    }
                    case "combobox": {
                        br.write(setting.get_tag() + ":" + setting.get_current_value() + "\r\n");
                        continue;
                    }
                    case "label": {
                        br.write(setting.get_tag() + ":" + setting.get_value("") + "\r\n");
                        continue;
                    }
                    case "doubleslider": {
                        br.write(setting.get_tag() + ":" + setting.get_value(1.0) + "\r\n");
                        continue;
                    }
                    case "integerslider": {
                        br.write(setting.get_tag() + ":" + setting.get_value(1) + "\r\n");
                        continue;
                    }
                }
            }
            br.close();
        }
    }
    
    private void load_hacks() throws IOException {
        for (final WurstplusHack hack : Wurstplus.get_hack_manager().get_array_hacks()) {
            final String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
            final File file = new File(file_name);
            final FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream di_stream = new DataInputStream(fi_stream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));
            final List<String> bugged_lines = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    final String colune = line.trim();
                    final String tag = colune.split(":")[0];
                    final String value = colune.split(":")[1];
                    final WurstplusSetting setting = Wurstplus.get_setting_manager().get_setting_with_tag(hack, tag);
                    try {
                        final String get_type = setting.get_type();
                        switch (get_type) {
                            case "button": {
                                setting.set_value(Boolean.parseBoolean(value));
                                continue;
                            }
                            case "label": {
                                setting.set_value(value);
                                continue;
                            }
                            case "doubleslider": {
                                setting.set_value(Double.parseDouble(value));
                                continue;
                            }
                            case "integerslider": {
                                setting.set_value(Integer.parseInt(value));
                                continue;
                            }
                            case "combobox": {
                                setting.set_current_value(value);
                                continue;
                            }
                        }
                    }
                    catch (Exception e) {
                        bugged_lines.add(colune);
                        Wurstplus.send_minecraft_log("Error loading '" + value + "' to setting '" + tag + "'");
                        break;
                    }
                }
                catch (Exception ex) {}
            }
            br.close();
        }
    }
    
    private void save_client() throws IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonParser parser = new JsonParser();
        final JsonObject main_json = new JsonObject();
        final JsonObject config = new JsonObject();
        final JsonObject gui = new JsonObject();
        config.add("name", (JsonElement)new JsonPrimitive(Wurstplus.get_name()));
        config.add("version", (JsonElement)new JsonPrimitive(Wurstplus.get_version()));
        config.add("user", (JsonElement)new JsonPrimitive(Wurstplus.get_actual_user()));
        config.add("prefix", (JsonElement)new JsonPrimitive(WurstplusCommandManager.get_prefix()));
        for (final WurstplusFrame frames_gui : Wurstplus.click_gui.get_array_frames()) {
            final JsonObject frame_info = new JsonObject();
            frame_info.add("name", (JsonElement)new JsonPrimitive(frames_gui.get_name()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(frames_gui.get_tag()));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)frames_gui.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)frames_gui.get_y()));
            gui.add(frames_gui.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("configuration", (JsonElement)config);
        main_json.add("gui", (JsonElement)gui);
        final JsonElement json_pretty = parser.parse(main_json.toString());
        final String json = gson.toJson(json_pretty);
        final OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("WURSTPLUSTWO/client.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }
    
    private void load_client() throws IOException {
        final InputStream stream = Files.newInputStream(this.CLIENT_PATH, new OpenOption[0]);
        final JsonObject json_client = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        final JsonObject json_config = json_client.get("configuration").getAsJsonObject();
        final JsonObject json_gui = json_client.get("gui").getAsJsonObject();
        WurstplusCommandManager.set_prefix(json_config.get("prefix").getAsString());
        for (final WurstplusFrame frames : Wurstplus.click_gui.get_array_frames()) {
            final JsonObject frame_info = json_gui.get(frames.get_tag()).getAsJsonObject();
            final WurstplusFrame frame_requested = Wurstplus.click_gui.get_frame_with_tag(frame_info.get("tag").getAsString());
            frame_requested.set_x(frame_info.get("x").getAsInt());
            frame_requested.set_y(frame_info.get("y").getAsInt());
        }
        stream.close();
    }
    
    private void save_hud() throws IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonParser parser = new JsonParser();
        final JsonObject main_json = new JsonObject();
        final JsonObject main_frame = new JsonObject();
        final JsonObject main_hud = new JsonObject();
        main_frame.add("name", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_name()));
        main_frame.add("tag", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_tag()));
        main_frame.add("x", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_x()));
        main_frame.add("y", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_y()));
        for (final WurstplusPinnable pinnables_hud : Wurstplus.get_hud_manager().get_array_huds()) {
            final JsonObject frame_info = new JsonObject();
            frame_info.add("title", (JsonElement)new JsonPrimitive(pinnables_hud.get_title()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(pinnables_hud.get_tag()));
            frame_info.add("state", (JsonElement)new JsonPrimitive(Boolean.valueOf(pinnables_hud.is_active())));
            frame_info.add("dock", (JsonElement)new JsonPrimitive(Boolean.valueOf(pinnables_hud.get_dock())));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_y()));
            main_hud.add(pinnables_hud.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("frame", (JsonElement)main_frame);
        main_json.add("hud", (JsonElement)main_hud);
        final JsonElement pretty_json = parser.parse(main_json.toString());
        final String json = gson.toJson(pretty_json);
        this.delete_file("WURSTPLUSTWO/hud.json");
        this.verify_file(this.HUD_PATH);
        final OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("WURSTPLUSTWO/hud.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }
    
    private void load_hud() throws IOException {
        final InputStream input_stream = Files.newInputStream(this.HUD_PATH, new OpenOption[0]);
        final JsonObject main_hud = new JsonParser().parse((Reader)new InputStreamReader(input_stream)).getAsJsonObject();
        final JsonObject main_frame = main_hud.get("frame").getAsJsonObject();
        final JsonObject main_huds = main_hud.get("hud").getAsJsonObject();
        Wurstplus.click_hud.get_frame_hud().set_x(main_frame.get("x").getAsInt());
        Wurstplus.click_hud.get_frame_hud().set_y(main_frame.get("y").getAsInt());
        for (final WurstplusPinnable pinnables : Wurstplus.get_hud_manager().get_array_huds()) {
            final JsonObject hud_info = main_huds.get(pinnables.get_tag()).getAsJsonObject();
            final WurstplusPinnable pinnable_requested = Wurstplus.get_hud_manager().get_pinnable_with_tag(hud_info.get("tag").getAsString());
            pinnable_requested.set_active(hud_info.get("state").getAsBoolean());
            pinnable_requested.set_dock(hud_info.get("dock").getAsBoolean());
            pinnable_requested.set_x(hud_info.get("x").getAsInt());
            pinnable_requested.set_y(hud_info.get("y").getAsInt());
        }
        input_stream.close();
    }
    
    private void save_binds() throws IOException {
        final String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        final Path file_path = Paths.get(file_name, new String[0]);
        this.delete_file(file_name);
        this.verify_file(file_path);
        final File file = new File(file_name);
        final BufferedWriter br = new BufferedWriter(new FileWriter(file));
        for (final WurstplusHack modules : Wurstplus.get_hack_manager().get_array_hacks()) {
            br.write(modules.get_tag() + ":" + modules.get_bind(1) + ":" + modules.is_active() + "\r\n");
        }
        br.close();
    }
    
    private void load_binds() throws IOException {
        final String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        final File file = new File(file_name);
        final FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
        final DataInputStream di_stream = new DataInputStream(fi_stream);
        final BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));
        String line;
        while ((line = br.readLine()) != null) {
            try {
                final String colune = line.trim();
                final String tag = colune.split(":")[0];
                final String bind = colune.split(":")[1];
                final String active = colune.split(":")[2];
                final WurstplusHack module = Wurstplus.get_hack_manager().get_module_with_tag(tag);
                module.set_bind(Integer.parseInt(bind));
                module.set_active(Boolean.parseBoolean(active));
            }
            catch (Exception ex) {}
        }
        br.close();
    }
    
    public void save_settings() {
        try {
            this.verify_dir(this.MAIN_FOLDER_PATH);
            this.verify_dir(this.CONFIGS_FOLDER_PATH);
            this.verify_dir(this.ACTIVE_CONFIG_FOLDER_PATH);
            this.save_hacks();
            this.save_binds();
            this.save_friends();
            this.save_enemies();
            this.save_client();
            this.save_drawn();
            this.save_ezmessage();
            this.save_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log("Something has gone wrong while saving settings");
            Wurstplus.send_minecraft_log(e.toString());
        }
    }
    
    public void load_settings() {
        try {
            this.load_binds();
            this.load_client();
            this.load_drawn();
            this.load_enemies();
            this.load_ezmessage();
            this.load_friends();
            this.load_hacks();
            this.load_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log("Something has gone wrong while loading settings");
            Wurstplus.send_minecraft_log(e.toString());
        }
    }
    
    public boolean delete_file(final String path) throws IOException {
        final File f = new File(path);
        return f.delete();
    }
    
    public void verify_file(final Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
    }
    
    public void verify_dir(final Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectory(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
    }
}
