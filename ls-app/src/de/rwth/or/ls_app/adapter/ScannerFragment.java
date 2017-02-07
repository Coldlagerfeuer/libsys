/**
 * 
 */
package de.rwth.or.ls_app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.rwth.or.ls_app.R;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class ScannerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_scanner, container, false);
         
        return rootView;
    }
}