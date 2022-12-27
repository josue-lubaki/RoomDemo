package ca.josue.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configuration()
        displaySubscribersList()
        displayToasts()
    }

    /***
     * This method is used to configure :
     * - the Factory
     * - the ViewModel
     * - the RecyclerView
     * - the adapter
     */
    private fun configuration() {
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)

        // viewModel configuration
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        // recyclerView configuration
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter { selectedItem: Subscriber ->
            listItemClicked(selectedItem)
        }
        binding.subscriberRecyclerView.adapter = adapter
    }

    /***
     * Observe and display the list of subscribers
     */
    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this) { subscribers ->
            adapter.setList(subscribers)
        }
    }

    /***
     * Display a toast message when the event is triggered
     */
    private fun displayToasts() {
        subscriberViewModel.messages.observe(this) { events ->
            events.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /***
     * This function is called when a list item is clicked
     * @param subscriber the subscriber that was clicked
     */
    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}