package com.a007.robot.icanhelp.Login;

/**
 * Created by Cherry on 2016/12/23.
 */

public class UserInfo {
    private int id;
    private String idNum;
    private String realName;
    private String jobNum;
    private String phoneNum;
    private String password;
    private String gender;
    private String sortkey;
    private String city;
    private String school;
    private String faceImage;
    private int rankScore;
    private int creditScore;
    private String createTime;

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getRealName() {
        return realName;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSortkey() {
        return sortkey;
    }

    public void setSortkey(String sortkey) {
        this.sortkey = sortkey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public int getRankScore() {
        return rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", idNum='" + idNum + '\'' +
                ", realName='" + realName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", sortkey='" + sortkey + '\'' +
                ", city='" + city + '\'' +
                ", school='" + school + '\'' +
                ", faceImage='" + faceImage + '\'' +
                ", rankScore=" + rankScore +
                ", creditScore=" + creditScore +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
