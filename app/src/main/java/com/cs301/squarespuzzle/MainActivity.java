package com.cs301.squarespuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * the onCreate method for the MainActivity class. this method is called
     * when the activity is first created. it sets the content view to the
     * activity_main layout. this layout contains the main view for the app.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the squares view and create the controller
        SquaresView squaresView = findViewById(R.id.squaresView);
        SquaresController squaresController = new SquaresController(squaresView);

        // set the listeners for the view
        squaresView.setOnTouchListener(squaresController);

        // get the reset button and set the listener
        Button resetButton = findViewById(R.id.buttonResetTable);
        resetButton.setOnClickListener(squaresController);

        // get the seek bar and set the listener
        SeekBar seekBar = findViewById(R.id.seekbarBlocksNum);
        seekBar.setOnSeekBarChangeListener(squaresController);
    }
}