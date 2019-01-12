package com.ugo.ugodriver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.fragments.Map.BookingModel;
import com.ugo.ugodriver.fragments.booking_history.HistoryDetails;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Developer on 11/25/16.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ProductHolder>  {

    private ArrayList<BookingModel> bookingModel;
    private Context context;
    Fragment fragment=null;
    FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    public HistoryAdapter(ArrayList<BookingModel> bookModel) {
        bookingModel = new ArrayList<>(bookModel);

    }

    @Override
    public HistoryAdapter.ProductHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view ;
        context = viewGroup.getContext();
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row, viewGroup, false);
        return new HistoryAdapter.ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ProductHolder holder, final int i) {
        holder.position = i;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        BookingModel product=bookingModel.get(i);
        holder.tv_name.setText(product.getCustomer_name());
        holder.tv_dateTime.setText(dateConversion(product.getTrip_start_time()));
        holder.tv_pickup.setText(product.getPick_address());
        holder.tv_drop.setText(product.getDrop_address());
        holder.tv_amt.setText(product.getTotal_fare());
        holder.tv_km.setText(product.getBooking_km()+"km");

        Picasso.with(context)
                .load(product.getCustomer_image())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(context)
                .into(holder.rider_image);

    }


    @Override
    public int getItemCount() {
        return bookingModel.size();
    }

    /** Filter Logic**/
    public void animateTo(ArrayList<BookingModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(ArrayList<BookingModel> newModels) {
        for (int i = bookingModel.size() - 1; i >= 0; i--) {
            final BookingModel model = bookingModel.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<BookingModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final BookingModel model = newModels.get(i);
            if (!bookingModel.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<BookingModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final BookingModel model = newModels.get(toPosition);
            final int fromPosition = bookingModel.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private BookingModel removeItem(int position) {
        final BookingModel model = bookingModel.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, BookingModel model) {
        bookingModel.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final BookingModel model = bookingModel.remove(fromPosition);
        bookingModel.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    class  ProductHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_dateTime, tv_pickup, tv_drop,tv_amt,tv_km;
        private CircleImageView rider_image;
        private int position;

        private ProductHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.rider_name);
            tv_dateTime =  itemView.findViewById(R.id.dateTime);
            rider_image = itemView.findViewById(R.id.rider_image);
            tv_pickup= itemView.findViewById(R.id.pickup);
            tv_drop= itemView.findViewById(R.id.drop);
            tv_amt= itemView.findViewById(R.id.tv_amt);
            tv_km= itemView.findViewById(R.id.tv_km);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToDetails(position);
                }
            });
        }
    }

    private String dateConversion(String date){
        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss" , java.util.Locale.getDefault() );
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("EEE, MMM dd, yyyy, hh:mm aa", java.util.Locale.getDefault());
        date = spf.format(newDate);
        return date;
    }

    private void moveToDetails(int pos){
        Intent i = new Intent(context , HistoryDetails.class);
        i.putExtra("booking" , bookingModel.get(pos));
        context.startActivity(i);
    }

}