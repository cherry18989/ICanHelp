package com.a007.robot.icanhelp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a007.robot.icanhelp.Login.UserInfo;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.profileActivity.UserEntity;
import com.a007.robot.icanhelp.util.ImgLoaderSave;

import java.util.List;

/**
 * Created by Cherry on 2017/3/13.
 */

public class RelationshipAdapter extends BaseAdapter{
    private  Context context;
    private List<UserEntity> resource;
    private LayoutInflater inflater;
    public RelationshipAdapter(Context context, List<UserEntity> resource) {
        this.context = context;
        this.resource = resource;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return resource.size();
    }

    @Override
    public Object getItem(int i) {
        return resource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        ViewHolder1 viewHolder1 = null;
        UserEntity userInfo = resource.get(i);
        int type = userInfo.getType();
        if(view==null){
            switch(type){
                case 1:
                case 2:
                    view = inflater.inflate(R.layout.activity_attention_item,null);
                    viewHolder = new ViewHolder();
                    viewHolder.picture = (ImageView) view.findViewById(R.id.attentionimg);
                    viewHolder.name = (TextView) view.findViewById(R.id.attentionName);
                    viewHolder.school = (TextView) view.findViewById(R.id.attentionSchool);
                    viewHolder.relationship = (TextView) view.findViewById(R.id.attended);
                    view.setTag(viewHolder);
                    break;
                case 3:
                    view = inflater.inflate(R.layout.activity_follower_item,null);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.picture = (ImageView) view.findViewById(R.id.followerimg);
                    viewHolder1.name = (TextView) view.findViewById(R.id.followerName);
                    viewHolder1.school = (TextView) view.findViewById(R.id.followerSchool);
                    viewHolder1.addFriend = (Button) view.findViewById(R.id.addFriend);
                    view.setTag(viewHolder1);
                    break;
            }

        }else{
            switch(type){
                case 1:
                case 2:
                    viewHolder = (ViewHolder) view.getTag();
                    break;
                case 3:
                    viewHolder1 = (ViewHolder1) view.getTag();
                    break;
            }

        }
        switch(type){
            case 1:
                new ImgLoaderSave().showImgByAsync(viewHolder.picture,userInfo.getFaceImage());
                viewHolder.name.setText(userInfo.getRealName());
                viewHolder.school.setText(userInfo.getSchool());
                viewHolder.relationship.setText("已关注");
                break;
            case 2:
                new ImgLoaderSave().showImgByAsync(viewHolder.picture,userInfo.getFaceImage());
                viewHolder.name.setText(userInfo.getRealName());
                viewHolder.school.setText(userInfo.getSchool());
                viewHolder.relationship.setText("互相关注");
                break;
            case 3:
                new ImgLoaderSave().showImgByAsync(viewHolder1.picture,userInfo.getFaceImage());
                viewHolder1.name.setText(userInfo.getRealName());
                viewHolder1.school.setText(userInfo.getSchool());
                break;
        }
        //viewHolder.picture.setBackgroundResource(R.drawable.head_nologin);
        return view;
    }
    static class ViewHolder{
        public ImageView picture;
        public TextView name;
        public TextView school;
        public TextView relationship;
    }
    static class ViewHolder1{
        public ImageView picture;
        public TextView name;
        public TextView school;
        public Button addFriend;
    }
}
