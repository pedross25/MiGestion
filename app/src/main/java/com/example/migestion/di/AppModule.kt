package com.example.migestion.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.migestion.data.cache.AlbaranCache
import com.example.migestion.data.cache.CustomerCache
import com.example.migestion.data.cache.InvoiceCache
import com.example.migestion.data.db.Database
import com.example.migestion.data.network.MiGestionHttpClient
import com.example.migestion.data.remote.AlbaranRemote
import com.example.migestion.data.remote.CustomerRemote
import com.example.migestion.data.remote.InvoiceRemote
import com.example.migestion.data.repositories.albaranrepository.AlbaranRepository
import com.example.migestion.data.repositories.albaranrepository.AlbaranRepositoryImpl
import com.example.migestion.data.repositories.albaranrepository.ICacheAlbaran
import com.example.migestion.data.repositories.albaranrepository.IRemoteAlbaran
import com.example.migestion.data.repositories.authrepository.AuthRepository
import com.example.migestion.data.repositories.authrepository.AuthRepositoryImpl
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.customerrepository.CustomerRepositoryImpl
import com.example.migestion.data.repositories.customerrepository.ICacheCustomer
import com.example.migestion.data.repositories.customerrepository.IRemoteCustomer
import com.example.migestion.data.repositories.invoicerepository.ICacheInvoice
import com.example.migestion.data.repositories.invoicerepository.IRemoteInvoice
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepositoryImpl
import com.example.migestion.usecases.GetInvoiceWithCustomer
import com.example.migestion.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient() = MiGestionHttpClient().getHttpClient()

    @Provides
    @Singleton
    fun provideAuthRepository(httpClient: HttpClient): AuthRepository {
        return AuthRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = app,
            name = "database.db"
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(sqlDriver: SqlDriver): Database {
        return Database(sqlDriver)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(cache: ICacheCustomer, remote: IRemoteCustomer): CustomerRepository {
        return CustomerRepositoryImpl(cache, remote)
    }

    @Provides
    @Singleton
    fun provideCustomerCache(database: Database): ICacheCustomer {
        return CustomerCache(database)
    }

    @Provides
    @Singleton
    fun provideInvoiceCache(database: Database): ICacheInvoice {
        return InvoiceCache(database)
    }

    @Provides
    @Singleton
    fun provideCustomerRemote(httpClient: HttpClient): IRemoteCustomer {
        return CustomerRemote(httpClient)
    }

    @Provides
    @Singleton
    fun provideAlbaranCache(database: Database): ICacheAlbaran {
        return AlbaranCache(database)
    }

    @Provides
    @Singleton
    fun provideAlbaranRemote(httpClient: HttpClient): IRemoteAlbaran {
        return AlbaranRemote(httpClient)
    }

    @Provides
    @Singleton
    fun provideAlbaranRepository(cache: ICacheAlbaran, remote: IRemoteAlbaran): AlbaranRepository {
        return AlbaranRepositoryImpl(cache, remote)
    }

    @Provides
    @Singleton
    fun provideInvoiceRepository(httpClient: HttpClient, cache: ICacheInvoice): InvoiceRepository {
        return InvoiceRepositoryImpl(httpClient, cache)
    }

    @Provides
    fun provideUseCases(
        invoiceRepository: InvoiceRepository,
        customerRepository: CustomerRepository
    ) = UseCases(
        GetInvoiceWithCustomer(invoiceRepository, customerRepository)
    )


}