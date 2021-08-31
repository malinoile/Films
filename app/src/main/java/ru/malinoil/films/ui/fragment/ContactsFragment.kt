package ru.malinoil.films.ui.fragment

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.malinoil.films.R
import ru.malinoil.films.databinding.FragmentContactsBinding
import ru.malinoil.films.model.entities.ContactEntity
import ru.malinoil.films.ui.ContactsAdapter

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter: ContactsAdapter by lazy { ContactsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactsRecycler.adapter = adapter
        binding.contactsRecycler.layoutManager = LinearLayoutManager(requireContext())
        requestPermission()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun requestPermission() {
        val permission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranded ->
            when {
                isGranded -> {
                    getContactsByContentProvider()
                }
                else -> {
                    binding.errorTextView.setText(R.string.contacts_error)
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.cancel_contacts_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        permission.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun getContactsByContentProvider() {
        context?.apply {
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let {
                for (i in 0..it.count) {
                    if (it.moveToPosition(i)) {
                        adapter.addOnList(
                            ContactEntity(
                                it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            )
                        )
                    }
                }
            }
            cursor?.close()
        }
    }

}