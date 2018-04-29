package edu.auburn.respectedprocess.revolvingfour;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import edu.auburn.respectedprocess.revolvingfour.views.GameBoardView;

public class MainActivity extends AppCompatActivity implements TopFragment.OnFragmentInteractionListener,
                                                    RotationFragment.OnFragmentInteractionListener{

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

    @Override
    public void reset() {
        gameBoardView.reset();
        Log.d("test", "Reset Clicked");
    }

    @Override
    public void rotate(int direction) {
        switch (direction) {
            case 0:
                gameBoardView.rotateLeft();
                break;
            case 1:
                gameBoardView.rotate180();
                break;
            case 2:
                gameBoardView.rotateRight();
                break;
        }
    }
}
