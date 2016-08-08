package lib.kingja.kpagerloader;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Description：TODO
 * Create Time：2016/8/8 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class KPagerLoader extends FrameLayout {
    View mEmptyView;
    View mErrorView;
    View mLoadingView;
    View mSuccessView;
    private Context context;
    private LoadStatus mLoadStatus = LoadStatus.SUCCEED;
    private LayoutParams layoutParams;

    public KPagerLoader(Context context) {
        this(context, null);
    }

    public KPagerLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KPagerLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPagers();
    }

    private void initPagers() {
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        if (mEmptyView == null) {
            mEmptyView = View.inflate(context, R.layout.pager_empty, null);
            addView(mEmptyView, layoutParams);
        }
        if (mLoadingView == null) {
            mLoadingView = View.inflate(context, R.layout.pager_loading, null);
            addView(mLoadingView, layoutParams);
        }
        if (mErrorView == null) {
            mErrorView = View.inflate(context, R.layout.pager_error, null);
            addView(mErrorView, layoutParams);
        }
        if (mSuccessView == null) {
            mSuccessView = View.inflate(context, getSuccessView(), null);
            addView(mSuccessView, layoutParams);
        }
        onLoad(mLoadStatus);
    }

    public void setEmptyView(int resource) {
        if (mEmptyView !=null ) {
            removeView(mEmptyView);
        }
        mEmptyView = View.inflate(context, resource, null);
        addView(mEmptyView, layoutParams);
    }

    public void setLoadingView(int resource) {
        if ( mLoadingView!=null) {
            removeView(mLoadingView);
        }
        mLoadingView = View.inflate(context, resource, null);
        addView(mLoadingView, layoutParams);
    }

    public void setErrorView(int resource) {
        if (mErrorView!=null) {
            removeView(mErrorView);
        }
        mErrorView = View.inflate(context, resource, null);
        addView(mErrorView, layoutParams);
        onLoad(mLoadStatus);
    }

    protected abstract int getSuccessView();

    public void onLoad(@NonNull final LoadStatus mLoadStatus) {
        if (isMainThread()) {
            load(mLoadStatus);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    load(mLoadStatus);
                }
            });
        }
    }

    private void load(LoadStatus mLoadStatus) {

        this.mLoadStatus = mLoadStatus;
        if (mLoadingView != null) {
            mLoadingView.setVisibility(mLoadStatus == LoadStatus.LOADING ? View.VISIBLE : View.GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(mLoadStatus == LoadStatus.ERROR ? View.VISIBLE : View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(mLoadStatus == LoadStatus.EMPTY ? View.VISIBLE : View.GONE);
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility((mLoadStatus == LoadStatus.SUCCEED) ? View.VISIBLE : View.GONE);
        }

    }

    public enum LoadStatus {
        LOADING, EMPTY, ERROR, SUCCEED
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public KPagerLoader setErrorClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            mErrorView.setOnClickListener(onClickListener);
        }
        return this;
    }

    public KPagerLoader setEmptyClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            mEmptyView.setOnClickListener(onClickListener);
        }
        return this;
    }

}
