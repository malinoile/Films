package ru.malinoil.films.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ru.malinoil.films.MainActivity
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentGeolocationBinding

class GeolocationFragment : Fragment() {
    private var _binding: FragmentGeolocationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGeolocationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        binding.geolocationButton.setOnClickListener {
            getMyLocation({
                renderMap(it.first, it.second)
            }, {
                binding.geolocationTextView.text = context?.getString(R.string.geolocation_error)
            })

        }
        binding.locationNameButton.setOnClickListener {
            renderDialog()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation(
        onSuccess: (Pair<Double, Double>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val locationManager =
                requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
            val provider = locationManager.getBestProvider(Criteria(), true).toString()
            locationManager.getLastKnownLocation(provider)?.let { location ->
                onSuccess(Pair(location.latitude, location.longitude))
            } ?: locationManager.requestLocationUpdates(provider, 5, 5f)
            { location ->
                onSuccess(Pair(location.latitude, location.longitude))
            }
        } catch (thr: Throwable) {
            onError(thr)
        }
    }

    private fun renderMap(lat: Double, lon: Double) {
        (activity as MainActivity).setFragment(
            MapsFragment.getInstance(lat, lon),
            requireContext().getString(R.string.map),
            backStack = true,
            setBackArrow = true
        )
    }

    private fun renderDialog() {
        val editText = EditText(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle(context?.getString(R.string.select_your_location))
            .setView(editText)
            .setPositiveButton(context?.getString(R.string.ok_button)) { _, _ ->
                getLocationFromDialog(editText.text.toString())
            }
            .setNegativeButton(context?.getString(R.string.cancel_button)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun getLocationFromDialog(name: String) {
        getLocationByName(name, {
            Handler(Looper.getMainLooper()).post {
                renderMap(it.first, it.second)
            }
        }, {
            binding.geolocationTextView.text = context?.getString(R.string.geolocation_error)
        })
    }

    private fun getLocationByName(
        name: String,
        onSuccess: (Pair<Double, Double>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (Geocoder.isPresent()) {
            Thread {
                Geocoder(requireContext())
                    .getFromLocationName(name, 1)
                    .firstOrNull()?.let { address ->
                        onSuccess(Pair(address.latitude, address.longitude))
                    }
            }.start()
        }
    }

    private fun requestPermission() {
        val permission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranded ->
            if (!isGranded) {
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.cancel_geolocation_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}