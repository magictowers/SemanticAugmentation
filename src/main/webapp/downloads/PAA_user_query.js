
//TODO get the following from the PAA plugin (via GM storage?)
var serverURL = 'http://localhost:8080/'
//var serverURL = 'http://localhost:8080/paa/'
//var serverURL = 'http://localhost:8080/at.jku.cis.wischenbart.paa-server/'
var homeURL = serverURL + 'ui/home'
var userQueryURL = serverURL + 'query/userQuery'
var sparqlQueryURL = serverURL + 'query/sparqlQuery'

//TODO in particular the username!!!
var userName = 'elboato';



// QUERIES ###########################################################################








var getCookies = function(){
  var pairs = document.cookie.split(";");
  var cookies = {};
  for (var i=0; i<pairs.length; i++){
    var pair = pairs[i].split("=");
    cookies[pair[0]] = unescape(pair[1]);
  }
  return cookies;
}



function userQuery(query) {
  console.log("User Query: "+query+ " "+userName+" "+userQueryURL);
  

//var myCookies = getCookies();
//alert(myCookies.JSESSIONID);


  GM_xmlhttpRequest({
    method: "POST",
    url: userQueryURL,
    //data: encodeURIComponent("user="+userName+"&query="+query),
    data: "user="+userName+"&query="+query,
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      "Accept": "application/json"
      //"Content-Length": "50",
      //"Cookie": "JSESSIONID=8F859C7B7EA83E646DEE025BC2387593"
    },
    //synchronous : true,
    onload: function(response) {
      console.log("result: "+response.status+" "+response.responseText);
      // TODO store response to local variable
      // callback to userscript --- TODO this should not be hardcoded
      hideTheOtherLanguages(response.responseText);
    }
  });
}



function sqarqlQuery(query) {
  console.log("SPARQL Query: "+query+ " "+userName+" "+sparqlQueryURL);
  

//var myCookies = getCookies();
//alert(myCookies.JSESSIONID);




  GM_xmlhttpRequest({
    method: "POST",
    url: sparqlQueryURL,
    //data: encodeURIComponent("user="+userName+"&query="+query),
    data: "user="+userName+"&sparql="+query,
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      "Accept": "text/plain"
      //"Content-Length": "50",
      //"Cookie": "JSESSIONID=8F859C7B7EA83E646DEE025BC2387593"
    },
    //synchronous : true,
    onload: function(response) {
      console.log("result: "+response.status+" "+response.responseText);
      // TODO store response to local variable
      // TODO callback to userscript
    }
  });
}