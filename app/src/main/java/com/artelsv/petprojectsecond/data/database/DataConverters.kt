package com.artelsv.petprojectsecond.data.database

import androidx.room.TypeConverter
import com.artelsv.petprojectsecond.domain.model.Avatar
import com.artelsv.petprojectsecond.domain.model.movie.*
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromCountryList(countryLang: List<Country>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Country>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCountryList(countryLangString: String?): List<Country>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Country>?>() {}.type
        return gson.fromJson<List<Country>>(countryLangString, type)
    }

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        if (genres == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre>?>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toGenreList(genresString: String?): List<Genre>? {
        if (genresString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre>?>() {}.type
        return gson.fromJson<List<Genre>>(genresString, type)
    }

    @TypeConverter
    fun fromCompanyList(companies: List<Company>?): String? {
        if (companies == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Company>?>() {}.type
        return gson.toJson(companies, type)
    }

    @TypeConverter
    fun toCompanyList(companiesString: String?): List<Company>? {
        if (companiesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Company>?>() {}.type
        return gson.fromJson<List<Company>>(companiesString, type)
    }

    @TypeConverter
    fun fromLanguageList(companies: List<Language>?): String? {
        if (companies == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Language>?>() {}.type
        return gson.toJson(companies, type)
    }

    @TypeConverter
    fun toLanguageList(languageString: String?): List<Language>? {
        if (languageString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Language>?>() {}.type
        return gson.fromJson<List<Language>>(languageString, type)
    }

    @TypeConverter
    fun fromLanguage(companies: Language?): String? {
        if (companies == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Language?>() {}.type
        return gson.toJson(companies, type)
    }

    @TypeConverter
    fun toLanguage(languageString: String?): Language? {
        if (languageString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Language>?>() {}.type
        return gson.fromJson<Language>(languageString, type)
    }

    @TypeConverter
    fun fromGenreIdsList(genreIds: List<Int>?): String? {
        if (genreIds == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int>?>() {}.type
        return gson.toJson(genreIds, type)
    }

    @TypeConverter
    fun toGenreIdsList(genreIds: String?): List<Int>? {
        if (genreIds == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int>?>() {}.type
        return gson.fromJson<List<Int>>(genreIds, type)
    }

    @TypeConverter
    fun toMovieType(value: Int) = enumValues<MovieType>()[value]

    @TypeConverter
    fun fromMovieType(value: MovieType) = value.ordinal

    @TypeConverter
    fun fromAvatar(avatar: Avatar): String {
        val gson = Gson()
        val type: Type = object : TypeToken<Avatar>() {}.type
        return gson.toJson(avatar, type)
    }

    @TypeConverter
    fun toAvatar(avatar: String): Avatar {
        val gson = Gson()
        val type: Type = object : TypeToken<Avatar>() {}.type
        return gson.fromJson(avatar, type)
    }
}