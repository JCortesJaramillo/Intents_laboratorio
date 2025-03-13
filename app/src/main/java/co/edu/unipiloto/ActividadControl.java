package co.edu.unipiloto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActividadControl extends AppCompatActivity {

    private TextView Historial;
    private EditText Respuesta;
    private Button Responder;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_control);

        Historial = findViewById(R.id.HistorialMensajes);
        Respuesta = findViewById(R.id.Respuesta);
        Responder = findViewById(R.id.Contestar);

        sharedPreferences = getSharedPreferences("chatData", MODE_PRIVATE);
        cargarHistorial();

        Intent intent = getIntent();
        String messageFromOwner = intent.getStringExtra("chatHistory"); // Recibe historial completo

        if (messageFromOwner != null) {
            Historial.setText(messageFromOwner);
        }

        Responder.setOnClickListener(v -> enviarRespuesta());
    }

    private void cargarHistorial() {
        String chatHistory = sharedPreferences.getString("historial", "");
        Historial.setText(chatHistory);
    }

    private void enviarRespuesta() {
        String responseMessage = Respuesta.getText().toString().trim();

        if (!responseMessage.isEmpty()) {
            String chatHistory = sharedPreferences.getString("historial", "");
            String updatedHistory = chatHistory.isEmpty() ? "Cuidador: " + responseMessage
                    : chatHistory + "\nCuidador: " + responseMessage;

            Historial.setText(updatedHistory);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("historial", updatedHistory);
            editor.apply();

            Respuesta.setText("");

            Intent intentBack = new Intent(ActividadControl.this, ActividadPrincipal.class);
            intentBack.putExtra("chatHistory", updatedHistory);
            startActivity(intentBack);
        } else {
            Toast.makeText(ActividadControl.this, "Esperando respuesta...", Toast.LENGTH_SHORT).show();
        }
    }
}

