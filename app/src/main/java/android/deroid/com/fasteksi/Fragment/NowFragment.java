package android.deroid.com.fasteksi.Fragment;

import android.deroid.com.fasteksi.Adapter.AutoCompleteTextForArea;
import android.deroid.com.fasteksi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

/**
 * Created by gulshank on 19-02-2016.
 */
public class NowFragment extends Fragment  implements AdapterView.OnItemClickListener{
    View view;
    AutoCompleteTextView dropArea;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now , container ,false);
        initViews();
        dropArea.setAdapter(new AutoCompleteTextForArea(getContext() ,android.R.layout.simple_list_item_1));
        dropArea.setOnItemClickListener(this);
        return view;
    }

    private void initViews() {
        dropArea = (AutoCompleteTextView) view.findViewById(R.id.edittxt_droparea);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String string = (String) parent.getItemAtPosition(position);
        Toast.makeText(getContext() ,string ,Toast.LENGTH_SHORT).show();
    }
}
