package com.android.testapp.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.testapp.R
import com.android.testapp.databinding.AlbumFragmentBinding
import com.android.testapp.ui.custom.TransparentProgressDialog

/**
 * Fragment for displaying albums.
 */
class AlbumFragment : Fragment() {

    companion object {
        /**
         * Creates an instance of [AlbumFragment].
         *
         * @return [AlbumFragment] instance.
         */
        fun newInstance() = AlbumFragment()
    }

    /**
     * View binding instance.
     */
    private var binding: AlbumFragmentBinding? = null

    /**
     * View model instance for [AlbumViewModel].
     */
    private val viewModel by lazy { ViewModelProvider(this).get(AlbumViewModel::class.java) }

    /**
     * Loading view for async operations.
     */
    private val loader by lazy {
        TransparentProgressDialog(requireContext())
    }

    /**
     * Adapter class to display list of albums.
     */
    private val albumAdapter by lazy { AlbumListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onDestroyView() {
        //Clear view binding here
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Assign view binding here
        binding = AlbumFragmentBinding.bind(view)

        //Initialize views
        binding?.rvAlbums?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = albumAdapter
        }

        //Register observables
        registerObservables()

        //Call the view model after initialization is finished
        viewModel.initialize()
    }

    /**
     * Registers the observable livedata from the view model to update views accordingly.
     */
    private fun registerObservables() {
        //Observe album list data
        viewModel.albumObservable().observe(viewLifecycleOwner) {
            it?.let { albums ->
                //Update albums list
                albumAdapter.updateData(albums)
            }
        }

        //Observe error in loading album data
        viewModel.errorMessageObservable().observe(viewLifecycleOwner) {
            it?.let { error ->
                //Show error
                binding?.tvError?.text = error
            }
        }

        //Observe loading state
        viewModel.showLoaderObservable().observe(viewLifecycleOwner) {
            it?.let { show ->
                //Show or hide loader based on the boolean value
                when (show) {
                    true -> loader.show()
                    false -> loader.dismiss()
                }
            }
        }
    }
}