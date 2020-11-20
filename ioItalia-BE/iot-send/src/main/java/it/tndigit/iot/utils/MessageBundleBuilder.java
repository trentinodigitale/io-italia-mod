package it.tndigit.iot.utils;

import org.bouncycastle.i18n.MessageBundle;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


@Service
public class MessageBundleBuilder{

    /**
     * Returns the value of 'msg' contained in the user's Locale ResourceBundle
     *
     * @param msg    name
     * @param params params
     * @return value of 'msg' contained in the user's Locale ResourceBundle
     */
    public String getMessage(String msg, String... params) {

        ResourceBundle bundle = ResourceBundle.getBundle("message", Locale.ITALY);
        MessageFormat formatter = new MessageFormat("");
        /*formatter.setLocale("");*/
        try{

            String valore = bundle.getString(msg);
            formatter.applyPattern(new String(valore.getBytes("ISO-8859-1"), "UTF-8"));
            return formatter.format(params);

        }catch (UnsupportedEncodingException uEx){
            return "Codifica errata";

        }



    }
}
