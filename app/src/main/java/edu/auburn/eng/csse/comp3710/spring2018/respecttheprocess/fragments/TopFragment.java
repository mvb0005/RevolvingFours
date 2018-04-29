package edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.fragments;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.activities.MainActivity;
import edu.auburn.eng.csse.comp3710.spring2018.respecttheprocess.R;

public class TopFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button resetButton;
    private Spinner spin1;
    private Spinner spin2;
    private List<String> colors;
    private int callCount;
    private TextView statusTextView;

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
            if (adapterView == getView().findViewById(R.id.player1Spinner)) {
                player = 1;
            } else if (adapterView == getView().findViewById(R.id.player2Spinner)) {
                player = 2;
            }
            int color = getColorInt(i);
            if (callCount >= 2) {
                mListener.changeColor(player, color);
            }
            callCount++;
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
        colors = new ArrayList<String>();
        colors.add("Black");
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Magenta");
        colors.add("Cyan");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, colors) {
            @Override
            public boolean isEnabled(int position){
                if (getColorInt(position) == ((MainActivity)getActivity()).getGameBoardView().getColor2()) {        // disable the color if the other player is using it
                    return false;
                }
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (getColorInt(position) == ((MainActivity)getActivity()).getGameBoardView().getColor2()) {
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, colors) {
            @Override
            public boolean isEnabled(int position){
                if (getColorInt(position) == ((MainActivity)getActivity()).getGameBoardView().getColor1()) {        // disable the color if the other player is using it
                    return false;
                }
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (getColorInt(position) == ((MainActivity)getActivity()).getGameBoardView().getColor1()) {
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1 = (Spinner) view.findViewById(R.id.player1Spinner);
        spin2 = (Spinner) view.findViewById(R.id.player2Spinner);
        spin1.setAdapter(adapter1);
        spin2.setAdapter(adapter2);
        spin1.setOnItemSelectedListener(spinListener);
        spin2.setOnItemSelectedListener(spinListener);
        spin1.setSelection(0);
        spin2.setSelection(1);
        resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(resetListener);
        statusTextView = view.findViewById(R.id.statusTextView);
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

    public void updateStatus(int[] winner) {
        Log.d("Test", String.valueOf(winner[0]) + " " + String.valueOf(winner[1]));
        if (winner[0] + Math.abs(winner[1]) == 0){
            statusTextView.setText("");
        } else {
            if (winner[0] == Math.abs(winner[1])){
                statusTextView.setText(winner[0] + "   TIE   " + Math.abs(winner[1]));
            } else if (winner[0] > Math.abs(winner[1])){
                statusTextView.setText("Player 1 Wins");
            } else {
                statusTextView.setText("Player 2 Wins");
            }
        }
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

    public int getColorInt(int position) {
        int resultColor = 0;
        switch(position){
            case 0:
                resultColor = Color.BLACK;
                break;
            case 1:
                resultColor = Color.RED;
                break;
            case 2:
                resultColor = Color.BLUE;
                break;
            case 3:
                resultColor = Color.GREEN;
                break;
            case 4:
                resultColor = Color.YELLOW;
                break;
            case 5:
                resultColor = Color.MAGENTA;
                break;
            case 6:
                resultColor = Color.CYAN;
                break;
        }
        return resultColor;
    }
}
