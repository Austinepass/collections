package com.example.task7.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.task7.PokemonViewModel
import com.example.task7.R
import com.example.task7.api.api
import com.example.task7.databinding.FragmentDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment() {
    var detailsFragmentBinding : FragmentDetailsBinding? = null
    lateinit var binding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()
    lateinit var id: String
    var position = 0
    var weight = 0
    lateinit var name: String
    private lateinit var viewModel: PokemonViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.bind(view)
        detailsFragmentBinding = binding
        id = args.id
        position = args.position


        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        binding.pokeId.text = args.id
        binding.nameTitle.text = args.name
        makeAPIRequest()

        return binding.root
    }


    /**
     * Function for making network request and adding the results in their respective
     * lists using Coroutines GlobalScope
     */
    private fun makeAPIRequest() {

        GlobalScope.launch(Dispatchers.IO){

            //When there is no internet, the try block will save us
            try{

                val response = api.getPokemonDetails(id)

                for(item in response.abilities) {

                    viewModel.addAbilityToList(item.ability)
                    // Log.i("Poke", "${item.ability}")

                }

                for(item in response.moves) {

                    viewModel.addMoveToList(item.move)
                    //Log.i("Poke", "${item.move}")
                }

                for (item in response.stats) {

                    viewModel.addStatToList(item.stat, item.base_stat)
                    Log.i("Poke", item.stat.name)
                    Log.i("Poke", "${item.base_stat}")
                }

                for (item in response.types) {
                    viewModel.addType(item.type)
                }


                binding.weight.text = "${response.weight} kg"
                Log.i("Poke", response.weight.toString())

                withContext(Dispatchers.Main) {
                    setUpDetails()
                }
            }catch (ex : Exception){
                Log.e("MainActivity", ex.toString())

            }
        }
    }

    /**
     * Helper function to bind the details to their respective views
     */
    fun setUpDetails() {
        Glide.with(requireActivity())
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png")
            .into(binding.imageView2)

        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png")
            .into(binding.imageView3)
        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png")
            .into(binding.imageView4)


        var abt = ""
        var mov = ""
        var stat = ""
        var typ = ""
        for(i in viewModel.abilityList.indices) {
            abt += if (i == viewModel.abilityList.lastIndex) {
                viewModel.abilityList[i].name
            } else {
                "${viewModel.abilityList[i].name}, "
            }
        }
        binding.ability.text = abt
        for(i in viewModel.moveList.indices){
            mov += if(i == viewModel.moveList.lastIndex){
                viewModel.moveList[i].name
            }else{
                "${viewModel.moveList[i].name}, "
            }
        }
        binding.moves.text = mov

        for(i in viewModel.statList) {
           stat += " ${i.first.name}: ${i.second}"
        }
        binding.stats.text = stat

        for (i in viewModel.typeList.indices) {
            typ += if(i == viewModel.typeList.lastIndex){
                viewModel.typeList[i].name
            }else{
                "${viewModel.typeList[i].name}, "
            }
        }
        binding.type.text = typ

    }

    //Release the view binding
    override fun onDestroyView() {
        super.onDestroyView()
        detailsFragmentBinding = null
    }
}