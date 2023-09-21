package alain.niyonema.babara3d.repositories

import alain.niyonema.babara3d.interfaces.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetUserUseCase(repository: Repository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

}