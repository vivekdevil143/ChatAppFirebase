package com.rai.vivek.chatappfirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.ViewHolder {

    View mView;

    public UsersAdapter(@NonNull View itemView) {
        super(itemView);

        mView = itemView;


    }


    public void setName(String name){
        TextView txt_username=(TextView) mView.findViewById(R.id.txt_username);
        txt_username.setText(name);

    }
   public void setthumbUser_Image(String thumb_image,Context mctx){
       CircleImageView userimage=(CircleImageView) mView.findViewById(R.id.User_image);
       Picasso.with(mctx).load(thumb_image).placeholder(R.drawable.profile_pic).into(userimage);

    }
    public void setstatus(String status){
        TextView txt_userstatus=(TextView)mView.findViewById(R.id.txt_userstatus);
        txt_userstatus.setText(status);

    }


}
