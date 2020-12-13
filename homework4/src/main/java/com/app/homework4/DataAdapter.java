package com.app.homework4;

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
        contactsCopy = contacts;
        this.inflater = LayoutInflater.from(context);
        this.onContactClickListener = onContactClickListener;
    }

    public ArrayList<ContactBody> getContactsCopy() {
        return contactsCopy;
    }

    public interface OnContactClickListener {
        void onContactClick(ContactBody contactBody, int position);
    }

    public ArrayList<ContactBody> getContacts() {
        return contacts;
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

        holder.imageView.setImageResource(contactBody.getImage());
        holder.nameView.setText(contactBody.getContactName());
        holder.numberView.setText(contactBody.getEmailOrNumber());

        bind(contactBody, onContactClickListener, holder, getItemCount());

    }

    private void bind(ContactBody contact,
                      OnContactClickListener onContactClickListener,
                      @NonNull DataAdapter.ViewHolder holder,
                      int position) {

        holder.layoutParent.setOnClickListener(v -> onContactClickListener.onContactClick(contact, position));
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

    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    public Filter contactFilter = new Filter() {
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

            contacts.clear();
            contacts.addAll((ArrayList) results.values);
            notifyDataSetChanged();

        }
    };


}
