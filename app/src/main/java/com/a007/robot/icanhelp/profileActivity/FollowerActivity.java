package com.a007.robot.icanhelp.profileActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.a007.robot.icanhelp.Adapters.RelationshipAdapter;
import com.a007.robot.icanhelp.Login.UserInfo;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowerActivity extends Activity {
    private ProgressBar followerPrograssBar;
    private LinearLayout followersView;
    private RelativeLayout reloadView;
    private ListView followerList;
    private RelationshipAdapter adapter;
    private String url = "http://114.55.101.163:8007/FinaRobot/admin.php/Index/getFollowers?";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_follower);
       followerPrograssBar = (ProgressBar) findViewById(R.id.followerProgressBar);
        followerList = (ListView) findViewById(R.id.followerList);
        reloadView = (RelativeLayout) findViewById(R.id.reloadView);
        followersView = (LinearLayout) findViewById(R.id.followersView);
        sp = this.getSharedPreferences("user",MODE_APPEND);
        editor = sp.edit();
        if(NetworkUtils.isNetworkConnected(this)){
            new MyAsyncTask().execute(url);
        }else{
            showReload();
        }

    }
    //显示重新加载页面
    private void showReload(){
        followerPrograssBar.setVisibility(View.GONE);
        reloadView.setVisibility(View.VISIBLE);
    }
    //显示关注列表界面
    private void showAttentionList(){
        followerPrograssBar.setVisibility(View.GONE);
        followersView.setVisibility(View.VISIBLE);
    }

    private class MyAsyncTask extends AsyncTask<String,Object,List<UserInfo>> {


        @Override
        protected List<UserInfo> doInBackground(String... strings) {
            //取出关注列表

            return getUserList();
        }

        private List<UserInfo> getUserList() {
            List<UserInfo> userList = null;
            String urlpath = url +"user_id="+sp.getInt("id",0);
            HttpURLConnection connection = null;
            StringBuffer sb=new StringBuffer();
            try {
                URL url = new URL(urlpath);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){

                    String readLine="";
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                    while((readLine=responseReader.readLine())!=null){
                        sb.append(readLine);
                    }
                    responseReader.close();
                    userList = gson.fromJson(sb.toString(),new TypeToken<List<UserInfo>>(){}.getType());
                    if(userList==null){
                        return null;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userList;
        }

        @Override
        protected void onPostExecute(List<UserInfo> userList) {
            super.onPostExecute(userList);
            if(userList==null){
                showReload();
                //表示网络原因没有取到数据，页面展示为“点击加载”
            }else if(userList.size()==0){
                //没有粉丝
            }else{
                showAttentionList();
                List<UserEntity> entityList = changeUserToEntity(userList);
                adapter = new RelationshipAdapter(FollowerActivity.this,entityList);
                followerList.setAdapter(adapter);
            }

        }

        private List<UserEntity> changeUserToEntity(List<UserInfo> userList) {
            List<UserEntity> entityList = new ArrayList<>();
            for(UserInfo user :userList){
                UserEntity entity = new UserEntity();
                entity.setId(user.getId());
                entity.setRealName(user.getRealName());
                entity.setSchool(user.getSchool());
                entity.setFaceImage(user.getFaceImage());
                Set<String> attentionSet = sp.getStringSet("attention",new HashSet<String>());
                if(attentionSet.contains(String.valueOf(user.getId()))){
                    //互相关注
                    entity.setType(2);
                }else{
                    //没有关注对方
                    entity.setType(3);
                }

                entityList.add(entity);
            }
            return entityList;
        }
    }
}
