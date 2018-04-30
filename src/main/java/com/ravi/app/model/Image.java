package com.ravi.app.model;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "images")
public class Image implements Serializable{

    @Id
    @Column(unique = true, nullable = false)
    private String title;

    private String uploadedby;
    private String modifiedby;
    private long nooflikes;
    private float ratingscore;
    private long noofratings;
    private String imgname;
    private Timestamp createdtime;
    private Timestamp modifiedtime;
    private Blob imgdata;
    private long size;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    protected Image(){

    }

    public long getNooflikes() {
        return nooflikes;
    }

    public void setNooflikes(long nooflikes) {
        this.nooflikes = nooflikes;
    }

    public float getRatingscore() {
        return this.ratingscore;
    }

    public float getScoreToDisplay(){
        float score = 0;
        if(this.noofratings == 0)
            score = this.ratingscore;
        else{
            score = (this.ratingscore)/noofratings;
        }
        return score;
    }

    public void setRatingscore(float ratingscore) {
        this.ratingscore = ratingscore;
    }

    public long getNoofratings() {
        return noofratings;
    }

    public void setNoofratings(long noofratings) {
        this.noofratings = noofratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadedby() {
        return uploadedby;
    }

    public void setUploadedby(String uploadedby) {
        this.uploadedby = uploadedby;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getCreatedtime() {

        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(createdtime);
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }

    public String getModifiedtime() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(modifiedtime);
    }

    public void setModifiedtime(Timestamp modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getImgdata() {

        String data = "ravi";
        byte[] ba = null;
        try {
            System.out.println("getBase64EncodedImgData method called---brgin " + this.imgdata.length());
            ba = this.imgdata.getBytes(1,(int)this.imgdata.length());
            data = Base64.encodeBase64String(ba);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setImgdata(Blob imgdata) {
        this.imgdata = imgdata;
    }
}
