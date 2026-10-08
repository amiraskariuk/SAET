package com.amir.askari.saet.di

import com.amir.askari.saet.shared.data.DefaultProductRepository
import com.amir.askari.saet.shared.data.remote.ProductApi
import com.amir.askari.saet.shared.data.remote.createHttpClient
import com.amir.askari.saet.shared.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = createHttpClient(OkHttp.create())

    @Provides
    @Singleton
    fun provideProductApi(client: HttpClient): ProductApi = ProductApi(client)

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi): ProductRepository = DefaultProductRepository(api)
}
