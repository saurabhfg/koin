package org.koin.experimental.builder

import org.junit.Assert
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules
import org.koin.test.get

class MVPArchitectureTest : AutoCloseKoinTest() {

    val MVPModule = module {
        single<Repository>()
        single<View>()
        single<Presenter>()
    }

    val DataSourceModule = module {
        single<DebugDatasource>() bind Datasource::class
    }

    @Test
    fun `should create all MVP hierarchy`() {
        startKoin {
            printLogger(Level.DEBUG)
            modules(MVPModule, DataSourceModule)
        }

        val view = get<View>()
        val presenter = get<Presenter>()
        val repository = get<Repository>()
        val datasource = get<Datasource>()

        Assert.assertEquals(presenter, view.presenter)
        Assert.assertEquals(repository, presenter.repository)
        Assert.assertEquals(repository, view.presenter.repository)
        Assert.assertEquals(datasource, repository.datasource)
    }

    @Test
    fun `check MVP hierarchy`() {
        startKoin {
            printLogger(Level.DEBUG)
            modules(MVPModule, DataSourceModule)
        }.checkModules()
    }
}