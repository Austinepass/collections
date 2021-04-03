package com.example.task7.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task7.PokemonAdapter
import com.example.task7.PokemonViewModel
import com.example.task7.R
import com.example.task7.api.api
import com.example.task7.databinding.FragmentListBinding
import com.example.task7.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.task7.util.getId

class ListFragment : Fragment(), PokemonAdapter.OnItemClickListener {
    private var fragmentListBinding: FragmentListBinding? = null
    lateinit var binding: FragmentListBinding
    private var myPokes =  mutableListOf<Result>()
    lateinit var viewModel: PokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)
        fragmentListBinding = null

        /**
         * Instantiate my [PokemonViewModel] with the [ViewModelProvider] method
         */
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        makeAPIRequest()

        return (binding.root)
    }

    /**
     * Function for making network call using coroutine's GlobalScopee
     */
    private fun makeAPIRequest() {


        GlobalScope.launch(Dispatchers.IO){

            /**
            *When there is no internet, this try - catch block will prevent the app from crashing
             */
            try{
               val call =  api.getPoke()


                for(item in call.results){

                    viewModel.addPictureToList(item)
                    Log.i("Main", item.name)

                }

                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            }catch (ex : Exception){
                Log.e("MainActivity", ex.toString())

            }
        }
    }

    fun setLimit(){

    }

    /**
     * Function to set up the recyclerview's adapter and layoutManager
     */
    private fun setUpRecyclerView() {
        viewModel.resultsLive.observe(viewLifecycleOwner, Observer {
        binding.recyclerView.adapter =  PokemonAdapter(it as ArrayList<Result>,this, requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        })
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
        var id = getId(viewModel.list[position].url)
        var name = viewModel.list[position].name
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(id,position, name)
        findNavController().navigate(action)

    }

}