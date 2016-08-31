package cheslavg.seeit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
//, TextView.OnEditorActionListener
    {
    EditText etSetMax;
    EditText etSetStep;
    EditText etSetAim;
    TextView tvAim;
    TextView tvMax;
    Button SetBtn;
    ClipDrawable clipDrawable = null;
    ImageView imageViewGrayScale;
    ColorMatrix matrix = new ColorMatrix();
    ImageView imageViewClip = null;
    int level_delta = 10000;
    private static final String TAG = "Seeit";
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //OnCreate and other standart stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find all relatives for Android Studio

        //first our images
        Log.d(TAG,"-"+"find our images");
        imageViewGrayScale = (ImageView)findViewById(R.id.imageViewGrayScale);
        imageViewClip = (ImageView) findViewById(R.id.imageViewClip);

        //than out EditTexts
        Log.d(TAG,"-"+"find out EditTexts");
        etSetAim = (EditText)findViewById(R.id.etSetAim);
        etSetMax = (EditText)findViewById(R.id.etSetMax);
        etSetStep = (EditText)findViewById(R.id.etSetStep);

        //than our TextViews
        Log.d(TAG,"-"+"find out TextViews");
        tvAim = (TextView)findViewById(R.id.tvAim);
        tvMax = (TextView)findViewById(R.id.tvMax);

        //and than our alone button

        Log.d(TAG,"-"+"find out SetButton");
        SetBtn = (Button)findViewById(R.id.SetBtn);

        //Now we set up our listeners

        Log.d(TAG,"-"+"set listener to the SetButton");
        SetBtn.setOnClickListener(this);
        //etSetMax.setOnEditorActionListener(this);


        //Than go to our inspirational pictures -- grayscale

        Log.d(TAG,"-"+"create grayscale filter");
        matrix.setSaturation(0);
        final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        Log.d(TAG,"-"+"set grayscale filter to ImageViewGrayScale");


        //And than -- our Clipped Drawable
        //Log.d(TAG,"-"+"create clipDrawable from the ImageViewClip");
        //clipDrawable = (ClipDrawable) imageViewClip.getDrawable();
    }

        public void loadImagefromGallery(View view){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            try {
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    imageViewClip.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    imageViewGrayScale.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    matrix.setSaturation(0);
                    final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    imageViewGrayScale.setColorFilter(filter);
                }
                else{
                    Toast.makeText(this,"You haven't picked Image", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
                Log.d(TAG,"-"+"Got picture from gallery");
        }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SetBtn:
                Log.d(TAG,"-"+"Check if you fields are not empty");
                if(TextUtils.isEmpty(etSetMax.getText().toString())||
                        TextUtils.isEmpty(etSetAim.getText().toString())
                        ||TextUtils.isEmpty(etSetStep.getText().toString()))
                {
                    break;
                }
                else {
                Log.d(TAG,"-"+"Get the percentage of coloring throw clipDrawable.setLevel(level)");
                    ClipDrawable drawable = new ClipDrawable (imageViewClip.getDrawable(), Gravity.NO_GRAVITY,ClipDrawable.HORIZONTAL);
                    int level = level_delta*Integer.parseInt(etSetStep.getText().toString())/Integer.parseInt(etSetMax.getText().toString());
                    drawable.setLevel(level);
                    Log.d(TAG,"-"+"Level is..." + level);
                    tvAim.setText(etSetAim.getText().toString());
                    tvMax.setText(etSetMax.getText().toString());
                    break;

                }



        /*
        //Hide OK button when output is finished
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);*/

    }

    /*@Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            int level = Integer.parseInt(tvMax.getText().toString());
            clipDrawable.setLevel(level);
        }
        return handled;

    }*/
}
    }


