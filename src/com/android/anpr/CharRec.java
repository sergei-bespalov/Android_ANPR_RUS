package com.android.anpr;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.*;

/**
 * Created by Сергей on 24.04.2014.
 */
public class CharRec {
    public List<SymbolMask> alphabet = new ArrayList<SymbolMask>();
    TessBaseAPI                    baseApi;
    public CharRec() 
        double[][] tempB =
                       {{1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1}};

        double[][] tempW =
                       {{1, 1, 2, 1, 1},
                        {1, 2, 1, 2, 1},
                        {2, 1, 1, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 1, 1, 2},
                        {1, 2, 1, 2, 1},
                        {1, 1, 2, 1, 1}};

        alphabet.add(new SymbolMask('0', tempB, tempW));
        tempB = new double[][]
                       {{2, 1, 1, 0, 0},
                        {1, 1, 0, 0, 0},
                        {1, 0, 0, 1, 0},
                        {0, 0, 1, 1, 0},
                        {1, 1, 1, 1, 0},
                        {2, 2, 2, 1, 0},
                        {3, 3, 2, 1, 0},
                        {10, 10, 2, 1, 0},
                        {10, 10, 2, 1, 0},
                        {10, 10, 2, 1, 0}};
        tempW = new double[][]
                       {{0, 1, 1, 2, 2},
                        {1, 1, 2, 2, 2},
                        {1, 2, 2, 1, 2},
                        {2, 2, 1, 1, 2},
                        {1, 1, 1, 1, 2},
                        {0, 0, 0, 1, 2},
                        {0, 0, 0, 1, 2},
                        {0, 0, 0, 1, 2},
                        {0, 0, 0, 1, 2},
                        {0, 0, 0, 1, 2}};
        alphabet.add(new SymbolMask('1', tempB, tempW));
        tempB = new double[][]
                       {{1, 0, 0, 0, 1},
                        {0, 0, 1, 0, 1},
                        {0, 1, 1, 1, 0},
                        {1, 2, 2, 1, 0},
                        {3, 2, 1, 0, 1},
                        {2, 1, 0, 0, 1},
                        {1, 1, 0, 1, 2},
                        {1, 0, 1, 2, 3},
                        {0, 0, 1, 1, 1},
                        {0, 0, 0, 0, 0}};
        tempW = new double[][]
                       {{1, 1, 2, 1, 1},
                        {1, 2, 1, 2, 1},
                        {2, 1, 1, 1, 2},
                        {1, 0, 0, 1, 2},
                        {0, 0, 1, 2, 1},
                        {0, 1, 1, 2, 1},
                        {1, 1, 2, 1, 0},
                        {1, 2, 1, 0, 0},
                        {1, 2, 1, 1, 1},
                        {1, 2, 2, 2, 2}};
        alphabet.add(new SymbolMask('2', tempB, tempW));
        tempB = new double[][]
                        {{0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 0},
                        {2, 2, 1, 0, 1},
                        {2, 1, 0, 1, 2},
                        {10, 1,0, 1, 2},
                        {2, 1, 0, 0, 1},
                        {1, 2, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1}};
        tempW = new double[][]
                        {{2, 2, 2, 2, 2},
                        {1, 1, 1, 3, 2},
                        {0, 0, 1, 3, 1},
                        {0, 1, 2, 2, 0},
                        {0, 1,2, 1, 0},
                        {0, 1, 2, 2, 1},
                        {1, 0, 1, 2, 2},
                        {2, 1, 1, 2, 2},
                        {2, 2, 1, 2, 1},
                        {1, 2, 2, 2, 1}};
        alphabet.add(new SymbolMask('3', tempB, tempW));
        tempB = new double[][]
                       {{5 , 1 , 1 , 0 , 1 },
                        {2 , 1 , 0 , 0 , 1 },
                        {1 , 1 , 0 , 1 , 1 },
                        {1 , 0 , 0 , 1 , 1 },
                        {1 , 0 , 1 , 1 , 0},
                        {0 , 0 , 1 , 1 , 0},
                        {0 , 0 , 0 , 0 , 0},
                        {1 , 1 , 1 , 1 , 0},
                        {10 , 5 , 2 , 1 , 0},
                        {10 , 5 , 2 , 1 , 0}};
        tempW = new double[][]
                       {{0 , 0 , 1 , 2 , 1 },
                        {0 , 1 , 2 , 2 , 1 },
                        {0 , 1 , 2 , 1 , 1 },
                        {1 , 2 , 2 , 1 , 1 },
                        {1 , 2 , 1 , 1 , 2},
                        {2 , 2 , 1 , 1 , 2},
                        {2 , 2 , 2 , 2 , 2},
                        {1 , 1 , 1 , 1 , 2},
                        {0 , 0 , 0 , 1 , 2},
                        {0 , 0 , 0 , 1 , 2}};
        alphabet.add(new SymbolMask('4', tempB, tempW));
        tempB = new double[][]
                       {{0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1},
                        {0, 0, 1, 2, 2},
                        {0, 0, 1, 1, 1},
                        {0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0},
                        {1, 2, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 0},
                        {1, 0, 0, 0, 1}};
        tempW = new double[][]
                        {{2, 2, 2, 2, 2},
                        {1, 2, 1, 1, 1},
                        {1, 2, 1, 0, 0},
                        {1, 2, 1, 1, 1},
                        {1, 2, 2, 2, 1},
                        {1, 1, 1, 2, 2},
                        {1, 0, 1, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 1, 2, 2},
                        {1, 2, 2, 2, 1}};
        alphabet.add(new SymbolMask('5', tempB, tempW));
        tempB = new double[][]
                        {{1, 1, 0, 1,2},
                        {1, 0, 0, 1, 3},
                        {1, 0, 1, 1, 4},
                        {1, 0, 1, 1, 1},
                        {0, 0, 0, 0, 1},
                        {0, 0, 1, 0, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1}};
        tempW = new double[][]
                        {{0, 1, 2, 1, 0},
                        {1, 2, 2, 1, 0},
                        {1, 2, 1, 1, 0},
                        {1, 2, 1, 1, 1},
                        {2, 2, 2, 2, 1},
                        {2, 2, 1, 2, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 1, 1, 2},
                        {1, 2, 1, 2, 1},
                        {1, 2, 2, 2, 1}};
        alphabet.add(new SymbolMask('6', tempB, tempW));
        tempB = new double[][]
                        {{0, 0, 0, 0, 0},
                        {1, 1, 1, 1, 0},
                        {2, 2, 1, 0, 1},
                        {5, 2, 1, 0, 1},
                        {2, 1, 1, 0, 1},
                        {2, 1, 0, 1, 1},
                        {2, 1, 0, 1, 2},
                        {1, 1, 0, 1, 2},
                        {1, 0, 0, 1, 5},
                        {1, 0, 1, 5, 5}};
        tempW = new double[][]
                        {{2, 2, 2, 2, 2},
                        {1, 1, 1, 1, 2},
                        {0, 0, 1, 2, 1},
                        {0, 0, 1, 2, 1},
                        {0, 1, 1, 2, 1},
                        {0, 1, 2, 1, 1},
                        {0, 1, 2, 1, 0},
                        {1, 1, 2, 1, 0},
                        {1, 2, 2, 1, 0},
                        {1, 2, 1, 1, 0}};

        alphabet.add(new SymbolMask('7', tempB, tempW));
        tempB = new double[][]
                      {{1, 0, 0, 0, 1},
                       {0, 0, 1, 0, 0},
                       {0, 1, 1, 1, 0},
                       {0, 1, 1, 1, 0},
                       {1, 0, 0, 0, 1},
                       {0, 0, 1, 0, 0},
                       {0, 1, 1, 1, 0},
                       {0, 1, 1, 1, 0},
                       {0, 0, 1, 0, 0},
                       {1, 0, 0, 0, 1}};
        tempW = new double[][]
                        {{1, 1, 2, 1, 1},
                        {1, 2, 2, 2, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {1, 3, 4, 3, 1},
                        {1, 3, 4, 3, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {1, 2, 1, 2, 1},
                        {1, 1, 2, 1, 1}};
        alphabet.add(new SymbolMask('8', tempB, tempW));
        tempB = new double[][]
                        {{1, 0, 0, 0, 1},
                        {0, 0, 1, 0, 1},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 0, 1, 0, 0},
                        {1, 0, 0, 0, 0},
                        {1, 1, 1, 0, 1},
                        {7, 1, 1, 0, 1},
                        {7, 1, 0, 0, 1},
                        {7, 1, 0, 1, 1}};
        tempW = new double[][]
                        {{1, 2, 2, 2, 1},
                        {2, 2, 1, 2, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 2, 1, 2, 2},
                        {1, 2, 2, 2, 2},
                        {1, 1, 1, 2, 1},
                        {0, 1, 1, 2, 1},
                        {0, 1, 2, 2, 1},
                        {0, 1, 2, 1, 0}};
        alphabet.add(new SymbolMask('9', tempB, tempW));
        tempB = new double[][]
                       {{5, 1, 0, 1, 5},
                        {2, 1, 0, 1, 2},
                        {1, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {1, 0, 1, 1, 1},
                        {0, 1, 1, 1, 0},
                        {0, 1, 5, 1, 0}};
        tempW = new double[][]
                       {{0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {1, 2, 1, 2, 1},
                        {1, 2, 0, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 2, 2, 2, 1},
                        {1, 2, 1, 1, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2}};
        alphabet.add(new SymbolMask('A', tempB, tempW));
        tempB = new double[][]
                        {{0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1}};
        tempW = new double[][]
                       {{2, 2, 2, 2, 1},
                        {2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 2, 2, 1},
                        {2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 2, 2, 2},
                        {2, 2, 2, 2, 1}};
        alphabet.add(new SymbolMask('B', tempB, tempW));
        tempB = new double[][]
                       {{1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0},
                        {0, 1, 1, 1, 1},
                        {0, 1, 2, 2, 2},
                        {0, 1, 2, 4, 8},
                        {0, 1, 2, 4, 8},
                        {0, 1, 2, 2, 2},
                        {0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0},
                        {1, 1, 0, 1, 1}};

        tempW = new double[][]
                        {{1, 1, 2, 2, 1},
                        {1, 2, 2, 2, 2},
                        {2, 1, 1, 1, 1},
                        {2, 1, 0, 0, 0},
                        {2, 1, 0, 0, 0},
                        {2, 1, 0, 0, 0},
                        {2, 1, 0, 0, 0},
                        {2, 2, 1, 1, 1},
                        {1, 2, 2, 2, 2},
                        {1, 1, 2, 2, 1}};
        alphabet.add(new SymbolMask('C', tempB, tempW));
        tempB = new double[][]
                       {{0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 2},
                        {0, 1, 1, 1, 2},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 2},
                        {0, 1, 1, 1, 2},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}};
        tempW = new double[][]
                        {{2, 2, 2, 2, 2},
                        {2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 0},
                        {2, 1, 1, 1, 0},
                        {2, 2, 2, 2, 2},
                        {2, 2, 2, 2, 2},
                        {2, 1, 1, 1, 0},
                        {2, 1, 1, 1, 0},
                        {2, 2, 2, 2, 2},
                        {2, 2, 2, 2, 2}};
        alphabet.add(new SymbolMask('E', tempB, tempW));
        tempB = new double[][]
                        {{0, 1, 8, 1, 0},
                        {0, 1, 4, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 4, 1, 0},
                        {0, 1, 8, 1, 0}};
        tempW = new double[][]
                        {{2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 4, 4, 2},
                        {2, 2, 4, 4, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2}};
        alphabet.add(new SymbolMask('H', tempB, tempW));
        tempB = new double[][]
                        {{0, 1, 2, 1, 0},
                        {0, 1, 1, 0, 1},
                        {0, 1, 0, 0, 1},
                        {0, 1, 0, 1, 1},
                        {0, 0, 0, 1, 5},
                        {0, 0, 0, 1, 1},
                        {0, 1, 0, 1, 1},
                        {0, 1, 1, 0, 1},
                        {0, 1, 1, 0, 1},
                        {0, 1, 2, 1, 0}};
        tempW = new double[][]
                       {{2, 0, 0, 0, 2},
                        {2, 1, 1, 2, 1},
                        {2, 1, 2, 2, 1},
                        {2, 1, 2, 1, 0},
                        {2, 2, 2, 1, 0},
                        {2, 2, 2, 1, 0},
                        {2, 1, 2, 1, 1},
                        {2, 1, 1, 2, 1},
                        {2, 1, 1, 2, 1},
                        {2, 0, 0, 0, 2}};
        alphabet.add(new SymbolMask('K', tempB, tempW));
        tempB = new double[][]
                       {{0, 1, 2, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0},
                        {0, 2, 1, 2, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 4, 1, 0},
                        {0, 1, 6, 1, 0}};
        tempW = new double[][]
                       {{2, 1, 0, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 1, 2, 2},
                        {2, 2, 1, 2, 2},
                        {2, 1, 2, 1, 2},
                        {2, 1, 2, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2}};
        alphabet.add(new SymbolMask('M', tempB, tempW));
        tempB = new double[][]
                       {{1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1},
                        {0, 0, 1, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 5, 1, 0},
                        {0, 1, 5, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 1, 1, 0, 0},
                        {1, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1}};
        tempW = new double[][]
                       {{1, 1, 2, 1, 1},
                        {1, 2, 2, 2, 1},
                        {2, 2, 1, 2, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 1, 1, 2, 2},
                        {1, 2, 2, 2, 1},
                        {1, 1, 2, 1, 1}};
        alphabet.add(new SymbolMask('O', tempB, tempW));
        tempB = new double[][]
                        {{0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 1},
                        {0, 1, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 1},
                        {0, 1, 1, 1, 1},
                        {0, 1, 2, 2, 2},
                        {0, 1, 2, 4, 4},
                        {0, 1, 2, 4, 8}};
        tempW = new double[][]
                        {{2, 2, 2, 2, 1},
                        {2, 2, 2, 2, 1},
                        {2, 1, 1, 1, 2},
                        {2, 1, 1, 1, 2},
                        {2, 2, 2, 2, 1},
                        {2, 2, 2, 2, 1},
                        {2, 1, 1, 1, 1},
                        {2, 1, 0, 0, 0},
                        {2, 1, 0, 0, 0},
                        {2, 1, 0, 0, 0}};
        alphabet.add(new SymbolMask('P', tempB, tempW));
        tempB = new double[][]
                        {{0, 0, 0, 0, 0},
                        {1, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2}};
        tempW = new double[][]
                       {{2, 2, 2, 2, 2},
                        {1, 1, 2, 1, 1},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0}};
        alphabet.add(new SymbolMask('T', tempB, tempW));
        tempB = new double[][]
                       {{0, 1, 2, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 2, 1, 0}};
        tempW = new double[][]
                       {{2, 1, 0, 1, 2},
                        {1, 2, 1, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 2, 2, 2, 1},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {1, 2, 1, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 2, 1, 2, 1},
                        {2, 1, 0, 1, 2}};
        alphabet.add(new SymbolMask('X', tempB, tempW));
        tempB = new double[][]
                        {{0, 2, 2, 2, 0},
                        {1, 1, 1, 1, 0},
                        {1, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {1, 1, 0, 0, 1},
                        {2, 1, 0, 1, 1},
                        {2, 1, 0, 1, 2},
                        {2, 1, 0, 1, 2},
                        {2, 0, 1, 1, 2},
                        {2, 0, 1, 1, 2}};
        tempW = new double[][]
                       {{2, 0, 0, 0, 2},
                        {1, 1, 1, 1, 2},
                        {1, 2, 1, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 1, 2, 2, 1},
                        {0, 1, 2, 1, 1},
                        {0, 1, 2, 1, 0},
                        {0, 1, 2, 1, 0},
                        {0, 2, 1, 1, 0},
                        {0, 2, 1, 1, 0}};
        alphabet.add(new SymbolMask('Y', tempB, tempW));
        
        baseApi = new TessBaseAPI();
        baseApi.init(Environment.getExternalStorageDirectory().getAbsolutePath(), "eng");






    }
    public double testMatch(Mat img, int sym)
    {
        return Match(img,alphabet.get(sym));
    }
    public double Match(Mat img,SymbolMask A)
    {
        double value = 0;
        double a[];
        //img = Crop(img);
        Imgproc.resize(img,img,new Size(5,10));
        //  System.out.println(CountConturs(img.clone()));

        for (int x = 0; x < img.width(); x++)
        {
            for (int y = 0; y < img.height(); y++) {
                a = img.get(y, x);
                if (sum(a) > 150) value+=A.penalty_white(x, y);
                else  value+=A.penalty_black(x, y);
            }
        }
        return value;
    }
    private double  sum(double [] a)
    {
        double sum = 0;
        for(double x : a)
        {
            sum+=x;
        }
        return sum;
    }
    public static Mat Crop ( Mat A)
    {
        Imgproc.threshold(A, A, 0, 255, Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(A.clone(), contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Rect R = Imgproc.boundingRect(contours.get(1));
        A = new Mat(A,R);
        Imgproc.resize(A,A,new Size(5,10));
        return A;
    }
    public static  List<Mat> CuttingCharacters(Mat plate)
    {   List<Mat> Characters = new ArrayList<Mat>();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        List<Rect> rects = new ArrayList<Rect>();
        Imgproc.threshold(plate, plate, 0, 255, Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU); //binarization
        Mat imgTrash = plate.clone();
        Imgproc.findContours(imgTrash, contours , new Mat() , Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        for(MatOfPoint contur: contours)
        {
            Rect tst = Imgproc.boundingRect(contur);
            rects.add(tst);
        }
        rects = RemoveConturs(rects, MaxHeight(rects));
        RemoveInternal(rects);
        if (rects.size()<5) return Characters;
        qSortRects Sort = new qSortRects();
        Sort.qSort(rects);
        for (Rect R: rects)
        {
            Characters.add(new Mat(plate,R));
        }
        return Characters;
    }
    private int CountBlack (Mat img)
    {
        Imgproc.resize(img,img,new Size(5,10));
        //  System.out.println(CountConturs(img.clone()));

        double[] a;
        int value =0;
        for (int x = 0; x < img.width(); x++)
        {
            for (int y = 0; y < img.height(); y++) {
                a = img.get(y, x);
                if (a[0] < 255) value++;
            }
        }
        return value;
    }
    public void MatchAll(List<Mat> imgs)
    {
        for(Mat img: imgs)
        {
            System.out.println("\n"+imgs.indexOf(img)+ " b = "+CountBlack(img));
            for (SymbolMask A : alphabet) {
                System.out.print(" "+A.sym + "=" + Match(img, A));
            }
        }
    }
    private static int MaxHeight(List<Rect> cont)
    {
        int count;
        int i;
        for (i = 0; i < 1000; i++)
        {count = 0;
            for(Rect x : cont)
            {
                if(x.height > x.width && x.height > i && x.width > i/2) count++;
            }
            if (count < 10) break;
        }
        return i;
    }
    private static  boolean inRects (List<Rect> Rects, Point a)
    {
        for(Rect x: Rects)
        {
            if (a.inside(x)) return true;
        }
        return false;
    }
    private static List<Rect> RemoveConturs(List<Rect> cont, int MaxHeight)
    {
        List<Rect> rects = new ArrayList<Rect>();
        for (Rect x : cont)
        {
            if(x.height >x.width && x.height > MaxHeight) rects.add(x);
        }
        return rects;
    }
    private static void  RemoveInternal(List<Rect> cont)
    {
        Point p;
        for (int i = 0; i < cont.size(); i++)
        {
            p = cont.get(i).tl();
            p.x-=5;  //проверить этот параметр
            p.y-=5;
            if (inRects(cont,p)) cont.remove(i);
        }
    }
   
    public String getString (Mat plate)
    {
      List<Mat> Chars = CuttingCharacters(plate);
      String value = "";
      if (Chars.size()<5) return "nope";
        double[][] mask;
        int a =getType(Chars);
        for (int i = 0; i < Chars.size(); i++)
      {
          mask = getMask(Chars.get(i));
          if (a==0){
          if ( i==0 || i==4 || i==5) value+=getLetter(mask);
          else value+=getNumeral(mask);}
          else {if ( i==0 || i==1) value+=getLetter(mask);
          else value+=getNumeral(mask);}
      }
      return value;
    }
    
    public int getType(List<Mat> Chars)
    {
    	int valueA = Chars.get(1).height()-Chars.get(0).height();
    	int valueB = Chars.get(2).height()-Chars.get(1).height();
    	if (valueA > valueB) return 0;
    	else return 1;
    }
    
    public String getTesseractString (Mat plate)
    {
    	List<Mat> Chars = CuttingCharacters(plate);
    	String value = "";
    	Bitmap bmp;
    	if (Chars.size()<5) return "nope";
    	 for (int i = 0; i < Chars.size(); i++)
         {
    		 bmp = Bitmap.createBitmap(Chars.get(i).width() , Chars.get(i).height(), Bitmap.Config.ARGB_8888);
    		 Utils.matToBitmap(Chars.get(i), bmp);
    	     baseApi.setImage(bmp);
    	     value+= baseApi.getUTF8Text();
         }
    	return value;
    }
    public String getFullTesseractString (Mat plate)
    {
    	String value = "";
    	Bitmap bmp;
    	Imgproc.threshold(plate, plate, 0, 255, Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
    	List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    	Mat imgTrash = plate.clone();
    	Imgproc.findContours(imgTrash, contours , new Mat() , Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    	List<Rect> rects = new ArrayList<Rect>();
    	for(MatOfPoint contur: contours)
    	{
    		Rect tst = Imgproc.boundingRect(contur);
    			rects.add(tst);
    	}
    	rects = RemoveConturs(rects,MaxHeight(rects));
        double a[];
    	for (int x = 0; x < plate.width(); x++)
    	{
    		for (int y = 0; y < plate.height(); y++)
    		{
    			if(!inRects(rects, new Point(x,y))) 
    			{			
    				a = plate.get(y, x);
    				a[0] = 255;
    				plate.put(y, x, a);
    			}
    		}
    	}
    	bmp = Bitmap.createBitmap(plate.width() , plate.height(), Bitmap.Config.ARGB_8888);
    	Utils.matToBitmap(plate, bmp);
    	baseApi.setImage(bmp);
    	value = baseApi.getUTF8Text();
    	return value;
    }
    
    private char getLetter(double[][] mask)
    {

        {   int indx =0;
            double minValue = MatchMask(mask, alphabet.get(indx));
            for (int i = 0; i < alphabet.size(); i++)
            {
                if(Character.isLetter(alphabet.get(i).sym))
                {
                    if (minValue > MatchMask(mask, alphabet.get(i)))
                    {
                        indx = i;
                        minValue =  MatchMask(mask, alphabet.get(i));
                    }
                }

            }
            return alphabet.get(indx).sym;
        }
    }
    private char getNumeral(double[][] mask)
    {   int indx =0;
        double minValue = MatchMask(mask, alphabet.get(indx));
        for (int i = 0; i < alphabet.size(); i++)
        {
            if(Character.isDigit(alphabet.get(i).sym))
            {
               if (minValue > MatchMask(mask, alphabet.get(i)))
               {
                   indx = i;
                   minValue =  MatchMask(mask, alphabet.get(i));
               }
            }

        }
        return alphabet.get(indx).sym;
    }
    private double MatchMask (double[][] mask, SymbolMask A)
    {
        double value = 0;
        for (int x = 0; x < 5; x++)
        {
            for (int y = 0; y < 10; y++) {
                if (mask[y][x] > 0) value+=A.penalty_white(x, y);
                else  value+=A.penalty_black(x, y);
            }
        }
        return value;
    }
    private double[][] getMask(Mat img)
    {
        Imgproc.resize(img,img,new Size(5,10));
        double[][] mask = new double[10][5];
        double[] a;
        for (int x = 0; x < img.width(); x++)
        {
            for (int y = 0; y < img.height(); y++) {
                a = img.get(y, x);
                if (a[0] > 150) mask[y][x]=1;
                else mask[y][x]=0;
            }
        }
    return mask;
    }
    
}
