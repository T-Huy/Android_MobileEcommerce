package vn.iotstar.AppMobileEcommerce.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import vn.iotstar.AppMobileEcommerce.R;

/* loaded from: classes.dex */
public class FourthFragment extends Fragment {
    private View view;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_fourth, viewGroup, false);
        this.view = inflate;
        return inflate;
    }
}
