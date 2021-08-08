// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.turok;

import me.travis.turok.draw.GL;
import me.travis.turok.task.TurokFont;

public class Turok
{
    private String tag;
    private TurokFont font_manager;
    
    public Turok(final String tag) {
        this.tag = tag;
    }
    
    public void resize(final int x, final int y, final float size) {
        GL.resize(x, y, size);
    }
    
    public void resize(final int x, final int y, final float size, final String tag) {
        GL.resize(x, y, size, "end");
    }
    
    public TurokFont get_font_manager() {
        return this.font_manager;
    }
}
