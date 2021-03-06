package com.fish.demo.plane.fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * 精灵类，所有其他用于绘制的类的基类
 */
public class FishSprite {
    protected boolean visible = true;
    protected float x = 0;
    protected float y = 0;
    protected float collideOffset = 0;
    protected Bitmap bitmap = null;
    protected boolean destroyed = false;
    protected int frame = 0;
    protected float scale = 1.0f;
    protected float scaleOrigin = 1.0f;

    public FishSprite(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setVisibility(boolean visible){
        this.visible = visible;
    }

    public boolean getVisibility(){
        return visible;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getX(){
        return x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        if(bitmap != null){
            return bitmap.getWidth();
        }
        return 0;
    }

    public float getHeight(){
        if(bitmap != null){
            return bitmap.getHeight();
        }
        return 0;
    }

    public float getScaleWidth(){
        if(bitmap != null){
            return getWidth() * scale * scaleOrigin;
        }
        return 0;
    }

    public float getScaleHeight(){
        if(bitmap != null){
            return getHeight() *scale * scaleOrigin;
        }
        return 0;
    }

    public void move(float offsetX, float offsetY){
        x += offsetX;
        y += offsetY;
    }

    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void centerTo(float centerX, float centerY){
        float w = getWidth();
        float h = getHeight();
        x = centerX - w / 2;
        y = centerY - h / 2;
    }

    public RectF getRectF(){
        float left = x;
        float top = y;
        float right = left + getScaleWidth();
        float bottom = top + getScaleHeight();
        RectF rectF = new RectF(left, top, right, bottom);
        return rectF;
    }

    public Rect getBitmapSrcRec(){
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = (int)getWidth();
        rect.bottom = (int)getHeight();
        return rect;
    }


    public RectF getCollideRectF(){
        RectF rectF = getRectF();
        rectF.left -= collideOffset;
        rectF.right += collideOffset;
        rectF.top -= collideOffset;
        rectF.bottom += collideOffset;
        return rectF;
    }

    public Point getCollidePointWithOther(FishSprite s){
        Point p = null;
        RectF rectF1 = getCollideRectF();
        RectF rectF2 = s.getCollideRectF();
        RectF rectF = new RectF();
        boolean isIntersect = rectF.setIntersect(rectF1, rectF2);
        if(isIntersect){
            p = new Point(Math.round(rectF.centerX()), Math.round(rectF.centerY()));
        }
        return p;
    }

    public final void draw(Canvas canvas, Paint paint, FishGameView gameView){
        frame++;
        beforeDraw(canvas, paint, gameView);
        onDraw(canvas, paint, gameView);
        afterDraw(canvas, paint, gameView);
    }

    protected void beforeDraw(Canvas canvas, Paint paint, FishGameView gameView){}

    public void onDraw(Canvas canvas, Paint paint, FishGameView gameView){
        if(!destroyed && this.bitmap != null && getVisibility()){
            Rect srcRef = getBitmapSrcRec();
            RectF dstRecF = getRectF();
            Log.d("FISH", "dst w:" + dstRecF.width());
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint);
        }
    }

    protected void afterDraw(Canvas canvas, Paint paint, FishGameView gameView){}

    public void destroy(){
        bitmap = null;
        destroyed = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public int getFrame(){
        return frame;
    }

}