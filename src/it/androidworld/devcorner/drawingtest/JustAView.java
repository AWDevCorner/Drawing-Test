package it.androidworld.devcorner.drawingtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class JustAView extends View{

    private Context mContext;
    private float mStartPosition = -90;
    private float mSweepingAngle = 45;

    public JustAView(Context context) {
        super(context);
        init(context);
    }

    public JustAView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        this.mContext = context;
    }

    //Overrides main method
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas); //avoid crashes

        final int x = getWidth(); //gets dimensions
        final int y = getWidth();
        final int radius = x/3; //radius

        //gets coordinates of screen center
        final int centerX = x/2;
        final int centerY = y/2;


        // Let's draw a circle
        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20);
        circlePaint.setColor(Color.DKGRAY);
        circlePaint.setAntiAlias(true);
        canvas.drawCircle( centerX, centerY, radius, circlePaint);

        // Let's draw a square
        final int squareX = centerX;
        final int squareY = centerY;
        final int sideHalfDim = 40;

        Paint squarePaint = new Paint();
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setColor(Color.RED);
        //creates side points of the square
        final int left = squareY-sideHalfDim;
        final int top = squareX-sideHalfDim;
        final int right = squareY+sideHalfDim;
        final int bottom = squareX+sideHalfDim;
        //and draws the square
        canvas.drawRect(left,top,right, bottom,squarePaint);

        // And a arc
        Paint arcPaint = new Paint();
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(Color.GREEN);
        arcPaint.setStrokeWidth(10);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setAntiAlias(true);

        //creates the Oval containing the arc
        final int arcRadius = radius + 40;
        final float arcLeft = centerY - arcRadius;
        final float arcRight = centerY + arcRadius;
        final float arcTop = centerX - arcRadius;
        final float arcBottom = centerX + arcRadius;
        final RectF oval = new RectF(arcLeft, arcTop, arcRight, arcBottom);

        //creates additional values for the angle
        final float startPosition = mStartPosition; // 0 is 3 o' clock
        final float sweepingAngle = mSweepingAngle; // this is how much should the ark be long
        final boolean useCenter = false; // this specify if we want it to draw a pie slice (true) or just the arc border (false)

        canvas.drawArc(oval, startPosition, sweepingAngle , useCenter, arcPaint);

    }

    public float getSweepAngle(){ return mSweepingAngle; }
    public void setSweepAngle(float sweepAngle){ mSweepingAngle = sweepAngle; }

    public void swipeRight(int angle){
        mSweepingAngle += angle;
        invalidate();
    }

    public void swipeLeft(int angle){
        mSweepingAngle -= angle;
        invalidate();
    }






}
