package awan.project.deleterecyclerviewbyid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import awan.project.deleterecyclerviewbyid.Api_interface.RetrofitArrayApi
import awan.project.deleterecyclerviewbyid.adapter.AdapterHome
import awan.project.deleterecyclerviewbyid.model.ModelAuthor
import awan.project.deleterecyclerviewbyid.model.ModelHome
import awan.project.deleterecyclerviewbyid.model.WPPost
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var ctx: Context
    lateinit var adapter: AdapterHome
    private var listModel:ArrayList<ModelHome>? = null
    var mListPost: List<WPPost>? = null
    lateinit var mLayoutManager: LinearLayoutManager

    private val baseUrl = "http://www.mywebbase.com/" //Add Url Website
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctx = this
        getNewpost()
    }

    fun getNewpost() {

        listModel = ArrayList<ModelHome>()
        //progressbarberita.visibility = View.VISIBLE
        mLayoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        recycler_view_post.layoutManager = mLayoutManager

        adapter = AdapterHome(listModel, ctx)
        recycler_view_post.adapter = adapter

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service =
            retrofit.create(RetrofitArrayApi::class.java)
        val call = service.postInfo

        call.enqueue(object : Callback<List<WPPost>> {
            override fun onResponse(call: Call<List<WPPost>>, response: Response<List<WPPost>>) {

                mListPost = response.body()
                //progressbarberita.setVisibility(View.GONE)
                for (i in response.body()!!.indices) {

                    var tempdetails = response.body()!![i].excerpt.rendered.toString()
                    var isiberita = response.body()!![i].content.rendered.toString()
                    tempdetails = tempdetails.replace("<p>", "")
                    tempdetails = tempdetails.replace("</p>", "")
                    tempdetails = tempdetails.replace("[&hellip;]", "")
                    val imagesss = response.body()!![i].betterFeaturedImage.sourceUrl.toString()
                    listModel?.add( ModelHome(
                        response.body()!![i].id,
                        ModelHome.IMAGE_TYPE,
                        response.body()!![i].title.rendered,
                        tempdetails,
                        isiberita,
                        response.body()!![i].date,
                        imagesss,
                        response.body()!![i].author.toString(),
                        response.body()!![i].tags[0].toString()
                    )
                    )
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<WPPost>>, t: Throwable) {
            }
        })
    }
}
