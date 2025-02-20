package miage.parisnanterre.fr.mynanterre2.implem.library;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import miage.parisnanterre.fr.mynanterre2.R;
import miage.parisnanterre.fr.mynanterre2.api.library.Library;
import miage.parisnanterre.fr.mynanterre2.helpers.api.LibraryApiHelper;
import miage.parisnanterre.fr.mynanterre2.implem.library.fragment.ConditionPretFragment;
import miage.parisnanterre.fr.mynanterre2.implem.library.fragment.InformationFragment;
import miage.parisnanterre.fr.mynanterre2.implem.library.fragment.AffluencesFragment;
import miage.parisnanterre.fr.mynanterre2.implem.library.fragment.ContactFragment;

public class LibraryDesc extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    TextView title;
    private LibraryApiHelper libraryApiHelper;
    private  int clickedLibraryIndex;
    private Library clickedLibrary;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bu_info);

        libraryApiHelper = LibraryApiHelper.getInstance();

        List<BarEntry> barEntries = new ArrayList<>();

        Intent myIntent = getIntent(); // gets the previously created intent

        //Insertion des données
        try {

            clickedLibraryIndex = myIntent.getIntExtra("clickedLibraryIndex", 0);
            GetLibraryAsync getLibrariesAsync = new GetLibraryAsync();
            getLibrariesAsync.execute().get();

            /*
            int xValues;
            int yValues;

            for(Attendance attendance : clickedLibrary.getAttendances())
            {
                xValues = attendance.getHour();
                yValues = attendance.getProportion();

                barEntries.add(new BarEntry(xValues,yValues));
            }
             */


            //Retour vers la liste des bibliothèques.
            ImageView back = findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ListeEspacesBu.class));
                }
            });

            title = findViewById(R.id.Title);
            title.setText(clickedLibrary.getName());
            title.setAutoSizeTextTypeUniformWithConfiguration(10,21,1, TypedValue.COMPLEX_UNIT_SP);

            //TextView txtResponsable = findViewById(R.id.ResponsableName);
            //txtResponsable.setText(clickedLibrary.getResponsables().stream().map(Responsable::getFullName).collect(Collectors.joining(", ")));



            // get the reference of FrameLayout and TabLayout
            simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
            tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);



            // Create a new Tab named "First"
            TabLayout.Tab firstTab = tabLayout.newTab();
            firstTab.setText("Information"); // set the Text for the first Tab
            tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

            //Send Intent data to fragment
            Intent recipientsIntent = new Intent(this, LibraryDesc.class);



            // Create a new Tab named "Second"
            TabLayout.Tab secondTab = tabLayout.newTab();
            secondTab.setText("Affluences"); // set the Text for the second Tab
            tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

            // Create a new Tab named "Third"
            TabLayout.Tab thirdTab = tabLayout.newTab();
            thirdTab.setText("Détails"); // set the Text for the fourth Tab
            tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

            // Create a new Tab named "Third"
            TabLayout.Tab fourthtab = tabLayout.newTab();
            fourthtab.setText("Contacts"); // set the Text for the third Tab
            tabLayout.addTab(fourthtab); // add  the tab at in the TabLayout







            Fragment fragment = new InformationFragment(clickedLibrary);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayout, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            // perform setOnTabSelectedListener event on TabLayout
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    // get the current selected tab's position and replace the fragment accordingly
                    Fragment fragment = null;
                    switch (tab.getPosition()) {
                        case 0:
                            fragment = new InformationFragment(clickedLibrary);
                            break;
                        case 1:
                            fragment = new AffluencesFragment(myIntent);
                            break;
                        case 2:
                            fragment = new ConditionPretFragment(clickedLibrary);
                            break;
                        case 3:
                            fragment = new ContactFragment(clickedLibrary);
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

    private final class GetLibraryAsync extends AsyncTask<Void, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Void... params) {
            clickedLibrary = libraryApiHelper.getLibrary(clickedLibraryIndex);
            return "executed";
        }
    }
}