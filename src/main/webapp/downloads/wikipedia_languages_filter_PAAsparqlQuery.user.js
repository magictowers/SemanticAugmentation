// ==UserScript==
// @name        wikipedia_languages_filter_PAAsparqlQuery
// @namespace   http://cis.jku.at/wischenbart
// @description sparqlTest
// @include     http*://*.wikipedia.org/wiki/*
// @version     1
// @grant       GM_xmlhttpRequest
// @require     http://boat.lachsfeld.at/paa/PAA_user_query.js
// ==/UserScript==


console.info('Hello aaaaaaaaaaaaaaaaaaaaaaaaa!')


// INITIALIZATION

// get list of spoken languages from the PAA_user_query / PAA recommendation server
// TODO give info about callback as parameter and call it from there with the result --- now hideTheOtherLanguages(response.responseText); is called from the other script hardcoded!
sqarqlQuery("SELECT ?person ?firstName ?lastName WHERE {?person <http://xmlns.com/foaf/0.1/firstName> ?firstName . OPTIONAL {?person <http://xmlns.com/foaf/0.1/lastName> ?lastName} .} limit 1");



console.info('Goodbye World!')
