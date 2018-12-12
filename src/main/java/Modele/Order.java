/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Matthias
 */
public class Order {
    private int order_num;
    private int customer_id;
    private int product_id;
    private int quantity;
    private float shipping_cost;
    private String sales_date;
    private String shipping_date;
    
    public Order(int order, int c_id, int p_id, int quantity, float cost,String sale_d, String shipping_d){
        this.order_num=order;
        this.customer_id=c_id;
        this.product_id=p_id;
        this.quantity=quantity;
        this.shipping_cost=cost;
        this.sales_date=sale_d;
        this.shipping_date=shipping_d;
    }
    
    public int getOrderNum(){
        return this.order_num;
    }
    
    public int getCustomerID(){
        return this.customer_id;
    }
    
    
    public int getProductID(){
        return this.product_id;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    public float getShippingCost(){
        return this.shipping_cost;
    }
    
    public String getSaleDate(){
        return this.sales_date;
    }
    
    public String getShippingDate(){
        return this.shipping_date;
    }
}
