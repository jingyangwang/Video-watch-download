package com.xyh.video.base;


import com.videoplayer.bd.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * Created by Tommy on 15/3/11.
 */

public class BaseFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
       // MobclickAgent.onPageStart(mContext.getClass().getSimpleName());
     //   MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
       // MobclickAgent.onPageEnd(mContext.getClass().getSimpleName());
       // MobclickAgent.onPause(mContext);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_in, 0);
    }
}
