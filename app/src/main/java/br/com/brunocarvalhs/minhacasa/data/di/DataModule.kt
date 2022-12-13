package br.com.brunocarvalhs.minhacasa.data.di

import android.content.Context
import android.content.SharedPreferences
import br.com.brunocarvalhs.minhacasa.R
import br.com.brunocarvalhs.minhacasa.data.repository.CostRepositoriesImpl
import br.com.brunocarvalhs.minhacasa.data.repository.HomeRepositoriesImpl
import br.com.brunocarvalhs.minhacasa.domain.repository.CostRepository
import br.com.brunocarvalhs.minhacasa.domain.repository.HomeRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Singleton
    @Provides
    fun providerFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun providerCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            context.getString(R.string.pref_key),
            Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun bindCostRepository(repositoryImpl: CostRepositoriesImpl): CostRepository =
        repositoryImpl

    @Provides
    @Singleton
    fun bindHomeRepository(repositoryImpl: HomeRepositoriesImpl): HomeRepository =
        repositoryImpl
}