package edu.auburn.respectedprocess.revolvingfour;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
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
    int player = 1;
    TopFragment topFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoardView = findViewById(R.id.gameboardview);
        topFragment = (TopFragment) getSupportFragmentManager().findFragmentById(R.id.topfragment);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameBoardView.isSafeToMove()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getX() > gameBoardView.getX() && event.getX() < gameBoardView.getX() + gameBoardView.getWidth()) {
                    if (event.getY() > gameBoardView.getY() && event.getY() < gameBoardView.getY() + gameBoardView.getHeight()) {
                        gameBoardView.newMove((int) (event.getX() - gameBoardView.getX()) / 200, player);
                        updatePlayer();
                    }
                }

            }
        }
        updateStatus();
        return super.onTouchEvent(event);

    }

    public void updateStatus() {
        topFragment.updateStatus(gameBoardView.getWinner());
    }

    @Override
    public void reset() {
        gameBoardView.reset();
        player = -1;
        updatePlayer();
        updateStatus();
        Log.d("test", "Reset Clicked");
    }

    @Override
    public void changeColor(int player, int color) {
        if (player == 1) {
            gameBoardView.setColor1(color);
        }
        else if (player == 2) {
            gameBoardView.setColor2(color);
        }
    }

    @Override
    public void rotate(int direction) {
        if (gameBoardView.isSafeToMove()) {
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
            updatePlayer();
        }
        updateStatus();
    }

    public void updatePlayer(){
        topFragment.updatePlayer(player);
        player *= -1;

    }

    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }
}
