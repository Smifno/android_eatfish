package com.fish.demo.plane.fish;

import android.app.Activity;
import android.os.Bundle;

import com.fish.demo.R;

/**
 * Created by HUITI on 16/11/21.
 */

public class FishActivity extends Activity {

    private FishGameView fishGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        fishGameView = (FishGameView) findViewById(R.id.fish_game_view);
        int[] bitmaps =  new int[]{
                R.drawable.fish222,
                R.drawable.enemyfish
        };
        fishGameView.start(bitmaps);
    }
}
