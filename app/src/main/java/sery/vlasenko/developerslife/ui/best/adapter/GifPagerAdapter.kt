package sery.vlasenko.developerslife.ui.best.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_page_gif.view.*
import kotlinx.android.synthetic.main.layout_error_data_load.view.*
import kotlinx.android.synthetic.main.layout_error_gif_load.view.*
import sery.vlasenko.developerslife.R
import sery.vlasenko.developerslife.ui.best.RandomGif
import sery.vlasenko.developerslife.utils.invisible
import sery.vlasenko.developerslife.utils.visible

class GifPagerAdapter(
    val clickListener: ClickInterface,
    val gifs: ArrayList<RandomGif?>
) : RecyclerView.Adapter<GifPagerAdapter.GifPagerVH>() {

    interface ClickInterface {
        fun onErrorDataClick(pos: Int)
    }

    override fun onBindViewHolder(holder: GifPagerVH, position: Int) {
        holder.bind(gifs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifPagerVH {
        return GifPagerVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_page_gif, parent, false)
        )
    }

    override fun getItemCount(): Int = gifs.size

    inner class GifPagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(gif: RandomGif?) {
            if (gif?.gif == null && gif?.isLoaded == false) {
                itemView.apply {
                    onErrorDataLoad()
                    retry_data_load.setOnClickListener {
                        clickListener.onErrorDataClick(this@GifPagerVH.adapterPosition)
                        onDataReload()
                    }
                }
            } else if (gif?.gif != null) {
                itemView.apply {
                    Glide.with(context)
                        .load(gif.gif.gifURL)
                        .addListener(RequestListener(gif.gif.gifURL))
                        .into(gif_iv)
                    gif_description.text = gif.gif.description
                }
            }
        }

        private fun onErrorDataLoad() {
            itemView.apply {
                error_data_section.visible()
                progress_bar.invisible()
            }
        }

        private fun onDataReload() {
            itemView.apply {
                error_data_section.invisible()
                progress_bar.visible()
            }
        }

        private fun onLoaded() {
            itemView.run {
                progress_bar.invisible()
                gif_layout.visible()
            }
        }

        private fun onLoading() {
            itemView.run {
                error_gif_section.invisible()
                progress_bar.visible()
                gif_layout.invisible()
            }
        }

        private fun onErrorGifLoad() {
            itemView.run {
                progress_bar.invisible()
                error_gif_section.visible()
            }
        }

        private inner class RequestListener(val gifUrl: String?) :
            com.bumptech.glide.request.RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onErrorGifLoad()
                itemView.run {
                    retry_gif_load.setOnClickListener {
                        Glide.with(context)
                            .load(gifUrl)
                            .addListener(RequestListener(gifUrl))
                            .into(gif_iv)
                        onLoading()
                    }
                }
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoaded()
                return false
            }
        }
    }
}