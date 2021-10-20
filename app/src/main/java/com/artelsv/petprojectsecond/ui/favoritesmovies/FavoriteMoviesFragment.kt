package com.artelsv.petprojectsecond.ui.favoritesmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.databinding.FragmentFavoriteMoviesBinding
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteMoviesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: FavoriteMoviesViewModel

    @Inject
    lateinit var router: Router

    private val binding: FragmentFavoriteMoviesBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}