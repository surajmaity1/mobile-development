package com.surajmaity1.testapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.adapters.IndivUserItemAdapter
import com.surajmaity1.testapp.models.User

class IndividualFragment : Fragment() {

    private lateinit var list: ArrayList<User>
    private lateinit var adapter: IndivUserItemAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list = ArrayList()


        val data1 = User(
            "Michael Murphy", "San Francisco", 1, false,
            R.drawable.michael_murphy, 67, arrayListOf("Friendship", "Coffee","Hangout"), "Hi community! I am open to new connections."
        )
        val data2 = User(
            "John Doe", "San Francisco", 8, true,
            R.drawable.john_doe, 67, arrayListOf("Coffee", "Coding","Meetup"), "Going to Berkley, would love to share a ride with someone like minded."
        )
        val data3 = User(
            "Jennifer Ray", "San Francisco", 22, false,
            R.drawable.jennifer, 67, arrayListOf("Coding", "Python","Hackathons"), "Hi community! I am open to new connections."
        )


        list.add(data1)
        list.add(data2)
        list.add(data3)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.ind_rec_view)

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = IndivUserItemAdapter(requireContext(), list)
        recyclerView.adapter = adapter
    }
}