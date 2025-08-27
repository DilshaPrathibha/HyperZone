package com.example.hyperzone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyperzone.R
import com.example.hyperzone.cart.CartManager
import com.example.hyperzone.databinding.FragmentCartBinding
import com.example.hyperzone.ui.cart.CartAdapter

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CartAdapter
    private val localItems = mutableListOf<com.example.hyperzone.data.model.Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CartAdapter(localItems) { index ->
            CartManager.removeAt(index)
        }

        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter

        // Observe items -> update list, total, empty state
        CartManager.itemsLiveData.observe(viewLifecycleOwner) { list ->
            localItems.clear()
            localItems.addAll(list)
            adapter.notifyDataSetChanged()
            updateTotal()
            toggleEmpty()
        }

        // Initial render if there are existing items
        localItems.clear()
        localItems.addAll(CartManager.items)
        adapter.notifyDataSetChanged()
        updateTotal()
        toggleEmpty()

        binding.btnCheckout.setOnClickListener {
            startActivity(
                android.content.Intent(
                    requireContext(),
                    com.example.hyperzone.ui.checkout.PaymentMethodActivity::class.java
                )
            )
        }
    }

    private fun updateTotal() {
        binding.tvTotal.text = getString(R.string.total_format, CartManager.total())
    }

    private fun toggleEmpty() {
        val empty = localItems.isEmpty()
        binding.emptyState.visibility = if (empty) View.VISIBLE else View.GONE
        binding.recyclerCart.visibility = if (empty) View.GONE else View.VISIBLE
        binding.totalBar.visibility = if (empty) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
