define("ikj/cookie/1.4.0/cookie",["$"],function(a){function b(a){return i.raw?a:encodeURIComponent(a)}function c(a){return i.raw?a:decodeURIComponent(a)}function d(a){return b(i.json?JSON.stringify(a):String(a))}function e(a){0===a.indexOf('"')&&(a=a.slice(1,-1).replace(/\\"/g,'"').replace(/\\\\/g,"\\"));try{return a=decodeURIComponent(a.replace(h," ")),i.json?JSON.parse(a):a}catch(b){}}function f(a,b){var c=i.raw?a:e(a);return g.isFunction(b)?b(c):c}var g=a("$"),h=/\+/g,i=g.cookie=function(a,e,h){if(void 0!==e&&!g.isFunction(e)){if(h=g.extend({},i.defaults,h),"number"==typeof h.expires){var j=h.expires,k=h.expires=new Date;k.setDate(k.getDate()+j)}return document.cookie=[b(a),"=",d(e),h.expires?"; expires="+h.expires.toUTCString():"",h.path?"; path="+h.path:"",h.domain?"; domain="+h.domain:"",h.secure?"; secure":""].join("")}for(var l=a?void 0:{},m=document.cookie?document.cookie.split("; "):[],n=0,o=m.length;o>n;n++){var p=m[n].split("="),q=c(p.shift()),r=p.join("=");if(a&&a===q){l=f(r,e);break}a||void 0===(r=f(r))||(l[q]=r)}return l};i.defaults={},g.removeCookie=function(a,b){return void 0===g.cookie(a)?!1:(g.cookie(a,"",g.extend({},b,{expires:-1})),!g.cookie(a))}});
