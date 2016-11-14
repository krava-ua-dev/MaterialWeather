package com.krava.dagger2test.presentation.view.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.krava.dagger2test.presentation.di.HasComponent;

/**
 * Created by krava2008 on 31.10.16.
 */

public abstract class BaseFragment extends Fragment {

    protected void showSnackbarMessage(View target, String message) {
        Snackbar.make(target, message, 2000).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
