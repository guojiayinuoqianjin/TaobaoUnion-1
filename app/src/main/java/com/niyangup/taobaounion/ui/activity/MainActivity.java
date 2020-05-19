package com.niyangup.taobaounion.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.base.BaseFragment;
import com.niyangup.taobaounion.ui.fragment.HomeFragment;
import com.niyangup.taobaounion.ui.fragment.RedPacketFragment;
import com.niyangup.taobaounion.ui.fragment.SearchFragment;
import com.niyangup.taobaounion.ui.fragment.SelectedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_navigation_bar)
    protected BottomNavigationView mBottoNavigationView;
    private BaseFragment mHomeFragment;
    private BaseFragment mSelectedFragment;
    private BaseFragment mRedPacketFragment;
    private BaseFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initListener();
        switchFragment(mHomeFragment);
    }

    //初始化fragment
    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mSelectedFragment = new SelectedFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
    }

    //监听底部导航切换fragment
    private void initListener() {
        mBottoNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    Log.d("Tag", "home");
                    switchFragment(mHomeFragment);
                    break;
                case R.id.search:
                    Log.d("Tag", "search");
                    switchFragment(mSearchFragment);
                    break;
                case R.id.selected:
                    Log.d("Tag", "selected");
                    switchFragment(mSelectedFragment);
                    break;
                case R.id.red_packet:
                    Log.d("Tag", "red_packet");
                    switchFragment(mRedPacketFragment);
                    break;
            }

            return true;
        });
    }

    //动态切换fragment
    private void switchFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_page_container, fragment);
        transaction.commit();
    }

}




















