package com.surajmaity1.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.adapters.ProfUserItemAdapter
import com.surajmaity1.testapp.models.User

class ProfessionalFragment : Fragment() {

    private lateinit var list: ArrayList<User>
    private lateinit var adapter: ProfUserItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_professional, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list = ArrayList()


        val data1 = User(
            "Rahul Roy", "San Francisco", 4, true,
            R.drawable.rahul_roy, 67, arrayListOf("Java", "Kotlin","SQL"), "Industry Hands-on 4 years experience"
        )
        val data2 = User(
            "Kate Dey", "San Francisco", 21, false,
            R.drawable.kate_dey, 30, arrayListOf("Business", "PowerBI","Analytics"), "Own Startup with 10 year experience in business"
        )
        val data3 = User(
            "Jeff Bando", "San Francisco", 13, true,
            R.drawable.jeff_bando, 84, arrayListOf("Linux", "Kubernetes","Go"), "Senior DevOps Engineer with 20 year experience"
        )


        list.add(data1)
        list.add(data2)
        list.add(data3)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.prof_rec_view)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ProfUserItemAdapter(requireContext(), list)
        recyclerView.adapter = adapter
    }

}