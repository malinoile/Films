package ru.malinoil.films.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.malinoil.films.R

class MapsFragment : Fragment() {
    private var latitude: Double = DEFAULT_LAT
    private var longitude: Double = DEFAULT_LON

    companion object {
        private const val EXTRA_LATITUDE_KEY = "latitude_key"
        private const val EXTRA_LONGITUDE_KEY = "longitude_key"

        private const val DEFAULT_LAT = -34.0
        private const val DEFAULT_LON = 151.0

        fun getInstance(lat: Double, lon: Double): Fragment {
            val fragment = MapsFragment()
            val bundle = Bundle()
            bundle.putDouble(EXTRA_LATITUDE_KEY, lat)
            bundle.putDouble(EXTRA_LONGITUDE_KEY, lon)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            latitude = it.getDouble(EXTRA_LATITUDE_KEY)
            longitude = it.getDouble(EXTRA_LONGITUDE_KEY)
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val point = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(point))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}