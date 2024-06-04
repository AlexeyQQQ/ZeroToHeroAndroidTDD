package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.add.AddNavigation
import ru.easycode.zerotoheroandroidtdd.add.AddScreen
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.details.DetailsNavigation
import ru.easycode.zerotoheroandroidtdd.details.DetailsScreen
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListNavigation
import ru.easycode.zerotoheroandroidtdd.list.ListScreen

class MainActivity : AppCompatActivity(), ProvideViewModel, Navigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) navigate(ListScreen)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }
}

interface Navigation : ListNavigation, AddNavigation, DetailsNavigation {

    fun navigate(screen: Screen)

    override fun navigateFromListToAdd() {
        navigate(AddScreen)
    }

    override fun navigateFromListToDetails(bundle: Bundle) {
        navigate(DetailsScreen(bundle))
    }

    override fun navigateFromAdd() {
        navigate(ListScreen)
    }

    override fun navigateFromDetails() {
        navigate(ListScreen)
    }
}

interface Screen {

    fun show(containerId: Int, fragmentManager: FragmentManager)

    object Empty : Screen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) = Unit
    }

    abstract class Replace : Screen {

        abstract fun fragment(): Fragment

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.popBackStack()
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment())
                .commit()
        }
    }

    abstract class ReplaceAndAddToBackstack : Screen {

        abstract fun fragment(): Fragment

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment())
                .addToBackStack(fragment().javaClass.simpleName)
                .commit()
        }
    }
}