package kz.magnum.app.application

class Generic<T : Any>(private val klass: Class<T>) {
    companion object {
        inline operator fun <reified T : Any> invoke() = Generic(T::class.java)
    }

    fun checkType(t: Any) = when {
        klass.isAssignableFrom(t.javaClass) -> true
        else -> false
    }
}