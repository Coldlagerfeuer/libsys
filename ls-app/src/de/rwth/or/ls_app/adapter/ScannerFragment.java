/**
 * 
 */
package de.rwth.or.ls_app.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import android.content.Intent;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_scanner, container, false);

		ImageView myImageView = (ImageView) rootView.findViewById(R.id.imgview);
		Bitmap myBitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
				R.drawable.puppy);
		myImageView.setImageBitmap(myBitmap);

		BarcodeDetector detector = new BarcodeDetector.Builder(getActivity().getApplicationContext())
				.setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();
		if (!detector.isOperational()) {
			// txtsView.setText("Could not set up the detector!");
			return null;
		}

		Button btn = (Button) rootView.findViewById(R.id.button);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
		SparseArray<Barcode> barcodes = detector.detect(frame);
		
		Barcode thisCode = barcodes.valueAt(0);
		TextView txtView = (TextView) rootView.findViewById(R.id.txtContent);
		txtView.setText(thisCode.rawValue);
		
		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {

		super.onResume();
	}
}