package com.jhotel.steven.jhotel_android_nurhazbiy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Response;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.BuatPesananActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.Object.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private int currentUserId;
    private ArrayList<Hotel> listHotel;
    private ArrayList<Room> listRoom ;
    private HashMap<Hotel ,ArrayList<Room>> childMapping ;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        super.onResume();

        refreshList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //TODO Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
        }

        //TODO check session if done or not
        checkSession();
        //System.out.println(currentUserId);

        //TODO get the listview
        expListView = (ExpandableListView) getView().findViewById(R.id.lvExp);

        //TODO preparing list data
        refreshList();

        //TODO Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        /*TODO Extra Features
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

        //TODO Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //TODO Intent with extra
                Intent regisInt = new Intent(getActivity(), BuatPesananActivity.class);
                regisInt.putExtra("currentUserId", currentUserId);
                regisInt.putExtra("id_hotel", listHotel.get(groupPosition).getId());
                regisInt.putExtra("daily_tariff", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getDailyTariff());
                regisInt.putExtra("room_number", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getRoomNumber());
                regisInt.putExtra("room_type", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getTipeKamar());
                getActivity().startActivity(regisInt);

                return false;
                /*TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
            }
        });
    }

    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO resetting data
                listHotel = new ArrayList<>();
                listRoom = new ArrayList<>();
                childMapping = new HashMap<> ();
                List<String> listDataHeader = new ArrayList<>();;
                HashMap<String, List<String>> listDataChild = new HashMap<>() ;
                int listHotelSize = 0;

                //TODO Json mapping
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int loop = 0; loop < jsonResponse.length(); loop ++) {

                        JSONObject e = jsonResponse.getJSONObject(loop).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");

                        //TODO JSON Mapping hotel object
                        List<String> _listRoom = new ArrayList<>();
                        ArrayList<Room> listRoomTmp = new ArrayList<>();
                        float x_coord = BigDecimal.valueOf(lokasi.getDouble("x")).floatValue();
                        float y_coord = BigDecimal.valueOf(lokasi.getDouble("y")).floatValue();
                        String description = lokasi.getString("deskripsi");
                        int id = e.getInt("id");
                        String nama = e.getString("nama");
                        int bintang = e.getInt("bintang");
                        Hotel newHotel = new Hotel(id, nama, new Lokasi(x_coord, y_coord, description), bintang);

                        //TODO checking list
                        if (hotelChecker(newHotel)) {
                            listHotel.add(newHotel);
                            listDataHeader.add(newHotel.getNama());
                            listHotelSize += 1;

                            //System.out.println(newHotel.getNama());
                            //TODO checking room with same hotel
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject getResponse = jsonResponse.getJSONObject(i);
                                JSONObject f = jsonResponse.getJSONObject(i).getJSONObject("hotel");

                                String namaHotel = f.getString("nama");
                                if (listHotel.get(listHotelSize - 1).getNama().compareTo(namaHotel) == 0) {
                                    //TODO JSON mapping with same hotel
                                    String nomorKamar = getResponse.getString("nomorKamar");
                                    String statusKamar = getResponse.getString("statusKamar");
                                    double dailyTariff = getResponse.getDouble("dailyTariff");
                                    String tipeKamar = getResponse.getString("tipeKamar");
                                    Room room = new Room(nomorKamar, statusKamar, dailyTariff, tipeKamar);

                                    listRoomTmp.add(room);
                                    listRoom.add(room);
                                    _listRoom.add(room.getRoomNumber());
                                    //System.out.println(namaHotel + i);
                                }
                            }
                            /*TODO Debugging vacant rooms
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
                    builder1.setMessage("Error connection\nPlease try again later")
                            .create()
                            .show();
                }
            }
        };
        //TODO add API request to queue
        MenuRequest menuRequest = new MenuRequest(responseListener);
        ApplicationVolley.getInstance().getRequestQueue().add(menuRequest);
    }

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

    private void checkSession(){
        if(currentUserId == 0){

        }
    }
}
