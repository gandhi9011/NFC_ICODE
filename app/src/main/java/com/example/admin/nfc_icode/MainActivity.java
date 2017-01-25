package com.example.admin.nfc_icode;
import core.A;
import core.Read;
import core.Size;
import core.Write;
import core.Delet;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcV;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener
{


        /*<-------------------------------------------NFC_Variables---------------------------------------------------->*/
        Tag mytag;
        NfcAdapter nfcAdapter;
        PendingIntent pendingIntent;
        IntentFilter writeTagFilters[];

        /*<---------------------------------------------------------------------------------------------------------------->*/
        A a1;
        Read r1;
        Write w1;
        Size s1;
        Delet d1;
        LinearLayout ll;
        RadioGroup rg;
        RadioButton rb1,rb2;
        TextView tv,tv1,tv2;
        Button b1,b2,b3,b4,b5,ifb1;
        EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*<---------------------------------------------------PendingIntent------------------------------------------------------------->*/
        nfcAdapter=NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

        /*<---------------------------------------------------------------------------------------------------------------->*/
        ifb1=new Button(MainActivity.this);
        ifb1.setOnClickListener(this);
        ifb1.setId(View.generateViewId());
        ll=(LinearLayout)findViewById(R.id.ll);
        tv=(TextView)findViewById(R.id.tv);
        tv1=(TextView)findViewById(R.id.tv1);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        a1=new A();
        r1=new Read();
        w1=new Write();
        s1=new Size();
        d1=new Delet();
        tv.setText(a1.check_nfc((NfcManager) this.getSystemService(NFC_SERVICE)));
        b1.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            String y = "";

            @Override
            public void onClick(View v) {
                x = a1.Tagsize(mytag);
                y = a1.Taginfo(mytag);
                Log.e("eeeeeeeeee", y);
                tv1.setText(x + "" + y);
                tv1.setTextSize(10);
                tv1.setTextColor((getResources().getColor(R.color.colorAccent)));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //String d=r1.read_cachemethod(new Intent(), mytag);
                //String d=r1.read_arrymethod(new Intent());
                String d=r1.read_getmethod(new Intent(), mytag);
               // Log.e("empty1",d);

                tv1.setText(d);


            tv1.setTextSize(10);
            tv1.setTextColor((getResources().getColor(R.color.colorPrimary)));

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                tv2=new TextView(MainActivity.this);
                int sz=s1.tag_size(mytag)-s1.written_tag_size(mytag);
                String sizetag="Tag has "+sz+"bytes empty.";
                tv2.setText(sizetag + "\n" + "Do you want Overwrite ?");
                tv2.setTextColor(getResources().getColor(R.color.brown));
                ll.addView(tv2);
                rg = new RadioGroup(MainActivity.this);
                rg.setOrientation(LinearLayout.VERTICAL);
                rb1 = new RadioButton(MainActivity.this);
                rb1.setText("Yes");
                rb1.setTextColor(getResources().getColor(R.color.DarkGreen));
                rb2 = new RadioButton(MainActivity.this);
                rb2.setText("No");
                rb2.setTextColor(getResources().getColor(R.color.DarkGreen));
                rg.addView(rb1);
                rg.addView(rb2);
                ll.addView(rg);
                et1=new EditText(MainActivity.this);
                ll.addView(et1);
                if(rg.getVisibility()==View.VISIBLE)
                {
                    b3.setVisibility(View.GONE);
                }
                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ans = "yes";
                        String data = et1.getText().toString();
                        if (data.equals(""))
                        {
                        Toast.makeText(MainActivity.this,"EMPTY DATA CAN'T WRITE",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String return_data = "";

                        return_data = w1.write_data(ans, data, mytag);
                        if (return_data.equals("true")) {
                            Toast.makeText(MainActivity.this, "DATA WRITTEN", Toast.LENGTH_LONG).show();
                            rb1.setVisibility(View.GONE);
                            rb2.setVisibility(View.GONE);
                            et1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                            b3.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(MainActivity.this, "ERROR:" + return_data, Toast.LENGTH_LONG).show();
                            rb1.setVisibility(View.GONE);
                            rb2.setVisibility(View.GONE);
                            et1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                            b3.setVisibility(View.VISIBLE);
                        }

                    }

                    }
                });


                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ans = "No";
                        String data = et1.getText().toString();
                        if (data.equals("")) {
                            Toast.makeText(MainActivity.this, "EMPTY DATA CAN'T WRITE", Toast.LENGTH_LONG).show();
                        } else {
                            String return_data = "";

                            return_data = w1.write_data(ans, data, mytag);

                            if (return_data.equals("appand")) {
                                Toast.makeText(MainActivity.this, "DATA WRITTEN", Toast.LENGTH_LONG).show();
                                rb1.setVisibility(View.GONE);
                                rb2.setVisibility(View.GONE);
                                et1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                                b3.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(MainActivity.this, "ERROR:" + return_data, Toast.LENGTH_LONG).show();
                                rb1.setVisibility(View.GONE);
                                rb2.setVisibility(View.GONE);
                                et1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                                b3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

            }
        });



        b4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if(w1.clear(mytag))

                {
                 Toast.makeText(MainActivity.this,"TAG CLEARED",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"TAG NOT CLEARED",Toast.LENGTH_LONG).show();
                }
            }
        });


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                tv2=new TextView(MainActivity.this);
                tv2.setText( "Do you want Delet data ?");
                tv2.setTextColor(getResources().getColor(R.color.brown));
                ll.addView(tv2);
                rg = new RadioGroup(MainActivity.this);
                rg.setOrientation(LinearLayout.VERTICAL);
                rb1 = new RadioButton(MainActivity.this);
                rb1.setText("Yes");
                rb1.setTextColor(getResources().getColor(R.color.DarkGreen));
                rb2 = new RadioButton(MainActivity.this);
                rb2.setText("No");
                rb2.setTextColor(getResources().getColor(R.color.DarkGreen));
                rg.addView(rb1);
                rg.addView(rb2);
                ll.addView(rg);
                et1=new EditText(MainActivity.this);
                ll.addView(et1);
                if(rg.getVisibility()==View.VISIBLE)
                {
                    b5.setVisibility(View.GONE);
                }
                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String data = et1.getText().toString();
                        if (data.equals(""))
                        {
                            Toast.makeText(MainActivity.this,"EMPTY DATA CAN'T DELETE",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String return_data = "";

                            return_data = d1.delete_recored(mytag,data);

                                Toast.makeText(MainActivity.this, "" + return_data, Toast.LENGTH_LONG).show();
                                rb1.setVisibility(View.GONE);
                                rb2.setVisibility(View.GONE);
                                et1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                                b5.setVisibility(View.VISIBLE);



                        }

                    }
                });


                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        rb1.setVisibility(View.GONE);
                        rb2.setVisibility(View.GONE);
                        et1.setVisibility(View.GONE);
                        tv2.setVisibility(View.GONE);
                        b5.setVisibility(View.VISIBLE);
                    }
                });

            }
        });


    }

    @Override
    public void onClick(View v)
    {
        try
        {
            Toast.makeText(this, "0" + ifb1.getId() + "     " + v.getId(), Toast.LENGTH_LONG).show();

            tv1.setText(w1.make_ndef(getIntent(),mytag));
            tv1.setTextColor(getResources().getColor(R.color.DarkGreen));
        }
        catch (Exception e)
        {
            Log.e("error_249",e.getMessage());
        }

    }
































    protected void onNewIntent(Intent intent)
    {  try
    {
        tv1.setText("");
        mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Toast.makeText(this, mytag.toString(), Toast.LENGTH_LONG).show();
        setIntent(intent);
        String tech[]=mytag.getTechList();
        String t="";
        for (int i=0;i<tech.length;i++)
        {
            t=t+tech[i]+"\n";

            if(tech[i].equals("android.nfc.tech.NdefFormatable"))
            {

                ifb1.setText("MAKE NDEF");
                ll.addView(ifb1);
            }
            else{
                ll.removeView(ifb1);
            }
        }
        Log.e("tagtech", t);
    }
    catch (Exception e)
    {
        Toast.makeText(this,e.getMessage()+"................",Toast.LENGTH_LONG).show();
    }
        // tv1.setText(r1.read_arrymethod(new Intent(), Ndef.get(mytag)));
    }


    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onResume()
    {   super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters,
                null);


    }



}
