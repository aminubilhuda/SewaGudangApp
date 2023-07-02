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


        val  adapter = WarehouseListAdapter { warehouse ->
//            ini list yang bisa di klik jadi data warehouse tidak null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(warehouse)
            findNavController().navigate(action)
        }


        binding.dataRecycleView.adapter = adapter
        binding.dataRecycleView.layoutManager = LinearLayoutManager(context)
        warehouseViewModel.allWarehouse.observe(viewLifecycleOwner) {warehouses ->
            warehouses.let {
                if (warehouses.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationImageView.visibility = View.VISIBLE
                }else {
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationImageView.visibility = View.GONE
                }
                adapter.submitList(warehouses)
            }
        }

        binding.addFAB.setOnClickListener {
            // ini button tambah jadi warehouse pasti null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}