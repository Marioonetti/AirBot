package com.airbot.data.room.connect

import android.content.Context
import androidx.room.Room
import com.airbot.R
import com.airbot.data.room.AirBotRoomDataBase
import com.airbot.data.room.dao.AirBotDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AirBotRoomDataBase::class.java,
            context.getString(R.string.db)
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun airbotDao(airBotRoomDataBase: AirBotRoomDataBase) = airBotRoomDataBase.airbotDao()
}