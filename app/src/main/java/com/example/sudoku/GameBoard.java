package com.example.sudoku;

import java.io.Serializable;
public class GameBoard extends Sudoku {

    GameBoard(int N) {
        super(N);
    }

    public static class GameCell{
        public int realValue;
        public int assumedValue;
        public boolean isInitial = false;
        public boolean [] marks = {false,false,false,false,false,false,false,false,false};

        public GameCell(int realValue){
            this.realValue = realValue;
        }

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

    private GameBoard(GameLevel level, GameCell [][] cells){
        super();
        this.level = level;
        this.cells = cells;
    }

    public int getSelectedValue(){
        if(currentCellX == -1) return 0;
        if(currentCellY == -1) return 0;
        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];
        return currentCell.assumedValue;
    }

    public void pushValue(int value){
        if(currentCellX == -1) return;
        if(currentCellY == -1) return;

        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];
        if(currentCell.isInitial) return;
        if(this.bigNumber){
            currentCell.assumedValue = value;
        } else{
            currentCell.marks[value-1] = !currentCell.marks[value-1];
        }
    }


    public void clearCell(){
        if(currentCellX == -1) return;
        if(currentCellY == -1) return;

        GameCell currentCell = this.cells[this.currentCellY][this.currentCellX];

        if(currentCell.isInitial) return;
        currentCell.assumedValue = 0;
        currentCell.marks = new boolean [] {false,false,false,false,false,false,false,false,false};
    }

    public static GameBoard getGameBoard(GameLevel level){
        if(level != GameLevel.Medium) throw new RuntimeException("Not yet Implemented");
        Sudoku sudoku = new Sudoku(9);
        sudoku.fillValues();


        return new GameBoard(level,new GameCell[][]{
                {new GameCell(mat[0][0],true),new GameCell(mat[0][1],false),new GameCell(mat[0][2],false),
                        new GameCell(mat[0][3],true),new GameCell(mat[0][4],false),new GameCell(mat[0][5],false),
                            new GameCell(mat[0][6],true),new GameCell(mat[0][7],false),new GameCell(mat[0][8],false)},
                { new GameCell(mat[1][0],false), new GameCell(mat[1][1],false), new GameCell(mat[1][2],false),
                        new GameCell(mat[1][3],true), new GameCell(mat[1][4],false), new GameCell(mat[1][5],false),
                            new GameCell(mat[1][6],false), new GameCell(mat[1][7],false), new GameCell(mat[1][8],false) },
                { new GameCell(mat[2][0],false), new GameCell(mat[2][1],true), new GameCell(mat[2][2],true),
                        new GameCell(mat[2][3],false), new GameCell(mat[2][4],true), new GameCell(mat[2][5],false),
                            new GameCell(mat[2][6],false), new GameCell(mat[2][7],true), new GameCell(mat[2][8],false) },

                { new GameCell(mat[3][0],true), new GameCell(mat[3][1],true), new GameCell(mat[3][2],false),
                        new GameCell(mat[3][3],false), new GameCell(mat[3][4],false), new GameCell(mat[3][5],false),
                            new GameCell(mat[3][6],true), new GameCell(mat[3][7],true), new GameCell(mat[3][8],false) },
                { new GameCell(mat[4][0],false), new GameCell(mat[4][1],true), new GameCell(mat[4][2],false),
                        new GameCell(mat[4][3],true), new GameCell(mat[4][4],false), new GameCell(mat[4][5],true),
                            new GameCell(mat[4][6],false), new GameCell(mat[4][7],true), new GameCell(mat[4][8],false) },
                { new GameCell(mat[5][0],false), new GameCell(mat[5][1],true), new GameCell(mat[5][2],true),
                        new GameCell(mat[5][3],false), new GameCell(mat[5][4],false), new GameCell(mat[5][5],false),
                            new GameCell(mat[5][6],false), new GameCell(mat[5][7],true), new GameCell(mat[5][8],true) },

                { new GameCell(mat[6][0],false), new GameCell(mat[6][1],true), new GameCell(mat[6][2],false),
                        new GameCell(mat[6][3],false), new GameCell(mat[6][4],true), new GameCell(mat[6][5],false),
                            new GameCell(mat[6][6],true), new GameCell(mat[6][7],true), new GameCell(mat[6][8],false) },
                { new GameCell(mat[7][0],false), new GameCell(mat[7][1],false), new GameCell(mat[7][2],false),
                        new GameCell(mat[7][3],true), new GameCell(mat[7][4],false), new GameCell(mat[7][5],true),
                            new GameCell(mat[7][6],false), new GameCell(mat[7][7],false), new GameCell(mat[7][8],false) },
                { new GameCell(mat[8][0],false), new GameCell(mat[8][1],false), new GameCell(mat[8][2],true),
                        new GameCell(mat[8][3],false), new GameCell(mat[8][4],false), new GameCell(mat[8][5],true),
                            new GameCell(mat[8][6],false), new GameCell(mat[8][7],false), new GameCell(mat[8][8],true) }
        });
    }
}
