package com.antonchuraev.loader.MVP.Presenter

import com.antonchuraev.loader.MVP.Model.LoadModel
import com.antonchuraev.loader.MVP.ModelInterface
import com.antonchuraev.loader.MVP.PresenterInterface
import com.antonchuraev.loader.MVP.ViewInterface

class Presenter(viewInterface: ViewInterface):PresenterInterface {

    private val view: ViewInterface=viewInterface
    private val model: ModelInterface = LoadModel()


    override fun startLoadingUsersButtonClicked() {
        view.showLoadResult(model.loadUsers())
    }

    override fun startLoadingPosts() {
        view.showLoadResult(model.loadPosts())
    }

}