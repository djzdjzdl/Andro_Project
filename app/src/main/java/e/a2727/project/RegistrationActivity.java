//회원가입 창이다. 이거 구현할려고 머가리 깨질 뻔 했다가 다시 조각모음 함
package e.a2727.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//이 부분은 딱히 신경 안 써도 됨. 그냥 길이 짧게 보여줄라 함
import static android.widget.Toast.LENGTH_SHORT;

public class RegistrationActivity extends AppCompatActivity {

    //회원가입 시 firebase 내 데이터 전송 및 받기 위해 사용
    private FirebaseAuth mAuth;
    //이메일 가입 방식인데 user Name도 저장함. 나중에 profile 추가 하던가 해야겠음
    private EditText userName, userPassword, userEmail;
    private Button nextButton, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //그냥 User 내용 입력 및 버튼 설정함
        userName = (EditText)findViewById(R.id.etUserNameSignUp);
        userPassword = (EditText)findViewById(R.id.etPassword2);
        userEmail = (EditText)findViewById(R.id.etEmail);
        nextButton = (Button)findViewById(R.id.btnNext);
        back = (Button)findViewById(R.id.btnBackToStarting);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }


        //뒤로가기 버튼 구현함
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, StartActivity.class));
                finish();
            }
        });

        //여기가 존나 중요함. Register 버튼을 눌렀을 때 작동함
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력값 가져옴
                String email = userEmail.getText().toString();
                String username = userName.getText().toString();
                String password = userPassword.getText().toString();

                //만약 전부 비어있으면? 하고 작동함. 위에 길이 설정 하고 싶으면 하셈
                if(email.isEmpty() || username.isEmpty() || password.isEmpty()){
                    Toast toast = Toast.makeText(RegistrationActivity.this, "Please enter all the details", LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {//정상적이라면 이메일과 비밀번호를 토대로 회원가입 절차를 진행함 여기서도 firebase에서 제공하는 함수를 그냥 적용함
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) { //firebase에서는 함수 자체에서 스스로 작동하므로 만약 일 끝나면?
                                    if (task.isSuccessful()) { //성공시
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegistrationActivity.this, "Authentication Successful.",LENGTH_SHORT).show();
                                        sendEmailVerification(); //이메일 인증메일 전송과 함께 여기서 초기 창으로 넘어가게 해야함. why? 여기다 하면 바로 로그인되버림
                                        mAuth.signOut(); //바로 로그인 상태로 되므로 로그아웃 시켜줘야함
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistrationActivity.this, "Authentication failed.",LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
            }
        });

    }

    @Override
    public void onBackPressed() { //킹갓 뒤로가기
        super.onBackPressed();
    }

    //이메일 인증 함수이다. 따로 어려운건 없음
    private void sendEmailVerification(){
        //여기서 firebase 함수로 현재 유저 상태를 불러옴. logout 전이므로 상관 없음
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            //여기서도 firebase 함수 내 Email Verification 이메일을 전송시키게 한다
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast toast = Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail has been sent!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();//다른건 없고 성공하면 초기 화면으로 보내줌
                        startActivity(new Intent(RegistrationActivity.this, StartActivity.class));
                    }else{
                        Toast toast = Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });
        }
    }


}
