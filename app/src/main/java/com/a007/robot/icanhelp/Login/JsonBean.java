package com.a007.robot.icanhelp.Login;

import java.util.List;
import java.util.Set;

/**
 * Created by Cherry on 2016/12/23.
 */

public class JsonBean {
     int status;
     String msg;
     UserInfo user;
    Set<Attention> attention;
    Set<Follower> follower;
    int signinStatus;

    public static class Attention{
        public int attention_id;

        public int getAttention_id() {
            return attention_id;
        }

        public void setAttention_id(int attention_id) {
            this.attention_id = attention_id;
        }
    }
    public static class Follower{
        public int user_id;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Set<Attention> getAttention() {
        return attention;
    }

    public void setAttention(Set<Attention> attention) {
        this.attention = attention;
    }

    public Set<Follower> getFollower() {
        return follower;
    }

    public void setFollower(Set<Follower> follower) {
        this.follower = follower;
    }

    public int getSigninStatus() {
        return signinStatus;
    }

    public void setSigninStatus(int signinStatus) {
        this.signinStatus = signinStatus;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", user=" + user +
                ", attention=" + attention +
                ", follower=" + follower +
                ", signinStatus=" + signinStatus +
                '}';
    }
}
