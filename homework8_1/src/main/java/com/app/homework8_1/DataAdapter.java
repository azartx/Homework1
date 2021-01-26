package com.app.homework8_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {

    private final LayoutInflater inflater;
    private final ArrayList<ContactBody> contacts;
    private final ArrayList<ContactBody> contactsCopy;
    private final DataAdapter.OnContactClickListener onContactClickListener;

    DataAdapter(Context context, ArrayList<ContactBody> contacts, DataAdapter.OnContactClickListener onContactClickListener) {
        this.contacts = contacts;
        contactsCopy = new ArrayList<>(contacts);
        this.inflater = LayoutInflater.from(context);
        this.onContactClickListener = onContactClickListener;
    }

    public interface OnContactClickListener {
        void onContactClick(ContactBody contactBody, int position);
    }

    public ArrayList<ContactBody> getContacts() {
        return contacts;
    }

    public void add(ContactBody contact) {
        contacts.add(contact);
        contactsCopy.add(contact);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        contacts.remove(position);
        contactsCopy.remove(position);
        notifyDataSetChanged();
    }

    public void edit(ContactBody contact, int position) {
        contacts.set(position, contact);
        contactsCopy.set(position, contact);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout_contact_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        ContactBody contactBody = contacts.get(position);
        holder.bind(contactBody, onContactClickListener, holder);
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, numberView;
        final ConstraintLayout layoutParent;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            nameView = view.findViewById(R.id.name);
            numberView = view.findViewById(R.id.numberOrEmail);
            layoutParent = view.findViewById(R.id.itemBody);
        }

        private void bind(ContactBody contact,
                          OnContactClickListener onContactClickListener,
                          @NonNull DataAdapter.ViewHolder holder) {

            holder.imageView.setImageResource(contact.getImage());
            holder.nameView.setText(contact.getContactName());
            holder.numberView.setText(contact.getEmailOrNumber());
            holder.layoutParent.setOnClickListener(v -> onContactClickListener.onContactClick(contact, getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private final Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ContactBody> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactsCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ContactBody item : contactsCopy) {
                    if (item.getContactName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (contacts != null) {
                contacts.clear();
                contacts.addAll((ArrayList) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
