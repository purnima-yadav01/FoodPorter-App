package com.food.foodporterapplication.customer.bottomsheet

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.addnewaddressapi.AddNewAddressActivity
import com.food.foodporterapplication.customer.activity.addnewaddressapi.OnDeleteAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.UpdateAddressListener
import com.food.foodporterapplication.customer.activity.addnewaddressapi.deleteadddress.DeleteAddressModelView
import com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi.SavedAddressResponse
import com.food.foodporterapplication.customer.activity.location.LocationActivity
import com.food.foodporterapplication.customer.adapter.AddAddressAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBottomSheet(private val addressList: MutableList<SavedAddressResponse.Datum>) :
    BottomSheetDialogFragment(), OnDeleteAddressListener, UpdateAddressListener {

    private val deleteAddressModelView: DeleteAddressModelView by viewModels()
    private lateinit var adapter: AddAddressAdapter
    private var deletingPosition: Int = -1

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout?
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
                behavior.isDraggable = true
                it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                it.requestLayout()
            }
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.add_address_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.addAddressrecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AddAddressAdapter(requireContext(), addressList, this, this)
        recyclerView.adapter = adapter

        deleteAddressModelView.mDeleteAddressResponse.observe(viewLifecycleOwner) {
            val response = it.getContentIfNotHandled()
            if (response?.success == true) {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                adapter.removeItem(deletingPosition)
            } else {
                Toast.makeText(
                    requireContext(),
                    response?.message ?: "Failed to delete address",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.findViewById<View>(R.id.dropDownImg).setOnClickListener {
            dialog?.dismiss()
        }

        view.findViewById<View>(R.id.changeAddressText).setOnClickListener {
            startActivity(Intent(requireContext(), LocationActivity::class.java))
            dismiss()
        }

        view.findViewById<View>(R.id.addNewAddressConst).setOnClickListener {
            startActivity(Intent(requireContext(), AddNewAddressActivity::class.java))
            dismiss()
        }
    }

    override fun onDeleteClick(position: Int, deleteAddressId: Int) {
        deletingPosition = position
        AlertDialog.Builder(requireContext())
            .setTitle("Delete address")
            .setMessage("Are you sure you want to delete this address?")
            .setPositiveButton("Yes") { _, _ ->
                deleteAddressModelView.deleteAddUser(requireActivity(), deleteAddressId)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun updateAddressClick(position: Int, updateAddressId: Int) {
        val intent = Intent(requireContext(), AddNewAddressActivity::class.java)
        val json = Gson().toJson(addressList[position])
        intent.putExtra("address_data", json)
        startActivity(intent)
        dismiss()
    }

}


