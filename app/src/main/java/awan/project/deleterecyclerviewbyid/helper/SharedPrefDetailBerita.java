package awan.project.deleterecyclerviewbyid.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefDetailBerita {

    public static final String SP_IDBERITA = "spIdberita";
    public static final String SP_FOTO = "spFoto";
    public static final String SP_JUDUL = "spJudul";
    public static final String SP_Tanggal = "spTanggal";
    public static final String SP_ISI_BERITA = "spIsiBerita";
    public static final String SP_Author = "spAuthor";
    public static final String SP_Tags = "spTags";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private static final String SP_DATA_APP = "spData";

    @SuppressLint("CommitPrefEdits")
    public SharedPrefDetailBerita(Context context) {
        sp = context.getSharedPreferences(SP_DATA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();

    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public String getSPIdberita() {
        return sp.getString(SP_IDBERITA, "");
    }
    public String getSPFoto() {
        return sp.getString(SP_FOTO, "");
    }
    public String getSpJudul() {
        return sp.getString(SP_JUDUL, "");
    }
    public String getSpTanggal() {
        return sp.getString(SP_Tanggal, "");
    }
    public String getSpIsiBerita() {
        return sp.getString(SP_ISI_BERITA, "");
    }
    public String getAuthor() {
        return sp.getString(SP_Author, "");
    }
    public String getTags() {
        return sp.getString(SP_Tags, "");
    }
}
