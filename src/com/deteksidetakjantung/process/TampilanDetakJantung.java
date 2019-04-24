package com.deteksidetakjantung.process;

import com.deteksidetakjantung.DetakJantung;
import com.deteksidetakjantung.DetakJantung.TYPE;
import com.deteksidetakjantung.R;
import com.deteksidetakjantung.R.drawable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TampilanDetakJantung extends View{
	private static final Matrix matrix = new Matrix();
	private static final Paint  paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static Bitmap netralBitmap = null;
	private static Bitmap redBitmap = null;
	private static int parentWidth = 0;
	private static int parentHeight = 0;
	
	public TampilanDetakJantung(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		netralBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
		redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_red);
	}
	public TampilanDetakJantung (Context context){
		super(context);
		netralBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
		redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_red);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(parentHeight, parentWidth);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas == null) throw new NullPointerException();
        	Bitmap bitmap = null;
        if (DetakJantung.getCurrent() == DetakJantung.TYPE.GREY) bitmap = netralBitmap;
        else bitmap = redBitmap;
        
        int bitmapX = bitmap.getWidth() / 2;
        int bitmapY = bitmap.getHeight() / 2;

        int parentX = parentHeight / 2;
        int parentY = parentWidth / 2;

        int centerX = parentX - bitmapX;
        int centerY = parentY - bitmapY;

        matrix.reset();
        matrix.postTranslate(centerX, centerY);
        canvas.drawBitmap(bitmap, matrix, paint);
	}
}