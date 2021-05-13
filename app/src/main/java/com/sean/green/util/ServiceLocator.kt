package app.appworks.school.stylish.util

/**
 * A Service Locator for the [StylishRepository].
 */
//object ServiceLocator {
//
//    @Volatile
//    var environmentRepository: EnvironmentRepository? = null
//        @VisibleForTesting set
//
//    fun provideTasksRepository(context: Context): EnvironmentRepository {
//        synchronized(this) {
//            return environmentRepository
//                ?: environmentRepository
//                ?: createStylishRepository(context)
//        }
//    }
//
//    private fun createStylishRepository(context: Context): EnvironmentRepository {
//        return DefaultStylishRepository(StylishRemoteDataSource,
//            createLocalDataSource(context)
//        )
//    }
//
//    private fun createLocalDataSource(context: Context): EnvironmenthRepository {
//        return StylishLocalDataSource(context)
//    }
//}