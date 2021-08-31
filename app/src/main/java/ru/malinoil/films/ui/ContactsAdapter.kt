package ru.malinoil.films.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.films.R
import ru.malinoil.films.databinding.ContactCardBinding
import ru.malinoil.films.model.entities.ContactEntity

class ContactsAdapter : RecyclerView.Adapter<ContactHolder>() {
    private var list: MutableList<ContactEntity> = emptyList<ContactEntity>().toMutableList()

    fun addOnList(contact: ContactEntity) {
        list.add(contact)
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(parent)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class ContactHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
) {
    private val binding: ContactCardBinding by lazy { ContactCardBinding.bind(itemView) }

    fun bind(contact: ContactEntity) {
        binding.contactNameTextView.text = contact.fullName
    }

}