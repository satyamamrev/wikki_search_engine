import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.File;
import java.util.HashMap;

import java.util.List;

import java.util.Map;
import java.lang.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class engine 
{
	public static  stemmer  s = new stemmer();
	//public static TreeMap<String, HashMap<String, HashMap<String, Integer> > > hash_map= new TreeMap<String, HashMap<String, HashMap<String, Integer> >>();
	public static HashMap<String, HashMap<String, HashMap<String, Integer> > > hash_map= new HashMap<String, HashMap<String, HashMap<String, Integer> >>();
	public static void main(String[] args) 
	{
		
		// TODO Auto-generated method stub
		 long starttime=System.nanoTime();
		  /*String inputFileName="", outputIndexFileName="";
		  if(args.length == 2)
		  {
				inputFileName = args[0].trim();
				outputIndexFileName = args[1].trim();
		  }
		  else
		  {
			  System.out.println("Invalid number of command line arguments...");
			  System.exit(0);
		  }*/

	      try 
	      {
	    	  String inputFileName="src/"+"sample_300.xml";
	    	  File inputFile = new File(inputFileName);
	    	  
	          SAXParserFactory factory = SAXParserFactory.newInstance();
	          SAXParser saxParser = factory.newSAXParser();
	          try
	          {
	        	  UserHandler userhandler = new UserHandler();
	           	  saxParser.parse(inputFile, userhandler);
	          }       
	          catch (Exception e) 
	          {
	        	  e.printStackTrace();
	          }
	         
	       } 
	      catch (Exception e) 
	      {
	         e.printStackTrace();
	      }
	      long endtime=System.nanoTime();
	      System.out.println();
	    
	      FileWriter f = null;
		try {
			f = new FileWriter("index1.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        Iterator<Entry<String, HashMap<String, HashMap<String, Integer>>>> it = engine.hash_map.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        try {
					f.write(pair.getKey()+" "+ pair.getValue()+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				it.remove();
		    }
		    try {
				f.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	      System.out.println((float)(endtime-starttime)/1000000+" ms");
	}   
}

class UserHandler extends DefaultHandler 
{
	boolean title=false;
	boolean TEXT=false;
	boolean revision=false;
	boolean id=false;
	StringBuilder str1=new StringBuilder("");
	StringBuilder title_data=new StringBuilder("");
	StringBuilder data=new StringBuilder("");
	StringBuilder doc_id=new StringBuilder("");
	
	String check="";
	
   @Override
   public void startElement(String uri, 
      String localName, String qName, Attributes attributes)
         throws SAXException 
   {
	   switch(qName)
	   {
	   case "text" : TEXT=true;
	   				 break;
	   case "mediawiki":
						
		   
		   				break;
		   				
	   case "revision" : revision=true;
	   						break;
	   case "id" : if(revision==false)
				   {
		   				id=true;
				   }
	   				break;
	   case "title" : title=true;
	   					break;
	   				
	   }
	   
	   
   }
      
   

   @Override
   public void endElement(String uri, 
      String localName, String qName) throws SAXException 
  {
	   wikiparser token = new wikiparser();
	   //FileWriter writer;
	   switch(qName)
	   {
	   case "text" : 	int length_page=data.length();
		   				int flag=0;
	   					for(int i=0;i<length_page-23;i++)
	   					{
	   						
	   						
	   						//************RECENT	FOR REMOVING REFERENCE (OPTIMIZED)**********//
	   						
	   						if(data.substring(i,i+4).equals("<ref"))
	   						{
	   							
	   							for(int j=i+50;j<length_page-13;j++)       
	   							{
	   								if(data.substring(j, j+4).equals("ref>"))
	   								{
	   							
	   									i=j+4;
	   									break;
	   								}
	   							}
	   							
	   							
	   							
	   						}
	   						
	   						
	   						// ******RECENT FOR CATEGORY (OPTIMIZED)**************
	   						
	   						if(data.substring(i,i+11).equals("[[Category:"))
	   						{	
	   							check="c";
	   							flag=0;
	   							for(int j=i+11;j<length_page;j++)
	   							{

	   								char c=data.charAt(j);

		   							if((c >=65 && c <=90 ) || (c >=97 && c <=122) )
		   							{
		   								if(flag==0)
		   									flag=1;
		   								str1.append(c);
		   							}
		   							else if(flag==1)
		   							{
		   								//category_text.append(str1.toString().to);
		   								token.tokenize(str1.toString().trim().toLowerCase(),doc_id.toString().trim(),check);
		   								//category_text+=str1.toString().toLowerCase();
		   								//category_text+=" ";
		   								str1.setLength(0);
		   								flag=0;
		   								break;
		   							}
	   							}
	   							
	   						}
	   						
	   						//	********** FOR INFOBOX (OPTIMIZED)************//
	   						
	   						else if(data.substring(i,i+9).equals("{{infobox") ||data.substring(i,i+9).equals("{{Infobox") )
	   						{	
	   							check="i";
	   							flag=0;
	   							int brktcnt=2; 
	   							for(int j=i+9;j<length_page;j++)
	   							{
	   								char c=data.charAt(j);
	   								switch(c)
	   								{
	   								case '{' :brktcnt++;
	   											break;
	   								case '}' : brktcnt--;
	   											break;
	   																			
	   								}
	   								
		   							if((c >=65 && c <=90 ) || (c >=97 && c <=122) )
		   							{
		   								if(flag==0)
		   									flag=1;
		   								str1.append(c);
		   							}
		   							else if(flag==1)
		   							{

		   								token.tokenize(str1.toString().trim().toLowerCase(),doc_id.toString().trim(),check);
		   								str1.setLength(0);
		   								flag=0;
		   							}
		   							if(brktcnt==0)
	   								{
		   								flag=0;
	   									i=j-1;
	   									break;
	   								}
	   								
	   							}
	   							
	   						
	   						}
	   						// ******* PARSING ==REFERENCES==  && == REFERENCES == || ==EXTERNAL LINKS== && == EXTERNAL LINKS == ***************
	   						else if(data.substring(i, i+2).equals("=="))
	   						{
	   							int j=length_page,x=0;
	   							flag=0;
	   								if(data.substring(i+2, i+13).equals(" References"))
	   								{
	   									check="r";
	   									j=i+15;
	   								}
	   								else if(data.substring(i+2, i+12).equals("References"))
	   								{
	   									check="r";
	   									j=i+14;
	   								}
	   								else if(data.substring(i+2, i+17).equals(" External links"))
	   								{
	   									check="l";
	   									j=i+19;
	   								}
	   								else if(data.substring(i+2, i+16).equals("External links"))
	   								{
	   									check="l";
	   									j=i+18;
	   								}
	   								while(j<length_page)
	   								{
	   									char c=data.charAt(j);
		   								if(c=='{' && data.charAt(j+1)=='{')
		   								{
		   									int brckcnt=2;
		   									for(int k=j+2;k<length_page;k++)
		   									{
		   										switch(data.charAt(k))
		   										{
		   										case '}' : brckcnt--;
		   													break;
		   										case '{' : brckcnt++;
		   													break;
		   												
		   										}
		   										if(brckcnt==0)
		   										{
		   											i=k;
		   											break;
		   										}
		   									}
		   								}
		   								else if(c=='*')
		   								{
		   							
		   									for(int k=j+1;k<length_page;k++)
		   									{
		   										c=data.charAt(k);
		   										if(c=='\n' && data.charAt(k+1)=='\n')
		   										{
		   											i=k;
		   											x=1;
		   											break;
		   										}
		   										else if((c>=65 && c<=90) || c>=97 && c<=122 )
		   										{
		   											if(flag==0)
		   			   									flag=1;
		   			   								str1.append(c);
		   										}
		   										else if(flag==1)
		   			   							{
		   											token.tokenize(str1.toString().trim().toLowerCase(),doc_id.toString().trim(),check);
		   											//System.out.println(str1 );
		   											//category_text.append(str1.toString().trim().toLowerCase());
		   			   								str1.setLength(0);
		   			   								//count++;
		   			   								
		   			   								flag=0;
		   			   							}
		   									}
		   								}
		   								else if(x==1 && c=='=')
		   								{
		   									//System.out.println(count);
		   									break;
		   								}
		   								j++;
		   							}
	   								
	   						}
	   						
	   						
	   						//***************** OPTIMIZE NORMAL TEXT (OPTIMIZED)
	   						else
	   						{
	   							//System.out.println("hey");
	   							check="b";
	   							char c=data.charAt(i);
	   							if((c >=65 && c <=90 ) || (c >=97 && c <=122) )
	   							{
	   								if(flag==0)
	   									flag=1;
	   								str1.append(c);
	   							}
	   							else if(flag==1)
	   							{
	   								//str1.append(' ');
	   								token.tokenize(str1.toString().trim().toLowerCase(),doc_id.toString().trim(),check);
	   								//System.out.println(str1 );
	   								str1.setLength(0);
	   								flag=0;
	   							}
	   							
	   							
	   						}
	   					
	   				}
	   					//System.out.println(infobox_text);
	   					/*System.out.println(category_text);
	   					System.out.println(infobox_text);*/
	   					//System.out.println(normal_text);
	   					//wikiparser tokens =new wikiparser();
	   					//tokens.tokenize(category_text);
	   					//token.addtofile(writer);
	   					
	   					//String cat_text[]=category_text.toString().split(" ");
	   					/*for(int i=0;i<cat_text.length;i++)
	   					{
	   						System.out.println(cat_text[i]);
	   					}*/
	   					str1.setLength(0);
	   					data.setLength(0);
		   				TEXT=false;
	   					break;
	   case "mediawiki":break; 
	   
	 
		   					
	   case "revision" : revision=false;
	   						break;
	   						
	   case "title" :	
						
		   				title=false;
		   				check="";
		   				break;
	   case "id" : if(revision==false)
				   {
		   				id=false;
				   }
	   				//data.setLength(0);
	   				break;
	   case "page" :int flag1=0,length=title_data.length();
					check="t";
					for(int j=0;j<length;j++)
					{
			
						char c=title_data.charAt(j);
			
						if((c >=65 && c <=90 ) || (c >=97 && c <=122) )
						{
							if(flag1==0)
								flag1=1;
							str1.append(c);
						}
						else if(flag1==1)
						{
							//category_text.append(str1.toString().to);
							token.tokenize(str1.toString().trim().toLowerCase(),doc_id.toString().trim(),check);
							//category_text+=str1.toString().toLowerCase();
							//category_text+=" ";
							//System.out.println(str1);
							str1.setLength(0);
							flag1=0;
						}
					} 
		   			doc_id.setLength(0);
	   				str1.setLength(0);
	   				data.setLength(0);
	   					check="";
	   					title=false;
	   					TEXT=false;
	   					revision=false;
	   					id=false;
	   					title_data.setLength(0);
	   					
	   					
	   					break;
	   	}
						
		   				
	   				
	   
			   
  }
   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException 
    {
	   if(TEXT==true)
	   {
		   data.append(new String(ch,start,length));
	   }
	   else if(title==true)
	   {
		   title_data.append(new String(ch,start,length));
	   }
	   else if(id==true)
	   {
		   doc_id.append(new String(ch,start,length));	
	   }
	   
	   
	   
		   
	}
}



