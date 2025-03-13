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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        Mensaje = findViewById(R.id.Mensaje);
        Historial = findViewById(R.id.Historial);
        BotonEnviar = findViewById(R.id.BotonEnviar);

        sharedPreferences = getSharedPreferences("chatData", MODE_PRIVATE);
        cargarHistorial();

        BotonEnviar.setOnClickListener(v -> enviarMensaje());
    }

    private void cargarHistorial() {
        String chatHistory = sharedPreferences.getString("historial", "");
        Historial.setText(chatHistory);
    }

    private void enviarMensaje() {
        String message = Mensaje.getText().toString().trim();

        if (!message.isEmpty()) {
            String chatHistory = sharedPreferences.getString("historial", "");
            String updatedHistory = chatHistory.isEmpty() ? "Propietario: " + message
                    : chatHistory + "\nPropietario: " + message;

            Historial.setText(updatedHistory);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("historial", updatedHistory);
            editor.apply();

            Mensaje.setText("");

            Intent intentToCaregiver = new Intent(ActividadPrincipal.this, ActividadControl.class);
            intentToCaregiver.putExtra("chatHistory", updatedHistory);
            startActivity(intentToCaregiver);
        } else {
            Toast.makeText(ActividadPrincipal.this, "Por favor, escribe un mensaje.", Toast.LENGTH_SHORT).show();
        }
    }
}
