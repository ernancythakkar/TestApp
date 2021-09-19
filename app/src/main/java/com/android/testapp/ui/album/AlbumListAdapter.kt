package com.android.testapp.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.testapp.R
import com.android.testapp.repository.model.ModelAlbum

/**
 * Adapter class for albums list.
 */
class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    /**
     * List of [ModelAlbum] for adapter reference.
     */
    private val arrayList = arrayListOf<ModelAlbum>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        //Create viewholder for the album
        return AlbumViewHolder(parent)
    }

    override fun getItemCount() = arrayList.size //Count of albums

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        //Bind the album into viewholder
        holder.bind(arrayList[position])
    }

    /**
     * Updates the album list.
     *
     * @param items New list of [ModelAlbum].
     */
    fun updateData(items: List<ModelAlbum>) {
        //Clear old albums
        arrayList.clear()
        //Add new albums
        arrayList.addAll(items)
        //Notify the adapter for the change
        notifyDataSetChanged()
    }

    /**
     * View holder for adapter.
     */
    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Title view for the album list view item.
         */
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        constructor(parent: ViewGroup) : this(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_album, parent, false
            )
        )

        /**
         * Binds the album data to list view item.
         *
         * @param item [ModelAlbum] containing album data.
         */
        fun bind(item: ModelAlbum) {
            //Set title of the album
            tvTitle.text = item.title
        }
    }
}