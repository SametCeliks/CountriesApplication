package com.samet.againcountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samet.againcountries.R
import com.samet.againcountries.adapter.CountryAdapter
import com.samet.againcountries.databinding.FragmentFeedBinding
import com.samet.againcountries.model.Country

import com.samet.againcountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    private lateinit var dataBinding : FragmentFeedBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        dataBinding.countryList.layoutManager = LinearLayoutManager(context)
        dataBinding.countryList.adapter = countryAdapter



        dataBinding.swipeRefrehLayout.setOnRefreshListener{
            dataBinding.countryList.visibility=View.GONE
            dataBinding.countryError.visibility=View.GONE
            dataBinding.countryLoading.visibility=View.VISIBLE

            viewModel.refreshFromAPI()
            dataBinding.swipeRefrehLayout.isRefreshing=false
        }

        observeLiveData()



        }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
        countries?.let {
            dataBinding.countryList.visibility=View.VISIBLE
            //countryList.visibility=View.VISIBLE
            countryAdapter.updateCountryList(countries)
        }

        })


        viewModel.countryError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if (it){
                    dataBinding.countryError.visibility=View.VISIBLE
                }else{
                    dataBinding.countryError.visibility=View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    dataBinding.countryLoading.visibility=View.VISIBLE
                    dataBinding.countryList.visibility=View.GONE
                    dataBinding.countryError.visibility=View.GONE
                }else{
                    dataBinding.countryLoading.visibility=View.GONE
                }
            }
        })

    }

    }


