package com.example.w3c_school.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class ImageCacheDIV implements ImageCache{

	
	private int maxSize = 1024*1024*10;
	private LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(maxSize){

		@Override
		protected int sizeOf(String key, Bitmap value) {
			
			return value.getRowBytes()*value.getHeight();
		}
		
	};
	
	@Override
	public Bitmap getBitmap(String arg0) {
		
		return mCache.get(arg0);
	}

	@Override
	public void putBitmap(String arg0, Bitmap arg1) {
		mCache.put(arg0, arg1);
	}

}
