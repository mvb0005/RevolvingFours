package edu.auburn.respectedprocess.revolvingfour.views;

import android.animation.Animator;
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

    public void reset(){
        gameBoard = new GameBoard(7, 7, paint1, paint2);
        invalidate();
    }

    public void rotateLeft(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", -90);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                gameBoard.rotateLeft();
                gameBoard.doGravity();
                setRotation(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void rotateRight(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 90);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                gameBoard.rotateRight();
                gameBoard.doGravity();
                setRotation(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void rotate180(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 180);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                gameBoard.rotate180();
                gameBoard.doGravity();
                setRotation(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void newMove(int col, int player){
        gameBoard.newMove(col, player);
        invalidate();
    }

    public void setColor1(int newColor) {
        paint1.setColor(newColor);
    }

    public void setColor2(int newColor) {
        paint1.setColor(newColor);
    }

    private class GameBoard {
        int rows;
        int cols;
        Paint color1;
        Paint color2;
        Circle[][] board;

        public GameBoard(int _rows, int _cols, Paint _color1, Paint _color2) {
            rows = _rows;
            cols = _cols;
            board = new Circle[cols][rows];
            color1 = _color1;
            color2 = _color2;
        }

        public void rotateLeft(){
            Circle[][] newBoard = new Circle[cols][rows];
            Circle c;
            for (int row = 0; row < rows; row++){
                for (int col = 0; col < cols; col++){
                    c = board[row][col];
                    if (c != null) {
                        c.setPosition(col * (CIRCLE_RADIUS * 2) + CIRCLE_RADIUS, (rows - 1 - row) * (CIRCLE_RADIUS * 2) + CIRCLE_RADIUS);
                    }
                    newBoard[col][rows - 1 - row] = c;
                }
            }
            board = newBoard;
            invalidate();
        }

        public void rotateRight(){
            rotateLeft();
            rotateLeft();
            rotateLeft();
        }

        public void rotate180(){
            rotateLeft();
            rotateLeft();
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

        public boolean newMove(int col, int player) {
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
            Log.d("Test", String.valueOf(getWinner()));
            return true;
        }

        public int getWinner(){
            int[] total = new int[2];
            int[] temp = checkHorizontal();
            total[0] += temp[0];
            total[1] += temp[1];
            temp = checkVertical();
            total[0] += temp[0];
            total[1] += temp[1];
            temp = checkDownwardDiag();
            total[0] += temp[0];
            total[1] += temp[1];
            temp = checkUpwardDiag();
            total[0] += temp[0];
            total[1] += temp[1];
            return total[0] + total[1];
        }

        private int[] checkUpwardDiag() {
            int[] total = new int[2];
            int check = 4;
            for (int x = 0; x <= 3; x++) {
                int diagTotal = 0;
                for (int y = rows - 1; y > rows - check; y--){
                    int sum = 0;
                    for (int yrange = 0; yrange < 4; yrange++){
                        if (board[x + (rows - 1 - y) + yrange][y - yrange] != null){
                            sum += board[x + (rows - 1 - y) + yrange][y - yrange].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        diagTotal = sum < 0 ? -1 : 1;
                    }
                }
                check--;
                if (diagTotal > 0){
                    total[0]++;
                }
                if (diagTotal < 0) {
                    total[1]--;
                }
            }
            check = 3;
            for (int y = rows - 1; y >= 3; y--) {
                int diagTotal = 0;
                for (int x = 0; x < check; x++){
                    int sum = 0;
                    for (int xrange = 0; xrange < 4; xrange++){
                        if (board[x + xrange][y - x - xrange] != null){
                            sum += board[x + xrange][y - x - xrange].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        diagTotal = sum < 0 ? -1 : 1;
                    }
                }
                check--;
                if (diagTotal > 0){
                    total[0]++;
                }
                if (diagTotal < 0) {
                    total[1]--;
                }
            }
            return total;
        }

        private int[] checkDownwardDiag() {
            int[] total = new int[2];
            int check = 4;
            for (int x = 0; x <= 3; x++) {
                int diagTotal = 0;
                for (int y = 0; y < check; y++){
                    int sum = 0;
                    for (int yrange = 0; yrange < 4; yrange++){
                        if (board[x + y + yrange][y + yrange] != null){
                            sum += board[x + y + yrange][y + yrange].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        diagTotal = sum < 0 ? -1 : 1;
                    }
                }
                check--;
                if (diagTotal > 0){
                    total[0]++;
                }
                if (diagTotal < 0) {
                    total[1]--;
                }
            }
            check = 3;
            for (int y = 1; y <= 3; y++) {
                int diagTotal = 0;
                for (int x = 0; x < check; x++){
                    int sum = 0;
                    for (int xrange = 0; xrange < 4; xrange++){
                        if (board[x + xrange][y + x + xrange] != null){
                            sum += board[x + xrange][y + x + xrange].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        diagTotal = sum < 0 ? -1 : 1;
                    }
                }
                check--;
                if (diagTotal > 0){
                    total[0]++;
                }
                if (diagTotal < 0) {
                    total[1]--;
                }
            }
            return total;
        }

        private int[] checkVertical() {
            int[] total = new int[2];
            for (int col = 0; col < cols; col++) {
                int colTotal = 0;
                for (int row = 0; row <= rows - 4; row++) {
                    int sum = 0;
                    for (int r = row; r < row + 4; r++) {
                        if (board[col][r] != null) {
                            sum += board[col][r].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        colTotal = sum < 0 ? -1 : 1;
                    }
                }
                if (colTotal > 0){
                    total[0]++;
                }
                if (colTotal < 0) {
                    total[1]--;
                }
            }
            return total;
        }

        private int[] checkHorizontal() {

            int[] total = new int[2];
            for (int row = 0; row < rows; row++) {
                int rowTotal = 0;
                for (int col = 0; col <= cols - 4; col++) {
                    int sum = 0;
                    for (int c = col; c < col + 4; c++) {
                        if (board[c][row] != null) {
                            sum += board[c][row].getPlayer();
                        }
                    }
                    if (Math.abs(sum) == 4){
                        rowTotal = sum < 0 ? -1 : 1;
                    }
                }
                if (rowTotal > 0){
                    total[0]++;
                }
                if (rowTotal < 0) {
                    total[1]--;
                }
            }
            return total;
        }



        public void doGravity(){
            int minRow;
            Circle c;
            for (int col = 0; col < cols; col++){
                minRow = rows - 1;
                for (int row = rows - 1; row >= 0; row--){
                    if (board[col][row] != null) {
                        c = board[col][row];
                        ObjectAnimator animator = ObjectAnimator.ofInt(c, "y", (2 * CIRCLE_RADIUS) * minRow + CIRCLE_RADIUS);
                        animator.setDuration((minRow * (2 * CIRCLE_RADIUS) + CIRCLE_RADIUS) - c.getY());
                        animator.start();
                        board[col][row] = null;
                        board[col][minRow] = c;
                        minRow--;
                    }
                }
            }
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

            public int getPlayer() {
                return player;
            }
        }
    }
}
