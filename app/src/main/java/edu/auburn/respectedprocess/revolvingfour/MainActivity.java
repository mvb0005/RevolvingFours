package edu.auburn.respectedprocess.revolvingfour;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import edu.auburn.respectedprocess.revolvingfour.views.GameBoardView;

public class MainActivity extends AppCompatActivity {

    GameBoardView gameBoardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoardView = (GameBoardView) findViewById(R.id.gameboardview);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            if (event.getX() > gameBoardView.getX() && event.getX() < gameBoardView.getX() + gameBoardView.getWidth()) {
                if (event.getY() > gameBoardView.getY() && event.getY() < gameBoardView.getY() + gameBoardView.getHeight()) {
                    gameBoardView.newMove((int) (event.getX() - gameBoardView.getX()) / 200);
                }
            }

        }
        return super.onTouchEvent(event);
    }
}
