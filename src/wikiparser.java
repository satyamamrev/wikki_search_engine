import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;


public class wikiparser {
	boolean instance=false;
	
	 HashSet stopWordsList = new HashSet<String>(Arrays.asList("a","again","about","above","across","after","against","all","almost","alone",
			"along","already","also","although","always","am","among","an","and","another","any","anybody","anyone","anything",
		"anywhere","are","led","zur","zum","zulu","width","area","areas","aren't","around","as","ask","asked","asking","asks","at","away","b","back","backed",
	"backing","backs","be","became","because","become","becomes","been","before","began","behind","being","beings","below",
	"best","better","between","big","both","but","by","c","cite","came","can","can't","cannot","case","cases","certain","certainly",
	"clear","clearly","com","come","coord","could","couldn't","d","did","didn't","differ","different","differently","do",
	"does","doesn't","doing","don't","done","down","downed","downing","downs","during","e","each","early","either","end",
	"ended","ending","ends","enough","even","evenly","ever","every","everybody","everyone","everything","everywhere","f",
	"face","faces","fact","facts","far","felt","few","find","finds","first","for","four","from","full","fully","further",
	"furthered","furthering","furthers","g","gave","general","generally","get","gets","give","given","gives","go","going",
	"good","goods","got","gr","great","greater","greatest","group","grouped","grouping","groups","h","had","hadn't","has",
	"hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","high","higher",
	"highest","him","himself","his","how","how's","however","http","https","i","i'd","i'll","i'm","i've","if","important",
	"in","interest","interested","interesting","interests","into","is","isn't","it","it's","its","itself","j","just","k",
	"keep","keeps","kind","knew","know","known","knows","l","large","largely","last","later","latest","least","less","let",
	"let's","lets","like","likely","long","longer","longest","m","made","make","making","man","many","may","me","member",
	"members","men","might","more","most","mostly","mr","mrs","much","must","mustn't","my","myself","n","nbsp","necessary",
	"need","needed","needing","needs","never","new","newer","newest","next","no","nobody","non","noone","nor","not",
	"nothing","now","nowhere","number","numbers","o","of","off","often","old","older","oldest","on","once","one","only",
	"open","opened","opening","opens","or","order","ordered","ordering","orders","other","others","ought","our","ours",
	"ourselves","out","over","own","p","part","parted","parting","parts","per","perhaps","place","places","point","pointed",
	"pointing","points","possible","present","presented","presenting","presents","problem","problems","put","puts","q",
	"quite","r","rather","really","right","room","rooms","s","said","same","saw","say","says","second","seconds","see",
	"seem","seemed","seeming","seems","sees","several","shall","shan't","she","she'd","she'll","she's","should","shouldn't",
	"show","showed","showing","shows","side","sides","since","small","smaller","smallest","so","some","somebody","someone",
	"something","somewhere","state","states","still","such","sure","t","take","taken","td","than","that","that's","the",
	"their","theirs","them","themselves","then","there","there's","therefore","these","they","they'd","they'll","they're",
	"they've","thing","things","think","thinks","this","those","though","thought","thoughts","three","through","thus","to",
	"today","together","too","took","toward","tr","turn","turned","turning","turns","two","u","under","until","up","upon",
	"us","use","used","uses","v","very","w","want","wanted","wanting","wants","was","wasn't","way","ways","we","we'd",
	"we'll","we're","we've","well","wells","went","were","weren't","what","what's","when","when's","where","where's",
	"whether","which","while","who","who's","whole","whom","whose","why","why's","will","with","within","without","won't",
	"work","worked","working","works","would","wouldn't","www","x","y","year","years","yet","you","you'd","you'll","you're",
	"you've","isbn","citation","near","org","wiki","net","major","rid","ism","en","help","young","younger","youngest","your","yours","yourself","yourselves","z",
	"ref","rev","rep","faq","main","jpg","php","none","refbegin","reflist","isbn",";",".","'","|","jpg","png","[","]","br","gt","&","lt","&gt","&lt","htm","en","php","isbn",
	"yes","aa","wikitext","wiki","faq","caption","page","div","left","thumb","thumbnail","file","aspx","index","edu","html","net","org","<",">","ref","refs","cite","pdf","url","web","link"
		));

		
		
	public void tokenize(String text,String doc_id,String check)
	{
		if(!stopWordsList.contains(text.toLowerCase()) && !text.isEmpty() && text.length()<12 )
		{
			if(!check.equals("t"))
			{
				engine.s.add(text.toCharArray(),text.length());
				try 
				{
					text=engine.s.stem();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(text.length()>2)
			{
				if(engine.hash_map.containsKey(text.toLowerCase()))
				{
					if(engine.hash_map.get(text).containsKey(doc_id))
					{
						if(engine.hash_map.get(text).get(doc_id).containsKey(check))
						{
							int value=engine.hash_map.get(text).get(doc_id).get(check)+1;
							engine.hash_map.get(text).get(doc_id).put(check, value);
						}
						else
						{
							engine.hash_map.get(text).get(doc_id).put(check, 1);
						}
					}
					else
					{
						
						HashMap<String, Integer> innerinner=new HashMap<String, Integer>();
						
						innerinner.put(check, 1);
						engine.hash_map.get(text).put(doc_id, innerinner);
	
						
						
					}
					
				}
				else
				{
					HashMap<String, Integer> innerinner=new HashMap<String, Integer>();
					innerinner.put(check,1 );
					HashMap<String, HashMap<String,Integer> > inner=new HashMap<String, HashMap<String,Integer> >();
					inner.put(doc_id,innerinner);
					engine.hash_map.put(text, inner);
				}
			}
		}
	}
	public void addtofile(FileWriter writer)
	{
		
	}
	
}

