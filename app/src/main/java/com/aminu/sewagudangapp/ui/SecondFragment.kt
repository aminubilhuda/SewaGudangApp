package com.aminu.sewagudangapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aminu.sewagudangapp.R
import com.aminu.sewagudangapp.application.WarehouseApp
import com.aminu.sewagudangapp.databinding.FragmentSecondBinding
import com.aminu.sewagudangapp.model.Warehouse

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val warehouseViewModel: WarehouseViewModel by viewModels {
        WarehouseViewModelFactory((applicationContext as WarehouseApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var warehouse: Warehouse? = null

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
        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val owner = binding.ownerEditText.text
        binding.saveButton.setOnClickListener {
            // kita kondisikan jika tidak di isi maka tidak bisa menyimpan
            if (name.isEmpty() || address.isEmpty() || owner.isEmpty()) {
                Toast.makeText(context, "Nama gudang tidak boleh kosong", Toast.LENGTH_SHORT).show()
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
}