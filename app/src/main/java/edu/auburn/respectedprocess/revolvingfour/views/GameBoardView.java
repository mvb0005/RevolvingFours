package edu.auburn.respectedprocess.revolvingfour.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.auburn.respectedprocess.revolvingfour.R;

/**
 * Created by Matt on 4/23/2018.
 */

public class GameBoardView extends View {
    final int CIRCLE_RADIUS = 100;
    Paint paint1;
    Paint paint2;
    Bitmap gridOverlay;
    Matrix matrix;
    GameBoard gameBoard;

    public GameBoardView(Context context) {
        super(context);
        init(null);
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GameBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet attrs) {
        paint1 = new Paint();
        paint1.setStrokeWidth(1);
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);

        gridOverlay = BitmapFactory.decodeResource(getResources(), R.drawable.gameboard);
        gridOverlay = Bitmap.createScaledBitmap(gridOverlay, 1400,1400, false);
        gameBoard = new GameBoard(7,7, paint1, paint2);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameBoard.onDraw(canvas);

    }

    @Override
    public void setRotation(float rotation){
        super.setRotation(rotation);
    }

    public void newMove(int col){
        gameBoard.newMove(col);
        invalidate();
    }

    private class GameBoard {
        int rows;
        int cols;
        Paint color1;
        Paint color2;
        Circle[][] board;
        int player = 1;

        public GameBoard(int _rows, int _cols, Paint _color1, Paint _color2) {
            rows = _rows;
            cols = _cols;
            board = new Circle[cols][rows];
            color1 = _color1;
            color2 = _color2;
        }

        public void rotateLeft(){
            Circle[][] newBoard = new Circle[cols][rows];
            for (int row = 0; row < rows; row++){
                for (int col = 0; col < cols; cols++){
                    newBoard[col][rows - 1 - row] = board[col][row];
                }
            }
            board = newBoard;
        }

        public void onDraw(Canvas canvas) {
            for (int col = 0; col < cols; col++) {
                for (Circle c : board[col]) {
                    if (c != null) {
                        c.onDraw(canvas);
                    }
                }
            }
            canvas.drawBitmap(gridOverlay, matrix, null);
        }

        public boolean newMove(int col) {
            int lowest = rows - 1;
            while (lowest >= 0 && board[col][lowest] != null) {
                lowest--;
            }
            if (lowest < 0) {
                return false;
            }
            Circle c = new Circle((2 * CIRCLE_RADIUS) * col + CIRCLE_RADIUS, 0, CIRCLE_RADIUS, player > 0 ? color1: color2, player);
            ObjectAnimator animator = ObjectAnimator.ofInt(c, "y", (2 * CIRCLE_RADIUS) * lowest + CIRCLE_RADIUS);
            animator.setDuration(lowest * (2 * CIRCLE_RADIUS) + CIRCLE_RADIUS);
            animator.start();
            board[col][lowest] = c;
            player *= -1;
            return true;
        }

        private class Circle {
            int x;
            int y;
            int radius;
            Paint color;
            int player;

            public Circle(int _x, int _y, int _radius, Paint _color, int _player) {
                x = _x;
                y = _y;
                radius = _radius;
                color = _color;
                player = _player;
            }

            public void setY(int _y){
                y = _y;
                invalidate();
            }

            public int getY(){
                return y;
            }

            public void setPosition(int _x, int _y){
                x = _x;
                y = _y;
            }
            protected void onDraw(Canvas canvas){
                canvas.drawCircle(x,y,radius,color);
            }
        }
    }
}
