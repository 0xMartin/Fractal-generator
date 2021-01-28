// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Data
{
    public static ArrayList<BufferedImage> images;
    public static ArrayList<long[][]> iterations;
    
    static {
        Data.images = new ArrayList<BufferedImage>();
    }
}
