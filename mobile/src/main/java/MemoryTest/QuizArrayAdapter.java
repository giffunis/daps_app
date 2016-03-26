package MemoryTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.giffunis.dapsapp.R;

import java.util.List;


/**
 * Created by drcaspa on 23/3/16.
 * email: giffunis@gmail.com
 */
public class QuizArrayAdapter extends ArrayAdapter<Quizes>{

    public QuizArrayAdapter(Context context, List<Quizes> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(R.layout.quiz_list_item, parent, false);
        }

        //Obteniendo instancias de los elementos
        TextView titulo = (TextView)listItemView.findViewById(R.id.text1);
        ImageView categoria = (ImageView)listItemView.findViewById(R.id.category);

        //Obteniendo instancia de la Tarea en la posición actual
        Quizes quiz = getItem(position);

        titulo.setText(quiz.getTestName());
        categoria.setImageResource(R.drawable.ic_tests);

        return listItemView;
    }
}