package edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RotationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RotationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RotationFragment extends Fragment {

    private Button rotateLeftButton;
    private Button rotate180Button;
    private Button rotateRightButton;
    private OnFragmentInteractionListener mListener;
    private View.OnClickListener rotateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rotateleft:
                    mListener.rotate(0);
                    break;
                case R.id.rotate180:
                    mListener.rotate(1);
                    break;
                case R.id.rotateright:
                    mListener.rotate(2);
            }
        }
    };

    public RotationFragment() {
    }


    public static RotationFragment newInstance() {
        RotationFragment fragment = new RotationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_rotation, container, false);
        Button rotateLeftButton = myView.findViewById(R.id.rotateleft);;
        Button rotate180Button = myView.findViewById(R.id.rotate180);
        Button rotateRightButton = myView.findViewById(R.id.rotateright);
        rotateLeftButton.setOnClickListener(rotateListener);
        rotate180Button.setOnClickListener(rotateListener);
        rotateRightButton.setOnClickListener(rotateListener);
        return myView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void rotate(int direction);
    }
}
