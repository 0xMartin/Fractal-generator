/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal_generator.Generator;

import java.awt.image.BufferedImage;

/**
 *
 * @author Marti
 */
public interface Fractal { 
    
    public BufferedImage renderImage(double[] data);
    
    public float getStatus();
    
}
