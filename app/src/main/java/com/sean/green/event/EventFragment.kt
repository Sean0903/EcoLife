package com.sean.green.event

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sean.green.MainActivity
import com.sean.green.R
import com.sean.green.data.Event
import com.sean.green.databinding.FragmentEventBinding

class EventFragment: Fragment() {

    private lateinit var binding : FragmentEventBinding
    private val viewModel : EventViewModel by lazy {
        ViewModelProvider(this).get(EventViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = EventAdapter()
        binding.recyclerViewEvent.adapter = adapter

        val mock3 = Event("Sean","淨灘","2021.5.18","宜蘭烏石港","之前去宜蘭玩的時候，看到沙灘的垃圾好多，我們一起去淨灘吧。","6")
        val mock4 = Event("Scolley","消毒","2021.5.20","台北市萬華區茶藝館","大夥們帶上自己家裡的漂白水，我們去萬華!!","100")
        val mock5 = Event("Jake","搶疫苗","2021.5.30","桃園市機場第二航廈","機密行動，疫苗將抵達台灣，快搶!","0")
        val mockList1 = listOf( mock3, mock4,mock5)

        adapter.submitList(mockList1)

        binding.fab.setOnClickListener {

            var dialogEvent = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_event, null)
            dialogEvent.setContentView(view)
            dialogEvent.show()

        }

        (activity as MainActivity).dismissFabButton(false)

        return binding.root
    }
}