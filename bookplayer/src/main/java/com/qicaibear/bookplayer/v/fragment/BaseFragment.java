package com.qicaibear.bookplayer.v.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yyx.commonsdk.log.MyLog;

public class BaseFragment extends Fragment {
    protected boolean created;
    protected boolean isVisibleToUser;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        created = true;
        setUserVisibleHint(isVisibleToUser);
    }
}
