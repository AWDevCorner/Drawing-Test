package it.androidworld.devcorner.drawingtest;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class MyActivity extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener{

    private GestureDetector gestureDetector;
    private JustAView jav;
    private Button btnSalvaScreenshot;
    private float layoutX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gestureDetector = new GestureDetector(this, this);
        jav = (JustAView) findViewById(R.id.justaview);

        btnSalvaScreenshot = (Button) findViewById(R.id.btnSalvaScreenshot);
        btnSalvaScreenshot.setOnClickListener(this);

        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        layoutX = size.x;
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gestureDetector.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent start, MotionEvent stop, float v, float v2) {

        final float diffx = stop.getX() - start.getX();
        final float diffy = stop.getY() - start.getY();

        if(Math.abs(diffx) > Math.abs(diffy)){

            final int angle = getAngle(diffx);

            /*
            if(diffx > 0) jav.swipeRight(angle);
            else jav.swipeLeft(angle);
            */

            if(diffx > 0) jav.setSweepAngle(jav.getSweepAngle() + angle);
            else jav.setSweepAngle(jav.getSweepAngle() - angle);

        }
        return true;
    }

    private int getAngle(float diff){

        // result : 360 = diff : width
        // result = diff * 360 / width

        final float coeff = Math.abs(diff) * 360;
        final float fResult = coeff / layoutX;
        return Math.round(fResult);
    }

    @Override
    public void onClick(View view) {

        final String tmpFileName = Environment.getExternalStorageDirectory().toString() +
                File.separator + String.valueOf(Calendar.getInstance().getTimeInMillis()) + ".png";

        final View tmpView = jav;

        final ViewHolder holder = new ViewHolder(tmpView,new Azione(),tmpFileName);

        new ImageSaver().execute(new ViewHolder[]{ holder });

    }

    private final class Azione implements ViewHolder.Callback{
        @Override
        public void action(boolean result) {
            Toast.makeText(getApplicationContext(),"Immagine salvata correttamente", Toast.LENGTH_SHORT).show();
        }
    }

}
