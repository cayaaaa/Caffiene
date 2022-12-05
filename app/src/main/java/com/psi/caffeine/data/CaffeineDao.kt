package com.psi.caffeine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.psi.caffeine.model.Cafe
import com.psi.caffeine.model.Menu
import com.psi.caffeine.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CaffeineDao {
    //user
    @Insert
    suspend fun insertUser(user: User)
    
    @Insert
    fun insertInitialUsers(vararg users: User)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE email = :email)")
    fun isEmailExist(email: String): Flow<Boolean>
    
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): Flow<User>?
    
    //cafe
    @Query("SELECT * FROM cafe WHERE distance >= 2.0")
    fun getRecommendationCafes(): Flow<List<Cafe>>
    
    @Query("SELECT * FROM cafe WHERE distance < 2.0")
    fun getClosestCafes(): Flow<List<Cafe>>
    
    @Query("SELECT * FROM cafe")
    fun getAllCafes(): Flow<List<Cafe>>
    
    @Query("SELECT * FROM cafe WHERE cafe_id = :cafeId")
    fun getCafeDetail(cafeId: Int): Flow<Cafe>
    
    @Insert
    fun insertInitialCafes(vararg cafe: Cafe)
    
    @Insert
    suspend fun insertCafe(cafe: Cafe)
    
    @Update
    suspend fun updateCafe(cafe: Cafe)
    
    @Query("DELETE FROM cafe WHERE cafe_id = :cafeId")
    suspend fun deleteCafe(cafeId: Int)
    
    //menu
    @Insert
    fun insertInitialMenus(vararg menus: Menu)
    
    @Query("SELECT * FROM menu")
    fun getMenus(): Flow<List<Menu>>
}