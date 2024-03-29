package com.example.homeactivity.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.R;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder>{

    private FilterType[] filters;
    private Context context;
    private int selected = 0;

    public int filter_id;

    public FilterAdapter(Context context, FilterType[] filters) {
        this.filters = filters;
        this.context = context;
    }

    public void setFilter(int filter){
        this.filter_id = filter;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thumbnail_list_item,
                parent, false);
        FilterHolder viewHolder = new FilterHolder(view);
        viewHolder.thumbImage = (ImageView) view
                .findViewById(R.id.thumbnail);
        viewHolder.filterName = (TextView) view
                .findViewById(R.id.filter_name);
        viewHolder.filterRoot = (FrameLayout)view
                .findViewById(R.id.filter_root);
        viewHolder.thumbSelected = (FrameLayout) view
                .findViewById(R.id.filter_thumb_selected);
        viewHolder.thumbSelected_bg = view.
                findViewById(R.id.filter_thumb_selected_bg);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilterHolder holder,final int position) {
        holder.thumbImage.setImageResource(FilterTypeHelper.FilterType2Thumb(filters[position]));
        holder.filterName.setText(FilterTypeHelper.FilterType2Name(filters[position]));
        holder.filterName.setBackgroundColor(context.getResources().getColor(
                FilterTypeHelper.FilterType2Color(filters[position])));
        if(position == selected){
            holder.thumbSelected.setVisibility(View.VISIBLE);
            holder.thumbSelected_bg.setBackgroundColor(context.getResources().getColor(
                    FilterTypeHelper.FilterType2Color(filters[position])));
            holder.thumbSelected_bg.setAlpha(0.7f);
        }else {
            holder.thumbSelected.setVisibility(View.GONE);
        }

        holder.filterRoot.setOnClickListener(new View.OnClickListener() {

            /** 필터 클릭했을 때 */
            @Override
            public void onClick(View v) {
                if(selected == position)
                    return;
                int lastSelected = selected;
                selected = position;
                notifyItemChanged(lastSelected);
                notifyItemChanged(position);

                /** filter id */
                setFilter(selected);

                Filterid();

                onFilterChangeListener.onFilterChanged(filters[position]);
            }
        });
    }

    /************************/
    public int Filterid() {
//        Log.d(TAG, "필터아이디" + filter_id);
        return filter_id;
    }

    @Override
    public int getItemCount() {
        return filters == null ? 0 : filters.length;
    }

    class FilterHolder extends RecyclerView.ViewHolder {
        ImageView thumbImage;
        TextView filterName;
        FrameLayout thumbSelected;
        FrameLayout filterRoot;
        View thumbSelected_bg;

        public FilterHolder(View itemView) {
            super(itemView);
        }
    }

    public interface onFilterChangeListener{
        void onFilterChanged(FilterType filterType);
    }

    private onFilterChangeListener onFilterChangeListener;

    public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener){
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
