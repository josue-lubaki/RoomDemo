package ca.josue.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ca.josue.roomdemo.databinding.ActivityMainBinding
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

        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this) { subscribers ->
            Log.i("MYTAG", subscribers.toString())
        }
    }
}