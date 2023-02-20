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

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private Context context;
    private ArrayList<String> leagues;

    public RankAdapter(Context context,ArrayList<String> leagues){
        this.context = context;
        this.leagues = leagues;
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{

        private TextView leagueName;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueName = itemView.findViewById(R.id.name_section);
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
        holder.leagueName.setText(leagues.get(position));
    }

    @Override
    public int getItemCount() {
        return leagues == null? 0: leagues.size();
    }


}
