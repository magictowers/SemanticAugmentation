// ==UserScript==
// @name        wikipedia_languages_filter_PAAquery
// @namespace   http://cis.jku.at/wischenbart
// @description Filters unwanted language links in Wikipedia articles
// @include     http*://*.wikipedia.org/wiki/*
// @version     1
// @grant       GM_xmlhttpRequest
// @require    http://boat.lachsfeld.at/paa/PAA_user_query.js
// ==/UserScript==




console.info('Hello World!')



// INITIALIZATION

var preferredLanguages; 

var buttonElement;




// hide languages in the page which are not preferred ones
function hideTheOtherLanguages(preferredLangsFromServer) {

  preferredLanguages = preferredLangsFromServer;
  
  if (preferredLanguages == null || preferredLanguages == undefined) {

    // if we do not have the information, do nothing --- no personalization
    
  } else {
    
    // hide languages that are not favorite ones

    var languagesList = document.getElementById('p-lang')
    var languageElemsList = document.evaluate('/html/body/div[4]/div[2]/div[@id="p-lang"]/div/ul', document.body, null, XPathResult.ANY_TYPE, null).iterateNext()
    // EN: /html/body/div[4]/div[2]/div[6]/div/ul
    // FR: /html/body/div[4]/div[2]/div[6]/div/ul
    // ES: /html/body/div[4]/div[2]/div[5]/div/ul
    // TODO replace with relative expression --> ('/div[id=p-lang]/ul/li[1]') ?

    var languageElems = languageElemsList.getElementsByTagName('li');

    // iterate through language links on website
    for (var i = 0; i < languageElems.length; ++i) {

      var lang = languageElems[i].children[0].getAttribute('lang')

      // if the current one is not a favorite one, hide it
      if (!isPreferredLanguage(lang)) {
        languageElems[i].style.display = 'none'
      }
    }

    // add a button to show all languages

    buttonElement = document.createElement('input');
    buttonElement.setAttribute('type', 'button');
    buttonElement.setAttribute('value', 'more');
    buttonElement.setAttribute('name', 'showMoreButton');
    buttonElement.onclick = showMoreButtonClicked;
    languageElemsList.appendChild(buttonElement);
  }
}



// get list of spoken languages from the PAA_user_query / PAA recommendation server
// TODO give info about callback as parameter and call it from there with the result --- now hideTheOtherLanguages(response.responseText); is called from the other script hardcoded!
userQuery("languages");




// FUNCTION DEFINITIONS

// if the 'more'-button is clicked, show all languages again
function showMoreButtonClicked() {
  var languageElemsList = document.evaluate('/html/body/div[4]/div[2]/div[@id="p-lang"]/div/ul', document.body, null, XPathResult.ANY_TYPE, null).iterateNext()
  var languageElems = languageElemsList.getElementsByTagName('li');
  for (var i = 0; i < languageElems.length; ++i) {
    var lang = languageElems[i].children[0].getAttribute('lang')
    if (isPreferredLanguage(lang)) {
      // do nothing for languages that are already shown
    } else {
      // show again the ones that were hidden
      languageElems[i].style.display = 'inherit'
    }
  }
  // finally hide the 'more'-button
  buttonElement.style.display = 'none'
}


// check if a given language is a preferred one (as given by the string preferredLanguages previously queried using the PAA_user_query)
function isPreferredLanguage(lang) {
  var isPreferred = false;
  for (var i = 0; i < preferredLanguages.length && !isPreferred; i++) {
    //if (preferredLanguages[i] == lang) {
    if (preferredLanguages.substring(i, i+2) == lang) { // TODO parsing the String like this is stupid --- TODO maybe use an implementation using Array or Set that is serializable!
      isPreferred = true;
    }
  }
  return isPreferred;
}














console.info('Goodbye World!')
