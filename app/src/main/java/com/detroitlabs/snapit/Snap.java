package com.detroitlabs.snapit;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by andrewgiang on 1/28/15.
 */
@ParseClassName("Snap")
public class Snap extends ParseObject{

    public static final String PHOTO_KEY = "photo";
    public static final String RECEIVER_KEY = "receiver";
    public static final String SENDER_KEY = "sender";

    public ParseUser getSender(){
        return getParseUser(SENDER_KEY);
    }

    public ParseUser getReceiver(){
        return getParseUser(RECEIVER_KEY);
    }

    public ParseFile getPhoto(){
        return getParseFile(PHOTO_KEY);
    }

    public void setSender(ParseUser sender){
        put(SENDER_KEY, sender);
    }

    public void setReceiver(ParseUser receiver){
        put(RECEIVER_KEY, receiver);
    }

    public void setPhoto(ParseFile photo){
        put(PHOTO_KEY, photo);
    }

}
