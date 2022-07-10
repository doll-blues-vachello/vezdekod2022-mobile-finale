@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package ru.kheynov.vezdekodfinale2022.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.kheynov.vezdekodfinale2022.data.api.PokeAPI
import ru.kheynov.vezdekodfinale2022.data.api.PokemonRepository
import ru.kheynov.vezdekodfinale2022.data.db.AlarmDatabase
import ru.kheynov.vezdekodfinale2022.data.db.AlarmRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://pokeapi.co/api/v2/"

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
    }

    @Provides
    @Singleton
    fun providePokeApi(retrofit: Retrofit): PokeAPI =
        retrofit.create(PokeAPI::class.java)

    @Provides
    @Singleton
    fun providePokeRepository(pokeAPI: PokeAPI): PokemonRepository = PokemonRepository(pokeAPI)

    @Provides
    @Singleton
    fun provideAlarmsDatabase(@ApplicationContext app: Context): AlarmDatabase {
        return Room.databaseBuilder(
            app,
            AlarmDatabase::class.java,
            AlarmDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(db: AlarmDatabase): AlarmRepository {
        return AlarmRepository(db.alarmDAO)
    }
}