package ca.josue.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) : Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): Flow<List<Subscriber>>

    @Query("SELECT * FROM subscriber_data_table WHERE subscriber_id = :id")
    suspend fun getSubscriber(id: Int): Subscriber
}