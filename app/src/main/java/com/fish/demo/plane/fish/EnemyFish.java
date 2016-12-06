package com.fish.demo.plane.fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/** 敌鱼
 * enemyfish.png
 * Created by HUITI on 16/11/22.
 */

public class EnemyFish extends AutoFishSprite {

    public int life;

    private int lastEatFrame;
    private static final int SAFE_FRAMES = 30;

    public EnemyFish(Bitmap bitmap, int life){
        super(bitmap);
        this.life = life;
        this.scale = (float)life / Fish.MY_FISH_DEF_LIFE;
    }

    @Override
    public float getWidth() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getWidth() / 6;
        }
        return 0;
    }

    @Override
    public float getHeight() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getHeight() / 3;
        }
        return 0;
    }


    @Override
    protected void afterDraw(Canvas canvas, Paint paint, FishGameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (!isDestroyed() && life > 0){
            Fish myFish = gameView.getMyFish();
            //是否交叉
            Point p = getCollidePointWithOther(myFish);
            if (p != null){
                if (myFish.life > life){//吃掉敌人鱼
                    myFish.life ++;
                    myFish.scale += 0.1;
                    gameView.setLife(myFish.life);
                    destroy();
                }else {//我方鱼被吃
                    if (lastEatFrame == 0 || getFrame() - lastEatFrame > SAFE_FRAMES){
                        lastEatFrame = getFrame();
                        myFish.life --;
                        myFish.scale -= 0.1;

                        //敌人
                        life++;
                        this.scale = (float)life / Fish.MY_FISH_DEF_LIFE;

                        gameView.setLife(myFish.life);
                        if (myFish.life <= 15){
                            myFish.destroy();
                        }
                    }

                }
            }
        }
    }

}
