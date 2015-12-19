package com.stephen.helloworld.views.pullrefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.stephen.helloworld.R;

/**
 * Created by Stephen on 2015/12/19.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private String TAG = "PullToRefreshListView";

    private View headerView;
    private FirstView firstView;
    private SecondView secondView;
    private ThirdView thirdView;
    private TextView header_tv;
    private AnimationDrawable second_ad;
    private AnimationDrawable third_ad;

    private int headerViewHeight = 0;
    private STATE current_state = STATE.DONE;
    private int mFirstVisibleItem;
    private boolean isRecord;
    private float startY;
    private float offsetY;

    private enum STATE {
        DONE,
        PULL_TO_REFRESH,
        RELEASE_TO_REFRESH,
        REFRESHING
    }

    private int RATIO = 3;
    private boolean isEnd = false;
    private boolean isRefreshable = false;
    private OnMyRefreshListener mOnRefreshListener;

    public PullToRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
//        setOverScrollMode(OVER_SCROLL_NEVER);
        setOverScrollMode(OVER_SCROLL_ALWAYS);
//        setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        setOnScrollListener(this);
        headerView = LayoutInflater.from(context).inflate(R.layout.header, null, false);

        firstView = (FirstView) headerView.findViewById(R.id.header_fv);

        secondView = (SecondView) headerView.findViewById(R.id.header_sv);
        secondView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        second_ad = (AnimationDrawable) secondView.getBackground();

        thirdView = (ThirdView) headerView.findViewById(R.id.header_tv);
        thirdView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
        third_ad = (AnimationDrawable) thirdView.getBackground();

        header_tv = (TextView) headerView.findViewById(R.id.header_textview);
        measureView(headerView);
        addHeaderView(headerView);

        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        current_state = STATE.DONE;
        isEnd = true;
        isRefreshable = false;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
    }

    private void measureView(View child) {

        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Log.d(TAG, "new ViewGroup LayoutParams");
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            Log.d(TAG, "lpHeight > 0");
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            Log.d(TAG, "lpHeight <= 0");
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
        Log.d(TAG, "childWidthSpec = " + childWidthSpec + "    childHeightSpec = " + childHeightSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isEnd) {
            if (isRefreshable) {//可刷新状态
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mFirstVisibleItem == 0 && !isRecord) {//当前是listview的顶部 并且没有记录Y的值
                            isRecord = true;
                            startY = ev.getY();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float tempY = ev.getY();
                        if (mFirstVisibleItem == 0 && !isRecord) {
                            isRecord = true;
                            startY = ev.getY();
                        }
                        if (current_state != STATE.REFRESHING && isRecord) {
                            offsetY = tempY - startY;
                            float currentHeight = (-headerViewHeight + offsetY / 3);
                            //这里除3的意思是：向下拉出3倍的headerView高度  headerView才全部显示出来
                            float currentProgress = 1 + currentHeight / headerViewHeight;
                            if (currentProgress >= 1) {
                                currentProgress = 1;
                            }
                            if (current_state == STATE.RELEASE_TO_REFRESH && isRecord) {
                                setSelection(0);
                                if (-headerViewHeight + offsetY / RATIO < 0) {//如果headerView没有被全部拉出，则回到初始状态
                                    current_state = STATE.PULL_TO_REFRESH;
                                    changeHeaderByState(current_state);
                                } else if (offsetY <= 0) {//向上滑动 offset　< 0
                                    current_state = STATE.DONE;
                                    changeHeaderByState(current_state);
                                }
                            }
                            if (current_state == STATE.PULL_TO_REFRESH && isRecord) {
                                setSelection(0);
//                                if (-headerViewHeight + offsetY / RATIO >= 0) {//正常的将headerview拉出
                                if (-headerViewHeight + offsetY / RATIO >= 100) {//正常的将headerview拉出
                                    current_state = STATE.RELEASE_TO_REFRESH;
                                    changeHeaderByState(current_state);
                                } else if (offsetY <= 0) {
                                    current_state = STATE.DONE;
                                    changeHeaderByState(current_state);
                                }
                            }
                            if (current_state == STATE.DONE && isRecord) {
                                if (offsetY >= 0) {
                                    current_state = STATE.PULL_TO_REFRESH;
                                }
                            }
                            if (current_state == STATE.PULL_TO_REFRESH) {
                                headerView.setPadding(0, (int) (-headerViewHeight + offsetY / RATIO), 0, 0);
                                firstView.setCurrentProgress(currentProgress);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (current_state == STATE.PULL_TO_REFRESH) {
//                            this.smoothScrollBy((int) (-headerViewHeight + offsetY / RATIO + headerViewHeight), 500);
                            this.smoothScrollBy(-headerViewHeight, 500);
                            changeHeaderByState(current_state);
                        }
                        if (current_state == STATE.RELEASE_TO_REFRESH) {
//                            this.smoothScrollBy((int) (-headerViewHeight + offsetY / RATIO), 500);
                            this.smoothScrollBy(-headerViewHeight, 500);
                            current_state = STATE.REFRESHING;
                            mOnRefreshListener.onRefresh();
                            changeHeaderByState(current_state);
                        }
                        isRecord = false;
                        break;
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    private void changeHeaderByState(STATE state) {
        switch (state) {
            case PULL_TO_REFRESH:
                header_tv.setText(R.string.pulltorefresh);
                firstView.setVisibility(VISIBLE);
                secondView.setVisibility(GONE);
                second_ad.stop();
                thirdView.setVisibility(GONE);
                third_ad.stop();
                break;
            case REFRESHING:
                header_tv.setText(R.string.refreshing);
                firstView.setVisibility(GONE);
                second_ad.stop();
                secondView.setVisibility(GONE);
                thirdView.setVisibility(VISIBLE);
                third_ad.start();

                break;
            case RELEASE_TO_REFRESH:
                header_tv.setText(R.string.release_to_refresh);
                firstView.setVisibility(GONE);
                secondView.setVisibility(VISIBLE);
                second_ad.start();
                thirdView.setVisibility(GONE);
                third_ad.stop();
                break;
            case DONE:
                header_tv.setText(R.string.pulltorefresh);
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                firstView.setVisibility(VISIBLE);
                secondView.setVisibility(GONE);
                second_ad.stop();
                thirdView.setVisibility(GONE);
                third_ad.stop();
                break;
            default:
                break;
        }
    }

    public void setOnMyRefreshListener(OnMyRefreshListener l) {
        this.mOnRefreshListener = l;
        isRefreshable = true;
    }

    public void setOnRefreshComplete() {
        isEnd = true;
        current_state = STATE.DONE;
        changeHeaderByState(current_state);
    }

    public interface OnMyRefreshListener {
        void onRefresh();
    }

}
