package com.averysync.voicerecorder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.averysync.voicerecorder.R
import com.averysync.voicerecorder.database.VoiceRecordDB
import com.averysync.voicerecorder.listeners.OnRecordItemClickListener
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale
class RecordListAdapter(
    var records : ArrayList<VoiceRecordDB>,
    var onRecordItemClickListener: OnRecordItemClickListener
)
    : RecyclerView.Adapter<RecordListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var mainName: TextView = itemView.findViewById(R.id.rec_file_main_name)
        var subName: TextView = itemView.findViewById(R.id.rec_file_sub_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                onRecordItemClickListener.onRecordItemClickListener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.voice_rec_itm, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position != RecyclerView.NO_POSITION){
            val record = records[position]

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = Date(record.timestamp)
            val strDate = sdf.format(date)

            holder.mainName.text = record.filename
            holder.subName.text = "${record.duration } $strDate"

        }
    }

    override fun getItemCount() = records.size
}