package com.example.statusnewwatsaap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Photos extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    // private static final int NUM_COLUMNS=2;
    FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_layout);
        FirebaseApp.initializeApp(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("post list");

        mRecyclerView = findViewById(R.id.rrecycleview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference("data");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Model(snapshot.child("id").getValue().toString(),
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("desc").getValue().toString());
                            }
                        })
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.raw, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Model model) {
                holder.setDetails(Photos.this, model.getImage());
                //holder.setTxtTitle(model.getmTitle());
                //holder.setTxtDesc(model.getmDesc());

//                holder.root.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        };

//                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(
//                        Model.class,
//                        R.layout.raw,
//                        ViewHolder.class,
//                        mRef
//
//                ) {
//                    @Override
//                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
//                        viewHolder.setDetails(getApplicationContext(), model.getImage());
//                    }
//
//                    @Override
//                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
//                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//                                Model model = firebaseRecyclerAdapter.getItem(position);
//                                Intent intent = new Intent(view.getContext(), Details.class);
//                                intent.putExtra("model", model);
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onItemLongClick(View view, int position) {
////TODO YOUR ONE IMPLIMENTATION ON LONG ITEM CLICK
//                            }
//                        });
//                        return viewHolder;
//                    }
//                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

}
