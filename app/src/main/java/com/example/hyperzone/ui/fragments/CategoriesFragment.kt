package com.example.hyperzone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hyperzone.data.SampleData
import com.example.hyperzone.adapters.ProductAdapter
import com.example.hyperzone.databinding.FragmentCategoriesBinding
import com.example.hyperzone.data.model.Product
import com.google.android.material.chip.Chip

class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private val allProducts: MutableList<Product> = SampleData.products.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupChipGroup()
        // Initially show all products
        updateProducts(allProducts)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList(), onItemClick = { /* Handle item click */ })
        binding.recyclerCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupChipGroup() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull() ?: -1
            val chip = group.findViewById<Chip>(selectedChipId)
            chip?.let { filterProducts(it.text.toString()) } ?: filterProducts("")
        }
    }

    private fun filterProducts(filter: String) {
        val filteredList = if (filter.isBlank() || filter.equals("All", ignoreCase = true)) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.name.contains(filter, ignoreCase = true)
            }
        }
        updateProducts(filteredList)
    }

    private fun updateProducts(products: List<Product>) {
        productAdapter.updateProducts(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}