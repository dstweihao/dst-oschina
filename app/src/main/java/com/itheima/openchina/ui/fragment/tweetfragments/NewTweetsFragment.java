package com.itheima.openchina.ui.fragment.tweetfragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itheima.openchina.R;
import com.itheima.openchina.adapters.tweetAdapter.TweetAdapter;
import com.itheima.openchina.bases.BaseFragment;
import com.itheima.openchina.beans.TweetInfoBean;
import com.itheima.openchina.cacheadmin.LoadData;
import com.itheima.openchina.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  张慧强
 * Version:  1.0
 * Date:    2017/11/4 0004
 * Modify:
 * Description: //TODO
 * Copyright notice:
 */
public class NewTweetsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<TweetInfoBean.ResultBean.TweetItem> tweetItems=new ArrayList<>();
    private TweetAdapter recyclerViewAdapter;
    private View view;


    @Override
    protected void dataOnRefresh() {

    }

    @Override
    protected View onCreateContentView() {
        view = View.inflate(getContext(), R.layout.recycleview_view, null);
        recyclerView = (RecyclerView) view;

           init();
        return view;
    }

    private void init() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerViewAdapter = new TweetAdapter<TweetInfoBean.ResultBean.TweetItem>(getContext(),tweetItems);

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onStartLoadData() {


        /*Thread thread = Thread.currentThread();
        Log.d("------------",thread+"");*/
           new Thread(new Runnable() {
               @Override
               public void run() {
                   TweetInfoBean beanData = LoadData.getInstance().getBeanData("http://www.oschina.net/action/apiv2/tweets?type=1", TweetInfoBean.class);
                   List<TweetInfoBean.ResultBean.TweetItem> tweetItemList = beanData.getResult().getItems();

                   tweetItems.addAll(tweetItemList);

                    doLoadData();
               }
           }).start();




    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerViewAdapter!=null){
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void doLoadData() {
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                setRefreshEnable(false);
                loadSuccess();

            }
        });
    }
}
