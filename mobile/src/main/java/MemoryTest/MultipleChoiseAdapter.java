package MemoryTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * Created by drcaspa on 12/4/16.
 * email: giffunis@gmail.com
 */
public class MultipleChoiseAdapter extends ArrayAdapter<String> {

    public MultipleChoiseAdapter(Context context, ArrayList<String> answers) {
        super(context, 0, answers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(R.layout.fragment_multiple_choise, parent, false);
        }

        //Obteniendo instancias de los elementos
        CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.checkbox);
        ImageView categoria = (ImageView)listItemView.findViewById(R.id.category);

        //Obteniendo instancia de la Tarea en la posición actual
        String answer = getItem(position);

        checkBox.setText(answer);
        categoria.setImageResource(R.drawable.ic_tests);

        return listItemView;
    }
}
