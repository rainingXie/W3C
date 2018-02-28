package com.example.w3c_school.utils;

import com.example.w3c_school.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderUitil {
	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.cc_default_news_img)//下载过程中需要的图片
	.showImageOnFail(R.drawable.cc_default_news_img_fail)//下载失败需要的图片
	.showImageForEmptyUri(R.drawable.w3welcome)//uri为空时显示的图片
	.cacheInMemory(true)//是否进行缓存
	.cacheOnDisk(true)//是否磁盘缓存
	.bitmapConfig(Bitmap.Config.RGB_565)//显示方式
	.resetViewBeforeLoading(true)
	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//图片不匹配时，大小显示
	.displayer(new FadeInBitmapDisplayer(200))//淡入淡出效果；RoundedBitmapDisplayer：圆角
	.build();
	
	public static void display(String url,ImageView imageView){
		
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
