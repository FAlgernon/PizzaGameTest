package co.algernon.pizzagame;

/**
 * Created by Fruanku on 3/14/2017.
 */

public class Offset {
    public int x = 0;
    public int y = 0;
    static int scale = 1;
    private Offset parent = null;

    public Offset(int offSetX, int offSetY){
        this.x = offSetX;
        this.y = offSetY;
    }

    public void setParent(Offset parent){
        this.parent = parent;
    }

    public int cx(){
        if(parent!=null) {
            return parent.cx() + x();
        }else {
            return x();
        }

    }

    public int cy(){
        if(parent!=null) {
            return parent.cy() + y();
        }else {
            return y();
        }
    }

    public int x(){
        return (scale*x);
    }

    public int y(){
        return (scale*y);
    }

    public void setScale(int s){
        scale=s;
    }

}
