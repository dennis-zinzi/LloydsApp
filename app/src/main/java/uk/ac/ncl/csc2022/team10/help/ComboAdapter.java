package uk.ac.ncl.csc2022.team10.help;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.lloydsapp.R;

/**
 * Created by Dennis on 15/4/15.
 */
public class ComboAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] contacts;
    private final Integer[] contactImages;

    public ComboAdapter(Activity context, String[] contacts, Integer[] contactImages) {
        super(context, R.layout.combo_list, contacts);
        this.context = context;
        this.contacts = contacts;
        this.contactImages = contactImages;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.combo_list, null, true);
        TextView textPart = (TextView) rowView.findViewById(R.id.textPart);
        ImageView imagePart = (ImageView) rowView.findViewById(R.id.imagePart);
        textPart.setText(contacts[position]);
        imagePart.setImageResource(contactImages[position]);
        return rowView;
    }
}
