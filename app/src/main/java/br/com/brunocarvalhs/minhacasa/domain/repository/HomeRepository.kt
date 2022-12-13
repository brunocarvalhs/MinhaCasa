package br.com.brunocarvalhs.minhacasa.domain.repository

import br.com.brunocarvalhs.minhacasa.domain.entities.Home

interface HomeRepository {
    suspend fun list(): List<Home>
    suspend fun view(home: Home): Home?
    suspend fun add(home: Home)
    suspend fun update(home: Home): Home
    suspend fun delete(home: Home)
    suspend fun include(token: String): Home?
}