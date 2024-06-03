package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.di.ProvideViewModel

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

interface Navigation : ListNavigation, AddNavigation {

    fun navigate(screen: Screen)

    override fun navigateFromList() {
        navigate(AddScreen)
    }

    override fun navigateFromAdd() {
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

    abstract class ReplaceAndAddBackStack : Screen {

        abstract fun fragment(): Fragment

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment())
                .addToBackStack(fragment().javaClass.simpleName)
                .commit()
        }
    }
}