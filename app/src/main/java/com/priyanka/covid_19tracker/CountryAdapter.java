package com.priyanka.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.priyanka.covid_19tracker.api.CountryData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private Context context;
    private List<CountryData>list;

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_item_layout,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CountryAdapter.CountryViewHolder holder, int position) {
         CountryData data= list.get(position);
         holder.countryCases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
         holder.countryName.setText(data.getCountry());
        Map<String,String>flagMap=data.getCountryInfo();
        Glide.with(context).load(flagMap.get("flag")).into(holder.flag);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView countryName,countryCases;
        private ImageView flag;
        public CountryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            countryName=itemView.findViewById(R.id.countryName);
            countryCases=itemView.findViewById(R.id.countryCases);
            flag=itemView.findViewById(R.id.flag);
        }
    }
}
