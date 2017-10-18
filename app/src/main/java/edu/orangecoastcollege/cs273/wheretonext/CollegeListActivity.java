package edu.orangecoastcollege.cs273.wheretonext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

public class CollegeListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;


    // References to the widgets needed
    private EditText mCollegeName;
    private EditText mPopulation;
    private EditText mTuition;
    private RatingBar mRatingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        //this.deleteDatabase(DBHelper.DATABASE_NAME);
        // Instantiate a new DBHelper
        db = new DBHelper(this);

        // TODO: Comment this section out once the colleges below have been added to the database,
        // TODO: so they are not added multiple times (prevent duplicate entries)
        db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));


        // TODO:  Fill the collegesList with all Colleges from the database
        collegesList = db.getAllColleges();

        // TODO:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);


        // TODO:  Set the list view to use the list adapter
        collegesListView = (ListView) findViewById(R.id.collegeListView);
        collegesListView.setAdapter(collegesListAdapter);
    }

    /**
    public void viewCollegeDetails(ListViewView view) {

        protected void onListItemClick(ListView l, View v, int position, long id)
        {
            // TODO: Implement the view college details using an Intent
            Intent detailsIntent = new Intent(this, CollegeDetailsActivity.class);
            College selectedCollege = collegesList.get(position);

            detailsIntent.putExtra("Name", selectedCollege.getImageName());
            detailsIntent.putExtra("Population", selectedCollege.getPopulation());
            detailsIntent.putExtra("Tuition", selectedCollege.getTuition());
            detailsIntent.putExtra("Rating", selectedCollege.getRating());
            detailsIntent.putExtra("ImageName", selectedCollege.getImageName());

            startActivities(detailsIntent);

        }


    }
     **/

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id)
    {
        // TODO: Implement the view college details using an Intent
        Intent detailsIntent = new Intent(this, CollegeDetailsActivity.class);
        College selectedCollege = collegesList.get(position);

        detailsIntent.putExtra("Name", selectedCollege.getImageName());
        detailsIntent.putExtra("Population", selectedCollege.getPopulation());
        detailsIntent.putExtra("Tuition", selectedCollege.getTuition());
        detailsIntent.putExtra("Rating", selectedCollege.getRating());
        detailsIntent.putExtra("ImageName", selectedCollege.getImageName());

        startActivity(detailsIntent);

    }

    public void addCollege(View view) {
        // TODO: Implement the code for when the user clicks on the addCollegeButton

        String name = mCollegeName.getText().toString();
        String pop = mPopulation.getText().toString();
        String tuition = mTuition.getText().toString();


        if (TextUtils.isEmpty(name) ||TextUtils.isEmpty(pop) || TextUtils.isEmpty(tuition) )
            Toast.makeText(this, "All information about the college must be provided", Toast.LENGTH_LONG).show();
        else
        {
            College college = new College(name, Integer.parseInt(pop), Double.parseDouble(tuition), mRatingBar.getRating());

            // Add college to the college list
            collegesList.add(college);

            // Add college to the database
            db.addCollege(college);

            // Notify the list adapter that it has been changed
            collegesListAdapter.notifyDataSetChanged();

            // Clear out the EditTexts
            mCollegeName.setText("");
            mPopulation.setText("");
            mTuition.setText("");

        }
    }
}
