package com.pvpbeach.coins.util

fun <K, V> notAbsentMapOf(default: V, vararg pairs: Pair<K, V>): NotAbsentHashMap<K, V> =
    NotAbsentHashMap<K, V>(default).apply { putAll(pairs) }

class NotAbsentHashMap<K, V>(private val default: V) : HashMap<K, V>()
{
    override fun get(key: K): V
    {
        return super.get(key)
            ?: default.also {
                this[key] = default
            }
    }
}