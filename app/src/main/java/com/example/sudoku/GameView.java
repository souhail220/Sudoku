package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameView extends View implements GestureDetector.OnGestureListener {

    GestureDetector gestureDetector;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final GameBoard gameBoard = GameBoard.getGameBoard(GameLevel.Medium);

    private final int[] buttonsTable = new int[9];
    private float gridWidth;
    private float gridSeparatorSize;
    private float cellWidth;
    private float buttonWidth;
    private float buttonRadius;
    private float buttonMargin;
    Bitmap eraserBitmap;
    Bitmap pencilBitmap;
    Bitmap littlePencilBitmap;

    public GameView(Context context) {
        super(context);
        this.init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){
        gestureDetector = new GestureDetector(getContext(), this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        gridSeparatorSize = (w/9f)/20f;
        gridWidth = w;
        cellWidth = gridWidth/9f;
        buttonWidth = w/7f;
        buttonRadius = buttonWidth / 10f;
        buttonMargin = (w-6*buttonWidth) / 7f;

        eraserBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);
        eraserBitmap = Bitmap.createScaledBitmap(eraserBitmap,
                (int) (buttonWidth*0.8f),(int) (buttonWidth*0.8f),false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pencil);
        pencilBitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (buttonWidth*0.8f),(int) (buttonWidth*0.8f),false);
        littlePencilBitmap = Bitmap.createScaledBitmap(bitmap, (int) (buttonWidth/3),(int) (buttonWidth/3),false);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        for(int i=0;i<9;i++){
            buttonsTable[i]=0;
        }
        drawGrid(canvas);
        drawSelectedCellHighlight(canvas);
        drawButtons(canvas);
        checkWin(canvas);
    }

    private void drawGrid(Canvas canvas){
        paint.setTextAlign(Paint.Align.CENTER);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int backgroundColor = Color.WHITE;

                //selecting the current row and column and big cell
                if(gameBoard.currentCellX != -1 && gameBoard.currentCellY != -1){
                    if( (x / 3 == gameBoard.currentCellX / 3 && y / 3 == gameBoard.currentCellY / 3)||
                            (x == gameBoard.currentCellX && y != gameBoard.currentCellY) ||
                            (y == gameBoard.currentCellY && x != gameBoard.currentCellX)){
                        backgroundColor = 0xFF_FF_F0_F0;
                    }
                }

                if(gameBoard.cells[y][x].assumedValue != gameBoard.cells[y][x].realValue && gameBoard.cells[y][x].assumedValue > 0){
                    backgroundColor = Color.RED;

                }

                if(gameBoard.cells[y][x].isInitial){
                    backgroundColor=0xFFF0F0F0;
                }

                paint.setColor( backgroundColor );
                canvas.drawRect(x * cellWidth,
                        y * cellWidth ,
                        (x+1) * cellWidth,
                        (y+1) * cellWidth,
                        paint);
                if(gameBoard.cells[y][x].isInitial){
                    paint.setColor(0xFF000000);
                    paint.setTextSize( cellWidth*0.7f );
                    canvas.drawText("" + gameBoard.cells[y][x].realValue,
                            x * cellWidth + cellWidth / 2,
                            y * cellWidth + cellWidth * 0.75f, paint);
                }
                else if (gameBoard.cells[y][x].assumedValue != 0) {

                    // Draw the assumed value for the cell.

                    paint.setColor(Color.BLUE);
                    paint.setTextSize( cellWidth*0.7f );
                    canvas.drawText("" + gameBoard.cells[y][x].assumedValue,
                            x * cellWidth + cellWidth / 2,
                            y * cellWidth + cellWidth * 0.75f, paint);

                }
                else {

                    // Draw each mark if exists
                    paint.setTextSize( cellWidth*0.33f );
                    paint.setColor( 0xFFA0A0A0 );
                    if ( gameBoard.cells[y][x].marks[0] ) {
                        canvas.drawText("1",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[1] ) {
                        canvas.drawText("2",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[2] ) {
                        canvas.drawText("3",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.3f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[3] ) {
                        canvas.drawText("4",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[4] ) {
                        canvas.drawText("5",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[5] ) {
                        canvas.drawText("6",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.6f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[6] ) {
                        canvas.drawText("7",
                                x * cellWidth + cellWidth * 0.2f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[7] ) {
                        canvas.drawText("8",
                                x * cellWidth + cellWidth * 0.5f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                    if ( gameBoard.cells[y][x].marks[8] ) {
                        canvas.drawText("9",
                                x * cellWidth + cellWidth * 0.8f,
                                y * cellWidth + cellWidth * 0.9f, paint);
                    }
                }


            }
        }

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(gridSeparatorSize/2);
        for(int i=0;i<=9;i++){
            canvas.drawLine(i*cellWidth,0,i*cellWidth,cellWidth*9,paint);
            canvas.drawLine(0,i*cellWidth,cellWidth*9,i*cellWidth,paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(gridSeparatorSize);
        for(int i =0;i<=3;i++){
            canvas.drawLine(i*(cellWidth*3),0,i*(cellWidth*3),cellWidth*9,paint);
            canvas.drawLine(0,i*(cellWidth*3),cellWidth*9,i*(cellWidth*3),paint);
        }
    }

    private void drawSelectedCellHighlight(Canvas canvas){
        if(gameBoard.currentCellX != -1 && gameBoard.currentCellY != -1){
            paint.setColor(0xFF_30_3F_9F);
            paint.setStrokeWidth(gridSeparatorSize*1.5f);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(gameBoard.currentCellX * cellWidth,
                    gameBoard.currentCellY * cellWidth,
                    (gameBoard.currentCellX + 1 ) * cellWidth,
                    (gameBoard.currentCellY + 1) * cellWidth,
                    paint);

            paint.setStyle( Paint.Style.FILL_AND_STROKE );
            paint.setStrokeWidth( 1 );
        }
    }

    private void drawButtons(@NonNull Canvas canvas){
        float buttonsTop = (cellWidth * 9) + gridSeparatorSize/2;

        paint.setColor(0xFFC7DAF8);
        canvas.drawRect(0,buttonsTop,gridWidth,getHeight(),paint);

        float buttonLeft = buttonMargin;
        float buttonTop = buttonsTop + buttonMargin;

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(buttonWidth * 0.7f);

        for(int i=1;i<=9;i++){
            paint.setColor( 0xFFFFFFFF );

            @SuppressLint("DrawAllocation") RectF rectF = new RectF(buttonLeft,
                    buttonTop,
                    buttonLeft+buttonWidth,
                    buttonTop+buttonWidth);
            canvas.drawRoundRect(rectF,buttonRadius,buttonRadius,paint);

            paint.setColor(0xFF000000);
            canvas.drawText(""+i,rectF.centerX(),rectF.top + rectF.height() * 0.75f,paint);

            if(i != 6){
                buttonLeft += buttonWidth + buttonMargin;
            } else{
                buttonLeft = buttonMargin;
                buttonTop += buttonMargin + buttonWidth;
            }

        }

        //int imageWidth = (int) (buttonWidth * 0.8f);
        int imageMargin = (int) (buttonWidth * 0.1f);

        //drawing eraser button
        paint.setColor(0xFFFFFFFF);
        canvas.drawRoundRect(buttonLeft,
                buttonTop,
                buttonLeft+buttonWidth,
                buttonTop+buttonWidth,buttonRadius,buttonRadius,paint);


        canvas.drawBitmap(eraserBitmap,buttonLeft + imageMargin,buttonTop+imageMargin,paint);
        buttonLeft += buttonWidth + buttonMargin;


        //drawing pencil button
        canvas.drawRoundRect(buttonLeft,
                buttonTop,
                buttonLeft+buttonWidth,
                buttonTop+buttonWidth,buttonRadius,buttonRadius,paint);

        Bitmap bitmap = gameBoard.bigNumber ? pencilBitmap : littlePencilBitmap;
        canvas.drawBitmap(bitmap,buttonLeft + imageMargin,buttonTop + imageMargin,paint);
    }

    // Events Handler
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        RectF rectF;
        //check grille cell click
        if(e.getY() < gridWidth){
            int cellX = (int) (e.getX() / cellWidth);
            int cellY = (int) (e.getY() / cellWidth);

            gameBoard.currentCellX = cellX;
            gameBoard.currentCellY = cellY;
            postInvalidate();
            return true;
        }

        float buttonLeft = buttonMargin;
        float buttonTop = 9 * cellWidth + gridSeparatorSize / 2 + buttonMargin;

        if(gameBoard.currentCellY != -1 && gameBoard.currentCellX != -1){
            //verifier numbers des buttons
            for(int i =1;i<=9;i++){
                rectF = new RectF(buttonLeft,
                        buttonTop,
                        buttonLeft + buttonWidth,
                        buttonTop + buttonWidth);
                if(rectF.contains(e.getX(),e.getY())){
                    gameBoard.pushValue(i);
                    postInvalidate();
                    return true;
                }
                if(i != 6){
                    buttonLeft += buttonWidth + buttonMargin;
                } else{
                    buttonLeft = buttonMargin;
                    buttonTop += buttonMargin + buttonWidth;
                }
            }
            //eraser button
            rectF = new RectF(buttonLeft,
                    buttonTop,
                    buttonLeft + buttonWidth,
                    buttonTop + buttonWidth);
            if(rectF.contains(e.getX(),e.getY())){
                gameBoard.clearCell();
                this.invalidate();
                return true;
            }
            buttonLeft += buttonWidth + buttonMargin;
        }

        //pencil button
        rectF = new RectF(buttonLeft,
                buttonTop,
                buttonLeft + buttonWidth,
                buttonTop + buttonWidth);
        if(rectF.contains(e.getX(),e.getY())){
            gameBoard.bigNumber = !gameBoard.bigNumber;
            this.invalidate();
            return true;
        }
        return true;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private void colorButtons(int value,Canvas canvas){
        if(buttonsTable[value-1] < 8){
            buttonsTable[value-1]++;
        } else {
            RectF rectF;
            float buttonLeft = buttonMargin;
            float buttonTop = 9 * cellWidth + gridSeparatorSize / 2 + buttonMargin;
            for(int i =1;i<=value-1;i++){

                if(i != 6){
                    buttonLeft += buttonWidth + buttonMargin;
                } else{
                    buttonLeft = buttonMargin;
                    buttonTop += buttonMargin + buttonWidth;
                }
            }
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(buttonWidth * 0.7f);
            paint.setColor(Color.GRAY);

            rectF = new RectF(buttonLeft,
                    buttonTop,
                    buttonLeft + buttonWidth,
                    buttonTop + buttonWidth);
            canvas.drawRoundRect(rectF,buttonRadius,buttonRadius,paint);
            paint.setColor(0xFF000000);
            canvas.drawText(""+value,rectF.centerX(),rectF.top + rectF.height() * 0.75f,paint);
        }
    }
    private void checkWin(Canvas canvas){
        boolean test = true;
        for(int i = 0; i <9;i++){
            for(int j = 0; j <9;j++){
                if(gameBoard.cells[i][j].assumedValue != 0) {
                    if (gameBoard.cells[i][j].assumedValue != gameBoard.cells[i][j].realValue) {
                        test = false;
                    } else {
                        colorButtons(gameBoard.cells[i][j].assumedValue,canvas);
                    }
                } else {
                    test =false;
                }
            }
        }
        if(test){
            float buttonLeft = buttonMargin * 16;
            float buttonsTop = gridWidth + gridSeparatorSize/2 + buttonWidth *4;
            paint.setColor(Color.GRAY);
            RectF rectF = new RectF(buttonLeft,buttonsTop,buttonLeft + buttonWidth*2,buttonsTop + buttonWidth*2);
            canvas.drawText("VICTORY",rectF.centerX(),rectF.top + rectF.height() * 0.75f,paint);
        }
    }
}
