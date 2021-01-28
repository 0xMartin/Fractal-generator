// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator.Generator;

public class Range
{
    public double START;
    public double END;
    
    public Range(final double s, final double e) {
        this.START = s;
        this.END = e;
    }
    
    public double getDistance() {
        return Math.abs(this.START - this.END);
    }
}
