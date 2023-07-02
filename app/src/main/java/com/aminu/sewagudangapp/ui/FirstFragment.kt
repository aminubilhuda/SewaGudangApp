package com.aminu.sewagudangapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminu.sewagudangapp.R
import com.aminu.sewagudangapp.application.WarehouseApp
import com.aminu.sewagudangapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val warehouseViewModel: WarehouseViewModel by viewModels {
        WarehouseViewModelFactory((applicationContext as WarehouseApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  adapter = WarehouseListAdapter {}
        binding.dataRecycleView.adapter = adapter
        binding.dataRecycleView.layoutManager = LinearLayoutManager(context)
        warehouseViewModel.allWarehouse.observe(viewLifecycleOwner) {warehouses ->
            warehouses.let {
                adapter.submitList(warehouses)
            }
        }

        binding.addFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}