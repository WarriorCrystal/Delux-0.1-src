// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.wurstplustwo.util;

public class WurstplusTimer
{
    private long time;
    
    public WurstplusTimer() {
        this.time = -1L;
    }
    
    public boolean passed(final long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }
    
    public WurstplusTimer reset() {
        this.time = System.nanoTime();
        return this;
    }
    
    public long getTime(final long time) {
        return time / 1000000L;
    }
    
    public boolean passedS(final double s) {
        return this.passedMs((long)s * 1000L);
    }
    
    public boolean passedDms(final double dms) {
        return this.passedMs((long)dms * 10L);
    }
    
    public boolean passedDs(final double ds) {
        return this.passedMs((long)ds * 100L);
    }
    
    public boolean passedNS(final long ns) {
        return System.nanoTime() - this.time >= ns;
    }
    
    public long convertToNS(final long time) {
        return time * 1000000L;
    }
    
    public boolean passedMs(final long ms) {
        return this.passedNS(this.convertToNS(ms));
    }
}
