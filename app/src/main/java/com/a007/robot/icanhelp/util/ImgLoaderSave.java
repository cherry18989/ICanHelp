package com.a007.robot.icanhelp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cherry on 2016/11/9.
 */

public class ImgLoaderSave {

    private  ImageView mImageView;
    private Set<NewsAsyncTask> mTask;
    private String pathSdcard = Environment.getExternalStorageDirectory()+"/com.a007.robot.icanhelp/pictures/";
    private String pathServer = "http://114.55.101.163:8007/FinaRobot/user_image/";

    public ImgLoaderSave(){
        mTask = new HashSet<>();
        File dirpath = new File(pathSdcard);
        if(!dirpath.exists()){
            dirpath.mkdirs();
        }
    }

    public void addBitMapToSdcard(Bitmap bitMap){
        File file = new File(pathSdcard);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitMap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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

    public void showImgByAsync(ImageView mImageView, final String picName){
        this.mImageView = mImageView;
        this.pathSdcard = pathSdcard+picName;
        this.pathServer = pathServer+picName;

        Bitmap bitMap = BitmapFactory.decodeFile(pathSdcard);
        if(bitMap==null){
            new NewsAsyncTask(pathServer).execute(pathServer);
        }else{
            mImageView.setImageBitmap(bitMap);
        }


    }

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
                //将网络获取的图片存入sd卡中
                addBitMapToSdcard(bitMap);
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
