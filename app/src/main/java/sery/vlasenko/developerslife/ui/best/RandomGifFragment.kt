package sery.vlasenko.developerslife.ui.best

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_page_fragment.*
import sery.vlasenko.developerslife.R
import sery.vlasenko.developerslife.ui.best.adapter.GifPagerAdapter

class RandomGifFragment : Fragment(R.layout.item_page_fragment) {

    private val viewModel: RandomGifViewModel = RandomGifViewModel()

    private var gifsAdapter: GifPagerAdapter = GifPagerAdapter(viewModel.gifs)

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

            Log.e("fefe", "button ${back_btn.isEnabled}")
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
        viewModel.currentPage.value?.let {
            gifsAdapter.notifyItemChanged(it)
        }
    }

    private fun onLoadingState() {

    }

    private fun onErrorState(error: BestGifState.ErrorState) {
        Toast.makeText(this.requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
    }
}