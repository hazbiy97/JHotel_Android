package com.jhotel.steven.jhotel_android_nurhazbiy.object;

/**
 * Created by hazbiy on 03/05/18.
 */

public class Lokasi {
    private double x_coord;
    private double y_coord;
    private String deskripsi;

    public Lokasi(float x_coord, float y_coord, String deskripsiLokasi)
    {
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.deskripsi = deskripsiLokasi;
    }

    public double getX_coord() {
        return x_coord;
    }

    public double getY_coord() {
        return y_coord;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setX_coord(double x_coord) {
        this.x_coord = x_coord;
    }

    public void setY_coord(double y_coord) {
        this.y_coord = y_coord;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
