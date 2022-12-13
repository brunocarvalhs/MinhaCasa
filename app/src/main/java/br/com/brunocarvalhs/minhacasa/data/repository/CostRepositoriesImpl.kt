package br.com.brunocarvalhs.minhacasa.data.repository

import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.repository.CostRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CostRepositoriesImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) : CostRepository {

    override suspend fun add(home: Home, cost: Cost) = withContext(dispatchers) {
        try {
            database.collection(routerCollection(home))
                .document(cost.id)
                .set(cost.toMap())
                .await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun list(home: Home): List<Cost> = withContext(dispatchers) {
        try {
            val result = database
                .collection(routerCollection(home))
                .get()
                .await()
            return@withContext result.map { it.toObject(Cost::class.java) }
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun view(home: Home, id: String): Cost? = withContext(dispatchers) {
        try {
            val result = database
                .collection(routerCollection(home))
                .document(home.id)
                .get()
                .await()
            return@withContext result.toObject(Cost::class.java)
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    override suspend fun update(home: Home, cost: Cost): Cost  = withContext(dispatchers) {
        try {
            database
                .collection(routerCollection(home))
                .document(cost.id)
                .update(cost.toMap())
                .await()
            return@withContext cost
        } catch (error: Exception) {
            throw error
        }
    }

    override suspend fun delete(home: Home, cost: Cost) = withContext(dispatchers) {
        try {
            database.collection(routerCollection(home))
                .document(cost.id)
                .delete()
                .await()
            return@withContext
        } catch (error: Exception) {
            throw error
        }
    }

    private fun routerCollection(home: Home) = "${Home.COLLECTION}/${home.id}/${Cost.COLLECTION}"
}