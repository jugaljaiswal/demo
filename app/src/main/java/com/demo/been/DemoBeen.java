package com.demo.been;

/**
 * Created by jugal on 7/4/16.
 */
public class DemoBeen
{
    String _id;
    String _title;
    String _img_uri;


    // Empty constructor
    public DemoBeen()
    {

    }
    // constructor
    public DemoBeen(String id, String title, String img_uri)
    {
        this._id = id;
        this._title = title;
        this._img_uri = img_uri;

    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_img_uri() {
        return _img_uri;
    }

    public void set_img_uri(String _img_uri) {
        this._img_uri = _img_uri;
    }
}
