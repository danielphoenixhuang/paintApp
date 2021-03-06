package d.da.paintapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;


//Create a new Dialog that lets you pick a brush size
public class BrushSizeDialog extends AppCompatDialogFragment {

    private TextView brushSize;
    private int newSize;

    private BrushSizeDialogListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_brush_size, null);

        brushSize = (TextView) view.findViewById(R.id.textView_brushSize);

        builder.setView(view)
                .setTitle("Set Brush Size between 1 - 1000")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing here
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textvalue = brushSize.getText().toString();
                        if (textvalue.isEmpty())
                            newSize = 10;
                        else
                            newSize = Integer.parseInt(textvalue);

                        if (newSize < 1 || newSize > 1000)
                            newSize = 10;

                        listener.changeBrush(newSize);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            listener = (BrushSizeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener for BrushSizeDialog");
        }


    }

    public interface BrushSizeDialogListener{
        void changeBrush(int brushSize);

    }
}
