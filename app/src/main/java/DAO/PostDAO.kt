package DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import Beans.tablePost

@Dao
interface PostDAO {

    @Query("SELECT * FROM post_table")
    fun getAll():List<tablePost>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tb: tablePost)

}