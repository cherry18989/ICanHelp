package com.a007.robot.icanhelp.profileActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.a007.robot.icanhelp.R;

public class SignInStatusActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    public static final String[] signinSource = new String[]{"清水河在岗","沙河在岗","会议中","出差","休假"};
    private ArrayAdapter<String> adapter;
    private ListView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in_status);
        status = (ListView) findViewById(R.id.statusList);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,signinSource);
        status.setAdapter(adapter);
        status.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("sign_in_status",i+1);
        setResult(1,intent);
        finish();
    }
}
