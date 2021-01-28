// 
// Decompiled by Procyon v0.5.36
// 

package FractalColoring;

import java.util.Iterator;
import java.awt.Color;
import java.util.ArrayList;

public class ColoringManager extends Data
{
    public ArrayList<ColorPoint> colorPoints;
    
    public ColoringManager() {
        this.colorPoints = new ArrayList<ColorPoint>();
    }
    
    public void clear(final int iterations) {
        ColoringManager.colors = new int[iterations];
    }
    
    public void addNewColorPoint(final int x, final float decrease, final int range, final Color c) {
        this.colorPoints.add(new ColorPoint(x, range, c));
    }
    
    public void computeColoring(final int iterations) {
        ColoringManager.colors = new int[iterations];
        for (final ColorPoint cp : this.colorPoints) {
            try {
                final Color cc = cp.color;
                int rc = cc.getRed() + Color.getColor("", ColoringManager.colors[cp.X]).getRed();
                int gc = cc.getGreen() + Color.getColor("", ColoringManager.colors[cp.X]).getGreen();
                int bc = cc.getBlue() + Color.getColor("", ColoringManager.colors[cp.X]).getBlue();
                if (rc > 255) {
                    rc = 255;
                }
                if (gc > 255) {
                    gc = 255;
                }
                if (bc > 255) {
                    bc = 255;
                }
                if (rc < 0) {
                    rc = 0;
                }
                if (gc < 0) {
                    gc = 0;
                }
                if (bc < 0) {
                    bc = 0;
                }
                ColoringManager.colors[cp.X] = new Color(rc, gc, bc).getRGB();
                try {
                    for (int i = 1; cp.X + i < ColoringManager.colors.length; ++i) {
                        Color c = cp.color;
                        int r = (int)(c.getRed() - c.getRed() * (i / (float)cp.range));
                        if (r < 0) {
                            r = 0;
                        }
                        if (r > 255) {
                            r = 255;
                        }
                        r += Color.getColor("", ColoringManager.colors[cp.X + i]).getRed();
                        if (r < 0) {
                            r = 0;
                        }
                        if (r > 255) {
                            r = 255;
                        }
                        int g = (int)(c.getGreen() - c.getGreen() * (i / (float)cp.range));
                        if (g < 0) {
                            g = 0;
                        }
                        if (g > 255) {
                            g = 255;
                        }
                        g += Color.getColor("", ColoringManager.colors[cp.X + i]).getGreen();
                        if (g < 0) {
                            g = 0;
                        }
                        if (g > 255) {
                            g = 255;
                        }
                        int b = (int)(c.getBlue() - c.getBlue() * (i / (float)cp.range));
                        if (b < 0) {
                            b = 0;
                        }
                        if (b > 255) {
                            b = 255;
                        }
                        b += Color.getColor("", ColoringManager.colors[cp.X + i]).getBlue();
                        if (b < 0) {
                            b = 0;
                        }
                        if (b > 255) {
                            b = 255;
                        }
                        c = new Color(r, g, b);
                        ColoringManager.colors[cp.X + i] = c.getRGB();
                    }
                }
                catch (Exception ex) {}
                for (int i = -1; cp.X + i >= 0; --i) {
                    Color c = cp.color;
                    int r = (int)(c.getRed() + c.getRed() * (i / (float)cp.range));
                    if (r < 0) {
                        r = 0;
                    }
                    if (r > 255) {
                        r = 255;
                    }
                    r += Color.getColor("", ColoringManager.colors[cp.X + i]).getRed();
                    if (r < 0) {
                        r = 0;
                    }
                    if (r > 255) {
                        r = 255;
                    }
                    int g = (int)(c.getGreen() + c.getGreen() * (i / (float)cp.range));
                    if (g < 0) {
                        g = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    g += Color.getColor("", ColoringManager.colors[cp.X + i]).getGreen();
                    if (g < 0) {
                        g = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    int b = (int)(c.getBlue() + c.getBlue() * (i / (float)cp.range));
                    if (b < 0) {
                        b = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    b += Color.getColor("", ColoringManager.colors[cp.X + i]).getBlue();
                    if (b < 0) {
                        b = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    c = new Color(r, g, b);
                    ColoringManager.colors[cp.X + i] = c.getRGB();
                }
            }
            catch (Exception ex2) {}
        }
    }
    
    public void removeLast() {
        try {
            this.colorPoints.remove(this.colorPoints.size() - 1);
        }
        catch (Exception ex) {}
    }
}
