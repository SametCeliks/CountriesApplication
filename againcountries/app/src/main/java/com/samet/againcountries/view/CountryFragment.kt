package com.samet.againcountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.samet.againcountries.view.CountryFragmentArgs
import com.samet.againcountries.R
import com.samet.againcountries.databinding.FragmentCountryBinding
import com.samet.againcountries.model.Country
import com.samet.againcountries.util.placeHolderProgressBar
import com.samet.againcountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {
    private lateinit var viewModel:CountryViewModel
    private var countryUuid=0
    private lateinit var dataBinding : FragmentCountryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                dataBinding.selectedCountry=country

                /*
                 countryName.text=country.countryName
                countryCapital.text=country.countryCapital
                countryCurrency.text=country.countryCurrency
                countryLanguage.text=country.countryLanguage
                countryRegion.text=country.countryRegion
                context?.let {
                    countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                }
                 */


            }
        })
    }

}