//로그인 성공 시 넘어오는 MainActivity. 별건 없고 로그인 성공 시 다시 뒤로 가려고 Logout 버튼만 구현해놓았다.
package e.a2727.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnLogout;
    private LinearLayout linearlayout1, linearlayout2, linearlayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        linearlayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        linearlayout3 = (LinearLayout)findViewById(R.id.linearLayout3);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "강의 클릭됨", LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                //강의 class 구현 시 풀어주세요
//                startActivity(new Intent(MainActivity.this, gangui.class));
//                finish();
            }
        });

        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "게시판 클릭됨", LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                //게시판 class 구현 시 풀어주세요
//                startActivity(new Intent(MainActivity.this, board.class));
//                finish();
            }
        });

        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "지도 클릭됨", LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                //지도 class 구현 시 풀어주세요
//                startActivity(new Intent(MainActivity.this, maps.class));
//                finish();
            }
        });

    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }
}
