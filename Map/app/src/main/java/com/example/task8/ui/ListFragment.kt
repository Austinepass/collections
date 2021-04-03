package com.example.task7.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task8.model.Result
import com.example.task8.R
import com.example.task8.network.PokemonAPI
import com.example.task8.network.RetrofitClient
import com.example.task8.databinding.FragmentListBinding
import com.example.task8.model.PokemonJson
import com.example.task8.util.PokemonAdapter
import com.example.task8.util.getId
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListFragment : Fragment(), PokemonAdapter.OnItemClickListener {
    private var fragmentListBinding: FragmentListBinding? = null
    lateinit var binding: FragmentListBinding
    private val disposable = CompositeDisposable()
    var limit = 50

    lateinit var api: PokemonAPI
    private var myPokes =  listOf<Result>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        fragmentListBinding = null

        /**
         * Instantiate my [RetrofitClient]
         */
        val retrofit = RetrofitClient.instance
        api = retrofit.create(PokemonAPI::class.java)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        makeAPIRequest()


        /**
         * Set the limit from the Limit [EditText] and make an API request
         */
            binding.limitButton.setOnClickListener {
                if (binding.limitText.text.isEmpty()) {
                   binding.limitText.error = "Limit can't be empty"
                } else {
                    binding.limitText.inputType = InputType.TYPE_NULL
                    limit = binding.limitText.text.toString().toInt()
                    makeAPIRequest()
                }

        }

        return (binding.root)
    }

    /**
     * Function for making network call using RxJava's [Disposable]
     */
    private fun makeAPIRequest() {

        disposable
            .add(api.getPoke(limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                myPokes = it.results
                displayItem(it)

            },
                {
                    Snackbar.make(requireView(), "Failed to load with Error: ${it.localizedMessage}", Snackbar.LENGTH_LONG).show()
                    Log.e("Error", it.localizedMessage)
                })
        )

    }

    /**
     * Function to set up the recyclerview's adapter
     */
    private fun displayItem(item: PokemonJson) {
        binding.recyclerView.adapter = PokemonAdapter(item.results as ArrayList<Result>, this, requireContext())

    }




    //Clean up the ViewBinding instance
    override fun onDestroyView() {
        super.onDestroyView()
        fragmentListBinding = null
    }

    /**
     * Handles click events on my list and passes the id and name to the [DetailsFragment]
     * using the NavController
     */
    override fun onItemClick(position: Int) {
        var id = getId(myPokes[position].url)
        var name = myPokes[position].name
        val action = ListFragmentDirections.actionPokemonListFragmentToDetailsFragment(id,position, name)
        findNavController().navigate(action)

    }

}