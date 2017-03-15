package co.algernon.pizzagame;

import android.content.Context;
import android.graphics.*;
import android.graphics.BitmapFactory;


/**
 * Created by Fruanku on 10/8/2016.
 */

public class PizzaMan extends GameObject {

    private Animation headAnimation = new Animation();
    private Animation bodyAnimation = new Animation();
    private Animation leftArmAnimation = new Animation();
    private Animation rightArmAnimation = new Animation();
    private Animation pizzaAnimation = new Animation();

    private Bitmap[] headAnimationFrames;
    private Bitmap[] bodyAnimationFrames;
    private Bitmap[] pizzaAnimationFrames;

    private SpriteSheet leftArmAnimationFrames;
    private SpriteSheet rightArmAnimationFrames;

    private Bitmap scaleTest;

    private Offset rootOffset = new Offset(0,0);
    private Offset headOffset = new Offset(10,-4);
    private Offset bodyOffset = new Offset(0,10);
    private Offset pizzaOffset = new Offset(0,0);
    private Offset leftArmOffset = new Offset(0,0);
    private Offset rightArmOffset = new Offset(10,0);

    public boolean gameOver = false;

    private int scaleFactor = 12;

    private float resizeFactor = 1.0f;

    private float pizzaRotation = 0.0f;

    private float pizzaForce = 0.0f;
    private float bumpForce = 9.5f;
    private float bumpHeight = 0.0f;
    private float pizzaGravity = 0.2f;

    private float  elasticity = 0.1f;
    private float  wander = 0.17f;
    private float  spinVelocity = 0f;
    private float  spinAngle = 90f;
    private float  tapNormalize = 0.6f;
    private float  deltaTap = 1000f;
    private float  timeWait = 0f;
    private float  failAngleA = 15f;
    private float  failAngleB = 165f;

    private int waitTime = 3*60;
    private int waitDelta = 0;
    private boolean restartWait = false;

    private long startTime;

    private int tmpPizzaWidth, tmpPizzaHeight;

    private Paint paint = new Paint();

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

        scaleTest = bitmaps[10];

        leftArmAnimationFrames = new SpriteSheet(bitmaps[11], 17, 17, 4);
        rightArmAnimationFrames = new SpriteSheet(bitmaps[12], 17, 17, 4);


        headAnimation.setFrames(headAnimationFrames);
        headAnimation.setDelay(800);
        headOffset.setParent(bodyOffset);

        bodyAnimation.setFrames(bodyAnimationFrames);
        bodyAnimation.setDelay(300);
        bodyOffset.setParent(rootOffset);



        leftArmAnimation.setFrames(leftArmAnimationFrames.getFrames());
        leftArmAnimation.setDelay(300);
        leftArmOffset.setParent(bodyOffset);

        rightArmAnimation.setFrames(rightArmAnimationFrames.getFrames());
        rightArmAnimation.setDelay(300);
        rightArmOffset.setParent(bodyOffset);

        pizzaAnimation.setFrames(pizzaAnimationFrames);
        pizzaAnimation.setDelay(200);
        pizzaOffset.setParent(bodyOffset);


        paint.setAntiAlias(false);
        paint.setDither(false);
        paint.setFilterBitmap(false);


        Offset.scale = scaleFactor;

    }

    public void setXY(Canvas canvas){
        setX(canvas.getWidth()/2 - 150);
        setY(canvas.getHeight()/2 - 150);
    }

    public void update(){

        if(!gameOver) {
            headAnimation.update();
            bodyAnimation.update();
            pizzaAnimation.update();
            leftArmAnimation.update();
            rightArmAnimation.update();

            spinVelocity += Math.random() * (wander * 2) - wander;
            spinAngle += spinVelocity;


            if(bumpHeight >= 0.01f){
                bumpHeight += pizzaForce;
                pizzaForce -= pizzaGravity;

            }else{
                bumpHeight=0.0f;
                pizzaForce=0.0f;
            }

            //A = 15
            //B = 165
            if (spinAngle<failAngleA || spinAngle>failAngleB) {
                gameOver();
                waitDelta++;
            }


        }else{
            if(restartWait && waitDelta < waitTime){
                waitDelta++;
            }else{
                restartWait=false;
                waitDelta = 0;
            }

        }


    }

    public void bumpLeft(){
        //pizzaForce -= bumpForce;
        pizzaNormalize(tapNormalize);
        pizzaBump();
    }

    public void bumpRight(){
        //pizzaForce += bumpForce;
        pizzaNormalize(tapNormalize*-1);
        pizzaBump();
    }

    public void pizzaBump(){
        if(bumpHeight<=0.01f) {
            pizzaForce += bumpForce;
            bumpHeight+=1.0f;
            System.out.println("BUMPFORCE");
        }
    }

    public void gameOver(){

        System.out.println("GAME OVER");

        gameOver = true;
        restartWait = true;
        spinAngle = 90f;
        spinVelocity = 0f;
    }

    public boolean isWaiting(){
        return restartWait;
    }

    public void pizzaNormalize(float normalize){
        spinVelocity += normalize;
    }

    public void draw(Canvas canvas){



/*    now = Date.now();
    delta = (this.now - this.then) / 1000; // seconds since last frame*/


        setX(canvas.getWidth()/2 - 150);
        setY(canvas.getHeight()/2 - 150);
        rootOffset.x = x/scaleFactor;
        rootOffset.y = y/scaleFactor;

        Matrix matrix = new Matrix();
        //pizzaRotation += pizzaForce;

        tmpPizzaWidth = pizzaAnimation.getImage().getWidth()*scaleFactor;
        tmpPizzaHeight = pizzaAnimation.getImage().getHeight()*scaleFactor;

        float px = tmpPizzaWidth/2;
        float py = tmpPizzaHeight/2;
        matrix.postTranslate(-tmpPizzaWidth/2, -tmpPizzaHeight/2);
        matrix.postRotate(spinAngle - 90f);
        matrix.postTranslate(pizzaOffset.cx()+px, Math.round(-bumpHeight) + y+pizzaOffset.cy()-py);
        //canvas.drawBitmap(pizzaAnimation.getImage(), matrix, null);

        //DRAWTEST
        Rect rectangle = new Rect(0,0,scaleTest.getWidth()*scaleFactor,scaleTest.getHeight()*scaleFactor);
        //canvas.drawBitmap(scaleTest, null, rectangle, paint);

        //canvas.drawBitmap(Bitmap.createScaledBitmap( pizzaAnimation.getImage(), pizzaAnimation.getImage().getWidth()*scaleFactor, pizzaAnimation.getImage().getHeight()*scaleFactor, false ),x + pizzaOffsetX,y+pizzaOffsetY,null);

        canvas.drawBitmap(Bitmap.createScaledBitmap( bodyAnimation.getImage(), bodyAnimation.getWidth()*scaleFactor, bodyAnimation.getHeight()*scaleFactor, false ),bodyOffset.cx(),bodyOffset.cy(),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap( headAnimation.getImage(), headAnimation.getWidth()*scaleFactor, headAnimation.getHeight()*scaleFactor, false ),headOffset.cx(),headOffset.cy(),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap( leftArmAnimation.getImage(), leftArmAnimation.getWidth()*scaleFactor, leftArmAnimation.getHeight()*scaleFactor, false ),leftArmOffset.cx(),leftArmOffset.cy(),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap( rightArmAnimation.getImage(), rightArmAnimation.getWidth()*scaleFactor, rightArmAnimation.getHeight()*scaleFactor, false ),rightArmOffset.cx(),rightArmOffset.cy(),paint);

        canvas.drawBitmap(
                Bitmap.createScaledBitmap(
                        pizzaAnimation.getImage(),
                        tmpPizzaWidth,
                        tmpPizzaHeight,
                        false ),
                        matrix,
                        paint
        );





    }



}
