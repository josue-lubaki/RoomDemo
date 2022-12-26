package ca.josue.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ca.josue.roomdemo.databinding.ActivityMainBinding
import ca.josue.roomdemo.db.Subscriber
import ca.josue.roomdemo.db.SubscriberDatabase
import ca.josue.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this) { subscribers ->
            Log.i("MYTAG", subscribers.toString())
            binding.subscriberRecyclerView.adapter = RecyclerViewAdapter(subscribers) { selectedItem: Subscriber ->
                listItemClicked(selectedItem)
            }
        }
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
    }
}