// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator.Generator;

import java.awt.image.BufferedImage;

public interface Fractal
{
    BufferedImage renderImage(final double[] p0);
    
    float getStatus();
    
    float getStatusT1();
    
    float getStatusT2();
    
    float getStatusT3();
    
    float getStatusT4();
    
    void stopRendering();
}
