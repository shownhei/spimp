define("ikj/autocomplete/1.1.0/autocomplete",["$"],function(a){var b=a("$");!function(a){a.fn.extend({autocomplete:function(b,c){var d="string"==typeof b;return c=a.extend({},a.Autocompleter.defaults,{url:d?b:null,data:d?null:b,delay:d?a.Autocompleter.defaults.delay:10,max:c&&!c.scroll?10:150},c),c.highlight=c.highlight||function(a){return a},c.formatMatch=c.formatMatch||c.formatItem,this.each(function(){new a.Autocompleter(this,c)})},result:function(a){return this.bind("result",a)},search:function(a){return this.trigger("search",[a])},flushCache:function(){return this.trigger("flushCache")},setOptions:function(a){return this.trigger("setOptions",[a])},unautocomplete:function(){return this.trigger("unautocomplete")}}),a.Autocompleter=function(b,c){function d(){var d=x.selected();if(!d)return!1;var e=d.result;if(t=e,c.multiple){var g=f(s.val());if(g.length>1){var h,i=c.multipleSeparator.length,k=a(b).selection().start,l=0;a.each(g,function(a,b){return l+=b.length,l>=k?(h=a,!1):(l+=i,void 0)}),g[h]=e,e=g.join(c.multipleSeparator)}e+=c.multipleSeparator}return s.val(e),j(),s.trigger("result",[d.data,d.value]),!0}function e(a,b){if(p==r.DEL)return x.hide(),void 0;var d=s.val();(b||d!=t)&&(t=d,d=g(d),d.length>=c.minChars?(s.addClass(c.loadingClass),c.matchCase||(d=d.toLowerCase()),l(d,k,j)):(n(),x.hide()))}function f(b){return b?c.multiple?a.map(b.split(c.multipleSeparator),function(c){return a.trim(b).length?a.trim(c):null}):[a.trim(b)]:[""]}function g(d){if(!c.multiple)return d;var e=f(d);if(1==e.length)return e[0];var g=a(b).selection().start;return e=g==d.length?f(d):f(d.replace(d.substring(g),"")),e[e.length-1]}function h(d,e){c.autoFill&&g(s.val()).toLowerCase()==d.toLowerCase()&&p!=r.BACKSPACE&&(s.val(s.val()+e.substring(g(t).length)),a(b).selection(t.length,t.length+e.length))}function i(){clearTimeout(o),o=setTimeout(j,200)}function j(){x.visible(),x.hide(),clearTimeout(o),n(),c.mustMatch&&s.search(function(a){if(!a)if(c.multiple){var b=f(s.val()).slice(0,-1);s.val(b.join(c.multipleSeparator)+(b.length?c.multipleSeparator:""))}else s.val(""),s.trigger("result",null)})}function k(a,b){b&&b.length&&v?(n(),x.display(b,a),h(a,b[0].value),x.show()):j()}function l(d,e,f){c.matchCase||(d=d.toLowerCase());var h=u.load(d);if(h&&h.length)e(d,h);else if("string"==typeof c.url&&c.url.length>0){var i={timestamp:+new Date};a.each(c.extraParams,function(a,b){i[a]="function"==typeof b?b():b}),a.ajax({mode:"abort",port:"autocomplete"+b.name,dataType:c.dataType,url:c.url,data:a.extend({q:g(d),limit:c.max},i),success:function(a){var b=c.parse&&c.parse(a)||m(a);u.add(d,b),e(d,b)}})}else x.emptyList(),f(d)}function m(b){for(var d=[],e=b.split("\n"),f=0;f<e.length;f++){var g=a.trim(e[f]);g&&(g=g.split("|"),d[d.length]={data:g,value:g[0],result:c.formatResult&&c.formatResult(g,g[0])||g[0]})}return d}function n(){s.removeClass(c.loadingClass)}var o,p,q,r={UP:38,DOWN:40,DEL:46,TAB:9,RETURN:13,ESC:27,COMMA:188,PAGEUP:33,PAGEDOWN:34,BACKSPACE:8},s=a(b).attr("autocomplete","off").addClass(c.inputClass),t="",u=a.Autocompleter.Cache(c),v=0,w={mouseDownOnSelect:!1},x=a.Autocompleter.Select(c,b,d,w);a.browser.opera&&a(b.form).bind("submit.autocomplete",function(){return q?(q=!1,!1):void 0}),s.bind((a.browser.opera?"keypress":"keydown")+".autocomplete",function(b){switch(v=1,p=b.keyCode,b.keyCode){case r.UP:b.preventDefault(),x.visible()?x.prev():e(0,!0);break;case r.DOWN:b.preventDefault(),x.visible()?x.next():e(0,!0);break;case r.PAGEUP:b.preventDefault(),x.visible()?x.pageUp():e(0,!0);break;case r.PAGEDOWN:b.preventDefault(),x.visible()?x.pageDown():e(0,!0);break;case c.multiple&&","==a.trim(c.multipleSeparator)&&r.COMMA:case r.TAB:case r.RETURN:if(d())return b.preventDefault(),q=!0,!1;break;case r.ESC:x.hide();break;default:clearTimeout(o),o=setTimeout(e,c.delay)}}).focus(function(){v++}).blur(function(){v=0,w.mouseDownOnSelect||i()}).click(function(){v++>1&&!x.visible()&&e(0,!0)}).bind("search",function(){function b(a,b){var d;if(b&&b.length)for(var e=0;e<b.length;e++)if(b[e].result.toLowerCase()==a.toLowerCase()){d=b[e];break}"function"==typeof c?c(d):s.trigger("result",d&&[d.data,d.value])}var c=arguments.length>1?arguments[1]:null;a.each(f(s.val()),function(a,c){l(c,b,b)})}).bind("flushCache",function(){u.flush()}).bind("setOptions",function(){a.extend(c,arguments[1]),"data"in arguments[1]&&u.populate()}).bind("unautocomplete",function(){x.unbind(),s.unbind(),a(b.form).unbind(".autocomplete")})},a.Autocompleter.defaults={inputClass:"ac_input",resultsClass:"ac_results",loadingClass:"ac_loading",minChars:1,delay:400,matchCase:!1,matchSubset:!0,matchContains:!1,cacheLength:10,max:100,mustMatch:!1,extraParams:{},selectFirst:!0,formatItem:function(a){return a[0]},formatMatch:null,autoFill:!1,width:0,multiple:!1,multipleSeparator:", ",highlight:function(a,b){return a.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)("+b.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1")+")(?![^<>]*>)(?![^&;]+;)","gi"),"<strong>$1</strong>")},scroll:!0,scrollHeight:180},a.Autocompleter.Cache=function(b){function c(a,c){b.matchCase||(a=a.toLowerCase());var d=a.indexOf(c);return"word"==b.matchContains&&(d=a.toLowerCase().search("\\b"+c.toLowerCase())),-1==d?!1:0==d||b.matchContains}function d(a,c){h>b.cacheLength&&f(),g[a]||h++,g[a]=c}function e(){if(!b.data)return!1;var c={},e=0;b.url||(b.cacheLength=1),c[""]=[];for(var f=0,g=b.data.length;g>f;f++){var h=b.data[f];h="string"==typeof h?[h]:h;var i=b.formatMatch(h,f+1,b.data.length);if(i!==!1){var j=i.charAt(0).toLowerCase();c[j]||(c[j]=[]);var k={value:i,data:h,result:b.formatResult&&b.formatResult(h)||i};c[j].push(k),e++<b.max&&c[""].push(k)}}a.each(c,function(a,c){b.cacheLength++,d(a,c)})}function f(){g={},h=0}var g={},h=0;return setTimeout(e,25),{flush:f,add:d,populate:e,load:function(d){if(!b.cacheLength||!h)return null;if(!b.url&&b.matchContains){var e=[];for(var f in g)if(f.length>0){var i=g[f];a.each(i,function(a,b){c(b.value,d)&&e.push(b)})}return e}if(g[d])return g[d];if(b.matchSubset)for(var j=d.length-1;j>=b.minChars;j--){var i=g[d.substr(0,j)];if(i){var e=[];return a.each(i,function(a,b){c(b.value,d)&&(e[e.length]=b)}),e}}return null}}},a.Autocompleter.Select=function(b,c,d,e){function f(){s&&(n=a("<div/>").hide().addClass(b.resultsClass).css("position","absolute").appendTo(document.body),o=a("<ul/>").appendTo(n).mouseover(function(b){g(b).nodeName&&"LI"==g(b).nodeName.toUpperCase()&&(q=a("li",o).removeClass(p.ACTIVE).index(g(b)),a(g(b)).addClass(p.ACTIVE))}).click(function(b){return a(g(b)).addClass(p.ACTIVE),d(),c.focus(),!1}).mousedown(function(){e.mouseDownOnSelect=!0}).mouseup(function(){e.mouseDownOnSelect=!1}),b.width>0&&n.css("width",b.width),s=!1)}function g(a){for(var b=a.target;b&&"LI"!=b.tagName;)b=b.parentNode;return b?b:[]}function h(a){l.slice(q,q+1).removeClass(p.ACTIVE),i(a);var c=l.slice(q,q+1).addClass(p.ACTIVE);if(b.scroll){var d=0;l.slice(0,q).each(function(){d+=this.offsetHeight}),d+c[0].offsetHeight-o.scrollTop()>o[0].clientHeight?o.scrollTop(d+c[0].offsetHeight-o.innerHeight()):d<o.scrollTop()&&o.scrollTop(d)}}function i(a){q+=a,0>q?q=l.size()-1:q>=l.size()&&(q=0)}function j(a){return b.max&&b.max<a?b.max:a}function k(){o.empty();for(var c=j(m.length),d=0;c>d;d++)if(m[d]){var e=b.formatItem(m[d].data,d+1,c,m[d].value,r);if(e!==!1){var f=a("<li/>").html(b.highlight(e,r)).addClass(0==d%2?"ac_even":"ac_odd").appendTo(o)[0];a.data(f,"ac_data",m[d])}}l=o.find("li"),b.selectFirst&&(l.slice(0,1).addClass(p.ACTIVE),q=0),a.fn.bgiframe&&o.bgiframe()}var l,m,n,o,p={ACTIVE:"ac_over"},q=-1,r="",s=!0;return{display:function(a,b){f(),m=a,r=b,k()},next:function(){h(1)},prev:function(){h(-1)},pageUp:function(){0!=q&&0>q-8?h(-q):h(-8)},pageDown:function(){q!=l.size()-1&&q+8>l.size()?h(l.size()-1-q):h(8)},hide:function(){n&&n.hide(),l&&l.removeClass(p.ACTIVE),q=-1},visible:function(){return n&&n.is(":visible")},current:function(){return this.visible()&&(l.filter("."+p.ACTIVE)[0]||b.selectFirst&&l[0])},show:function(){var d=a(c).offset();if(n.css({width:"string"==typeof b.width||b.width>0?b.width:a(c).width(),top:d.top+c.offsetHeight,left:d.left}).show(),b.scroll&&(o.scrollTop(0),o.css({maxHeight:b.scrollHeight,overflow:"auto"}),a.browser.msie&&"undefined"==typeof document.body.style.maxHeight)){var e=0;l.each(function(){e+=this.offsetHeight});var f=e>b.scrollHeight;o.css("height",f?b.scrollHeight:e),f||l.width(o.width()-parseInt(l.css("padding-left"))-parseInt(l.css("padding-right")))}},selected:function(){var b=l&&l.filter("."+p.ACTIVE).removeClass(p.ACTIVE);return b&&b.length&&a.data(b[0],"ac_data")},emptyList:function(){o&&o.empty()},unbind:function(){n&&n.remove()}}},a.fn.selection=function(a,b){if(void 0!==a)return this.each(function(){if(this.createTextRange){var c=this.createTextRange();void 0===b||a==b?(c.move("character",a),c.select()):(c.collapse(!0),c.moveStart("character",a),c.moveEnd("character",b),c.select())}else this.setSelectionRange?this.setSelectionRange(a,b):this.selectionStart&&(this.selectionStart=a,this.selectionEnd=b)});var c=this[0];if(c.createTextRange){var d=document.selection.createRange(),e=c.value,f="<->",g=d.text.length;d.text=f;var h=c.value.indexOf(f);return c.value=e,this.selection(h,h+g),{start:h,end:h+g}}return void 0!==c.selectionStart?{start:c.selectionStart,end:c.selectionEnd}:void 0}}(b)});
