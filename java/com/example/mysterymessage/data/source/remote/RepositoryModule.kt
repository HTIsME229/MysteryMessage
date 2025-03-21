package com.example.mysterymessage.data.source.remote


import com.example.mysterymessage.data.source.DefaultRepository
import com.example.mysterymessage.data.source.DataSource
import com.example.mysterymessage.data.source.local.LocalDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(): DataSource.LocalDataSource {
        return LocalDataSourceImp() // Thay bằng class cụ thể của bạn
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): DataSource.RemoteDataSource {
        return DefaultRemoteDataSource() // Thay bằng class cụ thể của bạn
    }

    @Provides
    @Singleton
    fun provideDefaultRepository(
        localDataSource: DataSource.LocalDataSource,
        remoteDataSource: DataSource.RemoteDataSource
    ): DefaultRepository {
        return DefaultRepository(localDataSource, remoteDataSource)
    }
    @Provides
    @Singleton
    fun provideAuthenticationRepository(
    ): AuthenticationRepository {
        return  AuthenticationRepository()
    }
    @Provides
    @Singleton
    fun provideScheduleNotification(
    ): ScheduleNotification {
        return  ScheduleNotification()
    }
}
