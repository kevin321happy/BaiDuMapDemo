package com.wh.jxd.com.baidumapdemo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.bean.ImageItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin321vip on 2017/12/18.
 */

public class OneAdapter extends RecyclerView.Adapter {
    private List<ImageItemBean> mList = new ArrayList<>();

    public List<ImageItemBean> getList() {
        return mList;
    }

    public void setList(List<ImageItemBean> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = View.inflate(parent.getContext(), R.layout.item_1, null);
        return new ViewHodler(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageItemBean imageItemBean = mList.get(position);
        if (imageItemBean == null) {
            return;
        }
        ViewHodler hodler = (ViewHodler) holder;
        hodler.iv_iam.setImageResource(imageItemBean.getRes());
        hodler.tv_title.setText(imageItemBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        private ImageView iv_iam;
        private TextView tv_title;

        public ViewHodler(View itemView) {
            super(itemView);
            iv_iam = (ImageView) itemView.findViewById(R.id.iv_ima);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
