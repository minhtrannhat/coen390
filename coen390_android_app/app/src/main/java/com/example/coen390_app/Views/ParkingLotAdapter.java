package com.example.coen390_app.Views;




import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.Models.SecondaryParkingLot;
import com.example.coen390_app.R;

import java.util.List;

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ParkingLotViewHolder> {

    private Context context;
    private List<ParkingLotProfile> parkingLotList;

    private List<SecondaryParkingLot> secondaryParkingLots;

    private boolean isAdmin;
    private boolean showFirst;

    private OnItemClickListener itemClickListener;

    public ParkingLotAdapter(Context context, List<ParkingLotProfile> parkingLotList, List<SecondaryParkingLot> secondaryParkingLots, boolean isAdmin, boolean showFirst) {
        this.context = context;
        this.parkingLotList = parkingLotList;
        this.secondaryParkingLots = secondaryParkingLots;
        this.isAdmin = isAdmin;
        this.showFirst = showFirst;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ParkingLotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_parking_lot_profile, parent, false);
        return new ParkingLotViewHolder(view, itemClickListener);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ParkingLotViewHolder holder, int position) {
        if(position == 0 && showFirst == true)
        {
            ParkingLotProfile parkingLot = parkingLotList.get(position);
            holder.tvName.setText(parkingLot.getName());
            holder.tvOccupancy.setText(String.format("%d/%d", parkingLot.getCurrent_occupancy(), parkingLot.getMax_occupancy()));
            holder.tvAddress.setText(parkingLot.getAddress());
            // Set data to views
        }
        else if(!isAdmin){
            int location = position;
            if(showFirst){location=position-1;}

            holder.tvName.setText(secondaryParkingLots.get(location).getName());
            holder.tvOccupancy.setText(secondaryParkingLots.get(location).getOccupancy()+"/10");
            holder.tvAddress.setText(secondaryParkingLots.get(location).getAddress());
            if(secondaryParkingLots.get(location).getOccupancy()<4){
                holder.frameLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_background));
            }
            else if(secondaryParkingLots.get(location).getOccupancy()<7){
                holder.frameLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_background_yellow));
            }else{
                holder.frameLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_background_red));
            }
        }


    }

    @Override
    public int getItemCount() {
        return parkingLotList.size() + secondaryParkingLots.size();
    }

    public static class ParkingLotViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvOccupancy, tvAddress;
        FrameLayout frameLayout;

        public ParkingLotViewHolder(@NonNull View itemView, final OnItemClickListener itemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.profile_name);
            tvOccupancy = itemView.findViewById(R.id.current_occupancy);
            tvAddress = itemView.findViewById(R.id.profile_addr);
            frameLayout = itemView.findViewById(R.id.profile_frame);

            // Move the click listener to the constructor
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}