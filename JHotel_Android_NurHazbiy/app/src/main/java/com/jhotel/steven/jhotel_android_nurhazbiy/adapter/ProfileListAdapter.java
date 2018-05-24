package com.jhotel.steven.jhotel_android_nurhazbiy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhotel.steven.jhotel_android_nurhazbiy.object.Pesanan;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *  This class is used for creating history list content
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class ProfileListAdapter extends BaseAdapter {
    private Context _context;
    private LayoutInflater inflter;
    private ArrayList<Pesanan> listPesanan;
    private ViewHolder mViewHolder = null;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");

    public ProfileListAdapter(Context context, ArrayList<Pesanan> listPesanan) {
        this._context = context;
        this.listPesanan = listPesanan;
    }

    @Override
    public int getCount() {
        return listPesanan.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            mViewHolder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.listview_history, null);
            mViewHolder.room_number = (TextView) view.findViewById(R.id.history_roomNumber);
            mViewHolder.hotel_name = (TextView) view.findViewById(R.id.history_hotelName);
            mViewHolder.order_date = (TextView) view.findViewById(R.id.history_orderDate);
            mViewHolder.order_status = (TextView) view.findViewById(R.id.history_status);
        }
        String final_status = null;
        boolean isDiproses = listPesanan.get(i).isStatusDiproses();
        boolean isSelesai = listPesanan.get(i).isStatusSelesai();

        if (isDiproses && !isSelesai) {
            final_status = "PROCESSING";
            mViewHolder.order_status.setTextColor(_context.getResources().getColor(R.color.colorProcessed));
        } else if (!isDiproses && !isSelesai) {
            final_status = "CANCELLED";
            mViewHolder.order_status.setTextColor(_context.getResources().getColor(R.color.colorCancelled));
        } else {
            final_status = "FINISHED";
            mViewHolder.order_status.setTextColor(_context.getResources().getColor(R.color.colorActive));
        }

        mViewHolder.room_number.setText(listPesanan.get(i).getRoom().getRoomNumber());
        mViewHolder.hotel_name.setText(listPesanan.get(i).getRoom().getHotel().getNama());
        mViewHolder.order_status.setText(final_status);
        mViewHolder.order_date.setText(DATE_FORMAT.format(listPesanan.get(i).getTanggalPesanan()));

        return view;
    }

    static class ViewHolder {
        TextView room_number;
        TextView hotel_name;
        TextView order_status;
        TextView order_date;
    }
}