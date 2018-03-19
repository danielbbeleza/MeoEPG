package com.example.meoepg.navigation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meoepg.R;
import com.example.meoepg.databinding.CardViewShowBinding;
import com.example.meoepg.model_objects.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class ShowsRecyclerViewAdapter extends RecyclerView.Adapter<ShowsRecyclerViewAdapter.ViewHolder> {

    private List<Card> mShowList;

    private Context mContext;

    private static final int CURRENT_SHOW = 0;
    private static final int NEXT_SHOW = 1;

    ShowsRecyclerViewAdapter(Context context){
        this.mShowList = new ArrayList<>();

        this.mContext = context;
    }

    public void updateData(List<Card> showList){
        this.mShowList = showList;

        // notify list that shows in it have changed
        this.notifyDataSetChanged();
    }

    @Override
    public ShowsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_view_show, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ShowsRecyclerViewAdapter.ViewHolder holder, int position) {

        Card currentCard = mShowList.get(position);

        // Show image
        Picasso.with(mContext).load(currentCard.getShowImage()).into(holder.getBinding().showImageView);

        // Channel title text
        holder.getBinding().channelTv.setText(currentCard.getChannel().getTitle());

        // Current show title text
        holder.getBinding().currentShowNameTv.setText(currentCard.getEpg().getShowList().get(CURRENT_SHOW).getTitle());

        if(currentCard.getEpg().getShowList().size() == 2) {
            // Next show title text
            holder.getBinding().nextShowNameTv.setText(currentCard.getEpg().getShowList().get(NEXT_SHOW).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mShowList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardViewShowBinding mBinding;

        ViewHolder(View layoutView) {
            super(layoutView);
            mBinding = DataBindingUtil.bind(layoutView);
        }

        CardViewShowBinding getBinding() {
            return mBinding;
        }

    }
}
