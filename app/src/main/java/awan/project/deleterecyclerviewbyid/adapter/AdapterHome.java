package awan.project.deleterecyclerviewbyid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import awan.project.deleterecyclerviewbyid.R;
import awan.project.deleterecyclerviewbyid.detailpost.WPPostDetails;
import awan.project.deleterecyclerviewbyid.helper.SharedPrefDetailBerita;
import awan.project.deleterecyclerviewbyid.model.ModelHome;

public class AdapterHome extends RecyclerView.Adapter {

    private ArrayList<ModelHome> dataset;
    private Context mContext;
    SharedPrefDetailBerita sharedPrefDetailBerita;

    public AdapterHome(ArrayList<ModelHome> mlist, Context context) {
        this.dataset = mlist;
        this.mContext = context;
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder{
        TextView judul, tangaal;
        HtmlTextView subjudul;
        ImageView imageView;
        LinearLayout cardbase, btn_click;
        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.cardbase = itemView.findViewById(R.id.linearLayoutNewpost);
            this.btn_click = itemView.findViewById(R.id.btn_clic);
            this.judul = itemView.findViewById(R.id.title);
            this.subjudul = itemView.findViewById(R.id.wb_subtitle);
            this.tangaal = itemView.findViewById(R.id.tanggal);
            this.imageView = itemView.findViewById(R.id.Icon);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.adapterhome, parent, false);
        sharedPrefDetailBerita = new SharedPrefDetailBerita(parent.getContext());
        return new ImageTypeViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ModelHome object = dataset.get(position);

        if (position == 0){
            ( (ImageTypeViewHolder) holder).itemView.setVisibility(View.GONE);
        }else{
            ( (ImageTypeViewHolder) holder).itemView.setVisibility(View.VISIBLE);
        }

        int hitam = Color.parseColor("#3D3939");
        int putih = Color.parseColor("#FFFFFF");

        ImageView img = ((ImageTypeViewHolder) holder).imageView;
        Glide.with(mContext)
                .load(object.Image)
                .into(img);
        ( (ImageTypeViewHolder) holder).judul.setText( object.title );
        ( (ImageTypeViewHolder) holder).subjudul.setHtml(object.subtitle, new HtmlHttpImageGetter(((ImageTypeViewHolder) holder).subjudul));

        String tanggal = object.tanggal;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputhariJson = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = inputFormat.parse(tanggal);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedHri = df.format(current);

        String formattedHariJs = outputhariJson.format(date);
        String formattedDate = outputDate.format(date);
        String formattedTime = outputTime.format(date);

        if(formattedHri.contains(formattedHariJs)){
            ( (ImageTypeViewHolder) holder).tangaal.setText( "Hari ini "+ formattedTime );
        }
        else {
            ( (ImageTypeViewHolder) holder).tangaal.setText( "Update At " + formattedDate );
        }
        ( (ImageTypeViewHolder) holder).btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = object.idbrita;
                String foto = object.Image;
                String judul = object.title;
                String tanggal = object.tanggal;
                String subJududl = object.isiberita;
                String author = object.author;
                String tags = object.tags;

                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_IDBERITA, id);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_FOTO, foto);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_JUDUL, judul);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_Tanggal, tanggal);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_ISI_BERITA, subJududl);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_Author, author);
                sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_Tags, tags);

                Intent intent = new Intent(mContext, WPPostDetails.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size() ;
    }
}
