package com.techno.takhdim.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Pix;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.NewDesign.Constant.BaseClass;
import com.techno.takhdim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.develpoeramit.mapicall.ApiCallBuilder;

import static com.android.volley.VolleyLog.e;
import static com.techno.takhdim.App.MySharedPref.getData;
import static com.techno.takhdim.App.MySharedPref.saveData;
import static com.techno.takhdim.App.MySharedPref.sp;

public class AddRequestDeatilsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private String id, name, user_id, date, time, Address, details, house_unit;
    private TextView txt_ttl, txt_location, txt_date, txt_time;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 200;
    private double lat, lon;
    private String img1, img2, img3, img4;
    private ImageView img_one_cross, img_two_cross, img_three_cross, img_four_cross;
    private ImageView img_one, img_two, img_three, img_four;
    private Button btn_send;
    private EditText edt_houseunit, edt_details;
    private LinearLayout lay_more;
    private ProgressBar progress1, progress2, progress3, progress4;
    private String obj;
    private String lastID;
    private ArrayList<String> paths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request_deatils);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        findId();
        id = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        user_id = getData(AddRequestDeatilsActivity.this, "user_id", "");
        txt_ttl.setText(name);
        img_back.setOnClickListener(this);
        txt_location.setOnClickListener(this);

        img_one.setOnClickListener(this);
        img_two.setOnClickListener(this);
        img_three.setOnClickListener(this);
        img_four.setOnClickListener(this);

        img_one_cross.setOnClickListener(this);
        img_two_cross.setOnClickListener(this);
        img_three_cross.setOnClickListener(this);
        img_four_cross.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        getFields();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        txt_ttl = findViewById(R.id.txt_ttl);
        txt_location = findViewById(R.id.txt_location);

        img_one = findViewById(R.id.img_one);
        img_two = findViewById(R.id.img_two);
        img_three = findViewById(R.id.img_three);
        img_four = findViewById(R.id.img_four);

        img_one_cross = findViewById(R.id.img_one_cross);
        img_two_cross = findViewById(R.id.img_two_cross);
        img_three_cross = findViewById(R.id.img_three_cross);
        img_four_cross = findViewById(R.id.img_four_cross);
        btn_send = findViewById(R.id.btn_send);
        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);
        edt_houseunit = findViewById(R.id.edt_houseunit);
        edt_details = findViewById(R.id.edt_details);
        lay_more = findViewById(R.id.lay_more);
        progress1 = findViewById(R.id.progress1);
        progress2 = findViewById(R.id.progress2);
        progress3 = findViewById(R.id.progress3);
        progress4 = findViewById(R.id.progress4);
    }

    @Override
    public void onClick(View view) {
        if (view == img_back) {
            finish();
        } else if (view == txt_location) {
            takeAddress();
        } else if (view == img_one) {
            if (img1 == null) {
                pickImage();
                //  PickImageDialog.build(new PickSetup()).show(this);
            }
        } else if (view == img_two) {
            if (img2 == null) {
                pickImage();
                //   PickImageDialog.build(new PickSetup()).show(this);
            }
        } else if (view == img_three) {
            if (img3 == null) {
                pickImage();
                //   PickImageDialog.build(new PickSetup()).show(this);
            }
        } else if (view == img_four) {
            if (img4 == null) {
                pickImage();
                //  PickImageDialog.build(new PickSetup()).show(this);
            }
        } else if (view == img_one_cross) {
            img1 = null;
            img_one.setImageResource(R.drawable.add_image);
        } else if (view == img_two_cross) {
            img2 = null;
            img_two.setImageResource(R.drawable.add_image);
        } else if (view == img_three_cross) {
            img3 = null;
            img_three.setImageResource(R.drawable.add_image);
        } else if (view == img_four_cross) {
            img4 = null;
            img_four.setImageResource(R.drawable.add_image);
        } else if (view == btn_send) {
            vaildate();
        } else if (view == txt_date) {
            showDatePicker(txt_date);
        } else if (view == txt_time) {
            showTimePicker(txt_time);
        }
    }

    private void vaildate() {
        house_unit = edt_houseunit.getText().toString();
        details = edt_details.getText().toString();
        if (Address == null) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Address", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (house_unit.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Enter house unit!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (date == null) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Date!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (time == null) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Select Time!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            if (img1 != null)
                paths.add(img1);
            if (img2 != null)
                paths.add(img2);
            if (img3 != null)
                paths.add(img3);
            if (img4 != null)
                paths.add(img4);
           obj= getCheckViewValid();
            SendServiceRequest();
        }
    }



    private void showDatePicker(final TextView textView) {
        // To show current date in the datepicker
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(AddRequestDeatilsActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                // textView.setText(sdf.format(mcurrentDate.getTime()));
                textView.setText(checkNumber(selectedday) + "-" + checkNumber(selectedmonth) + "-" + checkNumber(selectedyear));
                date = "" + checkNumber(selectedday) + "-" + checkNumber(selectedmonth) + "-" + checkNumber(selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    private void showTimePicker(final TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddRequestDeatilsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(checkNumber(selectedHour) + ":" + checkNumber(selectedMinute));
                time = "" + checkNumber(selectedHour) + ":" + checkNumber(selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private String checkNumber(int num) {
        String mnum = "" + num;
        if (num < 10) {
            mnum = "0" + num;
        }
        return mnum;
    }

    private void takeAddress() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(AddRequestDeatilsActivity.this), PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void pickImage() {
        if (img1 == null) {
            Pix.start(this, 100, 4);
        } else if (img2 == null) {
            Pix.start(this, 100, 3);
        } else if (img3 == null) {
            Pix.start(this, 100, 2);
        } else if (img4 == null) {
            Pix.start(this, 100, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(data, AddRequestDeatilsActivity.this);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(AddRequestDeatilsActivity.this, toastMsg, Toast.LENGTH_LONG).show();
                    //  Place place = PlaceAutocomplete.getPlace(this, data);
                    Log.e("Auto Complet Place", "Place: " + place.getName());
                    Log.e("ERROR CODE", "" + resultCode);
                    Log.e("place data", "" + place);
                    txt_location.setText("" + place.getAddress());
                    lat = place.getLatLng().latitude;
                    lon = place.getLatLng().longitude;
                    Address = place.getAddress().toString();
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 17);
//                gMap.animateCamera(cameraUpdate);
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(AddRequestDeatilsActivity.this, data);
                    // TODO: Handle the error.
                    Log.e("AutoCompletPlace", status.getStatusMessage());
                    Log.e("ERROR CODE ELSE", "" + resultCode);

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            } else if (resultCode == Activity.RESULT_OK && requestCode == 100) {
                ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                for (int i = 0; i < returnValue.size(); i++) {
                    if (img1 == null) {
                        img1 = returnValue.get(i);
                        Glide.with(this).load(img1).into(img_one);
                    } else if (img2 == null) {
                        img2 = returnValue.get(i);
                        Glide.with(this).load(img2).into(img_two);
                    } else if (img3 == null) {
                        img3 = returnValue.get(i);
                        Glide.with(this).load(img3).into(img_three);
                    } else if (img4 == null) {
                        img4 = returnValue.get(i);
                        Glide.with(this).load(img4).into(img_four);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFields() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_service_field(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("ServiceFields", "======>" + object);
                        if (object.getString("status").equals("1")) {
                            lay_more.setVisibility(View.VISIBLE);
                            JSONArray result = object.getJSONArray("result");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject obj = result.getJSONObject(i);
                                String type = obj.getString("type");
                                String label = obj.getString("label");
                                String value = obj.getString("value").replace("[", "").replace("]", "").replace("\"", "");
                                if (type.contains("radio")) {
                                    BindRadioView(label, value);
                                } else if (type.contains("checkbox")) {
                                    BindCheckBoxView(label, value);
                                } else if (type.contains("dropdown")) {
                                    BindDropdownView(label, value);
                                } else if (type.contains("description")) {
                                    BindDescription(label);
                                }
                            }
                        } else {
                            lay_more.setVisibility(View.GONE);

                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddRequestDeatilsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO: ===========Views===========
    private void BindRadioView(String label, String value) {
        Log.e("BindView", "===========>" + label + ";" + value);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_label = new TextView(this);
        tv_label.setTextAppearance(this, R.style.tv);
        tv_label.setPadding(10, 20, 10, 10);
        tv_label.setLayoutParams(params);
        tv_label.setText(label);
        tv_label.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_bullet, 0, 0, 0);
        tv_label.setCompoundDrawablePadding(20);
        RadioGroup group = new RadioGroup(this);
        group.setPadding(5, 5, 5, 5);
        group.setLayoutParams(params);
        String[] mField = value.split(",");
        group.setOrientation(mField.length > 2 ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        for (String name : mField) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(name);
            radioButton.setTextAppearance(this, R.style.tv);
            group.addView(radioButton);
        }
        lay_more.addView(tv_label);
        lay_more.addView(group);
    }

    private void BindCheckBoxView(String label, String value) {
        Log.e("BindView", "===========>" + label + ";" + value);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_label = new TextView(this);
        tv_label.setTextAppearance(this, R.style.tv);
        tv_label.setPadding(10, 20, 10, 10);
        tv_label.setLayoutParams(params);
        tv_label.setText(label);
        tv_label.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_bullet, 0, 0, 0);
        tv_label.setCompoundDrawablePadding(20);
        lay_more.addView(tv_label);
        String[] mField = value.split(",");
        LinearLayout layout = new LinearLayout(this);
        layout.setPadding(5, 5, 5, 5);
        layout.setLayoutParams(params);
        layout.setOrientation(mField.length > 2 ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        for (String name : mField) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(name);
            checkBox.setTextAppearance(this, R.style.tv);
            layout.addView(checkBox);
        }
        lay_more.addView(layout);

    }

    private void BindDropdownView(String label, String value) {
        Log.e("BindView", "===========>" + label + ";" + value);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_label = new TextView(this);
        tv_label.setTextAppearance(this, R.style.tv);
        tv_label.setPadding(10, 20, 10, 10);
        tv_label.setLayoutParams(params);
        tv_label.setText(label);
        tv_label.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_bullet, 0, 0, 0);
        tv_label.setCompoundDrawablePadding(20);
        lay_more.addView(tv_label);
        String[] mField = value.split(",");
        Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(params);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mField);
        spinner.setAdapter(adapter);
        lay_more.addView(spinner);

    }

    private void BindDescription(String label) {
        Log.e("BindView", "===========>" + label);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_label = new TextView(this);
        tv_label.setTextAppearance(this, R.style.tv);
        tv_label.setPadding(10, 20, 10, 10);
        tv_label.setLayoutParams(params);
        tv_label.setText(label);
        tv_label.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_bullet, 0, 0, 0);
        tv_label.setCompoundDrawablePadding(20);
        EditText editText = new EditText(this);
        editText.setHint(label);
        editText.setPadding(10, 2, 10, 2);
        editText.setBackgroundResource(R.drawable.et_border);
        editText.setTextAppearance(this, R.style.et);
        lay_more.addView(tv_label);
        lay_more.addView(editText);
    }

    private String getCheckViewValid() {
        JSONArray array = new JSONArray();
        if (lay_more.getVisibility()==View.VISIBLE){
            try {
                final int childCount = lay_more.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    JSONObject object=new JSONObject();
                    View v = lay_more.getChildAt(i);
                    if (v instanceof EditText) {
                        if (((EditText) v).getText().toString().isEmpty()) {
                            ((EditText) v).setError("Required");
                            ((EditText) v).requestFocus();
                            break;
                        } else {
                            object.put("description",((EditText)v).getText().toString());
                        }
                    }
                    if (v instanceof RadioGroup) {
                        RadioButton button=findViewById(((RadioGroup)v).getCheckedRadioButtonId());
                        if (button==null) {
                            Toast.makeText(this, "Field Required", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            object.put("radio",button.getText().toString());
                        }
                    }
                    if (v instanceof CheckBox) {
                        if (!((CheckBox)v).isChecked()) {
                            Toast.makeText(this, "Field Required", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            object.put("check_box",((CheckBox)v).getText().toString());
                        }
                    }
                    if (v instanceof Spinner) {
                            object.put("dropdown",((Spinner)v).getSelectedItem().toString());

                    }
                    if (object.length()>0)
                    array.put(object);
                }
                Log.e("onSubmitValue","====> "+array.toString());
            }catch (JSONException e){

            }
        }
        return array.toString();
    }
    //TODO: ===========Views===========


    private void SendServiceRequest() {
        new ApiCallBuilder().build(this).setUrl(BaseClass.get().ServiceRequest())
                .isShowProgressBar(true).setParam(getParam()).execute(new ApiCallBuilder.onResponse() {

            @Override
            public void Success(String response) {
                Log.e("SendServiceRequest", "=======>" + response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("1")) {
                        lastID = object.getString("last_id");
                        if (paths.size() > 0) {
                            UploadImage();
                        } else {
                            Toast.makeText(AddRequestDeatilsActivity.this, "Request Successfully Submit", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failed(String error) {

            }
        });
    }

    int ImageUploadCount = 0;

    private void UploadImage() {
        String mType = "img1";
        switch (ImageUploadCount) {
            case 0:
                progress1.setVisibility(View.VISIBLE);
                mType = "img1";
                break;
            case 1:
                progress2.setVisibility(View.VISIBLE);
                mType = "img2";
                break;
            case 2:
                progress3.setVisibility(View.VISIBLE);
                mType = "img3";
                break;
            case 3:
                progress4.setVisibility(View.VISIBLE);
                mType = "img4";
                break;
        }
        new ApiCallBuilder().build(this).setUrl(BaseClass.get().UploadServiceImage())
                .isShowProgressBar(false).setParam(getIDParam(mType))
                .setFile(mType, paths.get(ImageUploadCount))
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("ImageUpload", "=========>" + response);
                        ImageUploadCount++;
                        progress1.setVisibility(View.GONE);
                        progress2.setVisibility(View.GONE);
                        progress3.setVisibility(View.GONE);
                        progress4.setVisibility(View.GONE);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("status").equals("1")) {
                                if (paths.size() > ImageUploadCount) {
                                    UploadImage();
                                } else {
                                    Toast.makeText(AddRequestDeatilsActivity.this, "Request Successfully Submit", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void Failed(String error) {
                        progress1.setVisibility(View.GONE);
                        progress2.setVisibility(View.GONE);
                        progress3.setVisibility(View.GONE);
                        progress4.setVisibility(View.GONE);
                    }
                });
    }

    private HashMap<String, String> getIDParam(String type) {
        HashMap<String, String> param = new HashMap<>();
        param.put("service_id", lastID);
        param.put("type", type);
        return param;
    }

    private HashMap<String, String> getParam() {
        HashMap<String, String> param = new HashMap<>();
        param.put("service_id", id);
        param.put("user_id", user_id);
        param.put("date", date);
        param.put("time", time);
        param.put("address", Address);
        param.put("lat", "" + lat);
        param.put("lon", "" + lon);
        param.put("house_unit", house_unit);
        param.put("details", details);
        param.put("field_data", "" + obj);
        return param;
    }
}
