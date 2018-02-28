package com.example.w3c_school.view;

import com.example.w3c_school.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {

	private Bitmap background;

	private Bitmap newBackGround;

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.bookcase_bg);
	}

	protected void dispatchDraw(Canvas canvas) {

		// 取得加载的背景图片高，宽
		int width = background.getWidth();
		int height = background.getHeight();

		// 取得手机屏幕的高，宽，这里要注意，不要在构造方法中写，因为那样取到的值是0
		int scWidth = this.getWidth();
		int scHeight = this.getHeight();

		// 计算缩放率，新尺寸除原始尺寸,我这里因为高不用变，所以就是原大小的比例1
		// 这里一定要注意，这里的值是比例，不是值。
		float scaleWidth = ((float) scWidth) / width;
		float scaleHeight = 1;

		// Log.v("info", "width:" + width + "height:" + height + "scWidth:" +
		// scWidth + "scaleWidth:" + scaleWidth + "scaleHeight:" + scaleHeight);

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 生成新的图片
		newBackGround = Bitmap.createBitmap(background, 0, 0, width, height,
				matrix, true);

		int count = getChildCount();
		int top = 185;
		int backgroundWidth = newBackGround.getWidth();
		int backgroundHeight = newBackGround.getHeight();

		for (int y = top; y < scHeight; y += 223) {
			// for (int x = 0; x<scWidth; x += backgroundWidth){
			canvas.drawBitmap(newBackGround, 0, y, null);
			// }
		}

		super.dispatchDraw(canvas);
	}

	// 禁止滚动 条滚动
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
