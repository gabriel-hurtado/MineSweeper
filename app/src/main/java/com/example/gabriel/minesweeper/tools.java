package com.example.gabriel.minesweeper;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gabriel on 20/05/2015.
 */


public class tools implements Parcelable {
   protected int[] minePositions;
    protected grid gameGeneration;
    private int size;


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeIntArray(minePositions);
        dest.writeParcelable(gameGeneration, flags);
    }

    public tools(int size, double percentage) {
        this.size=size;
        this.minePositions = generateMines(percentage, size);
        this.gameGeneration = new grid(size, percentage);

    }

    public grid getGrid(){
        return this.gameGeneration;
    }

    public int[] getMinePos(){
        return this.minePositions;
    }


    public class grid implements Parcelable{
        public int[][] gridArray;

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeIntArray(tools.toOneDimension(gridArray));
        }

        public grid(int size, double percentage) {

            this.gridArray = new int[size][size];
            this.putMines(minePositions);
            this.addNumberOfMinesNear();
        }

        public int getSize() {
            return this.gridArray[0].length;
        }

        public int[] toSimpleArray() {
            int size = this.getSize();
            int[] simpleArray = new int[size * size];
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    simpleArray[counter] = this.gridArray[i][j];
                    counter++;
                }
            }
            return simpleArray;
        }

        private void putMines(int[] minesArray) {
            for (int minePosition : minesArray) {
                int line = getLine(minePosition, size);
                int column = getColumn(minePosition, size);
                this.gridArray[line][column] = -1;
            }
        }

        private void addNumberOfMinesNear() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (gridArray[i][j] != -1) {
                        gridArray[i][j] = minesNear(i, j);
                    }
                }
            }
        }

        public int minesNear(int i, int j) {
            int value = 0;
            /*counter the number of mines near and return an int*/
            for (int bouclei = 0; bouclei < size; bouclei++) {
                for (int bouclej = 0; bouclej < size; bouclej++) {
                    if (gridArray[bouclei][bouclej] == -1) {
                        if (((bouclei >= i - 1) && (bouclei <= i + 1) && ((bouclej >= j - 1) && (bouclej <= j + 1)))) {
                            value += 1;
                        }
                    }
                }
            }
            return value;
        }
    }
    public static int[] generateMines(double percentage, int size) {
        List<Integer> list = new ArrayList<>();
        int[] listArray;
        int numberOfMines = (int) ((size * size) * percentage);
        for (int i = 0; i < numberOfMines; ) {
            int rand = ((int) (Math.random() * (size * size) ));
            if (!list.contains(rand)) {
                list.add(rand);
                i++;
            }
        }
        listArray = convertIntegers(list);
        return listArray;
    }

    public static int getLine(int position, int size) {
        return position / size;
    }

    public static int getColumn(int position, int size) {
        return position % size;
    }

    public static int getColumnWidth(Context context, GridView gridView) {
        if (android.os.Build.VERSION.SDK_INT >= 16)
            return gridView.getColumnWidth();

        int cols = gridView.getNumColumns();
        int width = gridView.getWidth();

        return (width) / cols;
    }

    public Button[] BoxesNear(int position, GridView gridView) {
        int numberOfLine = position / this.size;
        int numberOfColumn = position % size;
        int[] positions = new int[8];
        int numNear = 0;
        boolean notSameCase;

        for (int bouclei = 0; bouclei < size; bouclei++) {
            for (int bouclej = 0; bouclej < size; bouclej++) {
                notSameCase = !((bouclei == numberOfLine) && (bouclej == numberOfColumn));
                if ((((bouclei >= numberOfLine - 1) && (bouclei <= numberOfLine + 1)) && ((bouclej >= numberOfColumn - 1) && (bouclej <= numberOfColumn + 1))) && notSameCase) {
                    positions[numNear] = bouclei * size + bouclej;
                    numNear += 1;
                }
            }
        }
        return getButtons(positions, numNear, gridView);

    }

    public static Button[] getButtons(int[] positions, int length, GridView gridView) {
        Button[] boxes = new Button[length];
        int tempPos;
        for (int i = 0; i < length; i++) {
            tempPos = positions[i];
            boxes[i] = (Button) gridView.getChildAt(tempPos);
        }
        return boxes;
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }


    public static int[] toOneDimension(int[][] input){
        int[] output = new int[input.length * input[0].length];

        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                output[i*j] = input[i][j];
            }
        }

        return output;
    }


    public static int[][] toTwoDimensions(int dimensions, int[] input){

        int[][] output = new int[input.length / dimensions][dimensions];

        for(int i = 0; i < input.length; i++){
            output[i/dimensions][i % dimensions] = input[i];
        }

        return output;
    }



}
