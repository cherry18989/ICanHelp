package com.a007.robot.icanhelp.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.a007.robot.icanhelp.MainActivity;
import com.a007.robot.icanhelp.R;


public class JudgeLoginActivity extends Activity {
    SharedPreferences sp;
    Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_judge_login);
        //判断是否登录

        sp = this.getSharedPreferences("user",MODE_APPEND);
        editor = sp.edit();
        String username = sp.getString("username","defaultName");
        String password = sp.getString("password","******");
        if(username.equals("defaultName")&&password.equals("******")){
            //表示用户未登录，跳转到登录页面
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{
            //用户已登录，跳转到首页
            //带参数跳转
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        }

    }
}
