package core;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.TagTechnology;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Admin on 26-07-2016.
 */
public class Write
{

    public boolean clear(Tag tag)
    {

        try {
            Ndef ndef=Ndef.get(tag);
            Log.e("---27",""+ndef);
            ndef.connect();
            Log.e("---29", "" + ndef);
            ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
            Log.e("clear end", ndef.getMaxSize() + "");
            ndef.close();
            return true;
        }
        catch(Exception e)
        {
            Log.d("clear","clear_error"+e.getMessage());
            return false;
        }
    }











    public String  write_data(String ans,String data,Tag tag)
    {


        try
        {     Ndef ndef=Ndef.get(tag);
            if (ans.equals("yes"))
            {

                NdefRecord ndefRecord = NdefRecord.createTextRecord("en", data);
                NdefMessage ndefMessage = new NdefMessage(ndefRecord);

  /*--------------Checking what it is....NDEF or NdefFormatable ---------------------------------------------*/

                if (ndef != (null))
                {
                    Log.d("rrrrrr", ndef + "");
                    ndef.connect();
                    ndef.writeNdefMessage(ndefMessage);
                    ndef.close();
                    return "true";
                } else

                {
                    ndef.close();
                    return "ERROR";
                }


            }
            if(ans.equals("No"))
            {
                ndef.connect();
                NdefMessage ndefMessage = ndef.getNdefMessage();


                if (ndefMessage != null)
                {
                    NdefRecord[] ndefRecords = ndefMessage.getRecords();
                    NdefRecord[] ndefRecords1=new NdefRecord[ndefRecords.length+1];
                    System.arraycopy(ndefRecords, 0, ndefRecords1, 0, ndefRecords.length);
                    NdefRecord ndefRecord = NdefRecord.createTextRecord("en", data);
                    ndefRecords1[ndefRecords.length]=ndefRecord;
                    ndef.writeNdefMessage(new NdefMessage(ndefRecords1));
                    ndef.close();
                    return "appand";
                }
                    else
                {
                    ndef.close();
                    return "ndefMessage is null";
                }


            }
                else
            {
                ndef.close();
                return "error";
            }

        }
                catch (IOException e)
                {
                    return e.getMessage();
                }

                catch (Exception e)
                {
                    return e.getMessage();
                }

            }

        public String make_ndef(Intent intent,Tag tag)
        {

            String s = "";
            Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            s = data.length + "\n\n  " + data[0].toString();
            Log.e("eeeee",s);

            try {
                if (data != null)
                {


                    for (int i = 0; i < data.length; i++)
                    {
                        NdefRecord[] recs = ((NdefMessage) data[i]).getRecords();
                    }


                    return "TAG_NDEF";
                }
                else return "ERROR";
            }
            catch (Exception e) {
                return e.getMessage();
            }

        }




    }

