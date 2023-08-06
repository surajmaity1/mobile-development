package com.surajmaity1.testapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.models.User
import java.lang.StringBuilder

class MerchUserItemAdapter  (
    private val context: Context,
    private var list: ArrayList<User>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.merchant_user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        val profInvitePending = holder.itemView.findViewById<TextView>(R.id.inv_pend_mer)
        val profUsrImg = holder.itemView.findViewById<ImageView>(R.id.mer_usr_img)
        val profUsrName = holder.itemView.findViewById<TextView>(R.id.mer_usr_nm)
        val profUsrAddKM = holder.itemView.findViewById<TextView>(R.id.mer_address_km)
        val profUsrPrgBr = holder.itemView.findViewById<ProgressBar>(R.id.mer_progress_bar)
        val profUsrHob = holder.itemView.findViewById<TextView>(R.id.mer_exp)
        val profUsrAbout = holder.itemView.findViewById<TextView>(R.id.mer_about)

        if (holder is MyViewHolder) {


            val resources = holder.itemView.resources

            if (model.invitePending ) {
                profInvitePending.text = resources.getString(R.string.pending)
            }
            else {
                profInvitePending.text = resources.getString(R.string.invite)
            }
            profUsrImg.setImageResource(model.img)
            profUsrName.text = model.name

            val addressKm = model.address + ", within " + model.km + " Km"
            profUsrAddKM.text = addressKm
            profUsrPrgBr.progress = model.progress

            val str = StringBuilder()

            for (item in model.hobby) {
                str.append("$item | ")

            }
            profUsrHob.text = str
            profUsrAbout.text = model.about


        }
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}