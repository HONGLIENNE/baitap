import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class contactAdapter  extends BaseAdapter {
    private  Context context;
    private ArrayList<contactModel>arrayContact;
    public contactAdapter (context conxt, ArrayList<contactModel>array){
    }@Override
    public int getCount(){
        return 0;
    }
    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int postision){
        return 0;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent){
        return null;
    }
}
