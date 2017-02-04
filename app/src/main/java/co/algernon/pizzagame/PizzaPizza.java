package co.algernon.pizzagame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Fruanku on 10/6/2016.
 */

public class PizzaPizza extends GameObject {

    private Rect rectangle;
    private double rotation;
    private Color color;
    private double x;
    private Animation pizzaAnimation;

    public void PizzaPizza(Bitmap[] bitmaps){
        x=0;
        rotation = 0;
        rectangle = new Rect(50,50,50,50);

        pizzaAnimation.setFrames(bitmaps);
        pizzaAnimation.setDelay(100);

    }

    //@Override
    public void draw(Canvas canvas){


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color.RED);

        canvas.drawCircle((float)50, (float)100,(float)50,paint);
        //System.out.println("draw pizza");
    }

    //Override
    public void update(Point point){
        //l,t,r,b
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);
    }

    //@Override
    public void update(){
        x+=0.01;
        pizzaAnimation.update();
    }
}
