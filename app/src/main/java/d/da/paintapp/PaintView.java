package d.da.paintapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    public int brushSize = 10;
    public static final int DEFAULT_COLOR = Color.GREEN;
    public static final int DEFAULT_BACKGROUND = Color.WHITE;
    private static final float tolerance = 5;
    private float X, Y;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<DrawPath> paths = new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BACKGROUND;
    private int drawWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        drawWidth = brushSize;
    }

    public void setBrushSize(int size)
    {
        brushSize = size;
    }

    public int getCurrentColor()
    {
        return currentColor;
    }

    public void setColor(int color)
    {
        currentColor = color;
    }

    public int getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackground(int color)
    {
        backgroundColor = color;
        invalidate();
    }

    public void clear(){
        backgroundColor = DEFAULT_BACKGROUND;
        paths.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (DrawPath dp : paths)
        {
            mPaint.setColor(dp.color);
            mPaint.setStrokeWidth(dp.lineWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(dp.path, mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y){
        mPath = new Path();
        DrawPath dp = new DrawPath(currentColor, brushSize, mPath);
        paths.add(dp);

        mPath.reset();
        mPath.moveTo(x, y);
        X = x;
        Y = y;
    }

    private void touchMove(float x, float y)
    {
        float dx = Math.abs(x - X);
        float dy = Math.abs(y - Y);

        if(dx >= tolerance || dy >= tolerance){
            mPath.quadTo(X, Y, (x+X)/2, (y+Y)/2);
            X = x;
            Y = y;
        }
    }

    private void touchUp(){
        mPath.lineTo(X, Y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }
}
