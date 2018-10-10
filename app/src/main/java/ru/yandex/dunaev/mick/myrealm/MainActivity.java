package ru.yandex.dunaev.mick.myrealm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {

            RealmResults<MyBook> books;

            @Override
            public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
                mRealm.beginTransaction();
                books = mRealm.where(MyBook.class).findAll();
                mRealm.commitTransaction();

                books.addChangeListener(new RealmChangeListener<RealmResults<MyBook>>() {
                    @Override
                    public void onChange(RealmResults<MyBook> myBooks) {
                        notifyDataSetChanged();
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                FrameLayout fl = (FrameLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_book,viewGroup,false);
                return new RecyclerView.ViewHolder(fl) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                FrameLayout fl = (FrameLayout)viewHolder.itemView;
                TextView tv = fl.findViewById(R.id.titleBook);

                fl.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(MainActivity.this,"Delete book",Toast.LENGTH_SHORT).show();
                        mRealm.beginTransaction();
                        books.get(i).deleteFromRealm();
                        mRealm.commitTransaction();
                        return true;
                    }
                });

                tv.setText(books.get(i).getTitle());
            }

            @Override
            public int getItemCount() {
                return books.size();
            }
        });
    }

    @OnClick(R.id.addButton)
    public void OnAddClick(View view){
        startActivity(new Intent(this, AddBookActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
