package com.aminu.sewagudangapp.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aminu.sewagudangapp.R
import com.aminu.sewagudangapp.application.WarehouseApp
import com.aminu.sewagudangapp.databinding.FragmentSecondBinding
import com.aminu.sewagudangapp.model.Warehouse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val warehouseViewModel: WarehouseViewModel by viewModels {
        WarehouseViewModelFactory((applicationContext as WarehouseApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var warehouse: Warehouse? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        warehouse = args.warehouse
        // kita cek jika warehouse null maka tampil default nambah gudang
        // jika warehouse tidak null tampil sedikit berubah ada tombol hapus

        if (warehouse != null) {
            binding.hapusButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(warehouse?.name)
            binding.addressEditText.setText(warehouse?.address)
            binding.ownerEditText.setText(warehouse?.owner)
        }

        // binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val owner = binding.ownerEditText.text
        binding.saveButton.setOnClickListener {
            // kita kondisikan jika tidak di isi maka tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama gudang tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat gudang tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (owner.isEmpty()) {
                Toast.makeText(context, "Pemilik gudang tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (warehouse == null) {
                    val warehouse = Warehouse(0, name.toString(), address.toString(), owner.toString())
                    warehouseViewModel.insert(warehouse)
                } else {
                    val warehouse = Warehouse(warehouse?.id!!, name.toString(), address.toString(), owner.toString())
                    warehouseViewModel.update(warehouse)
                }
                findNavController().popBackStack() //untuk dismiss halaman ini
            }

        }

        binding.hapusButton.setOnClickListener {
            warehouse?.let { warehouseViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // implement drag marker

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        val sydney = LatLng(-34.0, 151.0)
        val markerOption = MarkerOptions()
            .position(sydney)
            .title("Test")
            .draggable(true)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warehouse_pin))
        mMap.addMarker(markerOption)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akes lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // ngecek jika permission tidak disetujui maka akan berhenti di kondisi if
        if(ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"

                }
            }
    }
}

