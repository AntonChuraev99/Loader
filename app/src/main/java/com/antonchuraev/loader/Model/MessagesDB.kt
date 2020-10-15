package com.antonchuraev.loader.Model

class MessagesDB(list: List<MessageModel>) {

    val listMessage = list

    fun getAllMessagesById(id:Int): MutableList<MessageModel> {
        var retList = mutableListOf<MessageModel>()

        for (message in listMessage){
            if (message.userId==id){
                retList.add(message)
            }
        }

        return retList;
    }

}