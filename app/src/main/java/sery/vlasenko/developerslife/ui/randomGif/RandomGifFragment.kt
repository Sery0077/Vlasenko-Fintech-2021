package sery.vlasenko.developerslife.ui.randomGif

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_page_fragment.*
import sery.vlasenko.developerslife.R
import sery.vlasenko.developerslife.ui.randomGif.adapter.GifPagerAdapter

class RandomGifFragment : Fragment(R.layout.item_page_fragment), GifPagerAdapter.ClickInterface {

    private val viewModel: RandomGifViewModel = RandomGifViewModel()

    private var gifsAdapter: GifPagerAdapter = GifPagerAdapter(this, viewModel.gifs)

    override fun onAttach(context: Context) {
        viewModel.onAttach()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerInit()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is BestGifState.LoadedState<*> -> {
                    onLoadedState(state)
                }
                is BestGifState.LoadingState -> {
                    onLoadingState()
                }
                is BestGifState.ErrorState -> {
                    onErrorState(state)
                }
            }
        })

        viewModel.currentPage.observe(viewLifecycleOwner, {
            gif_pager.setCurrentItem(it, true)

            back_btn.isEnabled = it != 0
            next_btn.isEnabled = viewModel.gifs[it]?.isLoaded ?: true
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        setClickers()
        super.onResume()
    }

    private fun setClickers() {
        next_btn.setOnClickListener {
            viewModel.onNextBtnClick()
        }

        back_btn.setOnClickListener {
            viewModel.onBackBtnClick()
        }
    }

    private fun pagerInit() {
        gif_pager.adapter = gifsAdapter
        gif_pager.isUserInputEnabled = false
    }

    private fun onLoadedState(data: BestGifState.LoadedState<*>) {
        next_btn.isEnabled = true
        viewModel.currentPage.value?.let {
            gifsAdapter.notifyDataSetChanged()
        }
    }

    private fun onLoadingState() {

    }

    private fun onErrorState(error: BestGifState.ErrorState) {
        viewModel.currentPage.value?.let {
            gifsAdapter.notifyItemChanged(it)
        }
        next_btn.isEnabled = false
    }

    override fun onErrorData(pos: Int) {
        viewModel.onErrorDataClick(pos)
    }
}