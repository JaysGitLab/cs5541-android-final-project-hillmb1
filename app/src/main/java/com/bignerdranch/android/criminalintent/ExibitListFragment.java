package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ExibitListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private ExibitAdapter mAdapter;
    private boolean mSubtitleVisible;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onCrimeSelected(Exibit exibit);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Exibit exibit = new Exibit();
                ExibitLab.get(getActivity()).addCrime(exibit);
                updateUI();
                mCallbacks.onCrimeSelected(exibit);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        ExibitLab exibitLab = ExibitLab.get(getActivity());
        int exibitCount = exibitLab.getExibits().size();
        String subtitle = getString(R.string.subtitle_format, exibitCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        ExibitLab exibitLab = ExibitLab.get(getActivity());
        List<Exibit> exibits = exibitLab.getExibits();

        if (mAdapter == null) {
            mAdapter = new ExibitAdapter(exibits);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setExibits(exibits);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    /*public void sort(String gal) {
        ExibitLab exibitLab = ExibitLab.get(getActivity());
        List<Exibit> exibits = exibitLab.getExibits();
        List<Exibit> temp = new ArrayList<>();
        int index = 0;
        for(int i = 0; i < exibits.size(); i++)
        {
            if (exibits.get(i). == )
        }
        if (mAdapter == null) {
            mAdapter = new ExibitAdapter(exibits);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setExibits(exibits);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }*/

    private class ExibitHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private ImageView mImage;

        private Exibit mExibit;

        public ExibitHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            //mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mImage = (ImageView) itemView.findViewById(R.id.crime_photo2);
        }

        public void bindCrime(Exibit exibit) {
            mExibit = exibit;
            mTitleTextView.setText(mExibit.getTitle());
           // mDateTextView.setText(mExibit.getDate().toString());
            mSolvedCheckBox.setChecked(mExibit.isFaved());
            File mPhotoFile = ExibitLab.get(getActivity()).getPhotoFile(mExibit);
            Bitmap bitmap = PictureUtils.getScaledBitmap(
            mPhotoFile.getPath(), getActivity());
            mImage.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onCrimeSelected(mExibit);
        }
    }

    private class ExibitAdapter extends RecyclerView.Adapter<ExibitHolder> {

        private List<Exibit> mExibits;

        public ExibitAdapter(List<Exibit> exibits) {
            mExibits = exibits;
        }

        @Override
        public ExibitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.
                    inflate(R.layout.list_item_crime, parent, false);
            return new ExibitHolder(view);
        }

        @Override
        public void onBindViewHolder(ExibitHolder holder, int position) {
            Exibit exibit = mExibits.get(position);
            holder.bindCrime(exibit);
        }

        @Override
        public int getItemCount() {
            return mExibits.size();
        }

        public void setExibits(List<Exibit> exibits) {
            mExibits = exibits;
        }
    }
}
