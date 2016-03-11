package android.deroid.com.fasteksi.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.deroid.com.fasteksi.Adapter.AutoCompleteTextForArea;
import android.deroid.com.fasteksi.R;
import android.deroid.com.fasteksi.Services.AddressResultReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by gulshank on 19-02-2016.
 */
public class LaterFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    View view;
    TextView editlocation;
    AutoCompleteTextView droparea;
    Button btnContinue;
    AutoCompleteTextView currentLocation;
    CheckBox chkBoxRetainCab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_later ,container ,false);
            initViews();
            currentLocation.setText(MapsFragment.results);
            droparea.setAdapter(new AutoCompleteTextForArea(getActivity(), android.R.layout.simple_list_item_1));
            droparea.setOnItemClickListener(this);
            editlocation.setOnClickListener(this);
            chkBoxRetainCab.setOnClickListener(this);
            btnContinue.setOnClickListener(this);
            return view;
        }else
            return view;
    }

    private void initViews() {
        currentLocation = (AutoCompleteTextView)view.findViewById(R.id.location);
        droparea = (AutoCompleteTextView) view.findViewById(R.id.edittxt_droparea);
        editlocation = (TextView)view.findViewById(R.id.txt_edit);
        chkBoxRetainCab = (CheckBox) view.findViewById(R.id.checkbox_retaincab);
        btnContinue = (Button)view.findViewById(R.id.btn_continue);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(getContext() ,str ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.txt_edit:
                editLocation();
                break;
            case R.id.checkbox_retaincab:
                hourdialog();
                break;
            case R.id.btn_continue:
//                Fragment fragment = new WhenCabTime();
//                FragmentManager fm = getFragmentManager();
//                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.content_frame, fragment);
//                ft.commit();
                Toast.makeText(getContext() ,"Continue" ,Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void hourdialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.hours_dialog, null);
      /*  TimePicker hour = (TimePicker)view.findViewById(R.id.timepicker);
        hour.setCurrentHour(12);
        hour.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });*/
        final NumberPicker numberPicker = (NumberPicker)view.findViewById(R.id.numberpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        AlertDialog alertDialog  = builder.create();
        alertDialog.show();
    }


    private void editLocation() {
        Toast.makeText(getContext() ,"Edit" ,Toast.LENGTH_SHORT).show();
        currentLocation.setText("");
        currentLocation.setAdapter(new AutoCompleteTextForArea(getActivity(), android.R.layout.simple_list_item_1));
        currentLocation.setOnItemClickListener(this);
    }
}
