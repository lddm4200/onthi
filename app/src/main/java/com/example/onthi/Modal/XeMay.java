package com.example.onthi.Modal;

public class XeMay {
    private String _id;
    private String ten_xe_ph42469;
    private String mau_sac_ph42469;
    private int gia_ban_ph42469;
    private String mo_ta_ph42469;
    private String hinh_anh_ph42469;


    public XeMay(String ten_xe_ph42469, String mau_sac_ph42469, int gia_ban_ph42469, String mo_ta_ph42469, String hinh_anh_ph42469) {
        this.ten_xe_ph42469 = ten_xe_ph42469;
        this.mau_sac_ph42469 = mau_sac_ph42469;
        this.gia_ban_ph42469 = gia_ban_ph42469;
        this.mo_ta_ph42469 = mo_ta_ph42469;
        this.hinh_anh_ph42469 = hinh_anh_ph42469;
    }

    public XeMay(String _id, String ten_xe_ph42469, String mau_sac_ph42469, int gia_ban_ph42469, String mo_ta_ph42469, String hinh_anh_ph42469) {
        this._id = _id;
        this.ten_xe_ph42469 = ten_xe_ph42469;
        this.mau_sac_ph42469 = mau_sac_ph42469;
        this.gia_ban_ph42469 = gia_ban_ph42469;
        this.mo_ta_ph42469 = mo_ta_ph42469;
        this.hinh_anh_ph42469 = hinh_anh_ph42469;
    }

    public XeMay(String ten_xe_ph42469, String mau_sac_ph42469, int gia_ban_ph42469, String mo_ta_ph42469) {
        this.ten_xe_ph42469 = ten_xe_ph42469;
        this.mau_sac_ph42469 = mau_sac_ph42469;
        this.gia_ban_ph42469 = gia_ban_ph42469;
        this.mo_ta_ph42469 = mo_ta_ph42469;
    }

    public XeMay() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen_xe_ph42469() {
        return ten_xe_ph42469;
    }

    public void setTen_xe_ph42469(String ten_xe_ph42469) {
        this.ten_xe_ph42469 = ten_xe_ph42469;
    }

    public String getMau_sac_ph42469() {
        return mau_sac_ph42469;
    }

    public void setMau_sac_ph42469(String mau_sac_ph42469) {
        this.mau_sac_ph42469 = mau_sac_ph42469;
    }

    public int getGia_ban_ph42469() {
        return gia_ban_ph42469;
    }

    public void setGia_ban_ph42469(int gia_ban_ph42469) {
        this.gia_ban_ph42469 = gia_ban_ph42469;
    }

    public String getMo_ta_ph42469() {
        return mo_ta_ph42469;
    }

    public void setMo_ta_ph42469(String mo_ta_ph42469) {
        this.mo_ta_ph42469 = mo_ta_ph42469;
    }

    public String getHinh_anh_ph42469() {
        return hinh_anh_ph42469;
    }

    public void setHinh_anh_ph42469(String hinh_anh_ph42469) {
        this.hinh_anh_ph42469 = hinh_anh_ph42469;
    }
}
