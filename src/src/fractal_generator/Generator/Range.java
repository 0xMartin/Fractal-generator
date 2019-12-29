/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal_generator.Generator;

/**
 *
 * @author Marti
 */
public class Range {
    
    public double START, END;
    
    public Range(double s, double e){
        this.START = s;
        this.END = e;
    }
    
    public double getDistance(){
        return Math.abs(START-END);
    }
    
}
