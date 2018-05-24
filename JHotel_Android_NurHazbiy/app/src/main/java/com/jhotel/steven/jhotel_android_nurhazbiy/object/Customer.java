package com.jhotel.steven.jhotel_android_nurhazbiy.object;

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

    /**
     * Method to get the current value of object's ID
     *
     * @return id Current value of object's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the current value of object's Name
     *
     * @return nama Current value of object's Name
     */
    public String getNama() {
        return nama;
    }

    /**
     * Method to get the current value of object's email
     *
     * @return nama Current value of object's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get the current value of object's DOB
     *
     * @return nama Current value of object's DOB
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Method to get the current value of object's password
     *
     * @return nama Current value of object's password
     */
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to set new value of object's id
     *
     * @param id New value of object's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to set new value of object's Name
     *
     * @param nama New value of object's Name
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Method to set new value of object's email
     *
     * @param email New value of object's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to set new value of object's dob
     *
     * @param dob New value of object's dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

}
