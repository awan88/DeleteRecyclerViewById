package awan.project.deleterecyclerviewbyid.detailpost

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import awan.project.deleterecyclerviewbyid.Api_interface.RetrofitArrayApi
import awan.project.deleterecyclerviewbyid.R
import awan.project.deleterecyclerviewbyid.helper.SharedPrefDetailBerita
import awan.project.deleterecyclerviewbyid.model.ModelAuthor
import awan.project.deleterecyclerviewbyid.model.WPAuthor
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_postdetails.*
import muria.news.app.actifity.berita.detailberita.author.AdapterAuthor
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class WPPostDetails: AppCompatActivity(){

    lateinit var ctx: Context
    lateinit var sharedPrefDetailBerita: SharedPrefDetailBerita
    lateinit var adapterAuthor: AdapterAuthor
    private var listmodelauthor : ArrayList<ModelAuthor>? = null
    lateinit var mLayoutManager: LinearLayoutManager

    lateinit var inputFormat: SimpleDateFormat
    lateinit var outputhariJson: SimpleDateFormat
    lateinit var outputDate: SimpleDateFormat
    lateinit var outputTime: SimpleDateFormat
    lateinit var df: SimpleDateFormat
    lateinit var current: Date
    var date: Date? = null

    private val baseUrl = "http://www.murianews.com/"

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postdetails)

        ctx = this
        sharedPrefDetailBerita = SharedPrefDetailBerita(this)

        tv_jududl.text = sharedPrefDetailBerita.spIdberita
        html_text.setHtml(sharedPrefDetailBerita.spIsiBerita, HtmlHttpImageGetter(html_text))
        Glide.with(this).load(sharedPrefDetailBerita.spFoto)
            .into(img_berita)

        val tanggalJSON = sharedPrefDetailBerita.spTanggal
        inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        outputhariJson = SimpleDateFormat("dd-MM-yyyy")
        outputDate = SimpleDateFormat("dd MMMM yyyy")
        outputTime = SimpleDateFormat("HH:mm")
        try {
            date = inputFormat.parse(tanggalJSON)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        current = Calendar.getInstance().time
        df = SimpleDateFormat("dd-MM-yyyy")
        val formattedHri = df.format(current)

        val formattedHariJs = outputhariJson.format(date)
        val formattedDate = outputDate.format(date)
        val formattedTime = outputTime.format(date)

        if (formattedHri.contains(formattedHariJs)) {
            tv_tanggal.text = "Hari ini $formattedTime"
        } else {
            tv_tanggal.text = "Update At $formattedDate"
        }

        loadAuthor()
    }
    private fun loadAuthor() {
        //progressbar_olahraga.visibility = View.VISIBLE
        listmodelauthor = ArrayList<ModelAuthor>()
        mLayoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_author.layoutManager = mLayoutManager
        adapterAuthor = AdapterAuthor(listmodelauthor, ctx)
        rv_author.adapter = adapterAuthor



        val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RetrofitArrayApi::class.java)
        val author = sharedPrefDetailBerita.author.toInt()
        val perpage = 4

        /*val hashMap : HashMap<String, String> = HashMap()
        hashMap["author[]"] = author*/
        service.getPostAuthor(author, perpage).enqueue(object : Callback<List<WPAuthor>> {
            override fun onResponse(call: Call<List<WPAuthor>>, response: Response<List<WPAuthor>>) {
                    for (i in response.body()!!.indices) {

                        val idberita = response.body()!![i].id
                        if(sharedPrefDetailBerita.spIdberita == idberita){
                            listmodelauthor?.remove(ModelAuthor(
                                idberita,
                                ModelAuthor.IMAGE_TYPE,
                                response.body()!![i].title.rendered,
                                response.body()!![i].excerpt.rendered.toString(),
                                response.body()!![i].content.rendered.toString(),
                                response.body()!![i].date,
                                response.body()!![i].betterFeaturedImage?.sourceUrl.toString(),
                                response.body()!![i].author.toString(),
                                response.body()!![i].tags[0].toString()

                            ))
                        }else{
                            listmodelauthor?.add(
                                ModelAuthor(
                                    idberita,
                                    ModelAuthor.IMAGE_TYPE,
                                    response.body()!![i].title.rendered,
                                    response.body()!![i].excerpt.rendered.toString(),
                                    response.body()!![i].content.rendered.toString(),
                                    response.body()!![i].date,
                                    response.body()!![i].betterFeaturedImage?.sourceUrl.toString(),
                                    response.body()!![i].author.toString(),
                                    response.body()!![i].tags[0].toString()
                                )
                            )
                        }



                        /*rv_author.post {
                            @Override
                            fun run() {
                                adapterAuthor.notifyDataSetChanged()
                            }
                        }*/
                        adapterAuthor.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<WPAuthor>>, t: Throwable) {

                //progressbar_olahraga.visibility = View.GONE
            }
        })

        /*if (!rv_author.isComputingLayout && rv_author.scrollState == SCROLL_STATE_IDLE) {
            rv_author.adapter?.notifyDataSetChanged()
        }*/

    }



    private fun parseDateToddMMyyyy2(time: String): String? {
        if (time.isEmpty() || time == "null") {
            return "-"
        } else {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val outputPattern = "d MMMM y, HH:mm"
            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat(inputPattern)
            @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat(outputPattern)

            var date: Date? = null
            var str: String? = null
            var finalStr: String? = null

            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
                finalStr = str.replace("-", " ")
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return finalStr
        }
    }
}

