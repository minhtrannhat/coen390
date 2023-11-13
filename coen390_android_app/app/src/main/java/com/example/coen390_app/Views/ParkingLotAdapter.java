package com.example.coen390_app.Views;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen390_app.Models.ParkingLotProfile;
import com.example.coen390_app.R;

import java.util.List;

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ParkingLotViewHolder> {

    private Context context;
    private List<ParkingLotProfile> parkingLotList;

    private OnItemClickListener itemClickListener;

    public ParkingLotAdapter(Context context, List<ParkingLotProfile> parkingLotList) {
        this.context = context;
        this.parkingLotList = parkingLotList;
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

    @Override
    public void onBindViewHolder(@NonNull ParkingLotViewHolder holder, int position) {
        ParkingLotProfile parkingLot = parkingLotList.get(position);

        // Set data to views
        holder.tvName.setText(parkingLot.getName());
        holder.tvOccupancy.setText(String.format("%d/%d", parkingLot.getCurrent_occupancy(), parkingLot.getMax_occupancy()));
        holder.tvAddress.setText(parkingLot.getAddress());
    }

    @Override
    public int getItemCount() {
        return parkingLotList.size();
    }

    public static class ParkingLotViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvOccupancy, tvAddress;

        public ParkingLotViewHolder(@NonNull View itemView, final OnItemClickListener itemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.profile_name);
            tvOccupancy = itemView.findViewById(R.id.current_occupancy);
            tvAddress = itemView.findViewById(R.id.profile_addr);

            // Move the click listener to the constructor
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick();
                    }
                }
            });
        }
    }
    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick();
    }
}