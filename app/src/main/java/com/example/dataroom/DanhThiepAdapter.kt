package com.example.myapplicationaaaaaaa

import android.accounts.AccountManager.get
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dataroom.DanhThiep
import com.example.dataroom.R
import com.squareup.picasso.Picasso
import java.security.Permission
import java.util.ArrayList
import java.util.jar.Manifest

class DanhThiepAdapter(var mContext: Context) :
    RecyclerView.Adapter<DanhThiepAdapter.ViewHolder>() {

    private var list = ArrayList<DanhThiep>()
    fun setList(list: ArrayList<DanhThiep>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_Ten)
        val age: TextView = itemView.findViewById(R.id.tv_Tuoi)
        var avt : ImageView = itemView.findViewById(R.id.avttttt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].mName
        holder.age.text = list[position].mAge
      //  Picasso.with(mContext).load(list[position].img).into(holder.avt)
        holder.avt.setImageResource(0)
        Log.d("luu", "adapter"+list[position].img)
        holder.avt.setImageURI(Uri.parse(list[position].img))
        holder.avt.setOnClickListener(){
            Toast.makeText(mContext, list[position].img, Toast.LENGTH_LONG).show()
        }
    }
}