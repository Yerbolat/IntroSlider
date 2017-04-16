package info.androidhive.introslider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by NURAKHMET on 11.04.2017.
 */

public class ExamFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.exam_fragment,container,false);
        ImageButton logarithm = (ImageButton) rootView.findViewById(R.id.logarithm);
        ImageButton equation = (ImageButton)rootView.findViewById(R.id.equation);
        ImageButton derivative = (ImageButton)rootView.findViewById(R.id.derivative);

        logarithm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Equation.class));
            }
        });
        equation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Logarithm.class));
            }
        });
        derivative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Derivative.class));
            }
        });
        return rootView;

    }
}