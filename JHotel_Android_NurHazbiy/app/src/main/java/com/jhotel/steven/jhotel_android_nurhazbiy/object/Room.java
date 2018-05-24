package com.jhotel.steven.jhotel_android_nurhazbiy.object;

/**
 * Created by hazbiy on 03/05/18.
 */

public class Room {
    private Hotel hotel;
    private String roomNumber;
    private String statusKamar;
    private double dailyTariff;
    private String tipeKamar;

    public Room(Hotel hotel, String roomNumber, String statusKamar, double dailyTariff, String tipeKamar) {
        this.roomNumber = roomNumber;
        this.statusKamar = statusKamar;
        this.dailyTariff = dailyTariff;
        this.tipeKamar = tipeKamar;
        this.hotel = hotel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getStatusKamar() {

        return statusKamar;
    }

    public double getDailyTariff() {

        return dailyTariff;
    }

    public String getTipeKamar() {

        return tipeKamar;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setRoomNumber(String roomNumber) {

        this.roomNumber = roomNumber;
    }

    public void setStatusKamar(String statusKamar) {

        this.statusKamar = statusKamar;
    }

    public void setDailyTariff(double dailyTariff) {

        this.dailyTariff = dailyTariff;
    }

    public void setTipeKamar(String tipeKamar) {

        this.tipeKamar = tipeKamar;
    }

    public String toString() {
        /*return "Hotel{" +
                "nama='" + this.nama + "', " +
                "lokasi='" + this.lokasi.getDeskripsi() + "', " +
                "bintang=" + this.bintang +
                "}";*/
        return this.roomNumber;
    }
}
