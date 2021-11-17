package com.artelsv.petprojectsecond.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentUserListBinding
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class UserListFragment : BottomSheetDialogFragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

//    @Inject
//    lateinit var viewModel: UserListViewModel

    @Inject
    lateinit var router: Router

    private val binding: FragmentUserListBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val userListAdapter: UserListAdapter = UserListAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            binding.type = getString(it[TYPE] as Int)
            userListAdapter.submitList((it[MovieList::class.simpleName] as MovieList).results)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.viewModel = viewModel

        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.addItemDecoration(
            HorizontalMarginItemDecoration(
                requireContext().resources.getDimension(
                    R.dimen.viewpager_current_item_horizontal_margin
                ).toInt()
            )
        )
        binding.rvList.adapter = userListAdapter

        return binding.root
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    companion object {
        const val TYPE = "type"

        fun newInstance(data: Pair<MovieList, Int>): UserListFragment {
            val fragment = UserListFragment()

            val arguments = Bundle()
            arguments.putParcelable(data.first::class.simpleName, data.first)
            arguments.putInt(TYPE, data.second)

            fragment.arguments = arguments

            return fragment
        }
    }

}