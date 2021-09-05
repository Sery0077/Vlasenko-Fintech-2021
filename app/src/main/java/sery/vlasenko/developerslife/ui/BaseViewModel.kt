package sery.vlasenko.developerslife.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel: ViewModel() {
    protected val backgroundScope = CoroutineScope(Dispatchers.IO)
    protected val foregroundScope = CoroutineScope(Dispatchers.Main)
}