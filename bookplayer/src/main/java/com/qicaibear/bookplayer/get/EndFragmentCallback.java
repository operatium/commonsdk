package com.qicaibear.bookplayer.get;

import android.support.v4.app.Fragment;

/**
 *
 * Created by admin on 2018/9/13.
 */

public interface EndFragmentCallback {
    Fragment createEndPageFragment(int position);//添加最后一页的封底界面
}
