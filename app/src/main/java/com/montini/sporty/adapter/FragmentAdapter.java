package com.montini.sporty.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<androidx.fragment.app.Fragment> Fragment = new ArrayList<>(); //Fragment List
    private List<String> NamePage = new ArrayList<>(); // Fragment Name List

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment, String title) {
        Fragment.add(fragment);
        NamePage.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NamePage.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
