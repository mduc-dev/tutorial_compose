package com.compose.taptap.core.data.datasource.local

import com.tencent.mmkv.MMKV

class MMKVStorageImpl(private val mmkv: MMKV) : LocalStorage {

    override fun putString(key: String, value: String) {
        mmkv.encode(key, value)
    }

    override fun getString(key: String, default: String?): String? {
        return mmkv.decodeString(key, default)
    }

    override fun putBoolean(key: String, value: Boolean) {
        mmkv.encode(key, value)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return mmkv.decodeBool(key, default)
    }

    override fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }

    override fun clear() {
        mmkv.clearAll()
    }

    override fun contains(key: String): Boolean {
        return mmkv.containsKey(key)
    }
}
