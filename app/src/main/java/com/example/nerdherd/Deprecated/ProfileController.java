package com.example.nerdherd.Deprecated;

import com.example.nerdherd.Deprecated.GlobalVariable;
import com.example.nerdherd.Deprecated.Profile;
import com.example.nerdherd.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller class for Profile model class
 * The controller class for Profile to get notified of the user's behaviour and update the model class as needed
 * @author Ogooluwa S. osamuel
 * @author Zhipeng Z. zhipeng4
 */


public class ProfileController {
    private String name;
    private String password;
    private String email;
    private String id;
    private Integer avatar;
    private Profile profile;
    private Integer[] imageList= {R.drawable.zelda, R.drawable.link, R.drawable.mipha, R.drawable.urbosa, R.drawable.riju, R.drawable.revali, R.drawable.daruk, R.drawable.impa, R.drawable.purah, R.drawable.purah_6_years_old, R.drawable.yunobo, R.drawable.king_rhoam, R.drawable.sidon};
    private ArrayList<Integer> imageArray = new ArrayList(Arrays.asList(imageList));

    /**
     * Controls the profile with all the necessary parameters
     * Getter/setter of the app
     * @param name of user
     * @param password of user
     * @param email of user
     * @param id of user
     * @param avatar of user
     */

    public ProfileController(String name, String password, String email, String id, Integer avatar) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
        this.avatar = avatar;
    }


    public ProfileController() {
        this.name = "name";
        this.password = "password";
        this.email = "email";
        this.id = "id";
        this.avatar = 0;
    }

    public String getId(){return GlobalVariable.profile.getId();}

    public ArrayList<Integer> getImageArray() {
        return imageArray;
    }

    public Integer getAvatar(){return GlobalVariable.profile.getAvatar();}

    public String getEmail(){return GlobalVariable.profile.getEmail();}

    public ArrayList<Profile> profileArrayList(){return GlobalVariable.profileArrayList;};

    public String getName(){return GlobalVariable.profile.getName();}

    public String getPassword(){return GlobalVariable.profile.getPassword();}

    public void creator(){
        profile = new Profile(name, password, email, id, avatar);
    }

    public Profile getProfile() {
        return profile;
    }
}
