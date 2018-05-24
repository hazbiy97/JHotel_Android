package com.jhotel.steven.jhotel_android_nurhazbiy.object;

import java.util.Date;

/**
 * Created by hazbiy on 11/05/18.
 */

public class Pesanan {
    private int id ;
    private int biaya;
    private int jumlahHari;
    private boolean statusAktif;
    private boolean statusDiproses;
    private boolean statusSelesai;
    private Date tanggalPesanan;
    private Room room;

    public Pesanan(int id, int biaya, int jumlahHari, Date tanggalPesanan, boolean statusAktif, boolean statusDiproses, boolean statusSelesai,Room room) {
        this.id = id;
        this.biaya = biaya;
        this.jumlahHari = jumlahHari;
        this.tanggalPesanan = tanggalPesanan;
        this.statusAktif = statusAktif;
        this.statusDiproses = statusDiproses;
        this.statusSelesai = statusSelesai;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public int getBiaya() {
        return biaya;
    }

    public int getJumlahHari() {
        return jumlahHari;
    }

    public Room getRoom() {
        return room;
    }

    public Date getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(Date tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isStatusAktif() {
        return statusAktif;
    }

    public boolean isStatusDiproses() {
        return statusDiproses;
    }

    public boolean isStatusSelesai() {
        return statusSelesai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public void setJumlahHari(int jumlahHari) {
        this.jumlahHari = jumlahHari;
    }

    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public void setStatusDiproses(boolean statusDiproses) {
        this.statusDiproses = statusDiproses;
    }

    public void setStatusSelesai(boolean statusSelesai) {
        this.statusSelesai = statusSelesai;
    }
}
