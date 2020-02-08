package muria.news.app.actifity.berita.detailberita.author

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import awan.project.deleterecyclerviewbyid.R
import awan.project.deleterecyclerviewbyid.helper.SharedPrefDetailBerita
import awan.project.deleterecyclerviewbyid.model.ModelAuthor
import com.bumptech.glide.Glide
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AdapterAuthor (var item: ArrayList<ModelAuthor>?, val ctx: Context)
    : RecyclerView.Adapter<AdapterAuthor.ViewHolder>(){


    lateinit var sharedPrefDetailBerita : SharedPrefDetailBerita

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapterhome, parent, false)

        sharedPrefDetailBerita = SharedPrefDetailBerita(parent.context)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return item!!.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.item?.get(position)

        val id_data = data!!.idberita

        if(sharedPrefDetailBerita.spIdberita == id_data)
        {
            //deletedata(data)
            //removeItem(data)
            //holder.itemView.visibility = GONE
        }/*else{
            *//*item!!.add(data)*//*
            //notifyDataSetChanged()
            //holder.itemView.visibility = VISIBLE
        }*/

        Glide.with(ctx).load(data.image).into(holder.img)
        holder.judul.text = data.idberita
        holder.subjudul.setHtml(data.subtitle,  HtmlHttpImageGetter(holder.subjudul))

        val tgl = data.tanggal
        rawSimpleDate(tgl)

        val date = Calendar.getInstance().time
        val form = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        val formatted = form.format(date)

        if (rawSimpleDate(tgl).contains(formatted)){
            holder.tanggal.text = "Hari ini " + rawSimpleTime(tgl)
        }else{
            holder.tanggal.text = "Update At "+ rawSimpleDate(tgl)
        }

        holder.detail.setOnClickListener {
            val img = data.image
            val judul = data.title
            val isiberita = data.isiberita
            val tanggal = data.tanggal

            sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_FOTO, img)
            sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_JUDUL, judul)
            sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_ISI_BERITA, isiberita)
            sharedPrefDetailBerita.saveSPString(SharedPrefDetailBerita.SP_Tanggal, tanggal)

            removeItem(data)
        }
    }


    private fun removeItem( infoData: ModelAuthor?) {
        val currPosition = this.item!!.indexOf(infoData)
        item!!.removeAt(currPosition)
        notifyItemRemoved(currPosition)
        //notifyItemRangeChanged(currPosition,item!!.size)
    }

    private fun deletedata(infoData: ModelAuthor?){

        item!!.removeAt(getAdapterPosition())
        notifyItemRemoved(getAdapterPosition())
        notifyItemRangeChanged(getAdapterPosition(), item!!.size)
    }

    private fun getAdapterPosition(): Int {
        return item!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detail : LinearLayout = view.findViewById(R.id.btn_clic)
        val img: ImageView = view.findViewById(R.id.Icon)
        val judul: TextView = view.findViewById(R.id.title)
        val subjudul: HtmlTextView = view.findViewById(R.id.wb_subtitle)
        val tanggal: TextView = view.findViewById(R.id.tanggal)
    }

    @SuppressLint("SimpleDateFormat")
    private fun rawSimpleTime(timeRaw: String?): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var time: Date? = null
        try {
            time = formatter.parse(timeRaw)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (time != null) {
            val formatter_show = SimpleDateFormat("HH:mm")
            return formatter_show.format(time)
        }

        return ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun rawSimpleDate(dateRaw: String?): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date? = null

        try {
            date = formatter.parse(dateRaw)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            val formatter_show = SimpleDateFormat("dd MMMM yyyy")
            return formatter_show.format(date)
        }

        return ""
    }

}