package com.fish.demo.plane.fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


public class AutoFishSprite extends FishSprite {
    private float speed = 2;

    public AutoFishSprite(Bitmap bitmap){
        super(bitmap);
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint, FishGameView gameView) {
        if(!isDestroyed()){
            //在y轴方向移动speed像素
            //move(0, speed * gameView.getDensity());
            move(speed * gameView.getDensity(), 0);
        }
    }

    @Override
    protected void afterDraw(Canvas canvas, Paint paint, FishGameView gameView){
        if(!isDestroyed()){
            //检查Sprite是否超出了Canvas的范围，如果超出，则销毁Sprite
            RectF canvasRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            RectF spriteRecF = getRectF();
            if(!RectF.intersects(canvasRecF, spriteRecF)){
                destroy();
            }
        }
    }
}