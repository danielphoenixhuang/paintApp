package d.da.paintapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements BrushSizeDialog.BrushSizeDialogListener{

    private PaintView paintView;
    private Button clearButt;
    private Button colorPickButt;
    private Button backgroundPickerButt;
    private Button brushSizeButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = (PaintView) findViewById(R.id.paintView);
        clearButt = (Button) findViewById(R.id.clearButt);
        colorPickButt = (Button) findViewById(R.id.colorPickButt);
        backgroundPickerButt = (Button) findViewById(R.id.backgroundPickerButt);
        brushSizeButt = (Button) findViewById(R.id.changeBrushSizeButt);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);


        //Initialize Click Listeners
        clearButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });

        colorPickButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        backgroundPickerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBackgroundPicker();
            }
        });

        brushSizeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrushSizeDialog();
            }
        });

    }

    private void openBrushSizeDialog()
    {
        BrushSizeDialog brushSizeDialog = new BrushSizeDialog();
        brushSizeDialog.show(getSupportFragmentManager(), "brush size dialog");
    }


    private void openBackgroundPicker()
    {
        AmbilWarnaDialog colorPick = new AmbilWarnaDialog(this, paintView.getBackgroundColor(), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                paintView.setBackground(color);
            }
        });
        colorPick.show();
    }

    private void openColorPicker()
    {
        AmbilWarnaDialog colorPick = new AmbilWarnaDialog(this, paintView.getCurrentColor(), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                paintView.setColor(color);
            }
        });
        colorPick.show();
    }

    @Override
    public void changeBrush(int brushSize) {
        paintView.setBrushSize(brushSize);
    }
}
