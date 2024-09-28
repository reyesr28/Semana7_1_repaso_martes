package Beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class tablePost (

    @PrimaryKey()
    val id:Int?,

    @ColumnInfo(name="userId")
    val userId:Int?,

    @ColumnInfo(name="body")
    var body:String?,

    @ColumnInfo(name="title")
    var title:String?

)