package com.a007.robot.icanhelp.blogActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.a007.robot.icanhelp.Login.LoginActivity;
import com.a007.robot.icanhelp.MainActivity;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.util.NetworkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WriteBlogActivity extends Activity implements View.OnClickListener{
    private Button Back;
    private Button sendBlog;
    private EditText blogContent;
    private Button addPicture;
    private RelativeLayout blogTypeView;
    private int id;
    private int type=0;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //Toast.makeText(LoginActivity.this,String.valueOf(jsonBean),Toast.LENGTH_LONG).show();
                case 1:
                    Toast.makeText(WriteBlogActivity.this,"发送失败，请检查网络",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    new AlertDialog.Builder(WriteBlogActivity.this).setMessage("发送失败").create().show();
                    finish();
                    break;
                case 3:
                    new AlertDialog.Builder(WriteBlogActivity.this).setMessage("发送成功").create().show();
                    finish();
                    break;
            }
        }
    };
    String sendBlogUrl = "http://114.55.101.163:8007/FinaRobot/admin.php/Index/sendBlog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write_blog);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        Back = (Button) findViewById(R.id.Back1);
        sendBlog = (Button) findViewById(R.id.sendBlog);
        blogContent = (EditText) findViewById(R.id.blogContent);
        addPicture = (Button) findViewById(R.id.addPicture);
        blogTypeView = (RelativeLayout) findViewById(R.id.blogTypeView);
        Back.setOnClickListener(this);
        sendBlog.setOnClickListener(this);
        addPicture.setOnClickListener(this);
        blogTypeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Back1:
                finish();
                break;
            case R.id.sendBlog:
                if(!NetworkUtils.isNetworkConnected(WriteBlogActivity.this)){
                    new AlertDialog.Builder(WriteBlogActivity.this).setMessage("请检查网络");
                }else{
                    //发送动态
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendBlog();
                        }
                    }).start();
                }

                //发送动态
                break;
            case R.id.addPicture:
                //添加照片
                break;
            case R.id.blogTypeView:
                //选择动态类型
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteBlogActivity.this);
                builder.setTitle("动态类型");
                builder.setSingleChoiceItems(new String[]{"免费动态", "收费动态"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        type=i;
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
    }

    private void sendBlog() {
        String content = blogContent.getText().toString();
        HttpURLConnection connection = null;
        URL url = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(sendBlogUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            printWriter.write("user_id="+id+"&message_type="+type+"&message_text="+content+"&message_video= ");//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            connection.connect();
            if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){

                String readLine="";
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine);
                }
                responseReader.close();
                if(sb.equals("1")){
                    myHandler.sendEmptyMessage(3);

                }else{
                    myHandler.sendEmptyMessage(2);
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            myHandler.sendEmptyMessage(1);
            e.printStackTrace();

        }


    }
}
