package com.example.covid_19.DashBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;

import java.util.ArrayList;

public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.AdapterAllPreventionViewHolder>{
    ArrayList<PreventionHelperClass> viewAll;
    public PreventionAdapter(ArrayList<PreventionHelperClass> viewAll){
        this.viewAll=viewAll;
    }
    @NonNull
    @Override
    public PreventionAdapter.AdapterAllPreventionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prevention_card_design, parent, false);
        PreventionAdapter.AdapterAllPreventionViewHolder lvh = new PreventionAdapter.AdapterAllPreventionViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PreventionAdapter.AdapterAllPreventionViewHolder holder, int position) {

        PreventionHelperClass helperClass = viewAll.get(position);
        holder.imageView.setImageResource(helperClass.getImage());
        holder.textView.setText(helperClass.getTitle());
        holder.linearLayout.setBackground(helperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return viewAll.size();
    }

    public static class AdapterAllPreventionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;
        public AdapterAllPreventionViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.background_prevention);
            imageView = itemView.findViewById(R.id.prevention_image);
            textView = itemView.findViewById(R.id.prevention_title);
        }
    }
}
