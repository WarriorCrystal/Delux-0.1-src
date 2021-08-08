// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.command;

import java.util.function.Function;
import java.util.Comparator;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusConfig;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusEnemy;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusEzMessage;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusDrawn;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusFriend;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusHelp;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusAlert;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusToggle;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusSettings;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusPrefix;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusBind;
import net.minecraft.util.text.Style;
import me.travis.turok.values.TurokString;
import java.util.HashMap;
import java.util.ArrayList;

public class WurstplusCommands
{
    public static ArrayList<WurstplusCommand> command_list;
    static HashMap<String, WurstplusCommand> list_command;
    public static final TurokString prefix;
    public final Style style;
    
    public WurstplusCommands(final Style style_) {
        this.style = style_;
        add_command(new WurstplusBind());
        add_command(new WurstplusPrefix());
        add_command(new WurstplusSettings());
        add_command(new WurstplusToggle());
        add_command(new WurstplusAlert());
        add_command(new WurstplusHelp());
        add_command(new WurstplusFriend());
        add_command(new WurstplusDrawn());
        add_command(new WurstplusEzMessage());
        add_command(new WurstplusEnemy());
        add_command(new WurstplusConfig());
        WurstplusCommands.command_list.sort(Comparator.comparing((Function<? super WurstplusCommand, ? extends Comparable>)WurstplusCommand::get_name));
    }
    
    public static void add_command(final WurstplusCommand command) {
        WurstplusCommands.command_list.add(command);
        WurstplusCommands.list_command.put(command.get_name().toLowerCase(), command);
    }
    
    public String[] get_message(final String message) {
        String[] arguments = new String[0];
        if (this.has_prefix(message)) {
            arguments = message.replaceFirst(WurstplusCommands.prefix.get_value(), "").split(" ");
        }
        return arguments;
    }
    
    public boolean has_prefix(final String message) {
        return message.startsWith(WurstplusCommands.prefix.get_value());
    }
    
    public void set_prefix(final String new_prefix) {
        WurstplusCommands.prefix.set_value(new_prefix);
    }
    
    public String get_prefix() {
        return WurstplusCommands.prefix.get_value();
    }
    
    public static ArrayList<WurstplusCommand> get_pure_command_list() {
        return WurstplusCommands.command_list;
    }
    
    public static WurstplusCommand get_command_with_name(final String name) {
        return WurstplusCommands.list_command.get(name.toLowerCase());
    }
    
    static {
        WurstplusCommands.command_list = new ArrayList<WurstplusCommand>();
        WurstplusCommands.list_command = new HashMap<String, WurstplusCommand>();
        prefix = new TurokString("Prefix", "Prefix", ".");
    }
}
