package ca.josue.roomdemo

//open class Event <out T>(private val content: T) {
//
//    var hasBeenHandled = false
//        private set // Allow external read but not write
//
//    /**
//     * Returns the content and prevents its use again.
//     */
//    fun getContentIfNotHandled(): T? {
//        return if (hasBeenHandled) {
//            null
//        } else {
//            hasBeenHandled = true
//            content
//        }
//    }
//
//    /**
//     * Returns the content, even if it's already been handled.
//     */
//    fun peekContent(): T = content
//}

/**
 * An event class that holds a value of type [T] and provides a way to
 * retrieve the value, while preventing it from being used again.
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     *
     * @return the content if it has not been handled, or `null` if it has
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     *
     * @return the content
     */
    fun peekContent(): T = content
}