package com.android.anpr;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;



public class ANPRLogic implements Runnable{
    private float                  mRelativePlateSize   = 0.1f;
    private int                    mAbsolutePlateSize   = 0;
    public  final int              TESSERACT = 1, TESSERACT_F = 2 , PATTERN = 3;
    private int                    recType = 3;
    MatOfRect                      plates;
    String                         str;
    Mat                            img4T;
    Thread                         Rec;
    CharRec                        CRec;
    private static final Scalar    PLATE_RECT_COLOR     = new Scalar(0, 255, 0);
    
    public ANPRLogic(){ 
        str = "";
        Rec = new Thread(this, "Number recognition" );
        CRec = new CharRec();
    }
    
    public void DetectPlates(Mat img, CascadeClassifier detector)
    {
    	if (mAbsolutePlateSize == 0) {
            int height = img.rows();
            if (Math.round(height * mRelativePlateSize) > 0) {
                mAbsolutePlateSize = Math.round(height * mRelativePlateSize);
            }
        }
    	plates = new MatOfRect();
    	detector.detectMultiScale(img, plates, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                 new Size(mAbsolutePlateSize*3, mAbsolutePlateSize), new Size());
    }
    public Mat ShowDetected (Mat img)
    {
        Rect[] platesArray = plates.toArray();
        for (int i = 0; i < platesArray.length; i++)
            {Core.rectangle(img, platesArray[i].tl(), platesArray[i].br(), PLATE_RECT_COLOR, 3);break;}
    	return img;
    }
    public synchronized String Recog(Mat img)
    {
    	Rect[] platesArray = plates.toArray();
    	if (platesArray.length < 1) return "";
    	img = new Mat(img, platesArray[0]);
    	switch (recType)
    	{
    	case 1:  return  CRec.getTesseractString(img);
    	case 2:  return  CRec.getFullTesseractString(img);
    	default: return  CRec.getString(img);
    	}
    }
    
    
    public Mat ShowRecogText(Mat img)
    {
    	Rect[] platesArray = plates.toArray();
    	if (platesArray.length < 1) return img;
    	Core.putText(img, str, platesArray[0].tl(), Core.FONT_HERSHEY_SIMPLEX, 4, PLATE_RECT_COLOR,3);
    	return img;
    }
    
    public String RecogThread(Mat img)
    {
    	if(!Rec.isAlive())
        {   try {
			    Rec.join();
		        } catch (InterruptedException e) {
		     	e.printStackTrace();
		        }
            Rec = new Thread(this, "Number recognition" );
    		img4T = img.clone();
    		Rec.start();
    	}
    	return str;
    }
	public void run() {
		 str=Recog(img4T);
		}   
    public void setMinPlateSize(float plateSize) {
    mRelativePlateSize = plateSize;
    mAbsolutePlateSize = 0;
    }
    public void setRecType(int Type)
    {
    	recType = Type;
    }
}
	

