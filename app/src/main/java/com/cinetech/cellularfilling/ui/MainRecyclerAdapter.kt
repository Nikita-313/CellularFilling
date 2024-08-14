package com.cinetech.cellularfilling.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.cellularfilling.R
import com.cinetech.cellularfilling.databinding.ItemRecyclerMainActivityBinding
import com.cinetech.cellularfilling.domain.models.Entity

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    var items: List<Entity> = emptyList()
        set(newValue) {
            val diffCallback = ItemCallback(items, newValue)
            val diffCourses = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffCourses.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerMainActivityBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setContent(items[position])
    }


    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemRecyclerMainActivityBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(entity: Entity) {
            when (entity) {
                is Entity.DeadCellular -> {
                    binding.apply {
                        imageBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.dead_cellular_gradient)
                        imageIcon.text = DEAD_CELLULAR
                        status.text = ContextCompat.getString(itemView.context,R.string.main_activity_dead_cellular)
                        note.text = ContextCompat.getString(itemView.context,R.string.main_activity_dead_cellular_note)
                    }
                }

                is Entity.DeadLife -> {
                    binding.apply {
                        imageBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.life_gradient)
                        imageIcon.text = DEAD_CELLULAR
                        status.text = ContextCompat.getString(itemView.context,R.string.main_activity_dead_life)
                        note.text = ContextCompat.getString(itemView.context,R.string.main_activity_dead_life_note)
                    }
                }

                is Entity.Life -> {
                    binding.apply {
                        imageBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.life_gradient)
                        imageIcon.text = LIFE
                        status.text = ContextCompat.getString(itemView.context,R.string.main_activity_life)
                        note.text = ContextCompat.getString(itemView.context,R.string.main_activity_life_note)
                    }
                }

                is Entity.LiveCellular -> {
                    binding.apply {
                        imageBackground.background = ContextCompat.getDrawable(itemView.context, R.drawable.live_cellular_gradient)
                        imageIcon.text = LIVE_CELLULAR
                        status.text = ContextCompat.getString(itemView.context,R.string.main_activity_live_cellular)
                        note.text = ContextCompat.getString(itemView.context,R.string.main_activity_live_cellular_note)
                    }
                }
            }
        }

        companion object {
            const val DEAD_CELLULAR = "\uD83D\uDC80"
            const val LIVE_CELLULAR = "\uD83D\uDCA5"
            const val LIFE = "\uD83D\uDC23"
        }
    }

    class ItemCallback(private val oldList: List<Entity>, private val newList: List<Entity>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }

}
