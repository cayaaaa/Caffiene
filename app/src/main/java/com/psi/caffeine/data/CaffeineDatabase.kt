package com.psi.caffeine.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.psi.caffeine.R
import com.psi.caffeine.model.Cafe
import com.psi.caffeine.model.Menu
import com.psi.caffeine.model.User
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(entities = [User::class, Menu::class, Cafe::class], version = 1)
abstract class CaffeineDatabase: RoomDatabase() {
    abstract fun caffeineDao(): CaffeineDao
    
    companion object {
        @Volatile
        private var INSTANCE: CaffeineDatabase? = null
        
        @JvmStatic
        fun getInstance(context: Context): CaffeineDatabase {
            if (INSTANCE == null) {
                synchronized(CaffeineDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CaffeineDatabase::class.java,
                        "caffeine.db"
                    ).addCallback(object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { caffeineDatabase ->
                                Executors.newSingleThreadScheduledExecutor().execute {
                                    fillWithStartingData(
                                        context.applicationContext, R.raw.cafe, "cafes"
                                    ) { item ->
                                        caffeineDatabase.caffeineDao().insertInitialCafes(
                                            Cafe(
                                                item.getInt("id"),
                                                item.getString("cafeName"),
                                                item.getString("description"),
                                                item.getString("location"),
                                                item.getString("image"),
                                                item.getDouble("rating"),
                                                item.getDouble("distance"),
                                                item.getString("openTime"),
                                                item.getString("closeTime")
                                            )
                                        )
                                    }
                                    
                                    fillWithStartingData(
                                        context.applicationContext, R.raw.menu, "menus"
                                    ) { item ->
                                        caffeineDatabase.caffeineDao().insertInitialMenus(
                                            Menu(
                                                item.getInt("menuId"),
                                                item.getString("name"),
                                                item.getInt("price"),
                                                item.getString("type"),
                                                item.getString("image"),
                                                item.getBoolean("isHalal")
                                            )
                                        )
                                    }
                                    
                                    fillWithStartingData(
                                        context.applicationContext, R.raw.user, "users"
                                    ) { item ->
                                        caffeineDatabase.caffeineDao().insertInitialUsers(
                                            User(
                                                item.getString("uid"),
                                                item.getString("username"),
                                                item.getString("email"),
                                                item.getString("password"),
                                                item.getString("avatarUrl"),
                                                item.getBoolean("isAdmin")
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }).build()
                }
            }
            return INSTANCE as CaffeineDatabase
        }
    
        private fun fillWithStartingData(context: Context, raw: Int, array: String, action:(JSONObject) -> Unit) {
            val json = context.loadJsonArray(raw, array)
            try {
                if (json != null) {
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        action(item)
                    }
                }
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }
    
        private fun Context.loadJsonArray(raw: Int, array: String): JSONArray? {
            val builder = StringBuilder()
            val `in` = this.resources.openRawResource(raw)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray(array)
            } catch (exception: IOException) {
                exception.printStackTrace()
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}