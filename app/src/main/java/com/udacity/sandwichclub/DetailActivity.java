package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView ingredientsIv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent=getIntent();
        if (intent == null) {
            closeOnError();
        }
        ingredientsIv=findViewById(R.id.image_iv);
        makeLayout();
    }
    protected void makeLayout() {
        int position;
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        else {
            position=position%sandwiches.length;
        }
        String json = sandwiches[position];
        Sandwich sandwich=null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
                if (sandwich == null) {
                    // Sandwich data unavailable
                    closeOnError();
                    return;
                }
        }
        catch(JSONException e) {
            e.printStackTrace();
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_action_name)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView mPlaceOfOrigin=(TextView) findViewById(R.id.origin_tv);
        TextView mDescription=(TextView) findViewById(R.id.description_tv);
        TextView mIngredients=(TextView) findViewById(R.id.ingredients_tv);
        TextView mAlsoKnownAs=(TextView) findViewById(R.id.also_known_tv);

        String defaultText="Not Available!";

        String placeOfOrigin=sandwich.getPlaceOfOrigin();
        if(placeOfOrigin.length()==0) {
            mPlaceOfOrigin.setText(defaultText);
        }
        else {
            mPlaceOfOrigin.setText(placeOfOrigin);
        }

        String description=sandwich.getDescription();
        if(description.length()==0) {
            mDescription.setText(defaultText);
        }
        else {
            mDescription.setText(description);
        }

        List<String> alsoKnown=sandwich.getAlsoKnownAs();
        if(alsoKnown.size()==0) {
            mAlsoKnownAs.setText(defaultText);
        }
        else {
            for(String name:alsoKnown) {
                mAlsoKnownAs.append(name);
            }
        }

        List<String> listIngredients=sandwich.getIngredients();
        if(listIngredients.size()==0) {
            mIngredients.setText(defaultText);
        }
        else {
            for(String ingredient:listIngredients) {
                mIngredients.append("\n"+ingredient);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nextpage,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Next) {
            int pos=1+intent.getIntExtra(EXTRA_POSITION,DEFAULT_POSITION);
            Intent intentNew=new Intent(this,this.getClass());
            intentNew.putExtra(EXTRA_POSITION,pos);
            startActivity(intentNew);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        Intent closeIntent =new Intent(this,MainActivity.class);
        startActivity(closeIntent);
    }
}
