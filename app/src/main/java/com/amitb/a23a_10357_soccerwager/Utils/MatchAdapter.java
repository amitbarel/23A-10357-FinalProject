package com.amitb.a23a_10357_soccerwager.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.amitb.a23a_10357_soccerwager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MatchAdapter {

    private ArrayList<Match> fixtures;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    public void loadFixture(){
        DatabaseReference ref = db.getReference("fixture");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot match:snapshot.getChildren()) {
                    fixtures.add(match.getValue(Match.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    };

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
        MatchViewHolder mvh = new MatchViewHolder(view);
        return mvh;
    }

    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = fixtures.get(position);
        holder.team1.setText(match.getTeam1());
        holder.team2.setText(match.getTeam2());
    }

}
