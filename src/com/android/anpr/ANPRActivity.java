package com.android.anpr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import com.android.anpr.R;
import com.android.anpr.ANPRLogic;

public class ANPRActivity  extends Activity implements CvCameraViewListener2 {
private static final String        TAG                 = "ANPR::Activity";

private Mat                        mRgba;
private Mat                        mGray;
private File                       mCascadeFile;
private CascadeClassifier          mJavaDetector;
private CameraBridgeViewBase       mOpenCvCameraView;
private ANPRLogic                  ANPR;
private MenuItem                   mItemPlate30;
private MenuItem                   mItemPlate10;
private MenuItem                   mItemTess1;
private MenuItem                   mItemTess2;
private MenuItem                   mItemPattern;
private BaseLoaderCallback         mLoaderCallback = new BaseLoaderCallback(this) {
    public void onManagerConnected(int status) {
        switch (status) {
            case LoaderCallbackInterface.SUCCESS:
            {
                Log.i(TAG, "OpenCV loaded successfully");
                try {
                    // load cascade file from application resources
                	InputStream is = getResources().openRawResource(R.raw.cascade);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    mCascadeFile = new File(cascadeDir, "cascade.xml");                  
                    FileOutputStream os = new FileOutputStream(mCascadeFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }                                         
                    is.close();
                    os.close();
                    mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                    if (mJavaDetector.empty()) {
                        Log.e(TAG, "Failed to load cascade classifier");
                        mJavaDetector = null;
                    } else
                        Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
                    cascadeDir.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                }
                mOpenCvCameraView.enableView();
            } break;
            default:
            {
                super.onManagerConnected(status);
            } break;
        }
    }
};
    public ANPRActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.anpr_surface_view);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.anpr_surface_view);
        ANPR = new ANPRLogic();
        mOpenCvCameraView.setCvCameraViewListener(this);
        }
    
	public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
	}
	public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
	}
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		 mRgba = inputFrame.rgba();
	     mGray = inputFrame.gray();
	     ANPR.DetectPlates(mGray,mJavaDetector);
	     ANPR.RecogThread(mGray);
	     mRgba = ANPR.ShowDetected(mRgba);
	     mRgba = ANPR.ShowRecogText(mRgba);
	     return mRgba;
	}
	
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_7, this, mLoaderCallback);
    }
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemPlate30 = menu.add("Plate size 30%");
        mItemPlate10 = menu.add("Plate size 10%");
        mItemTess1   = menu.add("Tesseract 1");
        mItemTess2   = menu.add("Tesseract 2");
        mItemPattern = menu.add("Pattern");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemPlate30)
        	ANPR.setMinPlateSize(0.16f);
        else if (item == mItemPlate10)
        	ANPR.setMinPlateSize(0.1f);
        else if (item == mItemTess1)
        	ANPR.setRecType(ANPR.TESSERACT);
        else if (item == mItemTess2)
        	ANPR.setRecType(ANPR.TESSERACT_F);
        else if (item == mItemPattern)
        	ANPR.setRecType(ANPR.PATTERN);
    
        return true;
    }


}
