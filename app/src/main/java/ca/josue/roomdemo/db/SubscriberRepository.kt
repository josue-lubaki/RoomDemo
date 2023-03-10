package ca.josue.roomdemo.db

import kotlinx.coroutines.flow.Flow

class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers : Flow<List<Subscriber>> = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber) : Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) : Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() : Int {
        return dao.deleteAll()
    }

    suspend fun getSubscriber(id: Int): Subscriber {
        return dao.getSubscriber(id)
    }
}