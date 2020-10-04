package com.example.visiontranslator.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.main

class EpoxyControllerMain : TypedEpoxyController<String>(){
    override fun buildModels(data: String?) {
        for(i in 0..30){
            main {
                id(modelCountBuiltSoFar)
                title("$i: Epoxy RecyclerView Epoxy RecyclerView Epoxy RecyclerView")
            }
        }
    }

}