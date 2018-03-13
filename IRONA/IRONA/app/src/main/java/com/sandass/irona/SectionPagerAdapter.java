package com.sandass.irona;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.Switch;

/**
 * Created by abir on 5/3/18.
 */

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch(position) {
            case 0:
                reqFragment reqFrag= new reqFragment();
                return reqFrag;
            case 1:
                chatFragment chatfrag= new chatFragment();
                return chatfrag;
            case 2:
                frndFragment frndfrag=new frndFragment();
                return frndfrag;
            default:
                return  null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public  CharSequence getPageTitle(int Pos){

        switch(Pos){
            case 0:
                return "Requests";
            case 1:
                return "Chats";
            case 2:
                return "Friends";
            default:
                return null;
        }
    }
}
