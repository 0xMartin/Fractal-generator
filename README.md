# Fractal generator
 Program can ganerete mandelbrot or julia fractal. User can zoom in or out, change color of fractal, save image of fractal or print image.
 
<img src="https://github.com/0xMartin/Fractal-generator/blob/master/img1.PNG" width="70%">

## Menu
* File
  * New - create new fractal
  * Save - save fractal as image
  * Print - print fractal
* Tools
  * Render - render fractal
  * Grids - show/hide grids
  * Auto render (check box) - automatic render on mouse wheel scroll
  * Iteration analyser
* View
  * Zoom +
  * Zoom -
* About

## Mandelbrot fractal

<img src="https://github.com/0xMartin/Fractal-generator/blob/master/img2.PNG" width="70%">

## Julia fractal

<img src="https://github.com/0xMartin/Fractal-generator/blob/master/img3.PNG" width="70%">

## Rendering
Rendering of [mandelbrot fractal](https://github.com/0xMartin/Fractal-generator/blob/master/src/src/fractal_generator/Generator/Mandelbroth.java) use 4 threads.
```java
private void render(Graphics2D g2, int it, Range re, Range im, Dimension size, double[] data) {

        double RePerTick = re.getDistance()/2.0d;
        double ImPerTick = im.getDistance()/2.0d;
        
        Range re1 = new Range(
                re.START,
                re.START+RePerTick
        );
        
        Range re2 = new Range(
                re.START+RePerTick,
                re.START+RePerTick*2.0d
        );
        
        Range im1 = new Range(
                im.START,
                im.START+ImPerTick
        );
        
        Range im2 = new Range(
                im.START+ImPerTick,
                im.START+ImPerTick*2.0d
        );
        
        Dimension s = new Dimension((int) (size.width/2.0f), (int) (size.height/2.0f));
        
        SubThread t1 = new SubThread(it,re1,im1,s,data);
        t1.thread.start();
        SubThread t2 = new SubThread(it,re1,im2,s,data);
        t2.thread.start();
        SubThread t3 = new SubThread(it,re2,im1,s,data);
        t3.thread.start();
        SubThread t4 = new SubThread(it,re2,im2,s,data);
        t4.thread.start();
        
        while(t1.status!=100||t2.status!=100||t3.status!=100||t4.status!=100){
            this.status = (t1.status+t2.status+t3.status+t4.status)/4f;
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mandelbroth.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ImageObserver ob = null;
        g2.drawImage(t1.image, 0, 0, ob);
        g2.drawImage(t2.image, 0, s.height, ob);
        g2.drawImage(t3.image, s.width, 0, ob);
        g2.drawImage(t4.image, s.width, s.height, ob);
    }
```

## Author
Martin Krčma

## License
This project is licensed under GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/0xMartin/Fractal-generator/blob/master/LICENSE) file for details
