package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class ExibitListActivity extends SingleFragmentActivity
        implements ExibitListFragment.Callbacks, ExibitFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ExibitListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Exibit exibit) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ExibitPagerActivity.newIntent(this, exibit.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = ExibitFragment.newInstance(exibit.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Exibit exibit) {
        ExibitListFragment listFragment = (ExibitListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
