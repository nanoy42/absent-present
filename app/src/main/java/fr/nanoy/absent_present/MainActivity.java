package fr.nanoy.absent_present;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView presence_possible;
    private TextView presence_reel;
    private TextView T_pour_abs;
    private TextView T_pour_pres;
    private EditText edit_nb_eleve;
    private EditText edit_demi_jour;
    private EditText edit_nb_abs;
    private Button button;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        presence_possible = (TextView) findViewById(R.id.textView);
        presence_reel = (TextView) findViewById(R.id.textView2);
        T_pour_abs = (TextView) findViewById(R.id.textView3);
        T_pour_pres = (TextView) findViewById(R.id.textView4);

        edit_nb_eleve = (EditText) findViewById(R.id.editText);
        edit_demi_jour = (EditText) findViewById(R.id.editText2);
        edit_nb_abs = (EditText) findViewById(R.id.editText3);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        button.setOnClickListener(myhandler);


    }

    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
            if (v == button) {
                if(edit_nb_eleve.getText().length()==0 || edit_demi_jour.getText().length() == 0 || edit_nb_abs.getText().length() == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Un des champs est vide";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                int nb_eleve = Integer.parseInt(edit_nb_eleve.getText().toString());
                int demi_jour = Integer.parseInt(edit_demi_jour.getText().toString());
                int nb_abs = Integer.parseInt(edit_nb_abs.getText().toString());
                int int_presence_possible = nb_eleve * demi_jour;
                if(int_presence_possible < nb_abs){
                    Context context = getApplicationContext();
                    CharSequence text = "Le nombre d'absence ne peut être supérieur au nombre de présence possible";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    int int_presence_reel = int_presence_possible - nb_abs;
                    double pour_abs_float = ((double) nb_abs / int_presence_possible) * 100;
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    String pour_abs = df.format(pour_abs_float);
                    String pour_abs_sans_virgule = pour_abs.replaceAll(",", "\\.");
                    double pour_abs_double = Double.parseDouble(pour_abs_sans_virgule);
                    double pour_pre_double = 100 - pour_abs_double;
                    String pour_pre = String.valueOf(pour_pre_double);
                    presence_possible.setText("Nombre de présences possible : " + String.valueOf(int_presence_possible));
                    presence_reel.setText("Nombre de présences réel : " + String.valueOf(int_presence_reel));
                    T_pour_abs.setText("Pourcentage d'absence : " + pour_abs + "%");
                    T_pour_pres.setText("Pourcentage de présence : " + pour_pre.replaceAll("\\.", ",") + "%");
                    progressbar.setProgress((int) pour_pre_double);
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
