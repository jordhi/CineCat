package cat.jordihernandez.cinecat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

public class SessioParseXML {
    private static final String ns = null;
    Context mcontext;
 
    public List<Sessio> parse(InputStream in, Context c) throws XmlPullParserException, IOException {
    	mcontext = c;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    private List<Sessio> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Sessio> entries = new ArrayList<Sessio>();

        parser.require(XmlPullParser.START_TAG, ns, "dataroot");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //Log.d("FILM","tag:"+name);
            // Starts by looking for the entry tag
            if (name.equals("SESSIONS")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }  
        return entries;
    }
    
 // Parses the contents of an Film. If it encounters a ID, NOM, ADREÇA, LOCALITAT, or COMARCA tag, hands them off
 // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Sessio readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
    	gestioDBSessions gsessio = new gestioDBSessions(mcontext);
    	parser.require(XmlPullParser.START_TAG, ns, "SESSIONS");
    	String idfilm = null;
    	String sesid = null;
    	String cineid = null;
    	String titol = null;
    	String sesdata = null;
    	String cinenom = null;
    	String localitat = null;
    	String comarca = null;
    	String cicleid = null;
    	String ver= null;
    	
   	
    	while (parser.next() != XmlPullParser.END_TAG) {
    		if (parser.getEventType() != XmlPullParser.START_TAG) {
    			continue;
    		}
    		String name = parser.getName();
    		//Log.d("FILM","tag:"+name);
    		    		
    		switch(name) {
    			case "IDFILM": idfilm = readSessio(parser,name);break;
    			case "ses_id":sesid = readSessio(parser,name);break;
    			case "CINEID": cineid = readSessio(parser,name);break;
    			case "TITOL": titol = readSessio(parser,name);break;
    			case "ses_data": sesdata = readSessio(parser,name);break;
    			case "CINENOM": cinenom = readSessio(parser,name);break;
    			case "LOCALITAT": localitat = readSessio(parser,name);break;
    			case "COMARCA": comarca = readSessio(parser,name);break;
    			case "CICLEID": cicleid = readSessio(parser,name);break;
    			case "ver": ver = readSessio(parser,name);break;
    			
    			default: skip(parser);
    		}
    	}
    	Sessio sessio = new Sessio(idfilm,sesid,cineid,titol,sesdata,cinenom,localitat,comarca,cicleid,ver);
    	 
    	gsessio.obrir();
    	gsessio.addSessio(sessio);
    	gsessio.tancar();
    	
    	return sessio;
    }

    //Processes title tags in the feed.
    private String readSessio(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, tag);
    	String item = readText(parser);
    	parser.require(XmlPullParser.END_TAG, ns, tag);
    	return item;
    }

   
 
    //For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
    	String result = "";
    	if (parser.next() == XmlPullParser.TEXT) {
    		result = parser.getText();
    		parser.nextTag();
    	}
    	return result;
    }

   
	
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
}
