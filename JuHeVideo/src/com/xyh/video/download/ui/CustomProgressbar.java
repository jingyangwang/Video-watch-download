package com.xyh.video.download.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 内存使用情况横向柱状图
 * @author 庄宏岩
 *
 */
public class CustomProgressbar extends View {
	private int progress;
	private Paint bgPaint;
	private int drawProgress = 1;
	private Paint bluePaint;
	private int width;
	private int height;

	public CustomProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CustomProgressbar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomProgressbar(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bgPaint == null) {
			bgPaint = new Paint();
			bgPaint.setAntiAlias(true);
			bgPaint.setColor(0xffe4e4e4);
		}
		if (bluePaint == null) {
			bluePaint = new Paint();
			bluePaint.setAntiAlias(true);
			bluePaint.setColor(0xff6a71e5);
		}
		if (width == 0) {
			width = getWidth();
			height = getHeight();
		}
		// 定义一个矩形
		RectF oval = new RectF(0, 0, width, height);
		if (bgPaint != null) {
			canvas.drawRect(oval, bgPaint);
			if(progress>0){
				if (drawProgress < progress) {
					float rate = (float) drawProgress / 100;
					canvas.drawRect(new RectF(0, 0, width * rate, height), bluePaint);
					drawProgress += 2;
					invalidate();
				} else {
					float rate = (float) progress / 100;
					canvas.drawRect(new RectF(0, 0, width * rate, height), bluePaint);
				}
			}
		}

	}

	public void setProgress(int progress) {
		this.progress = progress;
		drawProgress = 1;
		invalidate();
	}

}
