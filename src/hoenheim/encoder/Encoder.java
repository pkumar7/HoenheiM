/**
 * 
 */
package hoenheim.encoder;

/**
 * @author eQuiNoX
 *
 */

public class Encoder
{
   public String urlEncoder(String input)
   {
      /*
      Encode : / # [ ] @
      ! $ & ' ( ) * + , ; =
      */
      StringBuilder output = new StringBuilder();
      for ( char c : input.toCharArray() )
      {
         //if c == "[:/#\\[\\]@!$\\&\\\\(\\)\\*\\+,;=]"
         if( Character.toString(c).matches("[:/#\\[\\]@!$\\&\\\\(\\)\\*\\+,;=-]") )
         /*if ( c == ':' || 
              c == '/' ||
              c == '#' ||
              c == '[' ||
              c == ']' ||
              c == '@' ||
              c == '!' ||
              c == '$' ||
              c == '&' ||
              c == '\'' ||
              c == '(' ||
              c == ')' ||
              c == '*' ||
              c == '+' ||
              c == ',' ||
              c == ';' ||
              c == '=' )    */
         {
            String hex;
            int ordinate = c - '0';
            hex = Integer.toHexString(ordinate);
            output.append("%" + hex);
         }
         else
            output.append(c);
      }
      return output.toString();
   }
   
   public String encodeLocationHeaders(String input)
   {
      // TODO : Need to find appropriate kind of encoding. Entity encoding is
      // probably not the right way.
      StringBuilder output = new StringBuilder();
      
      for( char c : input.toCharArray() )
      {
         if ( c == ';' )
            output.append("&#x" + (c - '0'));
      }
      return output.toString();
   }
   
   public String encodeRawInsideXMLTag(String input)
   {
      /* 
      Raw data that contains even < and & can be safely used inside
      an arbitrary XML tag by encapsulating it inside
      <![CDATA[    ....   ]]>
      */
      return "<![CDATA[" + input + "]]>";
   }
   
   public String encodeJsInsideString(String input)
   {
      /* This function is to be used in scenarios where user
      input goes/might go inside a quoted string inside a javascript block.
      Please note that for other kind of scripts, the encoding might vary
      and that this function is not to be used.
      For non alphanumerics, entity encode.
      NOTE : this is for JS inside string in HTML, NOT XHTML.
      For XHTML, you need to make sure the script block is inside a CDATA!!!
      */
      StringBuilder output = new StringBuilder();
      
      for( char c : input.toCharArray() )
      {
         if( c == '<' )
            output.append("&lt;");
         else if ( c == '>' )
            output.append("&gt;");
         else if ( c == '&' )
            output.append("&amp;");
         else if ( Character.isLetter(c) || Character.isDigit(c) )
            output.append("&#x" + Integer.toHexString(c - '0'));
      }
      return output.toString();
   }
   
   // CSS functions
   public String encodeCSSStringsAndUrls(String input)
   {
	   /* - convert a letter into \xxxxxx where i.e. e => \000065
	    * or you can have a \x65<space>, inside strings.
	    * 
	    * - if there are HTML characters such as <, > etc, do not encode
	    * using CSS encoding as they are rendered before CSS.
	    * 
	    * - IE does not allow this kind of escaping inside url(""); strings
	    * - #TODO #scanner check if https sites load css files from http
	    * - You can encode " and \ using another preceding \ BUT do not use this for anything else
	    * - also escape high bit characters
	    * - #TODO add stuff for adding URLs into CSS(ch2)
	    */
	   StringBuilder output = new StringBuilder();
	   
	   for ( char c : input.toCharArray() )
	   {
		   switch( c )
		   {
		   		case '"':
		   		case '\'':
		   			output.append('\\' + c);
		   			break;
		   		case '<':
		   		case '>':
		   			String temp = Integer.toHexString(c);
		   			StringBuilder f = new StringBuilder();
		   			f.append("\\");
		   			for ( int i = 0; i < 6 - temp.length(); ++i )
		   			{
		   				f.append("0");
		   			}
		   			f.append(temp);
		   			output.append(f.toString());
		   			break;
		   }
		   /*
		   // Control Character or High-Bit Character
		   if ( c <= 0x1F || c >= 0x8F )
		   {
			   String temp = Integer.toHexString(c);
			   StringBuilder f = new StringBuilder();
			   f.append("\\");
			   for ( int i = 0; i < 6 - temp.length(); ++i )
			   {
				   f.append("0");
			   }
			   f.append(temp);
			   output.append(f.toString());
		   }
		   */		   
	   }
	   return output.toString();
   }
}
