package consultancy.com.android.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import consultancy.com.android.R;
import consultancy.com.android.User;

/**
 * Created by lalit on 10/10/2016.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewFirstName.setText(listUsers.get(position).getFirst_name());
        holder.textViewLastName.setText(listUsers.get(position).getLast_name());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
        holder.textViewPhoneno.setText(listUsers.get(position).getPhoneno());
        holder.textViewGender.setText(listUsers.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewFirstName;
        public AppCompatTextView textViewLastName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;
        public AppCompatTextView textViewPhoneno;
        public AppCompatTextView textViewGender;
        public UserViewHolder(View view) {
            super(view);
            textViewFirstName = (AppCompatTextView) view.findViewById(R.id.text_firstname);
            textViewLastName = (AppCompatTextView) view.findViewById(R.id.text_lastname);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.text_emailaddress);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.text_password);
            textViewPhoneno = (AppCompatTextView) view.findViewById(R.id.text_phonenumber);
            textViewGender = (AppCompatTextView) view.findViewById(R.id.text_gender);
        }
    }


}
