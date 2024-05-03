package tr.com.enesakcam.urunlerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tr.com.enesakcam.urunlerfirebase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }







    public void kaydetOnClick(View view) {
        String eposta=binding.editEposta.getText().toString().trim();
        String sifre=binding.editSifre.getText().toString().trim();
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(eposta,sifre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        binding.txtDurum.setTextColor(Color.parseColor("#2e7d32"));
                        binding.txtDurum.setText("Kullanıcı oluşturuldu");
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.txtDurum.setTextColor(Color.parseColor("#ff1744"));
                        binding.txtDurum.setText("Kullanıcı oluşturulamadı\n"+
                                e.getLocalizedMessage());
                    }
                });
    }

    public void girisOnClick(View view) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String eposta=binding.editEposta.getText().toString().trim();
        String sifre=binding.editSifre.getText().toString().trim();
        if(eposta.isEmpty() || sifre.isEmpty()) {
            binding.txtDurum.setTextColor(Color.parseColor("#ff1744"));
            binding.txtDurum.setText("E-posta veya Şifre boş olamaz");
        } else {
            firebaseAuth
                    .signInWithEmailAndPassword(eposta,sifre)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            binding.txtDurum.setTextColor(Color.parseColor("#2e7d32"));
                            binding.txtDurum.setText("Giriş başarılı");

                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            binding.txtDurum.setTextColor(Color.parseColor("#ff1744"));
                            binding.txtDurum.setText("Giriş başarısız\n"+
                                    e.getLocalizedMessage());
                        }
                    });
        }
    }

    public void sifremiUnuttumOnClick(View view) {
        String eposta=binding.editEposta.getText().toString().trim();
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(eposta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.txtDurum.setTextColor(Color.parseColor("#2e7d32"));
                        binding.txtDurum.setText("Hatırlatma e-postası gönderildi");
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.txtDurum.setTextColor(Color.parseColor("#ff1744"));
                        binding.txtDurum.setText("Hatırlatma e-postası gönderilemedi\n"+
                                e.getLocalizedMessage());
                    }
                });
    }
}