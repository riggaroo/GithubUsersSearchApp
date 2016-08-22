package za.co.riggaroo.gus.presentation.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import za.co.riggaroo.gus.R;
import za.co.riggaroo.gus.data.remote.model.User;

class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final Context context;
    private List<User> items;

    UsersAdapter(List<User> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User item = items.get(position);

        holder.textViewBio.setText(item.getBio());
        if (item.getName() != null) {
            holder.textViewName.setText(item.getLogin() + " - " + item.getName());
        } else {
            holder.textViewName.setText(item.getLogin());
        }
        Picasso.with(context).load(item.getAvatarUrl()).into(holder.imageViewAvatar);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    void setItems(List<User> githubUserList) {
        this.items = githubUserList;
        notifyDataSetChanged();
    }
}