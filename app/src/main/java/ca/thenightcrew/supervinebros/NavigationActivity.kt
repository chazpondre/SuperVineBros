package ca.thenightcrew.supervinebros

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import ca.thenightcrew.supervinebros.database.AppDatabase

lateinit var db: AppDatabase

class NavigationActivity : AppCompatActivity() /*, Controller */ {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java)
            .build()

//        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "super-vine-bros")
//            .build()

        setContentView(R.layout.navigator)
    }
}

