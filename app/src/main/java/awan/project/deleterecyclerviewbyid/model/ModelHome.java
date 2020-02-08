package awan.project.deleterecyclerviewbyid.model;


public class ModelHome {

    public static final int IMAGE_TYPE =1;
    public String idbrita, title, subtitle, isiberita, tanggal, Image, author, tags;
    public int type;


    public ModelHome(String idberita, int mtype, String mtitle, String msubtitle, String isiberita, String mTanggal, String image, String author, String tags  ){
        this.idbrita = idberita;
        this.title = mtitle;
        this.subtitle = msubtitle;
        this.isiberita = isiberita;
        this.tanggal = mTanggal;
        this.type = mtype;
        this.Image = image;
        this.author = author;
        this.tags = tags;
    }

}

