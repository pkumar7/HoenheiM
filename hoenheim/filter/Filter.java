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
            
    public String filterUrl(String input)
    {
        StringBuilder filtered_query_section = new StringBuilder();
        StringBuilder filtered_host_name = new StringBuilder();
        StringBuilder filtered_path_section = new StringBuilder();
        StringBuilder filtered_fragment_identifier = new StringBuilder();
        StringBuilder filtered_url = new StringBuilder();

        String delim = "[:]+";
        String[] scheme_and_rest = input.split(delim);
        String url_scheme = scheme_and_rest[0];
        url_scheme = (url_scheme.trim()).toString();
        String url_rest = scheme_and_rest[1];

        // Permit only http://, https:// and ftp:// 
        if( (url_scheme.equals("https")  ||
            url_scheme.equals("http")    ||
            url_scheme.equals("ftp"))    && 
            url_rest.startsWith("//") )
            
        {
            // Remove first two chars i.e '//' from the url
            url_rest = url_rest.substring(2);

            for (int i = 0; i < url_rest.length(); ++i)
            {
                char symbol = url_rest.charAt(i);
                // most browsers will also accept “ \” as a delimiter in place of a forward slash
                if( symbol == '\'' )
                    symbol = '/';
                
                if( symbol == '/' || symbol == '?' || symbol == '#' )
                {
                    String authority_section = url_rest.substring(0, url_rest.indexOf(symbol));
                    String host_name = authority_section;
                    for (int k = 0; k < authority_section.length(); ++k)
                    {
                        // TODO handle login credentials part of url.
                        if( authority_section.charAt(k) == '@' )
                        {
                            String login_credentials = authority_section.substring(0, authority_section.indexOf('@'));
                            // Need to further break down login credentials to username and password
                            
                            host_name = authority_section.substring(authority_section.indexOf('@'));
                            //Removing first '@' symbol
                            host_name = host_name.substring(1);
                            break;
                        }
                    }                 

                    // Filter host name
                    for ( int j = 0; j < host_name.length(); ++j )
                    {
                            if( Character.isLetter(host_name.charAt(j)) ||
                                Character.isDigit(host_name.charAt(j))  ||
                                host_name.charAt(j) == '-'              ||
                                host_name.charAt(j) == '.' )
                          
                                    filtered_host_name.append(host_name.charAt(j));
                    }
                    
                    String after_host_section = url_rest.substring(url_rest.indexOf(symbol));

                    if( after_host_section.startsWith("/") || after_host_section.startsWith("\\"))
                        after_host_section = after_host_section.substring(1);

                    for (int k = 0; k < after_host_section.length(); ++k)
                    {
                         if( after_host_section.charAt(k) == '?' || after_host_section.charAt(k) == '#' )
                         {
                              String path_section = after_host_section.substring(0, after_host_section.indexOf(after_host_section.charAt(k)));
                              // Filter the path path_section
                              //TODO: Take each value between "/" filter then join again for better filtering.
                              
                              for ( int j = 0; j < path_section.length(); ++j )
                              {
                                  if( Character.isLetter(path_section.charAt(j)) ||
                                      Character.isDigit(path_section.charAt(j))  ||
                                      path_section.charAt(j) == '-'              ||
                                      path_section.charAt(j) == '_'              ||
                                      path_section.charAt(j) == '/' )
                                
                                          filtered_path_section.append(path_section.charAt(j));
                              }
                                
                              String fragment_identifier = "";
                              if( after_host_section.charAt(k) == '#' )
                              {
                                  fragment_identifier = after_host_section.substring(after_host_section.indexOf(after_host_section.charAt(k)));
                              }
                              if( after_host_section.charAt(k) == '?' )
                              {
                                  String query_section = after_host_section.substring(after_host_section.indexOf(after_host_section.charAt(k)));
                                  query_section = query_section.substring(1);
                                  
                                  // If Fragment Identifier is present
                                  for (int l = 0; l < query_section.length(); ++l)
                                  {
                                      if( query_section.charAt(l) == '#' )
                                      {    
                                          fragment_identifier = query_section.substring(query_section.indexOf('#'));
                                          fragment_identifier = fragment_identifier.substring(1);
                                          query_section = query_section.substring(0, query_section.indexOf('#'));
                                          break;
                                      }
                                          //TODO: Filter fragment identifier
                                  }
                                  
                                  // Filter query section
                                  for ( int j = 0; j < query_section.length(); ++j )
                                  {
                                      if( Character.isLetter(query_section.charAt(j)) ||
                                          Character.isDigit(query_section.charAt(j))  ||
                                          query_section.charAt(j) == '-'              ||
                                          query_section.charAt(j) == '.' )
                                    
                                              filtered_query_section.append(query_section.charAt(j));
                                  }
                              }

                              // Filter Fragment identifier
                              for ( int j = 0; j < fragment_identifier.length(); ++j )
                              {
                                  if( Character.isLetter(fragment_identifier.charAt(j))  ||
                                      Character.isDigit(fragment_identifier.charAt(j)) )
                                
                                          filtered_fragment_identifier.append(fragment_identifier.charAt(j));
                              }
                              

                                                                
                              break;
                         }
                    }
                break;
                }
            }

            //Recreate the url
            // Add the scheme
            filtered_url.append(url_scheme + "://");
            // Add host name
            filtered_url.append(filtered_host_name);
            if( filtered_path_section.length() > 0 )
            {
                filtered_url.append('/');
                filtered_url.append(filtered_path_section);
            }
            if( filtered_query_section.length() > 0 )
            {
                filtered_url.append("?");
                filtered_url.append(filtered_query_section);
            }
            if( filtered_fragment_identifier.length() > 0 )
            {
                filtered_url.append("#");
                filtered_url.append(filtered_fragment_identifier);
            }
          
        }
        //return filtered_url;
        return filtered_url.toString();            
    }
      
}

