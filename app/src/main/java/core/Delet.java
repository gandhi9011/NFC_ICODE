package core;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Admin on 06-08-2016.
 */
public class Delet
{
public String delete_recored(Tag tag,String data)

{

    String s = "",s1="";
    try

    {   Ndef ndef=Ndef.get(tag);
        ndef.connect();
        NdefMessage ndefMessage = ndef.getNdefMessage();
        if (ndefMessage != null) {
            NdefRecord[] ndefRecords = ndefMessage.getRecords();
            int len_records=ndefRecords.length;
            NdefRecord[] ndefRecords1=new NdefRecord[len_records-1];

            for (int j = 0; j < len_records; j++) {
                if (ndefRecords[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(ndefRecords[j].getType(), NdefRecord.RTD_TEXT)) {
                    byte[] payload = ndefRecords[j].getPayload();
                    String textEncoding;
                    if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
                    else textEncoding = "UTF-16";
                    //String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                    int langCodeLen = payload[0] & 0077;

                    s = (
                            new String(payload, langCodeLen + 1,
                                    payload.length - langCodeLen - 1, textEncoding)
                            );
                }
                Log.e("fffffffffffff",data+".."+s);
                if ((s.equals(data)))
                {
                    Log.e("jjjjjjjjjjjj",data+".."+s);

                    int z=j;
                    int x=0;
                    for(int y=0;y<len_records;y++)
                    {
                        if(y!=z)
                        {
                          ndefRecords1[x]=ndefRecords[y];
                            x++;

                        }
                    }


                }

            }

            if(ndefRecords1.length==0)
            {
                return "Error";
            }
            else
            {
                NdefMessage ndefMessage1=new NdefMessage(ndefRecords1);
                ndef.writeNdefMessage(ndefMessage1);
            }
            ndef.close();
            return "DELETED";
        } else {
            return "Error";
        }
    }
    catch(IOException io){
        return io.getMessage();
    }catch(FormatException fe){
        return fe.getMessage();
    }

}
}
