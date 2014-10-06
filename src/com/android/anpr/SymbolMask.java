package com.android.anpr;

/**
 * Created by Сергей on 24.04.2014.
 */
public class SymbolMask {
    char sym;
    double[][] Bmask;
    double[][] Wmask;
    public SymbolMask(char Isym, double[][] ImaskB,double[][] ImaskW)
    {
        sym = Isym;
        Bmask = ImaskB;
        Wmask = ImaskW;
    }
    public double penalty_black(int x, int y)
    {
        return Bmask[y][x];
    }
    public double penalty_white(int x, int y)
    {
        return Wmask[y][x];
    }
}
