package ca.mmess.gratefulhappinessdaily;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class DGHActivity extends Activity {

    public static final int DARK_BLUE = 0x0099CC;
    public static final int LIGHT_BLUE = 0x33B5E5;
    public static final int DARK_GREEN = 0x669900;
    public static final int LIGHT_GREEN = 0x99CC00;
    public static final int DARK_PURPLE = 0x9933CC;
    public static final int LIGHT_PURPLE = 0xAA66CC;
    public static final int DARK_RED = 0xCC0000;
    public static final int LIGHT_RED = 0xFF4444;
    public static final int DARK_ORANGE = 0xFF8800;
    public static final int LIGHT_ORANGE = 0xFFBB33;
    public static final int DARK_BLACK = 0x222222;
    public static final int LIGHT_GREY = 0xAAAAAA;

    private final static String TAG = "DGHActivity";
    private EditText editText;
    private ListView mainListView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> listAdapter;
    private View MainLinLay;
    private View OptLinLay;

    public String currTextViewPushed;

    SharedPreferences sPrefs;
    SharedPreferences.Editor sEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dgh);

        // instantiate or get handles for resources and sharedPreferences.
        arrayList = new ArrayList<String>();
        mainListView = (ListView) findViewById(R.id.mainListView);
        editText = (EditText) findViewById(R.id.editTextView);
        sPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sEdit = sPrefs.edit();
        MainLinLay = findViewById(R.id.mainLinLay);
        OptLinLay = findViewById(R.id.optLinLay);

        // load sharedPreferences' list of values into mainListView
        loadSharedPrefsValuesIntoMainListView();

        // setup editText field to handle input
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // add text in editText to listAdapter->ListView
                    addEntryToList(v);
                    handled = true;

                    // clear editText focus and clear text
                    v.clearFocus();
                    v.setText("");
                    Log.d(TAG, "every fucking thing cleared");

                    // hide keyboard
                    InputMethodManager inputManager =
                            (InputMethodManager) getWindow().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getWindow().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return handled;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveMainListViewToSharedPrefsValues();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void addEntryToList(TextView tv) {
        Log.d(TAG, "addEntryToList() called with " + tv.getText());

        String value = tv.getText().toString();

        // add value of editText to mainListView
//        listAdapter.add(value);
        // add to arrayList
        arrayList.add(0, value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dgh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //for testing purposes just delete whole array
            arrayList.clear();
            sPrefs.edit().clear();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   private void loadSharedPrefsValuesIntoMainListView() {

       int size=sPrefs.getInt("size",0);

       // get list from shared preferences if present
       if (size != 0) {
           for (int j = 0; j < size; j++) {
               arrayList.add(sPrefs.getString("val" + j, null));
           }
       } else {
           // Create and populate a List of planet names.
           String[] planets = new String[]{"Sun", "Mercury", "Venus", "Earth", "Mars",
                   "Jupiter", "Saturn", "Uranus", "Neptune", "Ceres", "Pluto"};
           arrayList.addAll(Arrays.asList(planets));
       }

       // Create ArrayAdapter using above list and set as the ListView's adapter
       listAdapter = new ArrayAdapter<String>(this, R.layout.simple_row, arrayList);
       mainListView.setAdapter(listAdapter);
   }

    private void saveMainListViewToSharedPrefsValues() {

        for(int i=0;i<arrayList.size();i++)
        {
            sEdit.putString("val"+i,arrayList.get(i));
        }
        sEdit.putInt("size",arrayList.size());
        sEdit.commit();
    }


    public void textViewClicked(View view) {

        String id = ((CustomTextView) view).getText().toString();
        Log.d(TAG, "textViewClicked() from " + id);

        // flag which textView was pushed
        currTextViewPushed = id;

        // delete entry and refresh mainListView
        listAdapter.remove(id);


        // show options layout
//        OptLinLay.setVisibility(View.VISIBLE);
//        MainLinLay.setVisibility(View.GONE);
    }

/*    public void onColour(View view) {

        int colour = DARK_BLACK;

        // set text colour
        switch (view.getId()) {
            case R.id.but_dblue:
                colour = DARK_BLUE;
                break;
            case R.id.but_dpurp:
                colour = DARK_PURPLE;
                break;
            case R.id.but_dblack:
                colour = DARK_BLACK;
                break;
            case R.id.but_lblue:
                colour = LIGHT_BLUE;
                break;
            case R.id.but_lpurp:
                colour = LIGHT_PURPLE;
                break;
            case R.id.but_lgrey:
                colour = LIGHT_GREY;
                break;
            case R.id.but_dgreen:
                colour = DARK_GREEN;
                break;
            case R.id.but_dorange:
                colour = DARK_ORANGE;
                break;
            case R.id.but_dred:
                colour = DARK_RED;
                break;
            case R.id.but_lgreen:
                colour = LIGHT_GREEN;
                break;
            case R.id.but_lorange:
                colour = LIGHT_ORANGE;
                break;
            case R.id.but_lred:
                colour = LIGHT_RED;
                break;
        }

        int currListArrayPushed = arrayList.indexOf(currTextViewPushed);

        // show main layout
        OptLinLay.setVisibility(View.GONE);
        MainLinLay.setVisibility(View.VISIBLE);

    }*/
}
