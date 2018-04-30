package edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.activities;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.fragments.RotationFragment;
import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.fragments.TopFragment;
import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.R;
import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.views.GameBoardView;


//                |-------------------------------------------------------|
//                |                    REVOLVING FOURS                    |
//                |-------------------------------------------------------|
//                     Created by Benjamin Williams and Matthew Bonsall
//
//                                  Auburn University
//                                      COMP 3710
//                                     Spring 2018


public class MainActivity extends AppCompatActivity implements TopFragment.OnFragmentInteractionListener,
        RotationFragment.OnFragmentInteractionListener {

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
                        if(gameBoardView.newMove((int) (event.getX() - gameBoardView.getX()) / 200, player)){
                            updatePlayer();
                            updateStatus();
                        }
                    }
                }

            }
        }
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
        gameBoardView.setSafeToMove(true);
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
    public void canMove(boolean canMove) {
        gameBoardView.setSafeToMove(canMove);
    }

    @Override
    public void rotate(int direction) {
        updateStatus();
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
    }

    public void updatePlayer(){
        topFragment.updatePlayer(player);
        player *= -1;

    }

    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }
}
