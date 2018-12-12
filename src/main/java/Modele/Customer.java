/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matthias
 */
public class Customer {

    // TODO : ajouter les autres propriétés
    private int customerId;
    private String discountcode;
    private int zip;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String phone;
    private String fax;
    private String email;
    private int creditLimit;

    public Customer(int customerId, String dc, int zip, String name, String addressLine1,String addressLine2, String city, String State, String phone, String fax, String Email, int Credit) {
        this.customerId = customerId;
        this.discountcode=dc;
        this.zip=zip;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = State;
        this.phone = phone;
        this.fax = fax;
        this.email = Email;
        this.creditLimit = Credit;
    }

    /**
     * Get the value of customerId
     *
     * @return the value of customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    public String getDiscoutCode() {
        return this.discountcode;
    }
    
    public int getZip() {
        return this.zip;
    }
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of addressLine1
     *
     * @return the value of addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }
    
    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public Map<String, String> getAllAttributs() {
        Map<String, String> HM = new HashMap<String, String>();
        HM.put("customerid", Integer.toString(this.getCustomerId()));
        HM.put("name", this.getName());
        HM.put("adress", this.getAddressLine1());
        HM.put("city", this.getCity());
        HM.put("state", this.getState());
        HM.put("phone", this.getPhone());
        HM.put("fax", this.getFax());
        HM.put("email", this.getEmail());
        HM.put("creditlimit", Integer.toString(this.getCreditLimit()));

        return HM;

    }

    @Override
    public String toString() {
        String s = "-------------------------------------------";
        return s + "\nCustomer_id = " + this.getCustomerId() + "\n"
                + "Name = " + this.getName() + "\n"
                + "Adresse = " + this.getAddressLine1() + "\n"
                + "City = " + this.getCity() + "\n"
                + "State = " + this.getState() + "\n"
                + "Phone = " + this.getPhone() + "\n"
                + "Fax = " + this.getFax() + "\n"
                + "Email = " + this.getEmail() + "\n"
                + "Credit limit = " + this.getCreditLimit()
                + "\n" + s;
    }
}
