package sery.vlasenko.developerslife

import android.app.Application
import dagger.Component
import sery.vlasenko.developerslife.di.NetworkModule
import sery.vlasenko.developerslife.ui.randomGif.RandomGifFragment
import sery.vlasenko.developerslife.ui.randomGif.RandomGifViewModel

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.create()

        super.onCreate()
    }
}

@Component(modules = [NetworkModule::class])
interface AppComponent {

    // Fragments
    fun inject(fragment: RandomGifFragment)

    // ViewModels
    fun inject(viewModel: RandomGifViewModel)
}