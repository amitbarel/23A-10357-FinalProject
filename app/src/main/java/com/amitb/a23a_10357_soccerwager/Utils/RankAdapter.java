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
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private Context context;
    private ArrayList<User> participants;
    private User user;
    private FirebaseDatabase fbdb = FirebaseDatabase.getInstance();

    public RankAdapter(Context context,ArrayList<User> participants,User user){
        this.context = context;
        this.participants = participants;
        this.user = user;
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{

        private TextView leagueName,rank;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueName = itemView.findViewById(R.id.name_section);
            rank = itemView.findViewById(R.id.points_section);
        }
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item,parent,false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        List <String> keys = (List<String>) user.getLeagues().keySet();
        holder.leagueName.setText(keys.get(position));
        holder.rank.setText(participants.indexOf(user)+1+"");
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
