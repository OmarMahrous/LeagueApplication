package com.digitalcreativity.leagueapplication.dao

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalcreativity.leagueapplication.LeagueApp
import com.digitalcreativity.leagueapplication.data.model.Competition
import com.digitalcreativity.leagueapplication.data.source.local.LeagueDatabase
import com.digitalcreativity.leagueapplication.data.source.local.competitions.CompetitionsDao
import com.digitalcreativity.leagueapplication.utils.CompetitionEntityGenerator
import com.digitalcreativity.leagueapplication.utils.CompetitionEntityMatcher
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import org.junit.runners.model.InitializationError
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
open class CompetitionsDaoTest {

    private final val TAG = javaClass.simpleName


    private val NUM_OF_INSERT_COINS = 100

    private lateinit var competitionsDb:LeagueDatabase
    private lateinit var competitionsDao: CompetitionsDao

    @Mock
    private lateinit var observer: Observer<List<Competition>>

    @Throws(Exception::class)
    @Before
    fun init() {
        MockitoAnnotations.initMocks(this);
//        val context = InstrumentationRegistry.getTargetContext()
        competitionsDb = Room.inMemoryDatabaseBuilder(LeagueApp.getAppContext(), LeagueDatabase::class.java)
            .allowMainThreadQueries().build()
        competitionsDao = competitionsDb.competitionsDao()
    }

    @Throws(Exception::class)
    @After
    public fun clean()  {
        competitionsDb.close();
    }


    private fun createRandomCompetitions() : List<Competition>{
        val competitions = mutableListOf<Competition>();
        for (i in 0.. NUM_OF_INSERT_COINS)
            CompetitionEntityGenerator.createRandomEntity()?.let { competitions.add(it) };
        return competitions;
    }

    @Throws(Exception::class)
    @Test
    public fun insertCompetitions()  {
        val latch = CountDownLatch(1);
        val competitionList= createRandomCompetitions();

        Handler(Looper.getMainLooper()).post {
            competitionsDao.getCompetitionsFlow().asLiveData().observeForever(observer);
        }

        competitionsDao.insertAllCompetitions(competitionList);
        latch.await(1, TimeUnit.SECONDS);
        verify(observer,atLeastOnce()).onChanged(argThat(CompetitionEntityMatcher(competitionList)));

    }

}