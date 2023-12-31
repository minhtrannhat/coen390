package com.example.coen390_app.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen390_app.Models.ParkingLotDevice;
import com.example.coen390_app.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{
    private ParkingLotAdapter.OnItemClickListener itemClickListener;

    private Context context;

    private List<ParkingLotDevice> parkingLotDeviceList;

    public DeviceAdapter(Context context, List<ParkingLotDevice> parkingLotDeviceList){
        this.context = context;
        this.parkingLotDeviceList = parkingLotDeviceList;
    }

    public void setOnItemClickListener(ParkingLotAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.device_profile, parent, false);
        return new DeviceViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.devName.setText("Device: " + parkingLotDeviceList.get(position).getName());
        if(parkingLotDeviceList.get(position).getStatus() == true){
            holder.devStatus.setText("free");
            holder.devStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_background));
        }
        else{
            holder.devStatus.setText("occupied");
            //holder.devStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_background_red);
        }

    }



    @Override
    public int getItemCount() {
        return parkingLotDeviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder{

        TextView devName, devStatus;
        public DeviceViewHolder(@NonNull View itemView,final ParkingLotAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            devName = itemView.findViewById(R.id.device_name);
            devStatus = itemView.findViewById(R.id.device_status);

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
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
