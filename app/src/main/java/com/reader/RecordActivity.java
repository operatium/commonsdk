package com.reader;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qicaibear.bookplayer.v.view.turnpage.PageTurnView;
import com.yyx.commonsdk.log.MyLog;

/**
 * Created by admin on 2018/9/5.
 */

public class RecordActivity extends AppCompatActivity {
    private PageTurnView root4;
    private TextView tv4, edit4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        root4 = findViewById(R.id.root4);

        int playerWidth = getResources().getDisplayMetrics().widthPixels;
        int playerHeight = getResources().getDisplayMetrics().heightPixels;
        if (playerWidth <= playerHeight){
            int temp = playerHeight;
            playerHeight = playerWidth;
            playerWidth = temp;
        }
        float f = playerHeight / 720f;
        MyLog.d("show", playerWidth + " * " + playerHeight + " scale = " + f);

//        CoordinatesConversion conversion = new CoordinatesConversion(f, new PointF(playerWidth * 0.5f, playerHeight * 0.5f)
//                , playerWidth, playerHeight);
//        PageTurnViewAdapter myAdapter = new PageTurnViewAdapter(null,getSupportFragmentManager()
//                ,conversion,null,null);
//        myAdapter.reLoad("");
//        root4.setMyAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {

    }
}
