package hoenheim.encoder;

public class Encoder
{
   public String urlEncoder(String input)
   {
      /*
      Encode : / # [ ] @
      ! $ & ' ( ) * + , ; =
      */
      int i = 0;
      StringBuilder output = new StringBuilder();
      
      for ( i = 0; i < input.length(); ++i )
      {
         if ( input.charAt(i) == ':' || 
              input.charAt(i) == '/' ||
              input.charAt(i) == '#' ||
              input.charAt(i) == '[' ||
              input.charAt(i) == ']' ||
              input.charAt(i) == '@' ||
              input.charAt(i) == '!' ||
              input.charAt(i) == '$' ||
              input.charAt(i) == '&' ||
              input.charAt(i) == '\'' ||
              input.charAt(i) == '(' ||
              input.charAt(i) == ')' ||
              input.charAt(i) == '*' ||
              input.charAt(i) == '+' ||
              input.charAt(i) == ',' ||
              input.charAt(i) == ';' ||
              input.charAt(i) == '=' )
         {
            String hex;
            int ordinate = input.charAt(i) - '0';
            hex = Integer.toHexString(ordinate);
            output.append("%" + hex);
         }
         else
            output.append(input.charAt(i));
      }
      return output.toString();
   }
   
   public String encodeLocationHeaders(String input)
   {
      // TODO : Need to find appropriate kind of encoding. Entity encoding is
      // probably not the right way.
      int i = 0;
      StringBuilder output = new StringBuilder();
      
      for( i = 0; i < input.length(); ++i )
      {
         if ( input.charAt(i) == ';' )
            output.append("&#x" + (input.charAt(i) - '0'));
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
      int i = 0;
      StringBuilder output = new StringBuilder();
      
      for( i = 0; i < input.length(); ++i )
      {
         if( input.charAt(i) == '<' )
            output.append("&lt;");
         else if ( input.charAt(i) == '>' )
            output.append("&gt;");
         else if ( input.charAt(i) == '&' )
            output.append("&amp;");
         else if ( Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)) )
            output.append("&#x" + Integer.toHexString(input.charAt(i) - '0'));
      }
      return output.toString();
   }
}
