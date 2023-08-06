package com.surajmaity1.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.adapters.MerchUserItemAdapter
import com.surajmaity1.testapp.adapters.ProfUserItemAdapter
import com.surajmaity1.testapp.models.User

class MerchantFragment : Fragment() {

    private lateinit var list: ArrayList<User>
    private lateinit var adapter: MerchUserItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list = ArrayList()


        val data1 = User(
            "ITC Mahal", "Kolkata", 7, false,
            R.drawable.itc, 82, arrayListOf("x", "y","z"), "B2B solution within custom requirements"
        )
        val data2 = User(
            "Jaex Palace", "Delhi", 12, false,
            R.drawable.palace, 79, arrayListOf("a", "b","c"), "Best Palace to stay in Delhi"
        )
        val data3 = User(
            "Sazy Pizz", "San Francisco", 19, true,
            R.drawable.sapizz, 47, arrayListOf("d", "e","f"), "IT solution services for troubleshooting"
        )


        list.add(data1)
        list.add(data2)
        list.add(data3)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.mer_rec_view)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MerchUserItemAdapter(requireContext(), list)
        recyclerView.adapter = adapter
    }
}