package com.jhotel.steven.jhotel_android_nurhazbiy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Hotel> listHotel;
    private ArrayList<Room> listRoom ;
    private HashMap<Hotel ,ArrayList<Room>> childMapping ;

    private List<String> listDataHeader ;
    private HashMap<String, List<String>> listDataChild ;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    private int currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button pesananButton = (Button) findViewById(R.id.pesanan);

        //getIntent
        if (savedInstanceState == null) {
            currentUserId = getIntent().getIntExtra("currentUserId",0);
        } else {
            currentUserId= (int) savedInstanceState.getSerializable("currentUserId");
        }

        System.out.println(currentUserId);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        refreshList();

        // Listview Group click listener
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
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent regisInt = new Intent(MainActivity.this, BuatPesananActivity.class);
                regisInt.putExtra("currentUserId", currentUserId);
                regisInt.putExtra("id_hotel", listHotel.get(groupPosition).getId());
                regisInt.putExtra("daily_tariff", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getDailyTariff());
                regisInt.putExtra("room_number", childMapping.get(listHotel.get(groupPosition)).get(childPosition).getRoomNumber());
                MainActivity.this.startActivity(regisInt);

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
                //change Activity
            }
        });

        // Pesanan on click listener
        pesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent regisInt = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                regisInt.putExtra("currentUserId", currentUserId);
                MainActivity.this.startActivity(regisInt);
            }
        });
    }

    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listHotel = new ArrayList<>();
                listRoom = new ArrayList<>();
                childMapping = new HashMap<> ();

                listDataHeader = new ArrayList<>();
                listDataChild = new HashMap<>();

                int listHotelSize = 0;

                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int loop = 0; loop < jsonResponse.length(); loop ++) {

                        JSONObject e = jsonResponse.getJSONObject(loop).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");

                        List<String> _listRoom = new ArrayList<>();
                        ArrayList<Room> listRoomTmp = new ArrayList<>();
                        float x_coord = BigDecimal.valueOf(lokasi.getDouble("x")).floatValue();
                        float y_coord = BigDecimal.valueOf(lokasi.getDouble("y")).floatValue();
                        String description = lokasi.getString("deskripsi");
                        int id = e.getInt("id");
                        String nama = e.getString("nama");
                        int bintang = e.getInt("bintang");
                        Hotel newHotel = new Hotel(id, nama, new Lokasi(x_coord, y_coord, description), bintang);

                        if (hotelChecker(newHotel)) {
                            listHotel.add(newHotel);
                            listDataHeader.add(newHotel.getNama());
                            listHotelSize += 1;

                            //System.out.println(newHotel.getNama());
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject getResponse = jsonResponse.getJSONObject(i);
                                JSONObject f = jsonResponse.getJSONObject(i).getJSONObject("hotel");

                                String namaHotel = f.getString("nama");
                                if (listHotel.get(listHotelSize - 1).getNama().compareTo(namaHotel) == 0) {
                                    //System.out.println(namaHotel + i);
                                    String nomorKamar = getResponse.getString("nomorKamar");
                                    String statusKamar = getResponse.getString("statusKamar");
                                    double dailyTariff = getResponse.getDouble("dailyTariff");
                                    String tipeKamar = getResponse.getString("tipeKamar");
                                    Room room = new Room(nomorKamar, statusKamar, dailyTariff, tipeKamar);

                                    listRoomTmp.add(room);
                                    listRoom.add(room);
                                    _listRoom.add(room.getRoomNumber());
                                }
                            }
                        /*Debug
                        for (String nems: _listRoom
                              ) {
                            System.out.println(nems);
                        }

                        System.out.println("---");*/
                            childMapping.put(newHotel, listRoomTmp);
                            listDataChild.put(newHotel.getNama(), _listRoom);
                        }
                    }
                    listAdapter = new MenuListAdapter(MainActivity.this, listDataHeader, listDataChild);

                    //Refresh view
                    expListView.setAdapter(listAdapter);
                }catch (JSONException ex){
                    System.out.println(ex.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Error connection")
                            .create()
                            .show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
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

}

