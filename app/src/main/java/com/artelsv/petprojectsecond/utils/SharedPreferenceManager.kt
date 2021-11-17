package com.artelsv.petprojectsecond.utils

import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val pref: ObscuredSharedPreferences,
) {

    fun addAuth(auth: Boolean) {
        addValue(auth, KEY_AUTH)
    }

    fun addSession(session: String) {
        addValue(session, KEY_SESSION)

        removeGuestSession()
    }

    fun addGuestSession(guestSession: String) {
        addValue(guestSession, KEY_GUEST_SESSION)

        removeSession()
        removeAuth()
    }

    fun getSession(): String? {
        return if (pref.getString(KEY_SESSION, null) == null) {
            pref.getString(KEY_GUEST_SESSION, "")
        } else {
            pref.getString(KEY_SESSION, "")
        }
    }

    fun isAuth(): Boolean {
        return pref.getBoolean(KEY_AUTH, false)
    }

    fun removeSession() {
        removeValue(KEY_SESSION)
    }

    fun removeGuestSession() {
        removeValue(KEY_GUEST_SESSION)
    }

    fun removeAuth() {
        removeValue(KEY_AUTH)
    }

    private fun addValue(value: String, key: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun addValue(value: Boolean, key: String) {
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun removeValue(key: String) {
        val editor = pref.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        const val KEY_GUEST_SESSION = "guestSession"
        const val KEY_SESSION = "session"
        const val KEY_AUTH = "userAuth"
    }
}