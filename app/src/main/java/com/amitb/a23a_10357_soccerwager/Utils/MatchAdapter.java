package com.amitb.a23a_10357_soccerwager.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.amitb.a23a_10357_soccerwager.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private Context context;
    private ArrayList<Match> fixtures;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    public MatchAdapter(Context context,ArrayList<Match> fixtures) {
        this.context = context;
        this.fixtures = fixtures;
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView team1;
        private AppCompatTextView team2;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            team1 = itemView.findViewById(R.id.TXT_team1);
            team2 = itemView.findViewById(R.id.TXT_team2);
        }
    }

    @NonNull
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchup_item,parent,false);
        return new MatchViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = fixtures.get(position);
        holder.team1.setText(match.getTeam1().getName());
        holder.team2.setText(match.getTeam2().getName());
    }

    @Override
    public int getItemCount() {return fixtures == null? 0: fixtures.size();
    }

}