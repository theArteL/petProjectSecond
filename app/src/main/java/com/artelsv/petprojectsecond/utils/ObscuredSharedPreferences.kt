package com.artelsv.petprojectsecond.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.provider.Settings
import android.util.Base64
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.UnsupportedOperationException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec

@SuppressLint("HardwareIds")
class ObscuredSharedPreferences(
    private val context: Context,
    private val delegate: SharedPreferences,
) : SharedPreferences {

    inner class Editor : SharedPreferences.Editor {
        private val delegate: SharedPreferences.Editor =
            this@ObscuredSharedPreferences.delegate.edit()

        override fun putBoolean(key: String, value: Boolean): Editor {
            delegate.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putFloat(key: String, value: Float): Editor {
            delegate.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putInt(key: String, value: Int): Editor {
            delegate.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putLong(key: String, value: Long): Editor {
            delegate.putString(key, encrypt(value.toString()))
            return this
        }

        override fun putString(key: String?, value: String?): Editor {
            delegate.putString(key, encrypt(value))
            return this
        }

        override fun putStringSet(p0: String?, p1: MutableSet<String>?): Editor {
            return this
        }

        override fun apply() {
            delegate.apply()
        }

        override fun clear(): Editor {
            delegate.clear()
            return this
        }

        override fun commit(): Boolean {
            return delegate.commit()
        }

        override fun remove(s: String): Editor {
            delegate.remove(s)
            return this
        }

    }

    override fun edit(): Editor {
        return Editor()
    }


    override fun getAll(): Map<String, *> {
        throw UnsupportedOperationException()
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val v = delegate.getString(key, null)
        return if (v != null) java.lang.Boolean.parseBoolean(decrypt(v)) else defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val v = delegate.getString(key, null)
        return if (v != null) decrypt(v).toFloat() else defValue
    }

    override fun getInt(key: String, defValue: Int): Int {
        val v = delegate.getString(key, null)
        return if (v != null) decrypt(v).toInt() else defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        val v = delegate.getString(key, null)
        return if (v != null) decrypt(v).toLong() else defValue
    }

    override fun getString(key: String?, defValue: String?): String? {
        val v = delegate.getString(key, null)
        return v?.let { decrypt(it) } ?: defValue
    }

    override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String> {
        return mutableSetOf()
    }

    override operator fun contains(s: String?): Boolean {
        return delegate.contains(s)
    }

    override fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {
        delegate.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {
        delegate.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }


    fun encrypt(value: String?): String? {
        return try {
            val bytes = value?.toByteArray(UTF8) ?: ByteArray(0)
            val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
            val key: SecretKey = keyFactory.generateSecret(PBEKeySpec(SECRET))
            val pbeCipher: Cipher = Cipher.getInstance("PBEWithMD5AndDES")
            pbeCipher.init(Cipher.ENCRYPT_MODE,
                key,
                PBEParameterSpec(Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID).toByteArray(UTF8), 20))
            String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP), UTF8)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun decrypt(value: String?): String {
        return try {
            val bytes = if (value != null) Base64.decode(value, Base64.DEFAULT) else ByteArray(0)
            val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
            val key: SecretKey = keyFactory.generateSecret(PBEKeySpec(SECRET))
            val pbeCipher: Cipher = Cipher.getInstance("PBEWithMD5AndDES")
            pbeCipher.init(Cipher.DECRYPT_MODE,
                key,
                PBEParameterSpec(Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID).toByteArray(UTF8), 20))
            String(pbeCipher.doFinal(bytes), UTF8)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private val UTF8 = charset("utf-8")
        private val SECRET = "ZA4GwMU7rB6fkAd7NA6UZsEchAT35JbPQPwqGGM9tuwn6uhBjE".toCharArray()
    }
}