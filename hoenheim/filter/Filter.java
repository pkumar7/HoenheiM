package hoenheim.filter;

public class Filter
{
    public String filterUsername(String input)
    /* TODO: 
            - use the builder pattern to make it such that it can
            accept an optional blacklist
      */
    {
        StringBuilder output = new StringBuilder();

        for( char c : input.toCharArray() )
        {
            if( Character.isLetter(c) || Character.isDigit(c) ) {
                output.append(c);
            }
        }
        return output.toString();
    }
    
    public String filterPhonenumber(String input)
    {
        StringBuilder output = new StringBuilder();
        
        for ( char c : input.toCharArray() )
        {
            if( c == '+' || Character.isDigit(c) )
                output.append(c);
        }
        return output.toString();
    }
    
    public String alphabetsOnlyFilter(String input)
    {
       StringBuilder output = new StringBuilder();
       
       for ( char c : input.toCharArray() )
       {
          if( Character.isLetter(c) )
             output.append(c);
         }
         return output.toString();
     }

    public String alphanumericsOnlyFilter(String input)
    {
       StringBuilder output = new StringBuilder();

       for ( char c : input.toCharArray() )
       {
          if( Character.isLetter(c) || Character.isDigit(c) )
             output.append(c);
       }
       return output.toString();
    }

    public String filterAddress(String input)
    {
        StringBuilder output = new StringBuilder();
        
        //Consider the usage of other different values in address

        for ( char c : input.toCharArray() )
        {
            
            if( c == '\'' )
                output.append('/');
                
            if( Character.isLetter(c) ||
                Character.isDigit(c)  ||
                c == ' '              ||
                c == '.'              ||
                c == '-'              ||
                c == ','              ||
                c == '/' )
                  output.append(c);
        }
        return output.toString();
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
          StringBuilder output = new StringBuilder();

          if(isLatin)
          {
             for ( char c : input.toCharArray() )
             {
                if( Character.isLetter(c) || Character.isDigit(c) 
                     || c == '.' || c == '-' || c == '_')
                   output.append(c);
             }
          }
          
          else
          {
             for ( char c : input.toCharArray() )
             {
                if( c - '0' > 0x1F && c !=';' &&
                    c != '\\' && c != '\'' && c != '"')
                {
                   output.append(c);
                }
             }
          }
          return output.toString();
     }

     public String filterCookies(String input)
     {
        /* Filter characters from 0x00 - 0x1F(control characters)
        and high bit characters(0x80-0xFF), stray quotes, commas, semicolons,
        backslashes
        */
        StringBuilder output = new StringBuilder();
        
        for ( char c : input.toCharArray() )
        {
              if( c != '\'' && c != ',' && c != ';' &&
                  c != ',' && c != '\\' &&
                  c - '0' > 0x1F && c - '0' < 0x80 )
                 output.append(c);
        }
        return output.toString();
      }
      
      public String htmlToPlaintext(String input)
      {
         /* in first pass, remove well formed tags, i.e. left angular bracket, some text
         and the right angular brackets.
         in second pass, encode/filter any remaining tags.
         */
         StringBuilder tempOutput = new StringBuilder();
         StringBuilder output = new StringBuilder();
         boolean insideTag = false;
         
         // First pass
         for ( char c : input.toCharArray() )
         {
            if ( c == '<' )
            {
               insideTag = true;
               continue;
            }
            else if ( c == '>' )
            {
               insideTag = false;
               continue;
            }

            if ( ! insideTag )
               tempOutput.append(c);
         } 
         
         // Second pass
         for( char c : input.toCharArray() )
         {
            if ( c == '<' )
               output.append("&lt;");
            else if ( c == '>' )
               output.append("&gt;");
            else if ( c == '&' )
               output.append("&amp;");
         }
         return output.toString();
      }
}