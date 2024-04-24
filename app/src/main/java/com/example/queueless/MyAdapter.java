package com.example.queueless;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<infoOfReservation> mData;
    private LayoutInflater mInflater;

    Context context;
    MyAdapter(Context context, List<infoOfReservation> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        
        infoOfReservation reservation = mData.get(position);

        
        holder.nameOfRestaurant.setText(reservation.getName());
        holder.Location.setText(reservation.getLocation());
        holder.NumOfPerson.setText(String.valueOf(reservation.getNumOfPerson()));
        holder.Time.setText(formatOfTime(reservation.getTime()));
        holder.ReservationNumber.setText("Reservation number #" + String.valueOf(reservation.getNumberOfReservation()));

        
        holder.Status.setText(reservation.getStatus());

        
        if (reservation.getStatus().equalsIgnoreCase("Reserved")) {
            holder.Edit.setVisibility(View.VISIBLE); 
            holder.Cancel.setVisibility(View.VISIBLE); 
        } else {
            holder.Edit.setVisibility(View.GONE); 
            holder.Cancel.setVisibility(View.GONE); 
        }

        
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                int reservationId = mData.get(position).getNumberOfReservation();

                
                Intent intent = new Intent(context, edit.class);
                intent.putExtra("RESERVATION_ID", reservationId);
                context.startActivity(intent);
            }
        });


        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    
                    int reservationId = mData.get(position).getNumberOfReservation();

                    
                    DBHelper db = new DBHelper(context);
                    boolean success = db.CancelReservation(String.valueOf(reservationId));

                    
                    if (success) {
                        
                        holder.Edit.setVisibility(View.GONE);
                        holder.Cancel.setVisibility(View.GONE);

                        
                        holder.Status.setText("Cancelled");
                    } else {
                        
                        
                        Toast.makeText(context, "Cancellation failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfRestaurant, Location, NumOfPerson, Time, ReservationNumber, Status, Edit, Cancel;
        ImageView Logo;
        ViewHolder(View itemView) {
            super(itemView);
            Logo = itemView.findViewById(R.id.Logo);
            Status = itemView.findViewById(R.id.Status);
            Edit = itemView.findViewById(R.id.Edit);
            Cancel = itemView.findViewById(R.id.Cancel);
            nameOfRestaurant = itemView.findViewById(R.id.nameOfRestaurant);
            Location = itemView.findViewById(R.id.Location);
            NumOfPerson = itemView.findViewById(R.id.NumOfPerson);
            Time = itemView.findViewById(R.id.Time);
            ReservationNumber = itemView.findViewById(R.id.ReservationNumber);

        }
    }

    public String formatOfTime (String Time) {
        String outputDate = "";
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.ENGLISH);
            Date date = inputDateFormat.parse(Time);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            outputDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }
}