package hoenheim.filter;

public class Filter
{
    public String filterUsername(String input)
    /* TODO: 
            - use the builder pattern to make it such that it can
            accept an optional blacklist
      */
    {
        int i = 0;
        String output = "";

        for( i = 0; i < input.length(); ++i )
        {
            if( Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)) ) {
                output += input.charAt(i);
            }
        }
        return output;
    }
    
    public String filterPhonenumber(String input)
    {
        int i = 0;
        String output = "";
        
        for ( i = 0; i < input.length(); ++i )
        {
            if( input.charAt(i) == '+' || Character.isDigit(input.charAt(i)) )
                output += input.charAt(i);
        }
        return output;
    }
    
    public String alphabetsOnlyFilter(String input)
    {
       int i = 0;
       String output = "";
       
       for ( i = 0; i < input.length(); ++i )
       {
          if( Character.isLetter(input.charAt(i)) )
             output += input.charAt(i);
         }
         return output;
     }

    public String alphanumericsOnlyFilter(String input)
    {
       int i = 0;
       String output = "";

       for ( i = 0; i < input.length(); ++i )
       {
          if( Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)) )
             output += input.charAt(i);
       }
       return output;
    }

    public String filterAddress(String input)
    {
        int i = 0;
        String output = "";
        
        //Consider the usage of other different values in address

        for ( i = 0; i<input.length(); ++i)
        {
            
            if( input.charAt(i) == '\'' )
                output += '/';
                
            if( Character.isLetter(input.charAt(i)) ||
                Character.isDigit(input.charAt(i))  ||
                input.charAt(i) == ' '              ||
                input.charAt(i) == '.'              ||
                input.charAt(i) == '-'              ||
                input.charAt(i) == ','              ||
                input.charAt(i) == '/' )
                
                output += input.charAt(i);
        }
        return output;
    }

    public String contentDispositionHeaderFilter(String input, boolean isLatin)
    {
       /* Content disposition headers might contain user controlled
          filenames. 
          For non latin characters,
            refer 2047 or 2231 or url style percentage encoding
            filter out 0x00 to 0x1F control characters
            escape semicolons, backslashes and quotes
       */
          int i = 0;
          String output = "";

          if(isLatin)
          {
             for ( i = 0; i < input.length(); ++i )
             {
                if( Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)) 
                     || input.charAt(i) == '.' || input.charAt(i) == '-' || input.charAt(i) == '_')
                   output += input.charAt(i);
             }
          }
          
          else
          {
             for ( i = 0; i < input.length(); ++i )
             {
                if( input.charAt(i) - '0' > 0x1F && input.charAt(i) !=';' &&
                    input.charAt(i) != '\\' && input.charAt(i) != '\'' && input.charAt(i) != '"')
                {
                   output += input.charAt(i);
                }
             }
          }
          return output;
     }

     public String filterCookies(String input)
     {
        /* Filter characters from 0x00 - 0x1F(control characters)
        and high bit characters(0x80-0xFF), stray quotes, commas, semicolons,
        backslashes
        */
        int i = 0;
        String output = "";
        
        for ( i = 0; i < input.length(); ++i )
        {
              if( input.charAt(i) != '\'' && input.charAt(i) != ',' && input.charAt(i) != ';' &&
                  input.charAt(i) != ',' && input.charAt(i) != '\\' &&
                  input.charAt(i) - '0' > 0x1F && input.charAt(i) - '0' < 0x80 )
                 output += input.charAt(i);
        }
        return output;
      }
}
