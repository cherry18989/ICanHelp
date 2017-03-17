package com.a007.robot.icanhelp.profileActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.a007.robot.icanhelp.Login.LoginActivity;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.view.LogoutDialog;

public class SettingActivity extends Activity implements View.OnClickListener{
    RelativeLayout logout;
    LogoutDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        sp = getSharedPreferences("user",MODE_APPEND);
        editor = sp.edit();
        logout = (RelativeLayout) findViewById(R.id.logoutView);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logoutView:
                //弹出dialog
                dialog = new LogoutDialog(this);
                dialog.setTitle("退出提醒");
                dialog.setMessage("确定退出登录？");
                dialog.setCancelable(false);
                dialog.setYesOnclickListener("确定", new LogoutDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialog.dismiss();
                        //清除登录信息
                        editor.clear().commit();
                        //跳转到登录界面
                        Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setNoOnclickListener("取消", new LogoutDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                //清空登录文件
                //跳转到登录界面
                break;
        }
    }
}
