package miage.parisnanterre.fr.mynanterre.implem.library;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import miage.parisnanterre.fr.mynanterre.R;
import miage.parisnanterre.fr.mynanterre.api.library.Attendance;
import miage.parisnanterre.fr.mynanterre.api.library.Library;
import miage.parisnanterre.fr.mynanterre.api.library.Responsable;
import miage.parisnanterre.fr.mynanterre.helpers.api.LibraryApiHelper;
import miage.parisnanterre.fr.mynanterre.implem.Accueil;

public class LibraryDesc extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bu_info);

        LibraryApiHelper libraryApiHelper = LibraryApiHelper.getInstance();
        List<Library> libraries = libraryApiHelper.getLibraries();

        List<BarEntry> barEntries = new ArrayList<>();

        Intent myIntent = getIntent(); // gets the previously created intent

        //Insertion des données
        try {
            int clickedLibraryIndex = myIntent.getIntExtra("clickedLibraryIndex", 0);
            Library clickedLibrary = libraries.get(clickedLibraryIndex);

            int xValues;
            int yValues;

            for(Attendance attendance : clickedLibrary.getAttendances())
            {
                xValues = attendance.getHour();
                yValues = attendance.getProportion();

                barEntries.add(new BarEntry(xValues,yValues));
            }


            //Retour vers la liste des bibliothèques.
            ImageView back = findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ListeEspacesBu.class));
                }
            });

            TextView txtview = findViewById(R.id.NameBU);
            txtview.setText(clickedLibrary.getName());

            //TextView txtResponsable = findViewById(R.id.ResponsableName);
            //txtResponsable.setText(clickedLibrary.getResponsables().stream().map(Responsable::getFullName).collect(Collectors.joining(", ")));



            // get the reference of FrameLayout and TabLayout
            simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
            tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
            // Create a new Tab named "First"
            TabLayout.Tab firstTab = tabLayout.newTab();
            firstTab.setText("Information"); // set the Text for the first Tab

            tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
            // Create a new Tab named "Second"
            TabLayout.Tab secondTab = tabLayout.newTab();
            secondTab.setText("Affluences"); // set the Text for the second Tab

            tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
            // Create a new Tab named "Third"
            TabLayout.Tab thirdTab = tabLayout.newTab();
            thirdTab.setText("Conditions de prêt"); // set the Text for the first Tab

            tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

            // perform setOnTabSelectedListener event on TabLayout
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    // get the current selected tab's position and replace the fragment accordingly
                    Fragment fragment = null;
                    switch (tab.getPosition()) {
                        case 0:
                            fragment = new FirstFragment();
                            break;
                        case 1:
                            fragment = new SecondFragment();
                            break;
                        case 2:
                            fragment = new ThirdFragment();
                            break;
                    }
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.simpleFrameLayout, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            } catch (Exception e) {
            e.printStackTrace();
        }

    }

}