// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

import java.util.ArrayList;
import java.util.List;

public class WurstplusDrawnUtil
{
    public static List<String> hidden_tags;
    
    public static void add_remove_item(String s) {
        s = s.toLowerCase();
        if (WurstplusDrawnUtil.hidden_tags.contains(s)) {
            WurstplusMessageUtil.send_client_message("Added " + s);
            WurstplusDrawnUtil.hidden_tags.remove(s);
        }
        else {
            WurstplusMessageUtil.send_client_message("Removed " + s);
            WurstplusDrawnUtil.hidden_tags.add(s);
        }
    }
    
    static {
        WurstplusDrawnUtil.hidden_tags = new ArrayList<String>();
    }
}
