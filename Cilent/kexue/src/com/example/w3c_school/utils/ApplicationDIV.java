package com.example.w3c_school.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Application;
import android.view.WindowManager;

/**
 * requestQueue全局变量，整个工程可用，其他地方不用创建
 *
 */

/**
 * 在AndroidManiFest.xml的<application>的 name 中注册该Application
 *
 */
public class ApplicationDIV extends Application {

	private static ApplicationDIV application;

	private RequestQueue requestQueue;
	private User user;

	private ApkInfo apkInfo;

	private WindowManager.LayoutParams windowParams;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;// 将ApplicationDIV赋值给application静态变量
		requestQueue = Volley.newRequestQueue(this);// 创建请求队列
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).denyCacheImageMultipleSizesInMemory()// 拒绝缓存图片多个尺寸
				// .threadPriority(Thread.NORM_PRIORITY-2)//下载图片线程优先级
				.taskExecutor(ExecutorManager.getInstance().getExecutors())// 设置下载线程的执行器（线程池）；自己写的
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)// 设置内存缓冲大小，系统分配大小的1/8为图片缓冲内存
				.discCacheSize(1024 * 1024 * 50)// 设置磁盘缓存大小
				.discCacheFileCount(100)// 设置磁盘的数量
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 使用Md5加密方式设置磁盘缓存文件的命名生成器
				.diskCache(
						new UnlimitedDiskCache(FileUitlity.getInstance(this)
								.makeDir("imagCache")))// 设置磁盘缓存的路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())// 默认图片加载项
				.imageDownloader(
						new BaseImageDownloader(this, 60 * 1000, 60 * 1000))// 设置具体的图片加载器
				.build();// 生成配置信息

		ImageLoader.getInstance().init(config);
		windowParams = new WindowManager.LayoutParams();
	}

	/**
	 * 返回 静态变量
	 */
	public static ApplicationDIV getInstance() {
		return application;
	}

	public RequestQueue getRequestQueue() {
		return this.requestQueue;

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WindowManager.LayoutParams getWindowParams() {
		return this.windowParams;
	}

	public ApkInfo getApkInfo() {
		return apkInfo;
	}

	public void setApkInfo(ApkInfo apkInfo) {
		this.apkInfo = apkInfo;
	}

}
