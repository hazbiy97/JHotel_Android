package com.jhotel.steven.jhotel_android_nurhazbiy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Response;
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.LoginActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.SessionManager;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.BuatPesananActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.MenuListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  This class is used for creating Home Fragment in Main Activity
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class HomeFragment extends Fragment {
    private int currentUserId;
    private ArrayList<Hotel> listHotel;
    private ArrayList<Room> listRoom ;
    private HashMap<Hotel ,ArrayList<Room>> childMapping ;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private View view;

    /**
     * Refresh lists everytime activity is resumed
     */
    @Override
    public void onResume(){
        super.onResume();

        refreshList();
    }

    /**
     * Getting view value for generating layout
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return current view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    /**
     * Generating layout when Main Activity is created
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
        }

        // get the layout
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        /*TODO Extra Features
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });*/

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Intent with extra
                Intent regisInt = new Intent(getActivity(), BuatPesananActivity.class);
                regisInt.putExtra("currentUserId", currentUserId);
                regisInt.putExtra("id_hotel", listHotel.get(groupPosition).getId());
                regisInt.putExtra("daily_tariff", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getDailyTariff());
                regisInt.putExtra("room_number", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getRoomNumber());
                regisInt.putExtra("room_type", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getTipeKamar());

                getActivity().startActivity(regisInt);

                return false;
            }
        });
    }

    /**
     * Refreshing list and updating expendable list
     */
    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // resetting data
                listHotel = new ArrayList<>();
                listRoom = new ArrayList<>();
                childMapping = new HashMap<> ();
                List<String> listDataHeader = new ArrayList<>();;
                HashMap<String, List<String>> listDataChild = new HashMap<>() ;
                int listHotelSize = 0;

                // Json mapping
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int loop = 0; loop < jsonResponse.length(); loop ++) {

                        JSONObject e = jsonResponse.getJSONObject(loop).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");

                        // JSON Mapping hotel object
                        List<String> _listRoom = new ArrayList<>();
                        ArrayList<Room> listRoomTmp = new ArrayList<>();
                        float x_coord = BigDecimal.valueOf(lokasi.getDouble("x")).floatValue();
                        float y_coord = BigDecimal.valueOf(lokasi.getDouble("y")).floatValue();
                        String description = lokasi.getString("deskripsi");
                        int id = e.getInt("id");
                        String nama = e.getString("nama");
                        int bintang = e.getInt("bintang");
                        Hotel newHotel = new Hotel(id, nama, new Lokasi(x_coord, y_coord, description), bintang);

                        // checking list
                        if (hotelChecker(newHotel)) {
                            listHotel.add(newHotel);
                            listDataHeader.add(newHotel.getNama());
                            listHotelSize += 1;

                            // checking room with same hotel
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject getResponse = jsonResponse.getJSONObject(i);
                                JSONObject f = jsonResponse.getJSONObject(i).getJSONObject("hotel");

                                String namaHotel = f.getString("nama");
                                if (listHotel.get(listHotelSize - 1).getNama().compareTo(namaHotel) == 0) {
                                    // JSON mapping with same hotel
                                    String nomorKamar = getResponse.getString("nomorKamar");
                                    String statusKamar = getResponse.getString("statusKamar");
                                    double dailyTariff = getResponse.getDouble("dailyTariff");
                                    String tipeKamar = getResponse.getString("tipeKamar");
                                    Room room = new Room(newHotel,nomorKamar, statusKamar, dailyTariff, tipeKamar);

                                    listRoomTmp.add(room);
                                    listRoom.add(room);
                                    _listRoom.add(room.getRoomNumber());
                                    //System.out.println(namaHotel + i);
                                }
                            }
                            /* Debugging vacant rooms
                            for (String nems: _listRoom
                                  ) {
                                System.out.println(nems);
                            }

                            System.out.println("---");*/

                            childMapping.put(newHotel, listRoomTmp);
                            listDataChild.put(newHotel.getNama(), _listRoom);
                        }
                    }
                    listAdapter = new MenuListAdapter(getActivity(), listDataHeader, listDataChild);

                    //Refresh view
                    expListView.setAdapter(listAdapter);
                }catch (JSONException ex){
                    System.out.println(ex.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                            .setMessage("Error connection\nPlease try again later")
                            .setCancelable(false)
                            .create()
                            .show();
                }
            }
        };

        // add API request to queue
        MenuRequest menuRequest = new MenuRequest(responseListener,new RequestErrorListener("Menu fetching failed", "Check your internet connection",getContext()));
        ApplicationVolley.getInstance().getRequestQueue().add(menuRequest);
    }

    /**
     * Method for checking wether hotel is existed or not
     *
     * @param baru
     * @return
     */
    private boolean hotelChecker(Hotel baru) {
        if(listHotel.size() != 0) {
            for (Hotel hotel :
                    listHotel) {
                if (hotel.getNama().compareTo(baru.getNama()) == 0 || (hotel.getLokasi().getX_coord() == baru.getLokasi().getX_coord() && hotel.getLokasi().getY_coord() == baru.getLokasi().getY_coord())) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

}
