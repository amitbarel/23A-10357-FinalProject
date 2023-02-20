package com.amitb.a23a_10357_soccerwager.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amitb.a23a_10357_soccerwager.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private Context context;
    private ArrayList<User> users;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();

    public LeagueAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    public class LeagueViewHolder extends RecyclerView.ViewHolder{

        private TextView username,points;

        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name_section);
            points = itemView.findViewById(R.id.points_section);
        }
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item,parent,false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.points.setText(user.getScore());
    }

    @Override
    public int getItemCount() {return users == null? 0: users.size();
    }
}
