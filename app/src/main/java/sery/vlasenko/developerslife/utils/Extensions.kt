package sery.vlasenko.developerslife.utils

import android.view.View
import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Int>.inc() = apply { value = value?.inc() }
fun MutableLiveData<Int>.dec() = apply { value = value?.dec() }
fun View.gone() = apply { visibility = View.GONE }
fun View.visible() = apply { visibility = View.VISIBLE }
fun View.invisible() = apply { visibility = View.INVISIBLE }