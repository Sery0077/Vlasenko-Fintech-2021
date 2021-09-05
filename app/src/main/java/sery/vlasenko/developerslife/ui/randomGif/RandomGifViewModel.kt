package sery.vlasenko.developerslife.ui.randomGif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import sery.vlasenko.developerslife.App
import sery.vlasenko.developerslife.data.RandomGifDAO
import sery.vlasenko.developerslife.data.repository.RandomGifRepository
import sery.vlasenko.developerslife.ui.BaseViewModel
import sery.vlasenko.developerslife.utils.dec
import sery.vlasenko.developerslife.utils.inc
import javax.inject.Inject

class RandomGifViewModel : BaseViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: RandomGifRepository

    var gifs: ArrayList<RandomGif?> = arrayListOf(null, null)

    private val _state: MutableLiveData<BestGifState> = MutableLiveData(BestGifState.LoadingState())
    val state: LiveData<BestGifState> = _state

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage

    private fun getGif(position: Int? = null) {
        _state.value = BestGifState.LoadingState()

        backgroundScope.launch {
            val response = repository.getRandomGif()

            if (response.data != null) {
                if (position == null) {
                    gifs[gifs.lastIndex - 1] = RandomGif(response.data, true)
                } else {
                    gifs[position] = RandomGif(response.data, true)
                }

                _state.postValue(BestGifState.LoadedState(response.data))
            } else {
                if (position == null) {
                    gifs[gifs.lastIndex - 1] = RandomGif(null, false)
                } else {
                    gifs[position] = RandomGif(null, false)
                }

                _state.postValue(BestGifState.ErrorState(response.error))
            }
        }
    }

    fun onAttach() {
        getGif()
    }

    fun onNextBtnClick() {
        _currentPage.inc()

        _currentPage.value?.let {
            if (gifs[it] == null) {
                gifs.add(null)
                getGif()
            }
        }
    }

    fun onBackBtnClick() {
        _currentPage.dec()

        _currentPage.value?.let {
            if (gifs[it] == null) {
                getGif(it)
            }
        }
    }

    fun onErrorDataClick(pos: Int) {
        getGif(pos)
    }
}

sealed class BestGifState {
    class LoadingState : BestGifState()
    class LoadedState<T>(val data: T) : BestGifState()
    class ErrorState(val message: Any?) : BestGifState()
}

data class RandomGif(
    val gif: RandomGifDAO?,
    var isLoaded: Boolean? = false
)