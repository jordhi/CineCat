package cat.jordihernandez.cinecat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

public class CinemaParseXML {
	// We don't use namespaces
    private static final String ns = null;
    private Context mcontext;

    public ArrayList<Cinema> parse(InputStream in, Context c) throws XmlPullParserException, IOException {
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
    
    private ArrayList<Cinema> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Cinema> entries = new ArrayList<Cinema>();
       
        
        parser.require(XmlPullParser.START_TAG, ns, "dataroot");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("CINEMES")) {
                entries.add(readEntry(parser));
               
            } else {
                skip(parser);
            }
        }  
        return entries;
    }
    
 // Parses the contents of an CINEMA. If it encounters a ID, NOM, ADREÇA, LOCALITAT, or COMARCA tag, hands them off
 // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Cinema readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
    	gestioDBCinemes gcine = new gestioDBCinemes(mcontext);
    	parser.require(XmlPullParser.START_TAG, ns, "CINEMES");
    	String IdCinema = null;
    	String NomCinema = null;
    	String Adreca = null;
    	String Localitat = null;
    	String Comarca = null;
    	while (parser.next() != XmlPullParser.END_TAG) {
    		if (parser.getEventType() != XmlPullParser.START_TAG) {
    			continue;
    		}
    		String name = parser.getName();
    		if (name.equals("CINEID")) {
        	 IdCinema = readIdCinema(parser);
    		} else if (name.equals("CINENOM")) {
             NomCinema = readNomCinema(parser);
    		} else if (name.equals("CINEADRECA")) {
             Adreca = readAdreca(parser);
    		} else if (name.equals("LOCALITAT")) {
        	 Localitat = readLocalitat(parser);
    		} else if (name.equals("COMARCA")) {
        	 Comarca = readComarca(parser);
    		} else {
             skip(parser);
    		}
    	}
    	Cinema noucine = new Cinema(IdCinema, NomCinema, Adreca, Localitat, Comarca);
    	gcine.obrir();
    	gcine.addCinema(noucine);
    	gcine.tancar();
    	return noucine ;
    }

    //Processes title tags in the feed.
    private String readIdCinema(XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, "CINEID");
    	String IdCinema = readText(parser);
    	parser.require(XmlPullParser.END_TAG, ns, "CINEID");
    	return IdCinema;
    }

    private String readNomCinema(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "CINENOM");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "CINENOM");
	    return title;
    }
    
    private String readAdreca(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "CINEADRECA");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "CINEADRECA");
	    return title;
    }
    
    private String readLocalitat(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "LOCALITAT");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "LOCALITAT");
	    return title;
    }

    private String readComarca(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "COMARCA");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "COMARCA");
	    return title;
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
