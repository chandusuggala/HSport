package com.example.chandusuggala.hsport;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT =1001 ;
    private ImageView iv;
    private CoordinatorLayout coordinatorLayout;
    public static final String PRODUCT_ID ="PRODUCT_ID";

    private static final int DETAIL_REQUEST = 1111;
    public static final String RETURN_MESSAGE = "RETURN_MESSAGE";

    private static String webUrl = "https://www.facebook.com/H-Sport-1388674971422183/";
    private static String email = "info@hplussport.com";
    private List<Product> products=DataProvider.productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] items=getResources().getStringArray(R.array.clothing);
        //ArrayAdapter<String> adapter =
                //new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1, items);

        ProductListAdapter adapter=new ProductListAdapter(this,R.layout.list_item,products);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Product product = products.get(position);
                intent.putExtra(PRODUCT_ID, product.getProductId());

                startActivityForResult(intent, DETAIL_REQUEST);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String[] addresses = {email};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
                intent.putExtra(Intent.EXTRA_TEXT, "Please send some information!");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, MENU_ITEM_LOGOUT, 1001, getString(R.string.Logout));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout, "You Selected Settings", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                   return true;
            case R.id.action_about:
               // Snackbar.make(coordinatorLayout, "You Selected about", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
           // return true;
                Intent intent=new Intent(this,AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_web:
                Intent webIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                if(webIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(webIntent);
                }
                return true;
            case R.id.action_cart:
                Snackbar.make(coordinatorLayout, "You Selected Shopping Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case MENU_ITEM_LOGOUT:
                Snackbar.make(coordinatorLayout, "You Selected Logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_REQUEST) {
            if (resultCode == RESULT_OK) {
                String message = data.getStringExtra(RETURN_MESSAGE);
                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                        .setAction("Go to cart", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,
                                        "Going to cart", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        }
    }
}
