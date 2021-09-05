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
import sery.vlasenko.developerslife.R
import sery.vlasenko.developerslife.data.RandomGif
import sery.vlasenko.developerslife.utils.gone
import sery.vlasenko.developerslife.utils.invisible
import sery.vlasenko.developerslife.utils.visible

class GifPagerAdapter(val gifs: ArrayList<RandomGif?>) : RecyclerView.Adapter<GifPagerVH>() {

    override fun onBindViewHolder(holder: GifPagerVH, position: Int) {
        holder.bind(gifs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifPagerVH {
        return GifPagerVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_page_gif, parent, false)
        )
    }

    override fun getItemCount(): Int = gifs.size
}

class GifPagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(gif: RandomGif?) {
        if (gif != null) {
            itemView.apply {
                Glide.with(context)
                    .load(gif.gifURL)
                    .addListener(RequestListener(gif.gifURL))
                    .into(gif_iv)
                gif_description.text = gif.description
            }
        }
    }

    private fun onLoaded() {
        itemView.run {
            progress_bar.gone()
            gif_iv.visible()
            gif_description.visible()
        }
    }

    private fun onLoading() {
        itemView.run {
            error_section.invisible()
            progress_bar.visible()
            gif_iv.invisible()
            gif_description.invisible()
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
            itemView.run {
                progress_bar.gone()
                error_section.visible()
                retry_tv.setOnClickListener {
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