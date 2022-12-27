package ca.josue.roomdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ca.josue.roomdemo.databinding.ListItemBinding
import ca.josue.roomdemo.db.Subscriber

class RecyclerViewAdapter(private val clickListener: (Subscriber) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private var subscribers: MutableList<Subscriber> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(subscribers: List<Subscriber>) {
        this.subscribers.clear()
        this.subscribers.addAll(subscribers)
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}