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
public class Discount {
    private String discountCode;

	private float rate;

	public Discount(String code, float rate) {
		this.discountCode = code;
		this.rate = rate;
	}


	public String getDiscountCode() {
		return discountCode;
	}

	public float getRate() {
		return rate;
	}
}
