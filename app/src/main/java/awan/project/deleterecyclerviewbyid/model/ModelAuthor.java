package awan.project.deleterecyclerviewbyid.model;


public class ModelAuthor {

    public static final int IMAGE_TYPE =1;
    public String idberita, title, subtitle, isiberita, tanggal, image, author, tags;
    public int type;

    public ModelAuthor(String idberita, int mtype, String mtitle, String msubtitle, String isiberita, String mTanggal, String image, String author, String tags  ){

        this.idberita = idberita;
        this.type = mtype;
        this.title = mtitle;
        this.subtitle = msubtitle;
        this.isiberita = isiberita;
        this.tanggal = mTanggal;
        this.image = image;
        this.author = author;
        this.tags = tags;
    }

}

