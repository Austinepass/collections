package com.example.task8.util


/**
 * A simple algorithm for extracting the id from the url so I can perform other
 * GET requests with it.
 */
fun getId(url:String): String{
    var id = ""
    for(i in 1..url.length ){
        if (i == url.lastIndex){
            break
        }
        if(url[i - 1] != 'v' && url[i].isDigit()){
            id += "${url[i]}"
        }
    }

    return id
}
