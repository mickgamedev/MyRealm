package ru.yandex.dunaev.mick.myrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import io.realm.Realm;

public class AddBookActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText titleBook;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mRealm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
    }

    @OnEditorAction(R.id.editText)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_DONE){

            mRealm.beginTransaction();
            MyBook book = mRealm.createObject(MyBook.class);
            book.setTitle(titleBook.getText().toString());
            mRealm.commitTransaction();

            finish();
            return true;
        } else return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
