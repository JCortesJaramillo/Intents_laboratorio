package co.edu.unipiloto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActividadPrincipal extends AppCompatActivity {

    private EditText Mensaje;
    private TextView Historial;
    private Button BotonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        Mensaje = findViewById(R.id.Mensaje);
        Historial = findViewById(R.id.Historial);
        BotonEnviar = findViewById(R.id.BotonEnviar);

        SharedPreferences sharedPreferences = getSharedPreferences("chatHistory", MODE_PRIVATE);
        String chatHistory = sharedPreferences.getString("history", "");
        Historial.setText(chatHistory);

        BotonEnviar.setOnClickListener(v -> {
            String message = Mensaje.getText().toString();

            if (!message.isEmpty()) {
                String updatedHistory = chatHistory + "\nPropietario: " + message;

                Historial.setText(updatedHistory);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("history", updatedHistory);
                editor.apply();

                Intent intentToCaregiver = new Intent(ActividadPrincipal.this, ActividadControl.class);
                intentToCaregiver.putExtra("message", message);
                startActivity(intentToCaregiver);
            } else {
                Toast.makeText(ActividadPrincipal.this, "Por favor, escribe un mensaje.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}