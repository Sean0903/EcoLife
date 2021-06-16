package app.appworks.school.stylish.util

import GreenRemoteDataSource
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.sean.green.data.source.DefaultGreenRepository
import com.sean.green.data.source.GreenDataSource
import com.sean.green.data.source.GreenRepository
import com.sean.green.data.source.local.GreenLocalDataSource


/**
 * A Service Locator for the [StylishRepository].
 */
object ServiceLocator {

    @Volatile
    var greenRepository: GreenRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): GreenRepository {
        synchronized(this) {
            return greenRepository
                ?: greenRepository
                ?: createGreenRepository(context)
        }
    }

    private fun createGreenRepository(context: Context): GreenRepository {
        return DefaultGreenRepository(GreenRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): GreenDataSource {
        return GreenLocalDataSource(context)
    }
}