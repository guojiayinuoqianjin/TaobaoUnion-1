package com.niyangup.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.niyangup.taobaounion.R;
import com.niyangup.taobaounion.model.domain.HomePageCentent;
import com.niyangup.taobaounion.utils.LogUtil;
import com.niyangup.taobaounion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {
    private List<HomePageCentent.DataBean> contents = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        InnerHolder holder = new InnerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePageCentent.DataBean dataBean = contents.get(position);
        holder.title.setText(dataBean.getTitle());
        holder.offPriceTv.setText("省" + dataBean.getCoupon_amount() + "元");
        LogUtil.d(this, "省:" + dataBean.getCoupon_amount());
        Glide.with(holder.itemView).load(UrlUtil.getCoverPath(dataBean.getPict_url())).into(holder.cover);
        int couponAmount = dataBean.getCoupon_amount();

        //TODO  计算finalPrice显示在tv上
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public void setData(List<HomePageCentent.DataBean> contents) {
        this.contents.clear();
        this.contents.addAll(contents);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cover)
        public ImageView cover;

        @BindView(R.id.tv_title)
        public TextView title;

        @BindView(R.id.goods_after_off_price)
        public TextView offPriceTv;

        @BindView(R.id.tv_final_price)
        public TextView finalPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
