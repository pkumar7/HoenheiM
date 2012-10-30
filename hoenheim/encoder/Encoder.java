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
      int i = 0;
      StringBuilder output = new StringBuilder();
      
      for( i = 0; i < input.length(); ++i )
      {
         if ( input.charAt(i) == ';' )
            output.append(Integer.toHexString(input.charAt(i) - '0'));
      }
      return output.toString();
   }

}