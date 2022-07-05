package com.pvpbeach.coins.util

fun <K, V> notAbsentMapOf(default: V, vararg pairs: Pair<K, V>): NotAbsentHashMap<K, V> =
    NotAbsentHashMap<K, V>(default).apply { putAll(pairs) }

fun mapCapacity(expectedSize: Int): Int = when
{
    // We are not coercing the value to a valid one and not throwing an exception. It is up to the caller to
    // properly handle negative values.
    expectedSize < 0 -> expectedSize
    expectedSize < 3 -> expectedSize + 1
    expectedSize < 1 shl (Int.SIZE_BITS - 2) -> ((expectedSize / 0.75F) + 1.0F).toInt()
    // any large value
    else -> Int.MAX_VALUE
}

class NotAbsentHashMap<K, V>(val default: V) : HashMap<K, V>()
{
    override fun get(key: K): V
    {
        return super.get(key)
            ?: default.also {
                this[key] = default
            }
    }
}