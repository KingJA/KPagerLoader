package com.kingja.kpagerloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lib.kingja.kpagerloader.KPagerLoader;

public class MainActivity extends AppCompatActivity {

    private KPagerLoader kPagerLoader;
    private KPagerLoader.LoadStatus mLoadStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kPagerLoader = new KPagerLoader(this) {
            @Override
            protected int getSuccessView() {
                return R.layout.activity_main;
            }
        };
        kPagerLoader.setEmptyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"数据为空",Toast.LENGTH_SHORT).show();
            }
        }).setErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"网络失败",Toast.LENGTH_SHORT).show();
            }
        }).setErrorView(R.layout.pager_error2);
        setContentView(kPagerLoader);
    }

    public void onError(View view) {
        mLoadStatus = KPagerLoader.LoadStatus.ERROR;
        kPagerLoader.onLoad(mLoadStatus);
    }

    public void onEmpty(View view) {
        mLoadStatus = KPagerLoader.LoadStatus.EMPTY;
        kPagerLoader.onLoad(mLoadStatus);

    }

    public void onSuccess(View view) {
        mLoadStatus = KPagerLoader.LoadStatus.SUCCEED;
        kPagerLoader.onLoad(mLoadStatus);
    }

    @Override
    public void onBackPressed() {
        if (mLoadStatus != KPagerLoader.LoadStatus.SUCCEED) {
            mLoadStatus = KPagerLoader.LoadStatus.SUCCEED;
            kPagerLoader.onLoad(mLoadStatus);
        } else {
            super.onBackPressed();
        }
    }
}
