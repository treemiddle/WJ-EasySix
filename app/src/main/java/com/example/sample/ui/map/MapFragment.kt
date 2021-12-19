package com.example.sample.ui.map

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentMapBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.utils.EventObserver
import com.example.sample.utils.LabelType
import com.example.sample.utils.MapLabelClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val viewModel by viewModels<MapViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    private val mapViewHandler: MapViewHandler by lazy {
        MapViewHandler(context, this, viewModel)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value == true }

        if (granted) {
            mapViewHandler.setMap()
        }
    }

    private val checkPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {
            moveCamera.observe(viewLifecycleOwner, {
                mapViewHandler.moveCameraPosition(it)
            })
            mapLabelClick.observe(viewLifecycleOwner, EventObserver {
                when (it) {
                    MapLabelClick.LABEL_A -> activityViewModel.setLabelAorB(getLabelA())
                    MapLabelClick.LABEL_B -> activityViewModel.setLabelAorB(getLabelB())
                    MapLabelClick.BOOK -> activityViewModel.setBothLabel(getBothLabel())
                    else -> { }
                }

                activityViewModel.moveScreen(it)
            })
        }

        with(activityViewModel) {
            inMemoryLabel.observe(viewLifecycleOwner, {
                it.nickname?.let { nick ->
                    when (it.type) {
                        LabelType.A -> viewModel.setLocationA(nick)
                        LabelType.B -> viewModel.setLocationB(nick)
                    }
                }
            })
            reset.observe(viewLifecycleOwner, {
                viewModel.reset()
            })
        }
    }

    override fun initData() {
        mapViewHandler.initMap()
        locationPermissionRequest.launch(checkPermissionList)
    }

    companion object {
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

}