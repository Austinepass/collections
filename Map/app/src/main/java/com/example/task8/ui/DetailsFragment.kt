package com.example.task8.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.task8.R
import com.example.task8.databinding.FragmentDetailsBinding
import com.example.task8.model.AbilityX
import com.example.task8.model.MoveX
import com.example.task8.model.StatX
import com.example.task8.model.TypeX
import com.example.task8.network.PokemonAPI
import com.example.task8.network.RetrofitClient
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsFragment : Fragment() {


    var detailsFragmentBinding: FragmentDetailsBinding? = null
    lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    lateinit var id: String
    var position = 0
    var weight = 0
    lateinit var name: String
    private val disposable = CompositeDisposable()
    lateinit var api: PokemonAPI

    private var abilityList = mutableListOf<AbilityX>()
    var moveList = mutableListOf<MoveX>()
    var statList = mutableListOf<Pair<StatX, Int>>()
    var typeList = mutableListOf<TypeX>()


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
        binding.pokeId.text = args.id
        binding.nameTitle.text = args.name
        val retrofit = RetrofitClient.instance
        api = retrofit.create(PokemonAPI::class.java)
        makeAPIRequest()

        return binding.root
    }


    /**
     * Function for making network request and adding the results in their respective
     * lists using RxJava's [CompositeDisposable]
     */
    private fun makeAPIRequest() {
        disposable.add(
            api.getPokemonDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (i in it.abilities) {
                        abilityList.add(i.ability)
                    }

                    for (i in it.moves) {
                        moveList.add(i.move)
                    }

                    for (i in it.stats) {
                        statList.add(Pair(i.stat, i.base_stat))
                    }

                    for (i in it.types) {
                        typeList.add(i.type)
                    }

                    weight = it.weight
                    setUpDetails()

                },
                    {
                        Snackbar.make(requireView(), "Failed to load with Error: ${it.localizedMessage}", Snackbar.LENGTH_LONG)
                            .show()
                    })
        )


    }

    /**
     * Helper function to bind the details to their respective views
     */

    private fun setUpDetails() {
        Glide.with(requireActivity())
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png")
            .placeholder(R.drawable.loading_animation)
            .into(binding.imageView2)

        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png")
            .placeholder(R.drawable.loading_animation)
            .into(binding.imageView3)

        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png")
            .placeholder(R.drawable.loading_animation)
            .into(binding.imageView4)

        binding.weight.text = weight.toString()


        var abt = ""
        var mov = ""
        var stat = ""
        var typ = ""
        for (i in abilityList.indices) {
            abt += if (i == abilityList.lastIndex) {
                abilityList[i].name
            } else {
                "${abilityList[i].name}, "
            }
        }
        binding.ability.text = abt
        for (i in moveList.indices) {
            mov += if (i == moveList.lastIndex) {
                moveList[i].name
            } else {
                "${moveList[i].name}, "
            }
        }
        binding.moves.text = mov

        for (i in statList) {
            stat += " ${i.first.name}: ${i.second}"
        }
        binding.stats.text = stat

        for (i in typeList.indices) {
            typ += if (i == typeList.lastIndex) {
                typeList[i].name
            } else {
                "${typeList[i].name}, "
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