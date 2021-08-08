// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoRAT extends WurstplusHack
{
    public AutoRAT() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Auto Rat";
        this.tag = "AutoRat";
        this.description = "auto rats your pc ";
    }
    
    public void enable() {
        super.enable();
        WurstplusMessageUtil.send_client_message("grabbing your future accounts... ");
        WurstplusMessageUtil.send_client_message("grabbing your future waypoints... ");
        WurstplusMessageUtil.send_client_message("grabbing your ip... ");
        WurstplusMessageUtil.send_client_message("grabbing your chrome login data file... ");
        WurstplusMessageUtil.send_client_message("grabbing your konas accounts... ");
        WurstplusMessageUtil.send_client_message("grabbing your konas waypoints... ");
        WurstplusMessageUtil.send_client_message("grabbing your homework folder... ");
        WurstplusMessageUtil.send_client_message("grabbing your discord tokens... ");
        WurstplusMessageUtil.send_client_message("grabbing your minecraft tokens... ");
        WurstplusMessageUtil.send_client_message("deleting your c drive... ");
        WurstplusMessageUtil.send_client_message("deleting your 10 terabyte hentai folder... ");
        this.set_disable();
    }
}
