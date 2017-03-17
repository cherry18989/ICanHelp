package com.a007.robot.icanhelp.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.a007.robot.icanhelp.MainActivity;
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
import java.util.HashSet;
import java.util.Set;


public class LoginActivity extends Activity  implements View.OnClickListener{
    EditText username;
    EditText password;
    Button login;
    TextView register;
    TextView fogetPassword;
    String usernameUser;
    String passwordUser;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String LoginUrl = "http://114.55.101.163:8007/FinaRobot/admin.php/Index/login?";
    Gson gson = new Gson();
    JsonBean jsonBean = null;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //Toast.makeText(LoginActivity.this,String.valueOf(jsonBean),Toast.LENGTH_LONG).show();
                case 1:
                    Toast.makeText(LoginActivity.this,"登录失败，请检查网络",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //带参数
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("faceImageName",jsonBean.getUser().getFaceImage());
                    intent.putExtra("realName",jsonBean.getUser().getRealName());
                    startActivity(intent);
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        fogetPassword = (TextView) findViewById(R.id.fogetPassword);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        fogetPassword.setOnClickListener(this);

        sp = this.getSharedPreferences("user",MODE_APPEND);
        editor = sp.edit();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login:
                if(!NetworkUtils.isNetworkConnected(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
                    break;
                }
                usernameUser = username.getText().toString();
                passwordUser = password.getText().toString();
                if(usernameUser.equals("")||passwordUser.equals("")){
                    Toast.makeText(this,"用户名密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                //做登录校验,验证通过
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str = login(usernameUser,passwordUser);
                        if(str.length()==0){
                            myHandler.sendEmptyMessage(1);
                        }else{
                            Message msg = new Message();
                            jsonBean = gson.fromJson(str,new TypeToken<JsonBean>(){}.getType());

                            // myHandler.sendEmptyMessage(0);
                            if(jsonBean==null){
                                myHandler.sendEmptyMessage(1);
                            }else if(jsonBean.getStatus()==1){
                                editor.putInt("id",jsonBean.getUser().getId());
                                editor.putString("idNum",jsonBean.getUser().getIdNum());
                                editor.putString("realName",jsonBean.getUser().getRealName());
                                editor.putString("jobNum",jsonBean.getUser().getJobNum());
                                editor.putString("phoneNum",jsonBean.getUser().getPhoneNum());
                                editor.putString("password",jsonBean.getUser().getPassword());
                                editor.putString("gender",String.valueOf(jsonBean.getUser().getGender()));
                                editor.putString("sortkey",jsonBean.getUser().getSortkey());
                                editor.putString("city",jsonBean.getUser().getCity());
                                editor.putString("school",jsonBean.getUser().getSchool());
                                editor.putString("faceImage",jsonBean.getUser().getFaceImage());
                                editor.putInt("rankScore",jsonBean.getUser().getRankScore());
                                editor.putInt("creditScore",jsonBean.getUser().getCreditScore());
                                editor.putString("createTime",jsonBean.getUser().getCreateTime());
                                if(jsonBean.getAttention()!=null){
                                    Set<String> setAtten = new HashSet<String>();
                                    for(JsonBean.Attention attention:jsonBean.getAttention()){
                                        setAtten.add(String.valueOf(attention.getAttention_id()));
                                    }
                                    editor.putStringSet("attention",setAtten);
                                }
                                if(jsonBean.getFollower()!=null){
                                    Set<String> setFollow = new HashSet<String>();
                                    for(JsonBean.Follower follower:jsonBean.getFollower()){
                                        setFollow.add(String.valueOf(follower.getUser_id()));
                                    }
                                    editor.putStringSet("follower",setFollow);
                                }
                                editor.putInt("signinStatus",jsonBean.getSigninStatus());

                                editor.commit();
                                msg.what= 2;
                                msg.obj = jsonBean.getUser();
                                myHandler.sendMessage(msg);

                            }else{
                                //提示用户验证失败
                                myHandler.sendEmptyMessage(3);
                            }
                        }

                    }
                }).start();

                break;
            case R.id.register:
                //跳转到注册页面
               /* Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);*/

                break;
        }
    }

    public String login(String username,String password){
        String urlpath = LoginUrl +"username="+username+"&password="+password;
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

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            myHandler.sendEmptyMessage(1);
            e.printStackTrace();
        }
        return sb.toString();

    }
    @Override
    public void onBackPressed() {
    }
}
