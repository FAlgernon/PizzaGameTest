package co.algernon.pizzagame;

import android.content.Context;
import android.graphics.*;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Fruanku on 10/6/2016.
 */



public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 480;
    public static final int HEIGHT = 856;
    private MainThread thread;
    private Background bg;
   // private PizzaPizza pizza;
    private PizzaMan pizzaMan;
    private Bitmap[] pizzaManAssets;
    private Bitmap[] uiAssets;
    private Paint paint = new Paint();
    private Bitmap scaleTest;
    private int uiScale = 4;
    private Animation gameOverAnimation = new Animation();


    public GamePanel(Context context)
    {
        super(context);




        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);

        pizzaManAssets = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_head_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_head_2),
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_head_3),
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_2),
                BitmapFactory.decodeResource(getResources(), R.drawable.chef_3),
                BitmapFactory.decodeResource(getResources(), R.drawable.sprite_pizza0),
                BitmapFactory.decodeResource(getResources(), R.drawable.sprite_pizza1),
                BitmapFactory.decodeResource(getResources(), R.drawable.sprite_pizza2),
                BitmapFactory.decodeResource(getResources(), R.drawable.sprite_pizza3),
                BitmapFactory.decodeResource(getResources(), R.drawable.background_small_widebounds),
                BitmapFactory.decodeResource(getResources(), R.drawable.armtoss_l_spritesheet),
                BitmapFactory.decodeResource(getResources(), R.drawable.armtoss_r_spritesheet)
        };

        uiAssets = new Bitmap[] {
                BitmapFactory.decodeResource(getResources(), R.drawable.mancato),
                BitmapFactory.decodeResource(getResources(), R.drawable.mancato_off),
                BitmapFactory.decodeResource(getResources(), R.drawable.purplehaze)
        };

        gameOverAnimation.setFrames(new Bitmap[]{uiAssets[0],uiAssets[1]});
        gameOverAnimation.setDelay(900);

        scaleTest = BitmapFactory.decodeResource(getResources(), R.drawable.background_small_widebounds);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        bg.setVector(-1);

        pizzaMan = new PizzaMan(pizzaManAssets);

        //pizza = new PizzaPizza();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
/*    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return super.onTouchEvent(event);
    }*/

    public void update()
    {

        bg.update();
        //pizza.update();
        pizzaMan.update();
        if(!pizzaMan.gameOver) {


        }else{
            gameOverAnimation.update();

            //wait
            // game restart

        }
    }
    @Override
    public void draw(Canvas canvas)
    {


        final float scaleFactorX = (float)getWidth() / WIDTH;
        final float scaleFactorY = (float)getHeight() / HEIGHT;

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            paint.setAntiAlias(false);
            paint.setDither(true);
            paint.setFilterBitmap(false);
//DRAWTEST
            Rect rectangle = new Rect(0,0,scaleTest.getWidth()*10,scaleTest.getHeight()*10);
            canvas.drawBitmap(scaleTest, null, rectangle, paint);

            //bg.draw(canvas);
            pizzaMan.draw(canvas);

            if(pizzaMan.gameOver){


                canvas.drawBitmap(uiAssets[2], null, rectangle, paint);
                canvas.drawBitmap(Bitmap.createScaledBitmap(gameOverAnimation.getImage(), uiAssets[0].getWidth() * uiScale, uiAssets[0].getHeight() * uiScale, false), 0, (canvas.getHeight()/4)-(gameOverAnimation.getHeight()/2), paint);
            }
            //pizza.draw(canvas);

/*            paint.setAntiAlias(false);
            paint.setDither(true);
            paint.setFilterBitmap(false);*/





            canvas.restoreToCount(savedState);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        //
        int action = MotionEventCompat.getActionMasked(event);
        // update the pizza
        if(pizzaMan.gameOver && !pizzaMan.isWaiting()){
            pizzaMan.gameOver = false;
        }

        if(!pizzaMan.isWaiting()){
            if (event.getX() > this.getWidth() / 2) {
                pizzaMan.bumpRight();
                System.out.println("RIGHT SIDE");

            } else {
                pizzaMan.bumpLeft();
                System.out.println("LEFT SIDE");
            }
        }
        return super.onTouchEvent(event);
        //what touch action was made...
       /* switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                System.out.println("Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                System.out.println("Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                System.out.println("Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                System.out.println("Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                System.out.println("Movement occurred outside bounds of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
*/

    }


}