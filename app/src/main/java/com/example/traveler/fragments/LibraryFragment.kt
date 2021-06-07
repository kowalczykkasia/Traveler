package com.example.traveler.fragments

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.traveler.MainActivity
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.example.traveler.adapters.PhotosAdapter
import com.example.traveler.database.Shared
import com.example.traveler.viewModels.LibraryViewModel
import kotlinx.android.synthetic.main.library_fragment.view.*
import kotlin.concurrent.thread

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private var viewModel: LibraryViewModel? = null

    private val photosAdapter by lazy {
        PhotosAdapter {
            DialogFragment(it).show(childFragmentManager, DialogFragment::class.java.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.library_fragment, container, false).apply {
            initView(this)
        }
    }

    private fun initView(rootView: View) {
        rootView.apply {
            this.rvLibrary.adapter = photosAdapter
            loadData()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)
        with(viewModel){
            this?.photoListModel?.observe(viewLifecycleOwner, Observer {
                refreshAdapter(it)
            })
        }
    }

    private fun loadData(){
        thread {
            val photoList = (activity as? MainActivity)?.getRepository()?.getAllPhotos()
            val photoListModel = photoList?.map { Pair(it, Shared.repository?.getPathForImage(it.id)) }?.toMutableList()
            viewModel?.update(photoListModel)
        }
    }

    private fun refreshAdapter(photoListModel: List<Pair<PhotoItemDto, Bitmap?>>?){
        photosAdapter.submitList(photoListModel)
    }
}