package com.exovum.main;

/**
 * Created by exovu on 3/17/2017.
 */

public class TestProduct {

    private int _id;
    private String _productname;

    public TestProduct() {
    }

    public TestProduct(String productname) {
        _productname = productname;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }
}
