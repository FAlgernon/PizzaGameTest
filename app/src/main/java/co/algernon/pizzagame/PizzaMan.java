package co.algernon.pizzagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


/**
 * Created by Fruanku on 10/8/2016.
 */

public class PizzaMan extends GameObject {
    private Bitmap spritesheet;
    private Animation headAnimation = new Animation();
    private Animation bodyAnimation = new Animation();
    private Animation pizzaAnimation = new Animation();

    private Bitmap[] headAnimationFrames;
    private Bitmap[] bodyAnimationFrames;
    private Bitmap[] pizzaAnimationFrames;

    private int headOffsetX = 100;
    private int headOffsetY = 0;

    private int bodyOffsetX = -30;
    private int bodyOffsetY = 60;

    private int pizzaOffsetX = 0;
    private int pizzaOffsetY = 100;

    private int scaleFactor = 8;

    private int pizzaRotation = 0;

    private long startTime;

    private int tmpPizzaWidth, tmpPizzaHeight;

    public PizzaMan(Bitmap[] bitmaps){
        headAnimationFrames = new Bitmap[3];
        headAnimationFrames[0] = bitmaps[0];
        headAnimationFrames[1] = bitmaps[1];
        headAnimationFrames[2] = bitmaps[2];

        bodyAnimationFrames = new Bitmap[3];
        bodyAnimationFrames[0] = bitmaps[3];
        bodyAnimationFrames[1] = bitmaps[4];
        bodyAnimationFrames[2] = bitmaps[5];

        pizzaAnimationFrames = new Bitmap[4];
        pizzaAnimationFrames[0] = bitmaps[6];
        pizzaAnimationFrames[1] = bitmaps[7];
        pizzaAnimationFrames[2] = bitmaps[8];
        pizzaAnimationFrames[3] = bitmaps[9];



        headAnimation.setFrames(headAnimationFrames);
        headAnimation.setDelay(800);

        bodyAnimation.setFrames(bodyAnimationFrames);
        bodyAnimation.setDelay(300);

        pizzaAnimation.setFrames(pizzaAnimationFrames);
        pizzaAnimation.setDelay(200);

    }

    public void update(){

        headAnimation.update();
        bodyAnimation.update();
        pizzaAnimation.update();
    }

    public void draw(Canvas canvas){

        setX(canvas.getWidth()/2 - 150);
        setY(canvas.getHeight()/2 - 150);
        canvas.drawBitmap(Bitmap.createScaledBitmap( bodyAnimation.getImage(), bodyAnimation.getImage().getWidth()*scaleFactor, bodyAnimation.getImage().getHeight()*scaleFactor, false ),x + bodyOffsetX,y+bodyOffsetY,null);
        canvas.drawBitmap(Bitmap.createScaledBitmap( headAnimation.getImage(), headAnimation.getImage().getWidth()*scaleFactor, headAnimation.getImage().getHeight()*scaleFactor, false ),x + headOffsetX,y+headOffsetY,null);

        Matrix matrix = new Matrix();
        pizzaRotation += 1;

        tmpPizzaWidth = pizzaAnimation.getImage().getWidth()*scaleFactor;
        tmpPizzaHeight = pizzaAnimation.getImage().getHeight()*scaleFactor;

        float px = tmpPizzaWidth/2;
        float py = tmpPizzaHeight/2;
        matrix.postTranslate(-tmpPizzaWidth/2, -tmpPizzaHeight/2);
        matrix.postRotate(pizzaRotation);
        matrix.postTranslate(x+pizzaOffsetX+px, y+pizzaOffsetY+py);
        //canvas.drawBitmap(pizzaAnimation.getImage(), matrix, null);


        //canvas.drawBitmap(Bitmap.createScaledBitmap( pizzaAnimation.getImage(), pizzaAnimation.getImage().getWidth()*scaleFactor, pizzaAnimation.getImage().getHeight()*scaleFactor, false ),x + pizzaOffsetX,y+pizzaOffsetY,null);

        canvas.drawBitmap(Bitmap.createScaledBitmap( pizzaAnimation.getImage(), tmpPizzaWidth, tmpPizzaHeight, false ), matrix ,null);



    }



}
