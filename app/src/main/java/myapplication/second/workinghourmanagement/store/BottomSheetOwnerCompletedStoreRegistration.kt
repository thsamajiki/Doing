package myapplication.second.workinghourmanagement.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetOwnerStoreCompleteRegistrationBinding

class BottomSheetOwnerCompletedStoreRegistration: BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: BottomSheetOwnerStoreCompleteRegistrationBinding? = null
    private val binding: BottomSheetOwnerStoreCompleteRegistrationBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetOwnerStoreCompleteRegistrationBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onClick(view: View) {
    }
}