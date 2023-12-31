package com.hiaryabeer.transferapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hiaryabeer.transferapp.Models.ExportData;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.Models.ImportData;
import com.hiaryabeer.transferapp.Models.ReplacementModel;
import com.hiaryabeer.transferapp.Models.RetrofitInstance;
import com.hiaryabeer.transferapp.Models.User;
import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.appSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Retrofit;

import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;

public class Login extends AppCompatActivity {
    private Button login;
    private ConstraintLayout loginBox;
    private LinearLayout request_ip_;
    public com.hiaryabeer.transferapp.appSettings setting;
    List<appSettings> appSettings;
    public String COMPANYNO;
    public String SET_qtyup;
    GeneralMethod generalMethod;
    public RoomAllData my_dataBase;
    public static TextView getListCom, selectedCompany,username;
    private String selectedCom;
    private String cono, coYear;
    ListView listCompany;
    EditText unameEdt,passEdt;
    public static int iraqFlage=1;
    public static EditText itemKintText1;
    public static List<User> allUsers= new ArrayList<>();
    ///B
    public static int serialsActive;
 ImportData   importData ;
    static {
        serialsActive =0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        my_dataBase = RoomAllData.getInstanceDataBase(Login.this);
        init();
     List<appSettings>   appSettings= appSettings = new ArrayList();

            appSettings = my_dataBase.settingDao().getallsetting();
        if (appSettings.size() != 0) {
            allUsers = my_dataBase.usersDao().getAllUsers();
            if (allUsers.size() == 0)
                getusers();
        }

    }

   void getusers(){
       my_dataBase.usersDao().deleteAll();
       allUsers.clear();
       importData=new ImportData(Login.this);
//       importData.getAllUsers(new ImportData.GetUsersCallBack() {
//           @Override
//           public void onResponse(JSONArray response) {
//
//
//               for (int i = 0; i < response.length(); i++) {
//
//                   try {
//                       //[{"USERNO":"11","USERNAME":"HISHAM - ZENTIC","USERPASS":"hshmhggiz"}
//
//                           allUsers.add(new User(
//                                   response.getJSONObject(i).getString("USERNO"),
//                                   response.getJSONObject(i).getString("USERNAME").toLowerCase(Locale.ROOT),
//                                   response.getJSONObject(i).getString("USERPASS") ));
//
//
//                   } catch (JSONException e) {
//                       e.printStackTrace();
//                   }
//
//               }
//               my_dataBase.usersDao().addAll(allUsers);
////               allUsers = my_dataBase.usersDao().getAllUsers();
//
//           }
//
//           @Override
//           public void onError(String error) {
//
//
//           }
//       });
       importData.fetchAllUsers();
   }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.login: {
                    getDataZone();
                    if (appSettings.size() != 0) {
//                        if(unameEdt.getText().toString().trim().toLowerCase().equals("admin"))
//                        {
//                            if( passEdt.getText().toString().trim().equals("100"))
//                            {
//                                Intent intent = new Intent(Login.this, MainActivity.class);
//                                startActivity(intent);
//                            }else{
//                                passEdt.setError("Invalid");
//                        }
//                        }else {
//                            unameEdt.setError("Invalid");
//                        }

                        checkUnameAndPass();

                    } else {
                        openSettingDialog();
                    }
                    break;
                }
                case R.id.request_ip_:
                    showpassworddailog();

                    break;

            }
        }
    };

//    public void showCompanyDialog(View view) {
//        if (view.getId() == R.id.companyName) {
//            if (existCoNo(2))
//                showCompany(Login.this);
//            else {
//                openSettingDialog();
//                //generalMethod.openSettingDialog();
//            }
//        }
//    }

//    private void showCompany(final Context context1) {
//
//        final Dialog dialog = new Dialog(context1);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.zone_search);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.CENTER;
//        appSettings settings;
//        ImportData importData = new ImportData(Login.this);
//        ArrayList<String> nameOfEngi = new ArrayList<>();
//        listCompany = dialog.findViewById(R.id.listViewEngineering);
//        importData.getCompanyInfo();
//
//        getListCom = dialog.findViewById(R.id.getListCom);
//
//        getListCom.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().length() != 0) {
//                    if (editable.toString().equals("fill")) {
//                        if (importData.companyInList.size() != 0) {
//                            for (int i = 0; i < importData.companyInList.size(); i++) {
//                                nameOfEngi.add(importData.companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
//                            }
//
//                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
//                                @Override
//                                public View getView(int position, View convertView, ViewGroup parent) {
//                                    /// Get the Item from ListView
//                                    View view = super.getView(position, convertView, parent);
//
//                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                                    // Set the text size 25 dip for ListView each item
//                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//                                    tv.setTextColor(getResources().getColor(R.color.text_color));
//                                    // Return the view
//                                    return view;
//                                }
//                            };
//
//                            // DataBind ListView with items from ArrayAdapter
//                            listCompany.setAdapter(arrayAdapter);
//
//
//                        }
//                    }
//
//                }
//
//            }
//        });
//
//
//        final int[] rowZone = new int[1];
//        selectedCom = "";
//
//        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                cono = "";
//                coYear = "";
//                rowZone[0] = position;
//                listCompany.requestFocusFromTouch();
//                listCompany.setSelection(position);
//                selectedCom = importData.companyInList.get(position).getCoNameA();
//                cono = importData.companyInList.get(position).getCoNo();
//                coYear = importData.companyInList.get(position).getCoYear();
//
//            }
//        });
//
//
//        Button btn_send = dialog.findViewById(R.id.btn_send);
//
//        btn_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedCompany.setText(selectedCom);
//
//                updateCono(cono, coYear);
//
//                dialog.dismiss();
//
//
//            }
//        });
//        dialog.show();
//
//    }

//    private boolean existCoNo(int flag) {
//        // ipAddress=my_dataBase.settingDao().getIpAddress().trim();
//        String CONO = "";
//        try {
//            if (flag == 1) {
//                CONO = my_dataBase.settingDao().getCono();
//            } else {
//                if (flag == 2)
//                    CONO = my_dataBase.settingDao().getIpAddress();
//            }
//
//            Log.e("CONO", "" + CONO);
//            if (CONO != null) {
//                if (CONO.length() != 0) {
//                    return true;
//                }
//
//            }
//        } catch (Exception e) {
//            return false;
//        }
//
//
//        return false;
//
//
//    }
//
//    private void updateCono(String cono, String coYear) {
//        my_dataBase.settingDao().updateCompanyInfo(cono);
//        my_dataBase.settingDao().updateCompanyYear(coYear);
//
//        //Log.e("updateCono",""+up);
//    }
public void showpassworddailog(){
Log.e("showpassworddailog","showpassworddailog");
     Dialog passwordDialog = new Dialog(Login.this);
    passwordDialog.setCancelable(false);
    passwordDialog.setContentView(R.layout.passworddailog);
    passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    TextView closeBtn = passwordDialog.findViewById(R.id.cancel);
    EditText passwordEt = passwordDialog.findViewById(R.id.passwordd);
    Button okBtn = passwordDialog.findViewById(R.id.done);
    passwordDialog.show();
    closeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordDialog.dismiss();
        }
    });

    okBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (passwordEt.getText().toString().trim().equals("")) {

                passwordEt.requestFocus();
                passwordEt.setError(getString(R.string.required));

            } else {

                if (passwordEt.getText().toString().trim().equals("2023000")) {
                  openSettingDialog();
                    passwordDialog.dismiss();

                } else {

                    showSweetDialog(Login.this, 3, getString(R.string.wrong_password), getResources().getString(R.string.wrong_password_msg));

                }


            }

        }
    });




}
    public void init() {
//        selectedCompany = findViewById(R.id.selectedCompany);
        //  itemKintText1 = findViewById(R.id.itemKintTextRE);

        username= findViewById(R.id.username);

        importData=new ImportData(Login.this);
        login = findViewById(R.id.login);
        loginBox = findViewById(R.id.loginBox);
        login.setOnClickListener(onClickListener);
        request_ip_ = findViewById(R.id.request_ip_);
        request_ip_.setOnClickListener(onClickListener);
        generalMethod=new GeneralMethod(this);
        unameEdt= findViewById(R.id.unameEdt);
        unameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
 if(s.length()!=0){
    String usernam= my_dataBase.usersDao().getUserName(unameEdt.getText().toString().trim());
     username.setText(usernam);
 }
            }
        });
        passEdt = findViewById(R.id.passEdt);

    }

    private void openSettingDialog() {

        BottomSheetDialog dialog = new BottomSheetDialog(Login.this, R.style.SheetDialog);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.setCancelable(true);
//        {
////            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
////            lp.copyFrom(dialog.getWindow().getAttributes());
////
////            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
////            dialog.getWindow().setAttributes(lp);
//            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
//            int height = 50;
//
//            dialog.getWindow().setLayout(width, height);
//        }
        dialog.show();
        loginBox.setVisibility(View.GONE);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(Login.this,Login.class));
            }
        });


//        final Dialog dialog = new Dialog(Login.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.ip_setting_dialog);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        dialog.show();


        final EditText ip = dialog.findViewById(R.id.ipEditText);
        final EditText portSetting = dialog.findViewById(R.id.portSetting);
//        final TextView editip = dialog.findViewById(R.id.editip);
        // ip.setEnabled(false);
        final EditText conNO = dialog.findViewById(R.id.cono);
        //  final EditText years=dialog.findViewById(R.id.storeNo_edit);
//        final CheckBox qtyUP = (CheckBox) dialog.findViewById(R.id.qtycheck);
        //     final EditText usernum= dialog.findViewById(R.id.usernumber);
        final EditText deviceId = dialog.findViewById(R.id.deviceId);

        ///////B
        final CheckBox checkboxQtyCheck = dialog.findViewById(R.id.checkboxQtyCheck);
        final CheckBox rawahnehAddQty = dialog.findViewById(R.id.rawahnehAddQty);
        final CheckBox  internal_repl = dialog.findViewById(R.id.internal_repl);
        internal_repl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int x = my_dataBase.settingDao().updateinternal_repl("1");
                    Log.e("x==",x+"");
                }
                else
                {  int x = my_dataBase.settingDao().updateinternal_repl("0");
                    Log.e("x==",x+"");
                }
            }
        });
        checkboxQtyCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                          if (isChecked) {
                                                              int x = my_dataBase.settingDao().updateQtyCheck("1");
                                                              Log.e("x==",x+"");
                                                          }
                                                          else
                                                          {  int x = my_dataBase.settingDao().updateQtyCheck("0");
                                                              Log.e("x==",x+"");
                                                          }
                                                      }
                                                  }
        );
        rawahnehAddQty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                   if (isChecked)

                                                       my_dataBase.settingDao().updateRawahneh_Add_ItemCheck("1");
                                                   else
                                                       my_dataBase.settingDao().updateRawahneh_Add_ItemCheck("0");
                                               }
                                           }
        );

        final RadioGroup printRG = dialog.findViewById(R.id.printRG);
        final LinearLayout printLinear = dialog.findViewById(R.id.printLinear);

        if (serialsActive == 1) {
            Log.e("case1==","serialsActive");
          //  checkboxQtyCheck.setChecked(false);
      //   checkboxQtyCheck.setVisibility(View.GONE);
         //   rawahnehAddQty.setChecked(true);
            rawahnehAddQty.setVisibility(View.VISIBLE);
       //  printLinear.setVisibility(View.GONE);

        } else {
            Log.e("case2==","serials not Active");
            checkboxQtyCheck.setVisibility(View.VISIBLE);
    //   rawahnehAddQty.setVisibility(View.GONE);
     //   rawahnehAddQty.setChecked(false);
            printLinear.setVisibility(View.VISIBLE);
        }


        // conNO.setEnabled(false);
        //  years.setEnabled(false);
        //  usernum.setEnabled(false);
//        editip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!ip.getText().toString().equals("")) {
//                    final Dialog dialog1 = new Dialog(Login.this);
//                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog1.setCancelable(false);
//                    dialog1.setContentView(R.layout.passworddailog);
//                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                    lp.copyFrom(dialog1.getWindow().getAttributes());
//                    ip.setEnabled(true);
//                    lp.gravity = Gravity.CENTER;
//                    dialog1.getWindow().setAttributes(lp);
//
//                    EditText editText = dialog1.findViewById(R.id.passwordd);
//                    Button donebutton = dialog1.findViewById(R.id.done);
//                    Button cancelbutton = dialog1.findViewById(R.id.cancel);
//                    donebutton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (editText.getText().toString().trim().equals("304555")) {
//                                ip.setEnabled(true);
//                                dialog1.dismiss();
//                            }
//                        }
//                    });
//                    cancelbutton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //   ip.setEnabled(false);
//                            dialog1.dismiss();
//                        }
//                    });
//
//
//                    dialog1.show();
//                } else {
//                    Log.e("dd", "dd");
//                    ip.setEnabled(true);
//                    ip.requestFocus();
//                }
//            }
//
//        });
        getDataZone();
        if (appSettings.size() != 0) {

            ip.setText(appSettings.get(0).getIP());
            conNO.setText(appSettings.get(0).getCompanyNum());
            COMPANYNO = appSettings.get(0).getCompanyNum();
            portSetting.setText(appSettings.get(0).getPort().trim());

            //    usernum.setText(appSettings.get(0).getUserNumber());
            //  years.setText(appSettings.get(0).getYears());
            try {
                deviceId.setText(appSettings.get(0).getDeviceId());
                deviceId.setEnabled(false);
            } catch (Exception e) {
                Log.e("deviceId", "" + e.getMessage());
            }

//            if (appSettings.get(0).getUpdateQTY().equals("1"))
//                qtyUP.setChecked(true);
            if (appSettings.get(0).getInternal_replanshment() .equals("0")) {
                internal_repl.setChecked(false);
            }else{
                internal_repl.setChecked(true);
            }
            if (appSettings.get(0).getCheckQty() != null) {

                if (appSettings.get(0).getCheckQty().equals("1")) {
                    checkboxQtyCheck.setChecked(true);
                } else {
                    checkboxQtyCheck.setChecked(false);
                }
                if (serialsActive == 1){
                    //checkboxQtyCheck.setChecked(false);
                }
            }

            if (appSettings.get(0).getRawahneh_add_item() != null) {

                if (appSettings.get(0).getRawahneh_add_item().equals("1")) {
                    rawahnehAddQty.setChecked(true);
                } else {
                    rawahnehAddQty.setChecked(false);
                }
                if (serialsActive == 0){
                  //  rawahnehAddQty.setChecked(false);
                }
            }

            if (appSettings.get(0).getPrint_option() != null) {

                if (appSettings.get(0).getPrint_option() == 0) {
                    printRG.check(R.id.wifi);
                } else {
                    printRG.check(R.id.bluetooth);
                }

            }

        } else {
            //  if(ip.getText().toString().equals(""))
             ip.setEnabled(true);
             String deviceNo=getDeviceNo();
             deviceId.setText(deviceNo.trim());
             deviceId.setEnabled(false);

            //   else ip.setEnabled(false);
        }

        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String SET_IP = ip.getText().toString();
                final String SET_conNO = conNO.getText().toString();
                COMPANYNO = conNO.getText().toString();
                //  final String SET_years = years.getText().toString();
                String device_Id = deviceId.getText().toString().trim();
                String port = portSetting.getText().toString().trim();

                ///////B
                String checkQty = checkboxQtyCheck.isChecked() ? "1" : "0";

                String rawahneh_add_item = rawahnehAddQty.isChecked() ? "1" : "0";
                String internal_rep = internal_repl.isChecked() ? "1" : "0";
                int print = printRG.getCheckedRadioButtonId() == R.id.wifi ? 0 : 1;

                setting = new appSettings();
                setting.setIP(SET_IP);
                setting.setCompanyNum(SET_conNO);
                setting.setPort(port);

                setting.setDeviceId(deviceId.getText().toString().trim());

                ////B
                setting.setCheckQty(checkQty);

                setting.setRawahneh_add_item(rawahneh_add_item);
                setting.  setInternal_replanshment(internal_rep);
                setting.setPrint_option(print);

                if (deviceId.getText().toString().trim().length() != 0) {
                    if (ip.getText().toString().trim().length() != 0) {
                        if (conNO.getText().toString().trim().length() != 0) {

                            saveData(setting);
                            RetrofitInstance.ourInstance=null;
                            getusers();
                            dialog.dismiss();
                            loginBox.setVisibility(View.VISIBLE);


                        } else {
                            conNO.setError(getString(R.string.reqired_filled));
                        }

                    } else {
                        ip.setError(getString(R.string.reqired_filled));
                    }

                } else
                    deviceId.setError(getString(R.string.reqired_filled));
            }
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                loginBox.setVisibility(View.VISIBLE);
            }
        });

    }

    private String getDeviceNo() {

        Random rand = new Random();
        String id = generalMethod.convertToEnglish( String.format("%04d", rand.nextInt(10000)));
        String dat=generalMethod.getCurentTimeDate(2);
      //  Log.e("randomNo","id="+id+"\t"+dat);
        dat=dat.substring(6);
        id=id+dat;

       // Log.e("randomNo","id="+id+"\t"+dat);
        return  id;
    }

    private void getDataZone() {
        appSettings = new ArrayList();
        try {
            appSettings = my_dataBase.settingDao().getallsetting();
        } catch (Exception e) {
            Log.e("Getting app settings", e.getMessage());
        }
    }


    private void saveData(appSettings settings) {
        my_dataBase.settingDao().deleteALL();
        my_dataBase.storeDao().deleteall();
        my_dataBase.itemDao().dELETEAll();
        my_dataBase.itemSwitchDao().dELETEAll();
        my_dataBase.serialsDao().deleteAllSerials();
        my_dataBase.settingDao().insert(settings);

        generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");

    }
    void checkUnameAndPass() {

        String uname = unameEdt.getText().toString().trim().toLowerCase(Locale.ROOT) + "";
        String pass = passEdt.getText().toString().trim() + "";

        allUsers = my_dataBase.usersDao().getAllUsers();
        Log.e("allUsers=",allUsers.size()+"");
        boolean valid = false;
        int i;

        if (allUsers.size() != 0) {

            if (!uname.equals("")) {

                if (!pass.equals("")) {

                    for (i = 0; i < allUsers.size(); i++) {

                        if ((uname.equals(allUsers.get(i).getUserId())||Integer.parseInt(uname)==Integer.parseInt(allUsers.get(i).getUserId())) &&
                                pass.equals(allUsers.get(i).getUserPassword()))  {

                            valid = true;
                            break;

                        }
                    }

                    if (valid) {


                        Toast.makeText(Login.this, "SUCCESS LOGIN!", Toast.LENGTH_SHORT).show();



                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(Login.this, "username or password not correct", Toast.LENGTH_SHORT).show();

                    }



                } else {


                    passEdt.setError(getString(R.string.required));
//                passEdt.setError("");

                }

            } else {


                unameEdt.setError(getString(R.string.required));

            }

        } else {

            Toast.makeText(this, "No Saved Users Found !", Toast.LENGTH_SHORT).show();


        }


    }

}