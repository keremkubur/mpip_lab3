package mk.ukim.finki.mpip.mpip_lab3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mk.ukim.finki.mpip.mpip_lab3.model.Movie


@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDAO(): MovieDAO

    companion object {

        private  var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = createInstance(context)
            }
            return instance!!
        }

        private fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "movies.db"
            ).build()
        }
    }
}