package tieorange.com.pjabuffet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

  @BindView(R.id.recycler) RecyclerView recycler;
  private MenuAdapter mAdapter;

  public MenuFragment() {
    // Required empty public constructor
  }

  public static MenuFragment newInstance() {
    Bundle args = new Bundle();
    MenuFragment fragment = new MenuFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_menu, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initRecycler();
  }

  private void initRecycler() {
    int spanCount;
    int orientation = getResources().getConfiguration().orientation;
    if (orientation == ORIENTATION_PORTRAIT) {
      spanCount = 2;
    } else {
      spanCount = 3;
    }

    recycler.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
    int spacing = 40;
    //recycler.addItemDecoration(new SpacesItemDecoration(spacing, spacing, spacing, spacing));
    recycler.addItemDecoration(new GridItemSpacingDecorator(spanCount, spacing));

    initAdapter();
  }

  private void initAdapter() {
    mAdapter = new MenuAdapter(getContext());
    recycler.setAdapter(mAdapter);
  }
}
