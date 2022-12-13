package br.com.brunocarvalhs.minhacasa.data.repository

import android.content.SharedPreferences
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.repository.HomeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoriesImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) : HomeRepository {

    override suspend fun add(home: Home) = withContext(dispatchers) {
        try {
            database
                .collection(Home.COLLECTION)
                .document(home.id)
                .set(home.toMap())
                .await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(): List<Home> = withContext(dispatchers) {
        try {
            val result = database
                .collection(Home.COLLECTION)
                .get()
                .await()
            return@withContext result.map { it.toObject(Home::class.java) }
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(home: Home): Home? = withContext(dispatchers) {
        try {
            val result = database
                .collection(Home.COLLECTION)
                .document(home.id)
                .get()
                .await()
            return@withContext result.toObject(Home::class.java)
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun update(home: Home): Home = withContext(dispatchers) {
        try {
            database
                .collection(routerCollection(home))
                .document(home.id)
                .update(home.toMap())
                .await()
            return@withContext home
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(home: Home) = withContext(dispatchers) {
        try {
            database
                .collection(Home.COLLECTION)
                .document(home.id)
                .delete()
                .await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun include(token: String): Home? = withContext(dispatchers) {
        try {
            val result = database
                .collection(Home.COLLECTION)
                .whereEqualTo(Home.TOKEN, token)
                .get()
                .await()
            return@withContext if (result.isEmpty) null
            else result.first().toObject(Home::class.java)
        } catch (error: Exception) {
            throw error
        }
    }

    private fun routerCollection(home: Home) = "${Home.COLLECTION}/${home.id}/${Cost.COLLECTION}"

    companion object {
        const val HOME_SESSION = "homesessionkye"
    }
}