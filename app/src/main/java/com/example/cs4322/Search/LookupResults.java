package com.example.cs4322.Search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.cs4322.Favorites.BookDetails;
import com.example.cs4322.Favorites.FavoritesMenu;
import com.example.cs4322.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tessTwo.OcrManager;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class LookupResults extends AppCompatActivity {

    private RecyclerView resultsView;
    private ResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private FirebaseUser user;

    private TessBaseAPI tessBaseAPI;

    private ArrayList<BookItem> bookList;

    private RequestQueue mRequestQueue;
    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=";

    private Button search;
    private TextView searchTxt;
    private FloatingActionButton camera;
    private String userID;

    private String pathToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_results);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();

            }
        };

        mRequestQueue = Volley.newRequestQueue(this);

        bookList = new ArrayList<>();

        resultsView = findViewById(R.id.results);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ResultsAdapter(bookList);
        mAdapter.setOnItemClickListener(new ResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = bookList.get(position).getTitle();
                String author = bookList.get(position).getAuthor();
                String isbn = bookList.get(position).getISBN();
                String img = bookList.get(position).getThumbnail();
                String details = bookList.get(position).getDetails();
                String preview = bookList.get(position).getPreview();
//
//                if (isbn.equals("No ISBN")) {
//                    Toast.makeText(LookupResults.this, "This book has no ISBN and can not be added to favorites", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                mRef.child(userID).child("Books").child(isbn).child("Title").setValue(title);
//                mRef.child(userID).child("Books").child(isbn).child("Author").setValue(author);
//                mRef.child(userID).child("Books").child(isbn).child("ISBN").setValue(isbn);
//
//                Toast.makeText(LookupResults.this, "New book added to Favorites!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LookupResults.this, BookDetails.class);
                intent.putExtra("title", title);
                intent.putExtra("author", author);
                intent.putExtra("isbn", isbn);
                intent.putExtra("img", img);
                intent.putExtra("details", details);
                intent.putExtra("preview", preview);
                startActivity(intent);
            }
        });

        resultsView.setLayoutManager(mLayoutManager);
        resultsView.setAdapter(mAdapter);

        searchTxt = findViewById(R.id.searchTxt);
        search = findViewById(R.id.searchBtn);
        camera = findViewById(R.id.cameraBtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookList.clear();
                search();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                if (takePic.resolveActivity(getPackageManager()) != null) {
//                    File photoFile = null;
//                    photoFile = createPhotoFile();
//
//                    if (photoFile != null) {
//                        pathToFile = photoFile.getAbsolutePath();
//                        Uri photoURI = FileProvider.getUriForFile(LookupResults.this, "com.example.cs4322.fileprovider", photoFile);
//                        takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        startActivityForResult(takePic, 1);
//                    }
//                }

                startActivityForResult(takePic, 1);
            }
        });
    }


//    private File createPhotoFile() {
//        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(name, ".jpg", storageDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return image;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        Bitmap gray = toGrayscale(bitmap);
        OcrManager manager = new OcrManager();
        manager.initAPI();
        String result = manager.startRecognizer(gray);
        searchTxt.setText(result);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String title ="";
                        String author ="";
                        String publishedDate = "Not Available";
                        String description = "No Description";
                        int pageCount = 1000;
                        String categories = "No categories Available ";
                        String buy ="";
                        String isbn ="No ISBN";

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++){
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                description = "No Description";
                                author = "Anonymous";
                                title = "Untitled";
                                isbn ="No ISBN";

                                try{
                                    title = volumeInfo.getString("title");

                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    if(authors.length() == 1){
                                        author = authors.getString(0);
                                    }else {
                                        author = authors.getString(0) + " | " +authors.getString(1);
                                    }


                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                    JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                                    JSONObject ISBN13 = industryIdentifiers.getJSONObject(1);
                                    JSONObject ISBN10 = industryIdentifiers.getJSONObject(0);
                                    isbn = ISBN13.getString("identifier");
                                    if (ISBN13.getString("identifier").equals(""))
                                        isbn = ISBN10.getString("identifier");

                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");
                                    description = volumeInfo.getString("description");
                                    buy = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);

                                }catch (Exception e){

                                }
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                                thumbnail = thumbnail + "&key=AIzaSyCXAJcQzncSJzs2fLQ0BiOwijpyqN8AZfw";

                                String previewLink = volumeInfo.getString("previewLink");
                                String url = volumeInfo.getString("infoLink");

                                if (!isbn.equals("No ISBN"))
                                    bookList.add(new BookItem(title, isbn, author, thumbnail, description, previewLink));

                                resultsView.setAdapter(mAdapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG" , e.toString());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    private boolean Read_network_state(Context context) {
        boolean is_connected;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }
    private void search() {
        String search_query = searchTxt.getText().toString();

        boolean is_connected = Read_network_state(this);

        if(!is_connected) {
            //error_message.setText(R.string.Failed_to_Load_data);
            //resultsView.setVisibility(View.INVISIBLE);
            //error_message.setVisibility(View.VISIBLE);
            return;
        }

        //  Log.d("QUERY",search_query);


        if(search_query.equals("")) {
            Toast.makeText(this,"Please enter your query",Toast.LENGTH_LONG).show();
            return;
        }
        String final_query=search_query.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query+"&printType=books&filter=ebooks&maxResults=40&key=AIzaSyCXAJcQzncSJzs2fLQ0BiOwijpyqN8AZfw");
        Uri.Builder builder = uri.buildUpon();

        parseJson(builder.toString());
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
}
