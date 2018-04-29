package edu.auburn.respectedprocess.revolvingfour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TopFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button resetButton;
    private Spinner spin1;
    private Spinner spin2;

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mListener.reset();
        }
    };

    private AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            int player = 0;
            if (view == getView().findViewById(R.id.player1Spinner)) {
                player = 1;
            } else if (view == getView().findViewById(R.id.player2Spinner)) {
                player = 2;
            }
            int color = 1;
            mListener.changeColor(player, color);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public TopFragment() {
        // Required empty public constructor
    }

    public static TopFragment newInstance() {
        TopFragment fragment = new TopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        List<String> colors = new ArrayList<String>();
        colors.add("Black");
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Magenta");
        colors.add("Cyan");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1 = (Spinner) view.findViewById(R.id.player1Spinner);
        spin2 = (Spinner) view.findViewById(R.id.player2Spinner);
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapter);
        spin1.setSelection(0);
        spin2.setSelection(1);
        spin1.setOnItemSelectedListener(spinListener);
        spin2.setOnItemSelectedListener(spinListener);
        resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(resetListener);
        return view;
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
        void reset();
        void changeColor(int player, int color);
    }

    public void updatePlayer(int player){
        spin1.setVisibility(player > 0 ? View.INVISIBLE : View.VISIBLE);
        spin2.setVisibility(player < 0 ? View.INVISIBLE : View.VISIBLE);
    }
}
