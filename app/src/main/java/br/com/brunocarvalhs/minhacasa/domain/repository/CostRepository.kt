package br.com.brunocarvalhs.minhacasa.domain.repository

import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home

interface CostRepository {
    suspend fun add(home: Home, cost: Cost)
    suspend fun list(home: Home): List<Cost>
    suspend fun update(home: Home, cost: Cost): Cost
    suspend fun delete(home: Home, cost: Cost)
    suspend fun view(home: Home, id: String): Cost?
}