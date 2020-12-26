package miage.parisnanterre.fr.mynanterre2.implem.crous;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import miage.parisnanterre.fr.mynanterre2.R;
import miage.parisnanterre.fr.mynanterre2.adapter.CrousGridAdapter;
import miage.parisnanterre.fr.mynanterre2.api.crous.SimpleCrous;
import miage.parisnanterre.fr.mynanterre2.helpers.api.CrousApiHelper;


public class CrousAttendance extends AppCompatActivity {

    BarChart barChart;
    private CrousApiHelper crousApiHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_frequentation_cafet);

        crousApiHelper = CrousApiHelper.getInstance();

        Bundle extras = getIntent().getExtras();
        int clickedSimpleCrousId = extras.getInt("clickedSimpleCrousId");

        SimpleCrous clickedSimpleCrous = crousApiHelper.getSimpleCrous(clickedSimpleCrousId);

        List<BarEntry> barEntries = new ArrayList<>();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "Problème au niveau du driver", Toast.LENGTH_SHORT).show();
//        }
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//
//
//        //Insertion des données
//        try {
//            conn = DriverManager.getConnection(url, user, psw);
//            String stringVariableName = //id batiment
//            String stringVariableName2 = extras.getString(CrousGridAdapter.EXTRA_MESSAGE2);//nom batiment
//
//            int idBat = Integer.parseInt(stringVariableName); //recup id du batiment cliqué
//            String nomBat = stringVariableName2; //recup nom du batiment concerné
//
//            //Get la frequentation de la cafet cliquée
//            String sqliD = "SELECT * FROM frequentation_cafet where id_cafet ='" + idBat + "';";
//            Statement st = conn.createStatement();
//            ResultSet rst = st.executeQuery(sqliD);
//            while (rst.next()) {
//                int xValues = rst.getInt("heure");
//                int yValues = rst.getInt("proportion");
//
//                //ajout au graphique ses données
//                barEntries.add(new BarEntry(xValues,yValues));
//            }
//
//            TextView txtview = findViewById(R.id.nomBatiment);
//            txtview.setText(nomBat);
//
//            barChart = findViewById(R.id.barchart);
//
//            //Config
//            barChart.setDrawBarShadow(false);
//            barChart.setDrawValueAboveBar(true);
//            barChart.setMaxVisibleValueCount(100);
//            barChart.setPinchZoom(false);
//            barChart.setVisibility(View.VISIBLE);
//            barChart.setDrawGridBackground(true);
//            barChart.animateY(3000);
//
//            Description description = new Description();
//            description.setText("heure (abscisse), proportion % (ordonnée)");
//            barChart.setDescription(description);
//
//            BarDataSet barDataSet =new BarDataSet(barEntries, "Affluence en %");
//
//            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//            BarData barData = new BarData(barDataSet);
//            barData.setBarWidth(1f);
//
//            barChart.setData(barData);
//            barChart.invalidate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

}