package ru.easycode.zerotoheroandroidtdd.repository

import java.io.Serializable

data class ItemUi(
    val id: Long,
    val text: String,
) : Serializable {
    fun areItemsSame(item: ItemUi): Boolean {
        return this.id == item.id
    }
}