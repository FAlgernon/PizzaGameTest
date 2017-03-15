package co.algernon.pizzagame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by Fruanku on 3/14/2017.
 */

public class SpriteSheet {
    private Bitmap sprites;
    //private Bitmap frames;
    private int width = 10;
    private int height = 10;
    private int frameCount = 0;
    private int currentFrame = 0;
    private int row = 0;
    private int col = 0;

    public SpriteSheet(Bitmap sprites, int width, int height, int frameCount){
        //Bitmap frames = Bitmap.createBitmap(10,10,Bitmap.Config.RGB_565);
        this.sprites = sprites;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
    }

    public Bitmap nextFrame(){
        Bitmap pixels;
        // Prevent out of bounds exceptions.
        if (currentFrame < 0 || currentFrame >= frameCount) {
            currentFrame = 0;
            return null;
        }else{
            pixels = Bitmap.createBitmap(sprites, col, (currentFrame * height), width, height);
            currentFrame++;
        }

        return pixels;
    }

    public Bitmap[] getFrames(){
        Bitmap[] frames = new Bitmap[frameCount];
        for(int i=0; i<frameCount; i++){
            frames[i] = Bitmap.createBitmap(sprites, col, (i * height), width, height);
            //System.out.println("frame generated - " + i);
        }

        return frames;
    }
}
