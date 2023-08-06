package com.surajmaity1.testapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.models.IndividualUser
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.StringBuilder

open class IndivUserItemAdapter (
    private val context: Context,
    private var list: ArrayList<IndividualUser>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.individual_user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        val invitePending = holder.itemView.findViewById<TextView>(R.id.inv_pend)
        var indUsrImg = holder.itemView.findViewById<ImageView>(R.id.ind_usr_img)
        val indUsrName = holder.itemView.findViewById<TextView>(R.id.ind_usr_nm)
        val indUsrAddKM = holder.itemView.findViewById<TextView>(R.id.ind_address_km)
        val indUsrPrgBr = holder.itemView.findViewById<ProgressBar>(R.id.simpleProgressBar)
        val indUsrHob = holder.itemView.findViewById<TextView>(R.id.ind_hobbies)
        val indUsrAbout = holder.itemView.findViewById<TextView>(R.id.ind_about)

        if (holder is MyViewHolder) {


            val resources = holder.itemView.resources

            if (model.invitePending ) {
                invitePending.text = resources.getString(R.string.pending)
            }
            else {
                invitePending.text = resources.getString(R.string.invite)
            }
            indUsrImg.setImageResource(model.img)
            indUsrName.text = model.name

            val addressKm = model.address + ", within " + model.km + " Km"
            indUsrAddKM.text = addressKm
            indUsrPrgBr.progress = model.progress

            val str = StringBuilder()

            for (item in model.hobby) {
                str.append("$item | ")

            }
            indUsrHob.text = str
            indUsrAbout.text = model.about


        }
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}