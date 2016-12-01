package c.egco428.a23269.userLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button Sig;
    Button Sup;
    Button Cancel;
    EditText User;
    EditText Pass;


    private CommentDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sig = (Button)findViewById(R.id.signinbtn);
        Sup = (Button)findViewById(R.id.signupbtn);
        Cancel = (Button)findViewById(R.id.cancelbtn);

        User = (EditText)findViewById(R.id.username);
        Pass = (EditText)findViewById(R.id.password);

        dataSource = new CommentDataSource(this);
        dataSource.open();

    }

    public void clickSignup(View view){
        startActivity(new Intent(MainActivity.this, signup.class));
    }

    public void clickSignin(View view){
        checkPassword();
    }

    public void clickcancle(View view){
        User.setText("");
        Pass.setText("");
    }

    private void checkPassword(){

        String name = User.getText().toString();
        String loginMessage = dataSource.findpass(name);

        String passw = Pass.getText().toString();

        if (loginMessage.equals("")) {


            Toast.makeText(getApplicationContext(), "Login fail",
                    Toast.LENGTH_SHORT).show();

        }

        else {
            if (loginMessage.equals(passw)) {
                Toast.makeText(getApplicationContext(), "Login success",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MainPage.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "Login fail",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

}