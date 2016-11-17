package tieorange.com.pjabuffet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tieorange.com.pjabuffet.R;

public class LineActivity extends AppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecycler();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        initAdapter();
    }

    private void initAdapter() {
//        FirebaseRecyclerAdapter
    }


    public class ViewHolderLineOrder extends RecyclerView.ViewHolder {
        @BindView(R.id.productAmount)
        TextView mProductsAmmount;

        public ViewHolderLineOrder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
