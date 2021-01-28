// 
// Decompiled by Procyon v0.5.36
// 

package fractal_generator.Generator;

import FractalColoring.Data;
import java.awt.Color;

public class FractalManager
{
    public static void createColorSpectrum(final int iterations, final int index) {
        int[] colors = new int[iterations];
        for (int i = 0; i < iterations; ++i) {
            switch (index) {
                case 0: {
                    colors[i] = Color.HSBtoRGB(i / 300.0f, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 1: {
                    colors[i] = Color.HSBtoRGB(i / 256.0f, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 2: {
                    colors[i] = Color.HSBtoRGB(i / 512.0f, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 3: {
                    colors[i] = Color.HSBtoRGB(i / 1024.0f, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 4: {
                    colors[i] = Color.HSBtoRGB(i / (float)iterations, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 5: {
                    colors[i] = Color.HSBtoRGB(1.0f - i / (float)iterations, 1.0f, i / (i + 15.0f));
                    break;
                }
                case 6: {
                    colors[i] = Color.HSBtoRGB(1.0f - i / 300.0f, 1.0f, i / (i + 15.0f));
                    break;
                }
            }
        }
        if (index == 7) {
            colors = Data.colors;
        }
        Data.colors = colors;
    }
    
    public static int getImageType(final int index) {
        int imageType = 0;
        switch (index) {
            case 0: {
                imageType = 2;
                break;
            }
            case 1: {
                imageType = 5;
                break;
            }
            case 2: {
                imageType = 6;
                break;
            }
            case 3: {
                imageType = 7;
                break;
            }
            case 4: {
                imageType = 12;
                break;
            }
            case 5: {
                imageType = 10;
                break;
            }
            case 6: {
                imageType = 13;
                break;
            }
            case 7: {
                imageType = 3;
                break;
            }
            case 8: {
                imageType = 4;
                break;
            }
            default: {
                imageType = 2;
                break;
            }
        }
        return imageType;
    }
}
