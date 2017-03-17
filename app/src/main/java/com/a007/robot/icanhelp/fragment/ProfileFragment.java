package com.a007.robot.icanhelp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a007.robot.icanhelp.Login.JsonBean;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.blogActivity.BlogMineActivity;
import com.a007.robot.icanhelp.profileActivity.AttentionActivity;
import com.a007.robot.icanhelp.profileActivity.FollowerActivity;
import com.a007.robot.icanhelp.profileActivity.PersonalInfoActivity;
import com.a007.robot.icanhelp.profileActivity.SettingActivity;
import com.a007.robot.icanhelp.profileActivity.SignInStatusActivity;
import com.a007.robot.icanhelp.util.ImgLoaderSave;
import com.a007.robot.icanhelp.util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cherry on 2017/3/1.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout MyBlogView;
    private RelativeLayout SignInView;
    private RelativeLayout profileView;
    private RelativeLayout settingView;
    private LinearLayout attentionView;
    private LinearLayout followerView;
    private TextView signIn;
    private TextView profileName;
    private TextView profileSchool;
    private TextView followerNum;
    private TextView attentionNum;
    private TextView signin;
    private ImageView profileimg;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Toast toast;
    private String signinPath = "http://114.55.101.163:8007/FinaRobot/admin.php/Index/signIn?";
    private String imgPath = "http://114.55.101.163:8007/FinaRobot/user_image/";
    private Gson gson = new Gson();
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    signIn.setText(SignInStatusActivity.signinSource[Integer.parseInt(String.valueOf(msg.obj))-1]);
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initView(view);
        sp = this.getActivity().getSharedPreferences("user", Context.MODE_APPEND);
        editor = sp.edit();
        initData();
        return view;
    }

    private void initView(View view){
        profileimg = (ImageView) view.findViewById(R.id.profileimg);
        SignInView = (RelativeLayout) view.findViewById(R.id.signinView);
        profileView = (RelativeLayout) view.findViewById(R.id.profileView);
        attentionView = (LinearLayout) view.findViewById(R.id.attentionView);
        followerView = (LinearLayout) view.findViewById(R.id.followerView);
        MyBlogView = (RelativeLayout) view.findViewById(R.id.MyBlogView);
        settingView = (RelativeLayout) view.findViewById(R.id.settingView);
        signIn = (TextView) view.findViewById(R.id.signin);
        profileName = (TextView) view.findViewById(R.id.profileName);
        profileSchool = (TextView) view.findViewById(R.id.profileSchool);
        followerNum = (TextView) view.findViewById(R.id.followerNum);
        attentionNum = (TextView) view.findViewById(R.id.attentionNum);
        signin = (TextView) view.findViewById(R.id.signin);
        MyBlogView.setOnClickListener(this);
        SignInView.setOnClickListener(this);
        profileView.setOnClickListener(this);
        attentionView.setOnClickListener(this);
        followerView.setOnClickListener(this);
        settingView.setOnClickListener(this);
        toast = Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT);
    }
    private  void initData(){
        String faceImage = sp.getString("faceImage","");
        if(faceImage!=""){
            new ImgLoaderSave().showImgByAsync(profileimg,faceImage);
        }
        Map<Integer,String> signInStatusMap = new HashMap<>();
        signInStatusMap.put(0,"未签到");
        signInStatusMap.put(1,"清水河在岗");
        signInStatusMap.put(2,"沙河在岗");
        signInStatusMap.put(3,"会议中");
        signInStatusMap.put(4,"出差");
        signInStatusMap.put(5,"休假");
        profileName.setText(sp.getString("realName",""));
        profileSchool.setText(sp.getString("school",""));
        Set<String> attentionSet = sp.getStringSet("attention",null);
        Set<String> followerSet = sp.getStringSet("follower",null);
       if(attentionSet==null||attentionSet.size()==0){
           attentionNum.setText("0");
       }else{
           attentionNum.setText(String.valueOf(attentionSet.size()));
       }

        if(followerSet==null||followerSet.size()==0){
            followerNum.setText("0");
        }else{
            followerNum.setText(String.valueOf(followerSet.size()));
        }
        signin.setText(signInStatusMap.get(sp.getInt("signinStatus",0)));

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signinView:
                if(!NetworkUtils.isNetworkConnected(getActivity())){
                    toast.show();
                    break;
                }
                Intent intent = new Intent(getActivity(), SignInStatusActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.profileView:
                Intent intent2= new Intent(getActivity(), PersonalInfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.settingView:
                Intent intent3= new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.attentionView:
                Intent intent4 = new Intent(getActivity(), AttentionActivity.class);
                startActivity(intent4);
                //我的关注
                break;
            case R.id.followerView:
                Intent intent5 = new Intent(getActivity(), FollowerActivity.class);
                startActivity(intent5);
                //我的粉丝
                break;
            case R.id.MyBlogView:
                Intent intent6 = new Intent(getActivity(), BlogMineActivity.class);
                intent6.putExtra("id",sp.getInt("id",0));
                startActivity(intent6);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==1){
                    final int signinstatus = data.getIntExtra("sign_in_status",1);

                    //上传到数据库中
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean isModifySignIn = uploadSignIn(sp.getInt("id",0),signinstatus);
                            if(isModifySignIn){
                                Message msg = new Message();
                                msg.what=1;
                                msg.obj = signinstatus;
                                myHandler.sendMessage(msg);

                                //写入sp中
                                editor.putInt("signinStatus",signinstatus);
                                editor.commit();
                            }
                        }
                    }).start();

                }
                break;
        }
    }
    public Boolean uploadSignIn(int user_id,int user_status){
        signinPath +="user_id="+user_id+"&user_status="+user_status;
        HttpURLConnection connection = null;
        JsonBean jsonBean = null;
        StringBuffer sb=new StringBuffer();
        try {
            URL url = new URL(signinPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){

                String readLine="";
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine);
                }
                responseReader.close();
                if(sb.length()!=0){
                    jsonBean = gson.fromJson(sb.toString(),new TypeToken<JsonBean>(){}.getType());
                    if(jsonBean.getStatus()==1) return true;
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
