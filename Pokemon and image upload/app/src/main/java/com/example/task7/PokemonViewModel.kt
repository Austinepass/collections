package com.example.task7

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task7.model.Result
import com.example.task7.model.TypeX
import com.example.task7.model.pokemon.*
import com.example.task7.model.pokemon.Stat

class PokemonViewModel : ViewModel() {

    /**
     * Viewmodel for observing the various lists from the endpoints
     */

    var list = ArrayList<Result>()
    var abilityList = ArrayList<AbilityX>()
    var moveList = ArrayList<MoveX>()
    var statList = ArrayList<Pair<StatX, Int>>()
    var typeList = ArrayList<TypeX>()


    var resultsLive: MutableLiveData<MutableList<Result>> = MutableLiveData()
    var abilityLive: MutableLiveData<MutableList<AbilityX>> = MutableLiveData()
    var moveLive: MutableLiveData<MutableList<MoveX>> = MutableLiveData()
    var statLive: MutableLiveData<MutableList<Pair<StatX, Int>>> = MutableLiveData()
    var pokeTypeLive : MutableLiveData<MutableList<TypeX>> = MutableLiveData()

    fun addPictureToList(result: Result) {
        list.add(result)
        resultsLive.postValue(list)
    }

    fun addAbilityToList(ability: AbilityX) {
        abilityList.add(ability)
        abilityLive.postValue(abilityList)
    }


    fun addMoveToList(move: MoveX) {
        moveList.add(move)
        moveLive.postValue(moveList)
    }


    fun addStatToList(stat: StatX, baseStat: Int) {
        statList.add(Pair(stat, baseStat))
        statLive.postValue(statList)
    }

    fun addType(typeX: TypeX){
        typeList.add(typeX)
        pokeTypeLive.postValue(typeList)
    }
}

