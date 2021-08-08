// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.other.Gamesense;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class BlockChangeEvent extends WurstplusEventCancellable
{
    private final BlockPos position;
    private final Block block;
    
    public BlockChangeEvent(final BlockPos position, final Block block) {
        this.position = position;
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
}
