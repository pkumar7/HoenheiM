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
}
