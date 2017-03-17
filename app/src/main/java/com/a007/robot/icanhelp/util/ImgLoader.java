package com.a007.robot.icanhelp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cherry on 2016/11/9.
 */

public class ImgLoader {

    private  ImageView mImageView;
    private String mUrl;
    private LruCache<String,Bitmap> mCache;
    private Set<NewsAsyncTask> mTask;

    public  ImgLoader(){
        mTask = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/4;
        mCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次加入缓存的时候调用，获得bitMap的大小
                return value.getByteCount();
            }
        };
    }

    public Bitmap getBitMapFromCache(String url){

        return mCache.get(url);
    }
    public void addBitMapToCache(String url,Bitmap bitMap){
        if(getBitMapFromCache(url)==null){
            mCache.put(url,bitMap);
        }

    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }


        }
    };
   /* public void showImgByThread(ImageView mImageView, final String urlString){
        this.mImageView = mImageView;
        this.mUrl = urlString;
        new Thread(){
            @Override
            public void run() {
                Bitmap bitMap = getBitMapFromCache(mUrl);
                Message msg = new Message();
                msg.obj = bitMap;
                mHandler.sendMessage(msg);
            }
        }.start();

    }*/

    private Bitmap getBitMapByUrl(String urlString){
        Bitmap bitMap = null;
        InputStream is = null;
        try {
            is = new URL(urlString).openStream();
            bitMap = BitmapFactory.decodeStream(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return bitMap;
        }

    }

    public void showImgByAsync(ImageView mImageView, final String urlString){
        this.mImageView = mImageView;
        this.mUrl = urlString;
        Bitmap bitMap = getBitMapFromCache(mUrl);
        if(bitMap==null){
            new NewsAsyncTask(mUrl).execute(mUrl);
        }else{
            mImageView.setImageBitmap(bitMap);
        }


    }

    /*public void loadImages(int start, int end){
        for(int i=start;i<end;i++){
            //加载区间内的图片
            String url = NewsAdapter.URLS[i];

            Bitmap bitmap = getBitMapFromCache(url);
            if(bitmap==null){//缓存中没有该图片
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            }else{//缓冲中有该图片
                ImageView imgView = (ImageView) newsListView.findViewWithTag(url);
                imgView.setImageBitmap(bitmap);
            }

        }
    }*/

    public void cacelAllTask(){
        for (NewsAsyncTask task:mTask
             ) {
            if(task!=null){
                task.cancel(true);
            }
        }
    }

    private class NewsAsyncTask extends AsyncTask<String,Object,Bitmap>{
        private String mUrl;

        public NewsAsyncTask(String mUrl){
            this.mUrl = mUrl;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            if(isCancelled()){
                return null;
            }
            Bitmap bitMap = getBitMapByUrl(strings[0]);
            if(bitMap!=null){
                //将网络获取的图片加入缓存中
                addBitMapToCache(strings[0],bitMap);
            }
            return bitMap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView!=null&&bitmap!=null){
                mImageView.setImageBitmap(bitmap);
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
