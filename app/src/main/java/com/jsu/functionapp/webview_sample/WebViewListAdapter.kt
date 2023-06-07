package com.jsu.functionapp.webview_sample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsu.functionapp.databinding.ListviewWebviewBinding


class WebViewListAdapter(
    private var context: Context,
    private var arrayUrl: ArrayList<String>,
    private val handler : (url: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val listBinding = ListviewWebviewBinding.inflate(LayoutInflater.from(context),parent,false)

        return WebViewListHolder(listBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is WebViewListHolder -> {

                holder.tvUrl.text = arrayUrl[position]

                holder.view.setOnClickListener { handler.invoke(arrayUrl[position]) }

            }
        }

    }

    override fun getItemCount(): Int = arrayUrl.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class WebViewListHolder(itemView: ListviewWebviewBinding) : RecyclerView.ViewHolder(itemView.root) {

        var view  = itemView.root
        var tvUrl = itemView.tvUrl


    }


}

