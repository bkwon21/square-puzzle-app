package com.cs301.squarespuzzle;

import android.view.View;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * SquaresController
 *
 * the controller for the SquaresPuzzle application. each controller contains
 * information about the view and model that it controls. the controller is
 * responsible for handling user input and modifying the model accordingly.
 *
 * @author Bryce Kwon
 * @version February 21, 2021
 */
public class SquaresController implements View.OnClickListener,
        View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    // these variables contain information about the table
    private SquaresView _squaresView;
    private SquaresModel _squaresModel;

    /**
     * SquaresController constructor
     *
     * the constructor for the SquaresController class. each controller
     * contains information about the view and model that it controls.
     *
     * @param view  the view that the controller controls
     */
    public SquaresController(SquaresView view) {
        this._squaresView = view;
        this._squaresModel = view.getModel();
    }

    /**
     * onClick
     *
     * this method is called when the user clicks on the reset button. it
     * resets the table to a random configuration. the table is then redrawn.
     *
     * @param view  the view that the user clicked on
     */
    @Override
    public void onClick(View view) {
        _squaresModel.shuffleTable();
        _squaresView.invalidate();
    }

    /**
     * onTouch
     *
     * this method is called when the user touches the screen. it checks to
     * see if the user touched a block and if so, it moves the block to the
     * empty space if valid. the table is then redrawn.
     *
     * @param view          the view that the user touched
     * @param motionEvent   the motion event that occurred
     * @return              true if the event was handled, false otherwise
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // get the x and y coordinates of the touch location
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        // get the size of the table panel
        float tableLeft = SquaresView.TABLE_MARGIN;
        float tableRight = SquaresView.TABLE_MARGIN + SquaresView.TABLE_LENGTH;
        float tableTop = SquaresView.TABLE_MARGIN;
        float tableBottom = SquaresView.TABLE_MARGIN + SquaresView.TABLE_LENGTH;

        // check if the touch was inside the table panel
        if (x < tableLeft || x > tableRight) {
            return false;
        } else if (y < tableTop || y > tableBottom) {
            return false;
        }

        // calculate the row and column of the block that was touched
        int row = (int) ((y) / _squaresView.getBlockLength());
        int col = (int) ((x) / _squaresView.getBlockLength());

        // check if row is valid (round values to nearest int)
        if (row > _squaresModel.getBlocksNum()) {
            row = _squaresModel.getBlocksNum();
        } else if (row < 0) {
            row = 0;
        }

        // check if col is valid (round values to nearest int)
        if (col > _squaresModel.getBlocksNum()) {
            col = _squaresModel.getBlocksNum();
        } else if (col < 0) {
            col = 0;
        }

        // attempt to move the block to the empty space
        if (_squaresModel.swapBlocks(row, col)) {
            // if the move was successful, redraw the table
            _squaresView.invalidate();
            return true;
        } else {
            // if the move was not successful, do nothing
            return false;
        }
    }

    /**
     * onProgressChanged
     *
     * this method is called when the user changes the value of the seek bar.
     * it changes the number of blocks in the table and redraws the table. it
     * can increment the number of boxes from 4 to 6.
     *
     * @param seekBar   the seek bar that was changed
     * @param i         the new value of the seek bar
     * @param b         true if the user changed the value, false otherwise
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // check if the user changed the value
        if (!b) {
            return;
        }

        // check if the value is valid
        if (i < 0 || i > 2) {
            return;
        }

        // set the number of blocks in the table
        _squaresModel.setBlocksNum(i + 4);

        // reset the table
        _squaresModel.resetTable();
        _squaresModel.shuffleTable();

        // redraw the table
        _squaresView.invalidate();
    }


    /**
     * onStartTrackingTouch
     *
     * this method is called when the user starts to change the value of the
     * seek bar. it does nothing in this case.
     *
     * @param seekBar   the seek bar that was changed
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    /**
     * onStopTrackingTouch
     *
     * this method is called when the user stops changing the value of the
     * seek bar. it does nothing in this case.
     *
     * @param seekBar   the seek bar that was changed
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
