package com.app.homework4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ContactBody> contacts;
    Context context;
    private final DataAdapter.OnContactClickListener onContactClickListener;

    DataAdapter(Context context, List<ContactBody> contacts, DataAdapter.OnContactClickListener onContactClickListener) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onContactClickListener = onContactClickListener;
    }

    public interface OnContactClickListener {
        void onContactClick(ContactBody contactBody, int position);
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

        bind(contacts.get(position), onContactClickListener, holder, position);

    }

    private void bind(ContactBody contact,
                      OnContactClickListener onContactClickListener,
                      @NonNull DataAdapter.ViewHolder holder,
                      int position) {

        holder.layoutParent.setOnClickListener(v -> onContactClickListener.onContactClick(contact, position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
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


}
