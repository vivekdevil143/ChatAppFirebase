package com.rai.vivek.chatappfirebase;

public class Users_modelclass {

    public String name;
    public String image;
    public String status;
    public String thumb_image;

  public Users_modelclass(){
    }

    public Users_modelclass(String name, String image, String status,String thumb_image) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image=thumb_image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getThumb_image() {
        return thumb_image;
    }


}
