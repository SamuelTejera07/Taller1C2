package com.example.taller1c2;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etX1, etY1, etX2, etY2;
    private Button btnPendiente, btnPuntoMedio, btnEcuacionRecta, btnCuadrantes;
    private TextView tvResultado;
    private ConstraintLayout layoutPrincipal;

    private Random aleatorio = new Random();
    private DecimalFormat formatoDecimal = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes UI
        etX1 = findViewById(R.id.etX1);
        etY1 = findViewById(R.id.etY1);
        etX2 = findViewById(R.id.etX2);
        etY2 = findViewById(R.id.etY2);
        btnPendiente = findViewById(R.id.btnPendiente);
        btnPuntoMedio = findViewById(R.id.btnPuntoMedio);
        btnEcuacionRecta = findViewById(R.id.btnEcuacionRecta);
        btnCuadrantes = findViewById(R.id.btnCuadrantes);
        tvResultado = findViewById(R.id.tvResultado);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);

        // Registrar campos EditText para menú contextual
        registerForContextMenu(etX1);
        registerForContextMenu(etY1);
        registerForContextMenu(etX2);
        registerForContextMenu(etY2);

        // Registrar layout para menú contextual
        registerForContextMenu(layoutPrincipal);

        // Configurar listeners de botones
        btnPendiente.setOnClickListener(v -> calcularPendiente());
        btnPuntoMedio.setOnClickListener(v -> calcularPuntoMedio());
        btnEcuacionRecta.setOnClickListener(v -> calcularEcuacionRecta());
        btnCuadrantes.setOnClickListener(v -> determinarCuadrantes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuPuntosPrimerCuadrante) {
            generarPuntosEnCuadrante(1);
            return true;
        } else if (itemId == R.id.menuPuntosSegundoCuadrante) {
            generarPuntosEnCuadrante(2);
            return true;
        } else if (itemId == R.id.menuPuntosTercerCuadrante) {
            generarPuntosEnCuadrante(3);
            return true;
        } else if (itemId == R.id.menuPuntosCuartoCuadrante) {
            generarPuntosEnCuadrante(4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v instanceof EditText) {
            // Menú para los campos de texto
            getMenuInflater().inflate(R.menu.menu_edittext, menu);
            // Etiquetar la vista para identificarla después
            menu.findItem(R.id.menuAleatorio).setActionView(v);
            menu.findItem(R.id.menuCambiarSigno).setActionView(v);
        } else if (v == layoutPrincipal) {
            // Menú para el layout
            getMenuInflater().inflate(R.menu.menu_layout, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        View vista = item.getActionView();
        int itemId = item.getItemId();

        // Menú para campos de texto
        if (vista instanceof EditText) {
            EditText campoTexto = (EditText) vista;

            if (itemId == R.id.menuAleatorio) {
                // Generar número aleatorio entre -10 y 10
                int valorAleatorio = aleatorio.nextInt(21) - 10;
                campoTexto.setText(String.valueOf(valorAleatorio));
                return true;
            } else if (itemId == R.id.menuCambiarSigno) {
                // Cambiar el signo del valor
                try {
                    double valor = Double.parseDouble(campoTexto.getText().toString());
                    campoTexto.setText(String.valueOf(-valor));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Ingrese un número válido primero", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }
        // Menú para el layout
        else {
            if (itemId == R.id.menuPuntosPrimerCuadrante) {
                generarPuntosEnCuadrante(1);
                return true;
            } else if (itemId == R.id.menuPuntosSegundoCuadrante) {
                generarPuntosEnCuadrante(2);
                return true;
            } else if (itemId == R.id.menuPuntosTercerCuadrante) {
                generarPuntosEnCuadrante(3);
                return true;
            } else if (itemId == R.id.menuPuntosCuartoCuadrante) {
                generarPuntosEnCuadrante(4);
                return true;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void generarPuntosEnCuadrante(int cuadrante) {
        // Generar coordenadas aleatorias basadas en el cuadrante
        int min = 1;
        int max = 10;

        int x1, y1, x2, y2;

        switch (cuadrante) {
            case 1: // Primer cuadrante (x+, y+)
                x1 = aleatorio.nextInt(max) + min;
                y1 = aleatorio.nextInt(max) + min;
                x2 = aleatorio.nextInt(max) + min;
                y2 = aleatorio.nextInt(max) + min;
                break;
            case 2: // Segundo cuadrante (x-, y+)
                x1 = -(aleatorio.nextInt(max) + min);
                y1 = aleatorio.nextInt(max) + min;
                x2 = -(aleatorio.nextInt(max) + min);
                y2 = aleatorio.nextInt(max) + min;
                break;
            case 3: // Tercer cuadrante (x-, y-)
                x1 = -(aleatorio.nextInt(max) + min);
                y1 = -(aleatorio.nextInt(max) + min);
                x2 = -(aleatorio.nextInt(max) + min);
                y2 = -(aleatorio.nextInt(max) + min);
                break;
            case 4: // Cuarto cuadrante (x+, y-)
                x1 = aleatorio.nextInt(max) + min;
                y1 = -(aleatorio.nextInt(max) + min);
                x2 = aleatorio.nextInt(max) + min;
                y2 = -(aleatorio.nextInt(max) + min);
                break;
            default:
                return;
        }

        // Actualizar la UI
        etX1.setText(String.valueOf(x1));
        etY1.setText(String.valueOf(y1));
        etX2.setText(String.valueOf(x2));
        etY2.setText(String.valueOf(y2));

        Toast.makeText(this, "Puntos generados en el cuadrante " + cuadrante, Toast.LENGTH_SHORT).show();
    }

    private boolean validarEntrada() {
        try {
            Double.parseDouble(etX1.getText().toString());
            Double.parseDouble(etY1.getText().toString());
            Double.parseDouble(etX2.getText().toString());
            Double.parseDouble(etY2.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void calcularPendiente() {
        if (!validarEntrada()) return;

        double x1 = Double.parseDouble(etX1.getText().toString());
        double y1 = Double.parseDouble(etY1.getText().toString());
        double x2 = Double.parseDouble(etX2.getText().toString());
        double y2 = Double.parseDouble(etY2.getText().toString());

        if (x2 - x1 == 0) {
            tvResultado.setText("La pendiente es infinita (línea vertical)");
        } else {
            double pendiente = (y2 - y1) / (x2 - x1);
            tvResultado.setText("Pendiente: " + formatoDecimal.format(pendiente));
        }
    }

    private void calcularPuntoMedio() {
        if (!validarEntrada()) return;

        double x1 = Double.parseDouble(etX1.getText().toString());
        double y1 = Double.parseDouble(etY1.getText().toString());
        double x2 = Double.parseDouble(etX2.getText().toString());
        double y2 = Double.parseDouble(etY2.getText().toString());

        double puntoMedioX = (x1 + x2) / 2;
        double puntoMedioY = (y1 + y2) / 2;

        tvResultado.setText("Punto Medio: (" + formatoDecimal.format(puntoMedioX) + ", " + formatoDecimal.format(puntoMedioY) + ")");
    }

    private void calcularEcuacionRecta() {
        if (!validarEntrada()) return;

        double x1 = Double.parseDouble(etX1.getText().toString());
        double y1 = Double.parseDouble(etY1.getText().toString());
        double x2 = Double.parseDouble(etX2.getText().toString());
        double y2 = Double.parseDouble(etY2.getText().toString());

        StringBuilder ecuacion = new StringBuilder();

        if (x1 == x2) {
            // Línea vertical
            ecuacion.append("x = ").append(formatoDecimal.format(x1));
        } else if (y1 == y2) {
            // Línea horizontal
            ecuacion.append("y = ").append(formatoDecimal.format(y1));
        } else {
            // Calcular pendiente (m) e intersección con eje y (b)
            double pendiente = (y2 - y1) / (x2 - x1);
            double interseccionY = y1 - pendiente * x1;

            ecuacion.append("y = ");

            // Añadir parte de la pendiente
            if (pendiente == 1) {
                ecuacion.append("x");
            } else if (pendiente == -1) {
                ecuacion.append("-x");
            } else {
                ecuacion.append(formatoDecimal.format(pendiente)).append("x");
            }

            // Añadir parte de la intersección con el eje y
            if (interseccionY > 0) {
                ecuacion.append(" + ").append(formatoDecimal.format(interseccionY));
            } else if (interseccionY < 0) {
                ecuacion.append(" - ").append(formatoDecimal.format(Math.abs(interseccionY)));
            }
        }

        tvResultado.setText("Ecuación de la recta: " + ecuacion.toString());
    }

    private void determinarCuadrantes() {
        if (!validarEntrada()) return;

        double x1 = Double.parseDouble(etX1.getText().toString());
        double y1 = Double.parseDouble(etY1.getText().toString());
        double x2 = Double.parseDouble(etX2.getText().toString());
        double y2 = Double.parseDouble(etY2.getText().toString());

        String cuadrante1 = obtenerCuadrante(x1, y1);
        String cuadrante2 = obtenerCuadrante(x2, y2);

        tvResultado.setText("Punto 1 (" + formatoDecimal.format(x1) + ", " + formatoDecimal.format(y1) + "): " + cuadrante1 +
                "\nPunto 2 (" + formatoDecimal.format(x2) + ", " + formatoDecimal.format(y2) + "): " + cuadrante2);
    }

    private String obtenerCuadrante(double x, double y) {
        if (x > 0 && y > 0) {
            return "Primer Cuadrante";
        } else if (x < 0 && y > 0) {
            return "Segundo Cuadrante";
        } else if (x < 0 && y < 0) {
            return "Tercer Cuadrante";
        } else if (x > 0 && y < 0) {
            return "Cuarto Cuadrante";
        } else if (x == 0 && y == 0) {
            return "Origen";
        } else if (x == 0) {
            return "Eje Y";
        } else {
            return "Eje X";
        }
    }
}