package co.algernon.pizzagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza_2),
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza_3),
                BitmapFactory.decodeResource(getResources(), R.drawable.pizza_4)};
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
        //pizza.update();
        pizzaMan.update();
        bg.update();
    }
    @Override
    public void draw(Canvas canvas)
    {


        final float scaleFactorX = (float)getWidth() / WIDTH;
        final float scaleFactorY = (float)getHeight() / HEIGHT;

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            bg.draw(canvas);
            pizzaMan.draw(canvas);
            //pizza.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        //
        int action = MotionEventCompat.getActionMasked(event);
        // update the pizza
        if(event.getX() > this.getWidth()/2){
            pizzaMan.bumpLeft();
            //System.out.println("RIGHT SIDE");

        }else{
            pizzaMan.bumpRight();
            //System.out.println("LEFT SIDE");
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