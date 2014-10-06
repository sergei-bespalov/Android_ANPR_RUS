package com.android.anpr;

import org.opencv.core.Rect;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Сергей on 26.04.2014.
 */
public class qSortRects {
    Random rand = new Random();
    public  void qSort(List<Rect> array) {
        int n = array.size();
        int i = 0;
        int j = n-1;
        double x = array.get(rand.nextInt(n)).tl().x;
        while (i <= j) {
            while (array.get(i).tl().x < x) {
                i++;
            }
            while (array.get(j).tl().x > x) {
                j--;
            }
            if (i <= j) {
                Collections.swap(array, i, j);
                i++;
                j--;
            }
        }
        if (j>0){
            qSort(array.subList(0, j + 1));
        }
        if (i<n){
            qSort(array.subList(i,n));
        }
    }
}
