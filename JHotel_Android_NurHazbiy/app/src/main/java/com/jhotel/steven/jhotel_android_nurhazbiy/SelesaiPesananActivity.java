package com.jhotel.steven.jhotel_android_nurhazbiy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SelesaiPesananActivity extends AppCompatActivity {
    private int currentUserId;

    private TextView idPesananTextView;
    private TextView biayaTextView;
    private TextView jumlahHariTextView ;
    private TextView tanggalPesananTextView;
    private Button batalPesananButton ;
    private Button selesaiPesananButton ;
    private ConstraintLayout consts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        idPesananTextView = (TextView) findViewById(R.id.id_pesanan);
        biayaTextView = (TextView) findViewById(R.id.biaya_pesanan);
        jumlahHariTextView = (TextView) findViewById(R.id.jumlah_hari);
        tanggalPesananTextView = (TextView) findViewById(R.id.tanggal_pesanan);
        batalPesananButton = (Button) findViewById(R.id.batalPesanan);
        selesaiPesananButton = (Button) findViewById(R.id.selesaiPesanan);
        consts = (ConstraintLayout) findViewById(R.id.constratinLayoutPesanan);

        // Get Intent Extra
        currentUserId = getIntent().getIntExtra("currentUserId",0);

        // Clear Layout
        consts.setVisibility(View.GONE);

        // Call fetch data
        fetchPesanan();

        batalPesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder1.setMessage("Cancel Pesanan Berhasil")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setMessage("Cancel Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(currentUserId,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananBatalRequest);
            }
        });

        selesaiPesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder1.setMessage("Selesaikan Pesanan Berhasil")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setMessage("Selesaikan Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(currentUserId,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
            }
        });
    }

    private void fetchPesanan() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse != null) {
                        idPesananTextView.setText(String.valueOf(jsonResponse.getInt("id")));
                        biayaTextView.setText(String.valueOf(jsonResponse.getDouble("biaya")));
                        jumlahHariTextView.setText(String.valueOf(jsonResponse.getInt("jumlahHari")));
                        tanggalPesananTextView.setText(jsonResponse.getString("tanggalPesanan"));

                        consts.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
                    builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            //change Activity
                            Intent regisInt = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                            SelesaiPesananActivity.this.startActivity(regisInt);
                        }
                    });
                    builder1.setMessage("Pesanan Kosong")
                            .create()
                            .show();
                }
            }
        };
        PesananFetchRequest fetchPesananRequest = new PesananFetchRequest(currentUserId,responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(fetchPesananRequest);
    }
}
