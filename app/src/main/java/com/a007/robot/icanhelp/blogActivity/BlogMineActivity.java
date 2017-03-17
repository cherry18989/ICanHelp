package com.a007.robot.icanhelp.blogActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.a007.robot.icanhelp.R;

public class BlogMineActivity extends Activity implements View.OnClickListener{
    private Button back;
    private Button writeBlog;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_blog_mine);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        back = (Button) findViewById(R.id.Back);
        writeBlog = (Button) findViewById(R.id.writeBlog);
        writeBlog.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Back:
                finish();
                break;
            case R.id.writeBlog:
                //跳转到写动态界面
                Intent intent = new Intent(BlogMineActivity.this,WriteBlogActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
    }
}
