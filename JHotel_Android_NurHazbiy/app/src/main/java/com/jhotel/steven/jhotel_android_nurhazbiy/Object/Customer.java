package com.jhotel.steven.jhotel_android_nurhazbiy.Object;

import java.util.Date;

/**
 * Created by hazbiy on 09/05/18.
 */

public class Customer {
    protected int id;
    protected String nama;
    protected String email;
    protected Date dob;
    protected String password;

    public Customer(int id, String nama, String email, Date dob,String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.dob = dob;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
