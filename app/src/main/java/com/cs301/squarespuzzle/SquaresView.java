package com.cs301.squarespuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * SquaresView
 *
 * the view for the SquaresPuzzle application. each view contains a table
 * that can be modified by the user by clicking on the squares. the view is
 * responsible for drawing the table and the blocks.
 *
 * @author Bryce Kwon
 * @version February 21, 2021
 */
public class SquaresView extends SurfaceView {
    // these variables contain information about the square model
    private SquaresModel _squaresModel;

    // these constants define dimensions of the square table
    public static final float TABLE_LENGTH = 900f;
    public static final float TABLE_MARGIN = 50f;
    private static final float TABLE_STROKE = 5f;
    private static final float TABLE_TEXT_SIZE = 50f;

    // these variables contain information about the square blocks
    private float _blockLength;
    private float _blocksNum;

    // these variables contain information about the graphics
    private Paint _tableOutline;
    private Paint _blockOutline;
    private Paint _blockValue;

    /**
     * SquareView constructor
     *
     * the constructor for the SquareView class. each view contains a table
     * (2D array) that can be modified by the user by clicking on the squares.
     *
     * @param context   the context of the application
     * @param attrs     the attributes of the application
     */
    public SquaresView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        // set the dimensions of the view
        setMinimumWidth((int) (TABLE_LENGTH + 2 * TABLE_MARGIN));
        setMinimumHeight((int) (TABLE_LENGTH + 2 * TABLE_MARGIN));

        // set the background color of the view
        setBackgroundColor(Color.WHITE);

        // initialize a new model for the square table
        _squaresModel = new SquaresModel();

        // define the paint objects for the table outline
        _tableOutline = new Paint();
        _tableOutline.setColor(Color.BLACK);
        _tableOutline.setStyle(Paint.Style.STROKE);
        _tableOutline.setStrokeWidth(TABLE_STROKE);

        // define the paint objects for the block outline
        _blockOutline = new Paint();
        _blockOutline.setColor(Color.BLACK);
        _blockOutline.setStyle(Paint.Style.STROKE);
        _blockOutline.setStrokeWidth(TABLE_STROKE);

        // define the paint objects for the block values
        _blockValue = new Paint();
        _blockValue.setStyle(Paint.Style.FILL);
        _blockValue.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * onDraw
     *
     * this method is called whenever the view needs to be redrawn. it is
     * responsible for drawing the square table in this view.
     *
     * @param canvas    the canvas on which to draw the view
     *                  (this is automatically passed by the system)
     */
    @Override
    public void onDraw(Canvas canvas) {
        // calculate the dimensions of the square table
        _blocksNum = _squaresModel.getBlocksNum();
        _blockLength = TABLE_LENGTH / _blocksNum;

        // set the appropriate text size for the block values
        _blockValue.setTextSize(TABLE_TEXT_SIZE);

        drawTable(canvas);
        drawValue(canvas);
    }

    /**
     * drawTable
     *
     * this method is called by the onDraw method to draw the square table. it
     * is responsible for drawing the table outline and the blocks in this view.
     *
     * @param canvas    the canvas on which to draw the table
     */
    private void drawTable(Canvas canvas) {
        // draw the table outline
        canvas.drawRect(TABLE_MARGIN, TABLE_MARGIN, TABLE_LENGTH + TABLE_MARGIN,
                        TABLE_LENGTH + TABLE_MARGIN, _tableOutline);

        // draw the blocks outline
        for (int i = 0; i < _blocksNum; i++) {
            // draw vertical lines
            canvas.drawLine(TABLE_MARGIN + (_blockLength * i), TABLE_MARGIN,
                            TABLE_MARGIN + (_blockLength * i),
                            TABLE_MARGIN + TABLE_LENGTH, _blockOutline);

            // draw horizontal lines
            canvas.drawLine(TABLE_MARGIN, TABLE_MARGIN + (_blockLength * i),
                            TABLE_MARGIN + TABLE_LENGTH,
                            TABLE_MARGIN + (_blockLength * i), _blockOutline);
        }
    }

    /**
     * drawValues
     *
     * this method is called by the onDraw method to draw the values of the
     * blocks values in the square table in this view.
     *
     * @param canvas    the canvas on which to draw the values
     */
    private void drawValue(Canvas canvas) {
        // get the values of the blocks in the table
        int[][] table = _squaresModel.getTable();

        // draw the values of the blocks
        for (int i = 0; i < _blocksNum; i++) {
            for (int j = 0; j < _blocksNum; j++) {
                // get the value of the current block
                int value = table[i][j];

                // check if the value is correct
                if (value == (i * _blocksNum) + j + 1) {
                    _blockValue.setColor(Color.GREEN);
                } else {
                    _blockValue.setColor(Color.BLACK);
                }

                // get the x and y coordinates of the current block
                float x = TABLE_MARGIN + (_blockLength * j) + (_blockLength / 2);
                float y = TABLE_MARGIN + (_blockLength * i) + (_blockLength / 2);

                // draw the value of the current block
                if (value == 0) {
                    canvas.drawText("", x, y, _blockValue);
                } else {
                    canvas.drawText(Integer.toString(value), x, y, _blockValue);
                }
            }
        }
    }

    /**
     * getModel
     *
     * this method returns the model of the square table in this view. it is
     * used to track the changes of the table from the controller.
     *
     * @return  the model of the square table in this view
     */
    public SquaresModel getModel() {
        return _squaresModel;
    }

    /**
     * getBlockLength
     *
     * this method returns the length of a block in the square table in this
     * view. it is used to track the changes of the table from the controller.
     *
     * @return  the length of a block in the square table in this view
     */
    public float getBlockLength() {
        return _blockLength;
    }
}
