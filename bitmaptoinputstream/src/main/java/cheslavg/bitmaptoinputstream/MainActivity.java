package cheslavg.bitmaptoinputstream;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView iv = null;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnConvert = (Button)findViewById(R.id.btnConvert);
        iv = (ImageView) findViewById(R.id.iv_source);
        Button buttonLoadPicture = (Button)findViewById(R.id.buttonLoadPicture);
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
                iv.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                iv.getDrawable();
            }
            else{
                Toast.makeText(this,"You haven't picked Image", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }}

    /*@Override
    public void onClick(View view) {
        try
        {

            Bitmap bitmap = BitmapFactory.decodeStream();
            Drawable drawable = ConvertBitmapToDrawable(bitmap);
            iv.setImageDrawable(drawable);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Drawable created.",Toast.LENGTH_LONG).show();
    }
    public Drawable ConvertBitmapToDrawable(Bitmap bitmap)
    {
        Drawable drawable = new BitmapDrawable(getResources(),bitmap);
        return drawable;
    }
    }*/

