package com.example.sudoku;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class GameBoard extends Sudoku {

    public static class GameCell{
        public int realValue;
        public int assumedValue;
        public boolean isInitial;
        public boolean [] marks = {false,false,false,false,false,false,false,false,false};

        public GameCell(int realValue,boolean isInitial){
            this.realValue = realValue;
            this.isInitial = isInitial;
            if(isInitial){
                this.assumedValue = realValue;
            }
        }
    }

    public GameLevel level;
    public boolean bigNumber = true;
    public int currentCellX = -1;
    public int currentCellY = -1;
    public GameCell [][] cells;

    private GameBoard(int N,GameLevel level, GameCell [][] cells){
        super(N);
        this.level = level;
        this.cells = cells;
    }

    public int getSelectedValue() {
        indexOutOFBound();
        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];
        return currentCell.assumedValue;
    }

    public void pushValue(int value) {
        indexOutOFBound();
        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];
        if (currentCell.isInitial) return;
        if (this.bigNumber) {
            currentCell.assumedValue = value;
        } else {
            currentCell.marks[value - 1] = !currentCell.marks[value - 1];
        }
    }

    public void clearCell() {
        indexOutOFBound();
        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];
        if (currentCell.isInitial) return;
        currentCell.assumedValue = 0;
        currentCell.marks = new boolean[]{false, false, false, false, false, false, false, false, false};
    }

    public void indexOutOFBound(){
        if (currentCellX == -1 || currentCellY == -1 || currentCellY >= cells.length || currentCellX >= cells[0].length) {
            throw new IndexOutOfBoundsException("Current cell indices are out of bounds");
        }
    }

    @NonNull
    @Contract("_ -> new")
    public static GameBoard getGameBoard(@NonNull String level){

        Sudoku sudoku = new Sudoku(9);
        sudoku.fillValues();
        switch (level) {
            case "Medium":
                return new GameBoard(9,GameLevel.Medium, new GameCell[][]{
                        {new GameCell(mat[0][0].value, true), new GameCell(mat[0][1].value, false), new GameCell(mat[0][2].value, false),
                                new GameCell(mat[0][3].value, true), new GameCell(mat[0][4].value, false), new GameCell(mat[0][5].value, false),
                                new GameCell(mat[0][6].value, true), new GameCell(mat[0][7].value, false), new GameCell(mat[0][8].value, false)
                        },
                        {new GameCell(mat[1][0].value, false), new GameCell(mat[1][1].value, false), new GameCell(mat[1][2].value, false),
                                new GameCell(mat[1][3].value, true), new GameCell(mat[1][4].value, false), new GameCell(mat[1][5].value, false),
                                new GameCell(mat[1][6].value, false), new GameCell(mat[1][7].value, false), new GameCell(mat[1][8].value, false)
                        },
                        {new GameCell(mat[2][0].value, false), new GameCell(mat[2][1].value, true), new GameCell(mat[2][2].value, true),
                                new GameCell(mat[2][3].value, false), new GameCell(mat[2][4].value, true), new GameCell(mat[2][5].value, false),
                                new GameCell(mat[2][6].value, false), new GameCell(mat[2][7].value, true), new GameCell(mat[2][8].value, false)
                        },
                        {new GameCell(mat[3][0].value, true), new GameCell(mat[3][1].value, true), new GameCell(mat[3][2].value, false),
                                new GameCell(mat[3][3].value, false), new GameCell(mat[3][4].value, false), new GameCell(mat[3][5].value, false),
                                new GameCell(mat[3][6].value, true), new GameCell(mat[3][7].value, true), new GameCell(mat[3][8].value, false)},
                        {new GameCell(mat[4][0].value, false), new GameCell(mat[4][1].value, true), new GameCell(mat[4][2].value, false),
                                new GameCell(mat[4][3].value, true), new GameCell(mat[4][4].value, false), new GameCell(mat[4][5].value, true),
                                new GameCell(mat[4][6].value, false), new GameCell(mat[4][7].value, true), new GameCell(mat[4][8].value, false)},
                        {new GameCell(mat[5][0].value, false), new GameCell(mat[5][1].value, true), new GameCell(mat[5][2].value, true),
                                new GameCell(mat[5][3].value, false), new GameCell(mat[5][4].value, false), new GameCell(mat[5][5].value, false),
                                new GameCell(mat[5][6].value, false), new GameCell(mat[5][7].value, true), new GameCell(mat[5][8].value, true)},

                        {new GameCell(mat[6][0].value, false), new GameCell(mat[6][1].value, true), new GameCell(mat[6][2].value, false),
                                new GameCell(mat[6][3].value, false), new GameCell(mat[6][4].value, true), new GameCell(mat[6][5].value, false),
                                new GameCell(mat[6][6].value, true), new GameCell(mat[6][7].value, true), new GameCell(mat[6][8].value, false)},
                        {new GameCell(mat[7][0].value, false), new GameCell(mat[7][1].value, false), new GameCell(mat[7][2].value, false),
                                new GameCell(mat[7][3].value, true), new GameCell(mat[7][4].value, false), new GameCell(mat[7][5].value, true),
                                new GameCell(mat[7][6].value, false), new GameCell(mat[7][7].value, false), new GameCell(mat[7][8].value, false)},
                        {new GameCell(mat[8][0].value, false), new GameCell(mat[8][1].value, false), new GameCell(mat[8][2].value, true),
                                new GameCell(mat[8][3].value, false), new GameCell(mat[8][4].value, false), new GameCell(mat[8][5].value, true),
                                new GameCell(mat[8][6].value, false), new GameCell(mat[8][7].value, false), new GameCell(mat[8][8].value, true)}
                });
            case "Hard":
                return new GameBoard(9,GameLevel.Hard, new GameCell[][]{
                        {new GameCell(mat[0][0].value, true), new GameCell(mat[0][1].value, false), new GameCell(mat[0][2].value, false),
                                new GameCell(mat[0][3].value, true), new GameCell(mat[0][4].value, false), new GameCell(mat[0][5].value, false),
                                new GameCell(mat[0][6].value, true), new GameCell(mat[0][7].value, false), new GameCell(mat[0][8].value, false)
                        },
                        {new GameCell(mat[1][0].value, false), new GameCell(mat[1][1].value, false), new GameCell(mat[1][2].value, false),
                                new GameCell(mat[1][3].value, true), new GameCell(mat[1][4].value, false), new GameCell(mat[1][5].value, false),
                                new GameCell(mat[1][6].value, false), new GameCell(mat[1][7].value, false), new GameCell(mat[1][8].value, false)
                        },
                        {new GameCell(mat[2][0].value, false), new GameCell(mat[2][1].value, true), new GameCell(mat[2][2].value, true),
                                new GameCell(mat[2][3].value, false), new GameCell(mat[2][4].value, true), new GameCell(mat[2][5].value, false),
                                new GameCell(mat[2][6].value, false), new GameCell(mat[2][7].value, true), new GameCell(mat[2][8].value, false)
                        },
                        {new GameCell(mat[3][0].value, true), new GameCell(mat[3][1].value, true), new GameCell(mat[3][2].value, false),
                                new GameCell(mat[3][3].value, false), new GameCell(mat[3][4].value, false), new GameCell(mat[3][5].value, false),
                                new GameCell(mat[3][6].value, true), new GameCell(mat[3][7].value, true), new GameCell(mat[3][8].value, false)},
                        {new GameCell(mat[4][0].value, false), new GameCell(mat[4][1].value, true), new GameCell(mat[4][2].value, false),
                                new GameCell(mat[4][3].value, true), new GameCell(mat[4][4].value, false), new GameCell(mat[4][5].value, true),
                                new GameCell(mat[4][6].value, false), new GameCell(mat[4][7].value, true), new GameCell(mat[4][8].value, false)},
                        {new GameCell(mat[5][0].value, false), new GameCell(mat[5][1].value, true), new GameCell(mat[5][2].value, true),
                                new GameCell(mat[5][3].value, false), new GameCell(mat[5][4].value, false), new GameCell(mat[5][5].value, false),
                                new GameCell(mat[5][6].value, false), new GameCell(mat[5][7].value, true), new GameCell(mat[5][8].value, true)},

                        {new GameCell(mat[6][0].value, false), new GameCell(mat[6][1].value, true), new GameCell(mat[6][2].value, false),
                                new GameCell(mat[6][3].value, false), new GameCell(mat[6][4].value, true), new GameCell(mat[6][5].value, false),
                                new GameCell(mat[6][6].value, true), new GameCell(mat[6][7].value, true), new GameCell(mat[6][8].value, false)},
                        {new GameCell(mat[7][0].value, false), new GameCell(mat[7][1].value, false), new GameCell(mat[7][2].value, false),
                                new GameCell(mat[7][3].value, true), new GameCell(mat[7][4].value, false), new GameCell(mat[7][5].value, true),
                                new GameCell(mat[7][6].value, false), new GameCell(mat[7][7].value, false), new GameCell(mat[7][8].value, false)},
                        {new GameCell(mat[8][0].value, false), new GameCell(mat[8][1].value, false), new GameCell(mat[8][2].value, true),
                                new GameCell(mat[8][3].value, false), new GameCell(mat[8][4].value, false), new GameCell(mat[8][5].value, true),
                                new GameCell(mat[8][6].value, false), new GameCell(mat[8][7].value, false), new GameCell(mat[8][8].value, true)}
                });
            case "Easy":
                return new GameBoard(9,GameLevel.Easy, new GameCell[][]{
                        {new GameCell(mat[0][0].value, true), new GameCell(mat[0][1].value, false), new GameCell(mat[0][2].value, false),
                                new GameCell(mat[0][3].value, true), new GameCell(mat[0][4].value, false), new GameCell(mat[0][5].value, false),
                                new GameCell(mat[0][6].value, true), new GameCell(mat[0][7].value, false), new GameCell(mat[0][8].value, false)
                        },
                        {new GameCell(mat[1][0].value, false), new GameCell(mat[1][1].value, false), new GameCell(mat[1][2].value, false),
                                new GameCell(mat[1][3].value, true), new GameCell(mat[1][4].value, false), new GameCell(mat[1][5].value, false),
                                new GameCell(mat[1][6].value, false), new GameCell(mat[1][7].value, false), new GameCell(mat[1][8].value, false)
                        },
                        {new GameCell(mat[2][0].value, false), new GameCell(mat[2][1].value, true), new GameCell(mat[2][2].value, true),
                                new GameCell(mat[2][3].value, false), new GameCell(mat[2][4].value, true), new GameCell(mat[2][5].value, false),
                                new GameCell(mat[2][6].value, false), new GameCell(mat[2][7].value, true), new GameCell(mat[2][8].value, false)
                        },
                        {new GameCell(mat[3][0].value, true), new GameCell(mat[3][1].value, true), new GameCell(mat[3][2].value, false),
                                new GameCell(mat[3][3].value, false), new GameCell(mat[3][4].value, false), new GameCell(mat[3][5].value, false),
                                new GameCell(mat[3][6].value, true), new GameCell(mat[3][7].value, true), new GameCell(mat[3][8].value, false)},
                        {new GameCell(mat[4][0].value, false), new GameCell(mat[4][1].value, true), new GameCell(mat[4][2].value, false),
                                new GameCell(mat[4][3].value, true), new GameCell(mat[4][4].value, false), new GameCell(mat[4][5].value, true),
                                new GameCell(mat[4][6].value, false), new GameCell(mat[4][7].value, true), new GameCell(mat[4][8].value, false)},
                        {new GameCell(mat[5][0].value, false), new GameCell(mat[5][1].value, true), new GameCell(mat[5][2].value, true),
                                new GameCell(mat[5][3].value, false), new GameCell(mat[5][4].value, false), new GameCell(mat[5][5].value, false),
                                new GameCell(mat[5][6].value, false), new GameCell(mat[5][7].value, true), new GameCell(mat[5][8].value, true)},

                        {new GameCell(mat[6][0].value, false), new GameCell(mat[6][1].value, true), new GameCell(mat[6][2].value, false),
                                new GameCell(mat[6][3].value, false), new GameCell(mat[6][4].value, true), new GameCell(mat[6][5].value, false),
                                new GameCell(mat[6][6].value, true), new GameCell(mat[6][7].value, true), new GameCell(mat[6][8].value, false)},
                        {new GameCell(mat[7][0].value, false), new GameCell(mat[7][1].value, false), new GameCell(mat[7][2].value, false),
                                new GameCell(mat[7][3].value, true), new GameCell(mat[7][4].value, false), new GameCell(mat[7][5].value, true),
                                new GameCell(mat[7][6].value, false), new GameCell(mat[7][7].value, false), new GameCell(mat[7][8].value, false)},
                        {new GameCell(mat[8][0].value, false), new GameCell(mat[8][1].value, false), new GameCell(mat[8][2].value, true),
                                new GameCell(mat[8][3].value, false), new GameCell(mat[8][4].value, false), new GameCell(mat[8][5].value, true),
                                new GameCell(mat[8][6].value, false), new GameCell(mat[8][7].value, false), new GameCell(mat[8][8].value, true)}
                });
            default:
                throw new RuntimeException("Not yet Implemented");
        }
    }
}
