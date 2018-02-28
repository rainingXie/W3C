package com.example.w3c_school.utils;

import com.example.w3c_school.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.content.Context;
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
	

	
	
	public static void display(String url,ImageView imageView,Context mContext){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext
				).denyCacheImageMultipleSizesInMemory()// 拒绝缓存图片多个尺寸
				// .threadPriority(Thread.NORM_PRIORITY-2)//下载图片线程优先级
				.taskExecutor(ExecutorManager.getInstance().getExecutors())// 设置下载线程的执行器（线程池）；自己写的
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)// 设置内存缓冲大小，系统分配大小的1/8为图片缓冲内存
				.discCacheSize(1024 * 1024 * 50)// 设置磁盘缓存大小
				.discCacheFileCount(100)// 设置磁盘的数量
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 使用Md5加密方式设置磁盘缓存文件的命名生成器
				.diskCache(
						new UnlimitedDiskCache(FileUitlity.getInstance(mContext)
								.makeDir("imagCache")))// 设置磁盘缓存的路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())// 默认图片加载项
				.imageDownloader(
						new BaseImageDownloader(mContext, 60 * 1000, 60 * 1000))// 设置具体的图片加载器
				.build();// 生成配置信息
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
