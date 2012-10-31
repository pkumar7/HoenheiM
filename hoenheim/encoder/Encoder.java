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
   

}