package c.egco428.a23269.userLocation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Random;

public class signup extends AppCompatActivity implements SensorEventListener {
    EditText usernamesignup;
    EditText passwordsignup;
    EditText latitude;
    EditText longtitude;
    private int countShake = 0;
    Button add;
    Button random;
    private SensorManager sensorManager;
    private long lastUpdate;
    private CommentDataSource dataSource;
    private View view;
    private MySQLiteHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        database = new MySQLiteHelper(this);
        usernamesignup = (EditText) findViewById(R.id.EditName);
        passwordsignup = (EditText) findViewById(R.id.EditPass);
        latitude = (EditText) findViewById(R.id.EditLati);
        longtitude = (EditText) findViewById(R.id.EditLongi);
        random = (Button) findViewById(R.id.randomBtn);
        add = (Button) findViewById(R.id.addBtn);


            ActionBar mActionBar = getSupportActionBar();
            //mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E3F2FD")));
            mActionBar.setTitle(Html.fromHtml("<font color=\"white\">" + "Sign-up Page" + "</font>"));
            mActionBar.setDisplayHomeAsUpEnabled(true);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            usernamesignup = (EditText) findViewById(R.id.EditName);
            passwordsignup = (EditText) findViewById(R.id.EditPass);
            latitude = (EditText) findViewById(R.id.EditLati);
            longtitude = (EditText) findViewById(R.id.EditLongi);

            dataSource = new CommentDataSource(this);
            dataSource.open();
        }

        @Override
        public void onSensorChanged (SensorEvent event){
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                getAccelerometer(event);
            }
        }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 4) {
            if (actualTime - lastUpdate < 700) {
                return;
            }
            countShake += 1;
            lastUpdate = actualTime;
            if (countShake > 1) {
                pressRandom();
                countShake = 0;
                return;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);// get sensor when open app again
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // close sensor when exit app
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public void pressAdd(View view) {

        String Username = usernamesignup.getText().toString();
        String Password = passwordsignup.getText().toString();
        String Latitude = latitude.getText().toString();
        String Longitude = longtitude.getText().toString();

        Comment comment = dataSource.createmap(Username, Password, Latitude, Longitude);

        finish();

    }

    public void random(View view) {
        pressRandom();
    }

    public void pressRandom() {

        Random r = new Random();
        double randomLati = -85.000000 + (85.000000 - (-85.000000)) * r.nextDouble();
        randomLati = Double.parseDouble(new DecimalFormat("##.####").format(randomLati));
        latitude.setText(String.valueOf(randomLati));

        Random g = new Random();
        double randomLongi = -179.999989 + (179.999989 - (-179.999989)) * g.nextDouble();
        randomLongi = Double.parseDouble(new DecimalFormat("##.####").format(randomLongi));
        longtitude.setText(String.valueOf(randomLongi));

    }
}



