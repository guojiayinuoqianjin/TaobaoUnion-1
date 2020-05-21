package com.niyangup.taobaounion.ui.adapter;

import android.graphics.Paint;
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


        ViewGroup.LayoutParams layoutParams = holder.cover.getLayoutParams();
        int width = layoutParams.width;
        int height = layoutParams.height;
        int finalSize = (width > height ? width : height) ;
        LogUtil.d(this, "size:" + finalSize);


        Glide.with(holder.itemView).load(UrlUtil.getCoverPath(dataBean.getPict_url(), finalSize)).into(holder.cover);
        String price = dataBean.getZk_final_price();
        float priceFloat = Float.parseFloat(price);
        int couponAmount = dataBean.getCoupon_amount();
        float finalPriceFload = priceFloat - couponAmount;
        holder.finalPrice.setText(String.format("%.2f", finalPriceFload));//优惠后的价格
        holder.startPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.startPrice.setText(String.format("¥%.1f", Float.parseFloat(price)));//原价
        holder.saleNum.setText(dataBean.getVolume() + "人已购");
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

    public void addData(List<HomePageCentent.DataBean> data) {
        int olderSize = contents.size();

        contents.addAll(data);
        notifyItemRangeChanged(olderSize, data.size());
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

        @BindView(R.id.tv_start_price)
        public TextView startPrice;

        @BindView(R.id.tv_sale_num)
        public TextView saleNum;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
