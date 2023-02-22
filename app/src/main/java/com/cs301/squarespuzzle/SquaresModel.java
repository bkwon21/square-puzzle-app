package com.cs301.squarespuzzle;

import java.util.Random;

/**
 * SquaresModel
 *
 * the model for the SquaresPuzzle application. each model contains information
 * about the state of the table that is displayed in the view and modified
 * by the controller.
 *
 * @author Bryce Kwon
 * @version February 21, 2021
 */
public class SquaresModel {
    // these variables contain information about the table model
    private int _blocksNum;
    private int[][] _table;

    // these variables specify the location of the empty block
    private int _emptyRow;
    private int _emptyCol;

    /**
     * SquaresModel constructor
     *
     * the constructor for the SquaresModel class. each model contains
     * information about the state of the square table that is displayed
     * in the view and modified by the controller.
     */
    public SquaresModel() {
        // set initial squares to 4x4 (16 total)
        _blocksNum = 4;

        // initialize the table
        this.resetTable();
        this.shuffleTable();
    }

    /**
     * resetTable
     *
     * this method resets the table to a random configuration. a new table
     * is generated of blocksNum x blocksNum size and filled with numbers in
     * ascending order. the first block is set to 0 to represent the empty.
     */
    public void resetTable() {
        // initialize the table
        _table = new int[_blocksNum][_blocksNum];

        // fill the table with numbers in ascending order
        int count = 0;
        for (int i = 0; i < _blocksNum; i++) {
            for (int j = 0; j < _blocksNum; j++) {
                _table[i][j] = count;
                count++;
            }
        }

        // track the location of the empty block
        _emptyRow = 0;
        _emptyCol = 0;
    }

    /**
     * External Citation
     *      Date:   February 21, 2023
     *      Problem: Trouble shuffling the contents of a 2D array.
     *
     * Resource:
     *      * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
     *      * https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-
     *        yates-shuffle-algorithm/
     * Solution: I used the algorithm to shuffle the contents of the 2D array.
     */

    /**
     * shuffleTable
     *
     * this method shuffles the table with the Fisher-Yates algorithm. the
     * algorithm randomly selects a block and swaps it with the current block.
     * the algorithm is repeated a number of times equal to the number of
     * blocks in the table.
     */
    public void shuffleTable() {
        // initialize the random number generator
        Random random = new Random();

        // shuffle the table
        for (int i = _blocksNum - 1; i > 0; i--) {
            for (int j = _blocksNum - 1; j > 0; j--) {
                // randomly select a block
                int row = random.nextInt(i + 1);
                int col = random.nextInt(j + 1);

                // track the location of the empty block
                if (_table[row][col] == 0) {
                    _emptyRow = i;
                    _emptyCol = j;
                } else if (_table[i][j] == 0) {
                    _emptyRow = row;
                    _emptyCol = col;
                }

                // swap the selected block with the current block
                int temp = _table[row][col];
                _table[row][col] = _table[i][j];
                _table[i][j] = temp;
            }
        }

        if (!isValidTable() || this.checkWin()) {
            this.shuffleTable();
        }
    }

    /**
     * External Citation
     *      Date:   February 21, 2023
     *      Problem: Trouble checking if the N-Square puzzle is solvable.
     *
     * Resource:
     *      * https://mathworld.wolfram.com/15Puzzle.html
     *      * https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     * Solution: I used the algorithm to check if the N-Square puzzle is solvable.
     */

    /**
     * isValidTable
     *
     * this method is used to check if the table is solvable. the method
     * counts the number of inversions in the table. if the number of
     * inversions is even, the table is solvable.
     *
     * @return  true if the table is valid, false otherwise
     */
    private boolean isValidTable() {
        // count the number of inversions
        int inversions = 0;
        for (int i = 0; i < _blocksNum; i++) {
            for (int j = 0; j < _blocksNum; j++) {
                for (int k = i; k < _blocksNum; k++) {
                    for (int l = (k == i) ? j + 1 : 0; l < _blocksNum; l++) {
                        if (_table[i][j] != 0 && _table[k][l] != 0 &&
                            _table[i][j] > _table[k][l]) {
                                inversions++;
                        }
                    }
                }
            }
        }

        // check if the table is solvable
        if (_blocksNum % 2 == 1) {
            return inversions % 2 == 0;
        } else {
            return (inversions + _emptyRow) % 2 == 1;
        }
    }

    /**
     * swapBlocks
     *
     * this method swaps the selected block with the empty block. the method
     * checks if the selected block is adjacent to the empty block. if the
     * adjacent, the method swaps the blocks and increments the total count.
     *
     * @param row   the row of the selected block
     * @param col   the column of the selected block
     * @return      true if the blocks were swapped, false otherwise
     */
    public boolean swapBlocks(int row, int col) {
        if (row == _emptyRow && col == _emptyCol) {
            return false;
        } else if (row != _emptyRow && col != _emptyCol) {
            return false;
        } else if (col - _emptyCol > 1 || col - _emptyCol < -1) {
            return false;
        } else if (row - _emptyRow > 1 || row - _emptyRow < -1) {
            return false;
        }

        _table[_emptyRow][_emptyCol] = _table[row][col];
        _table[row][col] = 0;

        _emptyRow = row;
        _emptyCol = col;

        return true;
    }

    /**
     * checkWin
     *
     * this method checks if the game has been won. the method checks if the
     * table is in ascending order. the empty block should be the last block.
     *
     * @return  true if the game has been won, false otherwise
     */
    private boolean checkWin() {
        // check if the table is in ascending order
        int count = 1;
        for (int i = 0; i < _blocksNum; i++) {
            for (int j = 0; j < _blocksNum; j++) {
                if (_table[i][j] != count && _table[i][j] != 0) {
                    return false;
                }
                count++;
            }
        }

        return true;
    }

    /**
     * getBlocksNum
     *
     * this method returns the number of blocks in the table. the number of
     * blocks is equal to the number of rows and columns in the table.
     *
     * @return  the number of blocks in the table
     */
    public int getBlocksNum() {
        return _blocksNum;
    }

    /**
     * getTable
     *
     * this method returns the table. the table is a two-dimensional array
     * of integers. the table contains the numbers 0 to n^2 - 1, where n is
     * the number of blocks in the table.
     *
     * @return  the table of blocks
     */
    public int[][] getTable() {
        return _table;
    }

    /**
     * setBlocksNum
     *
     * this method sets the number of blocks in the table. the number of
     * blocks is equal to the number of rows and columns in the table.
     *
     * @param blocksNum the number of blocks in the table
     */
    public void setBlocksNum(int blocksNum) {
        _blocksNum = blocksNum;
    }
}
