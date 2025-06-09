package com.mtabarkevych.mymovie.movies.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mtabarkevych.mymovie.core.data.local.MyMovieDatabase
import com.mtabarkevych.mymovie.movies.data.local.model.RemoteKeysEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoteKeysDaoTest {

    private lateinit var database: MyMovieDatabase
    private lateinit var dao: RemoteKeysDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MyMovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.keyDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndQueryByMovieId() = runBlocking {
        val key = RemoteKeysEntity(movieId = 1, prevKey = null, nextKey = 2, order = 0)
        dao.insertAll(listOf(key))
        val loaded = dao.remoteKeysMovieId(1)
        assertEquals(key, loaded)
    }

    @Test
    fun clearRemoteKeysEmptiesTable() = runBlocking {
        dao.insertAll(listOf(RemoteKeysEntity(1, null, 2, 0)))
        dao.clearRemoteKeys()
        val loaded = dao.remoteKeysMovieId(1)
        assertNull(loaded)
    }
}