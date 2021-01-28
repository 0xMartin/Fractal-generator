// 
// Decompiled by Procyon v0.5.36
// 

package FractalColoring;

import java.awt.Color;

public class ColorPoint
{
    public int X;
    public int range;
    public Color color;
    
    public ColorPoint(final int x, final int r, final Color c) {
        this.X = x;
        this.range = r;
        this.color = c;
    }
}
