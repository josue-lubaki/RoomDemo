package ca.josue.roomdemo.db

class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber) : Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) {
        dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) {
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun getSubscriber(id: Int): Subscriber {
        return dao.getSubscriber(id)
    }
}