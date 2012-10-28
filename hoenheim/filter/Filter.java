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
            if( Character.isLetter(input.charAt(i)) ) {
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
    

}
